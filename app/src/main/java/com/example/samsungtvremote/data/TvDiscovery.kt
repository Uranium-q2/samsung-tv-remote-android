package com.example.samsungtvremote.data

import android.content.Context
import android.net.wifi.WifiManager
import org.json.JSONObject
import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.HttpURLConnection
import java.net.InetAddress
import java.net.SocketTimeoutException
import java.net.URL
import kotlin.concurrent.thread

/** A Samsung TV found on the local network. */
data class TvDevice(val ip: String, val name: String, val model: String)

/**
 * Finds Samsung Smart TVs on the local network.
 *
 * Sends an SSDP (UPnP) M-SEARCH multicast, collects the Samsung replies and then asks each
 * candidate for its name via the TV's REST endpoint (http://IP:8001/api/v2/). Devices that do
 * not answer that endpoint (phones, soundbars, ...) are ignored.
 */
class TvDiscovery(context: Context) {
    private val appContext = context.applicationContext

    /** Runs the scan on a background thread; [onDone] is called from that thread. */
    fun scan(timeoutMs: Int = 4000, onDone: (List<TvDevice>) -> Unit) {
        thread(name = "tv-discovery") {
            val result = runCatching { discover(timeoutMs) }.getOrDefault(emptyList())
            onDone(result)
        }
    }

    private fun discover(timeoutMs: Int): List<TvDevice> {
        val wifi = appContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val lock = wifi.createMulticastLock("tv-discovery").apply {
            setReferenceCounted(false)
            acquire()
        }
        val ips = linkedSetOf<String>()
        try {
            DatagramSocket().use { socket ->
                socket.soTimeout = 400
                val group = InetAddress.getByName("239.255.255.250")
                val targets = listOf("urn:samsung.com:device:RemoteControlReceiver:1", "ssdp:all")
                repeat(2) {
                    for (st in targets) {
                        val msg = ("M-SEARCH * HTTP/1.1\r\n" +
                            "HOST: 239.255.255.250:1900\r\n" +
                            "MAN: \"ssdp:discover\"\r\n" +
                            "MX: 2\r\n" +
                            "ST: $st\r\n\r\n").toByteArray()
                        socket.send(DatagramPacket(msg, msg.size, group, 1900))
                    }
                }
                val end = System.currentTimeMillis() + timeoutMs
                val buf = ByteArray(2048)
                while (System.currentTimeMillis() < end) {
                    try {
                        val p = DatagramPacket(buf, buf.size)
                        socket.receive(p)
                        val text = String(p.data, 0, p.length)
                        if (text.contains("samsung", ignoreCase = true)) {
                            p.address.hostAddress?.let(ips::add)
                        }
                    } catch (_: SocketTimeoutException) {
                        // keep listening until the deadline
                    }
                }
            }
        } finally {
            lock.release()
        }
        return ips.mapNotNull(::describe)
    }

    /** Returns the TV's description, or null if the host is not a Samsung Smart TV. */
    private fun describe(ip: String): TvDevice? = runCatching {
        val conn = URL("http://$ip:8001/api/v2/").openConnection() as HttpURLConnection
        conn.connectTimeout = 1500
        conn.readTimeout = 1500
        val body = conn.inputStream.bufferedReader().use { it.readText() }
        val device = JSONObject(body).optJSONObject("device")
        TvDevice(
            ip = ip,
            name = device?.optString("name")?.takeIf { it.isNotBlank() } ?: "Samsung TV",
            model = device?.optString("modelName").orEmpty(),
        )
    }.getOrNull()
}
