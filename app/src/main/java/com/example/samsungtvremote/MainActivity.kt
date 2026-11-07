package com.example.samsungtvremote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.samsungtvremote.data.TvClient
import com.example.samsungtvremote.data.TvPrefs
import com.example.samsungtvremote.ui.RemoteScreen
import com.example.samsungtvremote.ui.theme.TvRemoteTheme

class MainActivity : ComponentActivity() {
    private lateinit var tv: TvClient
    private var status by mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val prefs = TvPrefs(this)
        status = getString(R.string.status_idle)
        tv = TvClient(
            onStatus = { runOnUiThread { status = it } },
            onToken = { prefs.token = it },
        )
        setContent {
            TvRemoteTheme {
                RemoteScreen(
                    initialIp = prefs.ip,
                    status = status,
                    onConnect = { ip ->
                        prefs.ip = ip
                        tv.connect(ip, prefs.token)
                    },
                    onKey = { tv.sendKey(it) },
                    onApp = { tv.runApp(it) },
                )
            }
        }
    }

    override fun onDestroy() {
        tv.disconnect()
        super.onDestroy()
    }
}
