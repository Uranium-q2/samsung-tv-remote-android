package com.example.samsungtvremote.data

import android.util.Base64
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import org.json.JSONObject
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.X509TrustManager

/** WebSocket client for Samsung Smart TVs (Tizen), port 8002 (wss). */
class TvClient(
    private val onStatus: (String) -> Unit,
    private val onToken: (String) -> Unit,
) {
    // The TV uses a self-signed certificate, so we accept it (home network only).
    private val trustAll = object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun getAcceptedIssuers(): Array<X509Certificate> = emptyArray()
    }
    private val client: OkHttpClient = run {
        val ctx = SSLContext.getInstance("TLS").apply { init(null, arrayOf(trustAll), SecureRandom()) }
        OkHttpClient.Builder()
            .sslSocketFactory(ctx.socketFactory, trustAll)
            .hostnameVerifier { _, _ -> true }
            .pingInterval(20, TimeUnit.SECONDS)
            .connectTimeout(8, TimeUnit.SECONDS)
            .build()
    }
    private var socket: WebSocket? = null

    @Volatile
    var connected = false
        private set

    fun connect(ip: String, token: String?) {
        disconnect()
        val name = Base64.encodeToString("Android Remote".toByteArray(), Base64.NO_WRAP)
        val tokenPart = if (token.isNullOrBlank()) "" else "&token=$token"
        val url = "wss://$ip:8002/api/v2/channels/samsung.remote.control?name=$name$tokenPart"
        onStatus("Connecting to $ip... accept the request on the TV")
        socket = client.newWebSocket(Request.Builder().url(url).build(), object : WebSocketListener() {
            override fun onMessage(webSocket: WebSocket, text: String) {
                val json = runCatching { JSONObject(text) }.getOrNull() ?: return
                when (json.optString("event")) {
                    "ms.channel.connect" -> {
                        connected = true
                        json.optJSONObject("data")?.optString("token")
                            ?.takeIf { it.isNotBlank() }?.let(onToken)
                        onStatus("Connected to $ip")
                    }
                    "ms.channel.timeOut" -> onStatus("The TV did not accept the connection (timeout)")
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                connected = false
                onStatus("Error: ${t.message ?: "connection failed"}")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                connected = false
                onStatus("Disconnected")
            }
        })
    }

    private fun ready(): WebSocket? {
        val s = socket
        if (s == null || !connected) {
            onStatus("Connect to the TV first")
            return null
        }
        return s
    }

    fun sendKey(key: String): Boolean {
        val s = ready() ?: return false
        val msg = JSONObject().apply {
            put("method", "ms.remote.control")
            put("params", JSONObject().apply {
                put("Cmd", "Click")
                put("DataOfCmd", key)
                put("Option", "false")
                put("TypeOfRemote", "SendRemoteKey")
            })
        }
        return s.send(msg.toString())
    }

    fun runApp(appId: String): Boolean {
        val s = ready() ?: return false
        val msg = JSONObject().apply {
            put("method", "ms.channel.emit")
            put("params", JSONObject().apply {
                put("event", "ed.apps.launch")
                put("to", "host")
                put("data", JSONObject().apply {
                    put("action_type", "DEEP_LINK")
                    put("appId", appId)
                })
            })
        }
        return s.send(msg.toString())
    }

    fun disconnect() {
        socket?.close(1000, null)
        socket = null
        connected = false
    }
}
