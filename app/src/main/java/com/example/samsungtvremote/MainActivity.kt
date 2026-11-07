package com.example.samsungtvremote

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.samsungtvremote.data.TvClient
import com.example.samsungtvremote.data.TvDiscovery
import com.example.samsungtvremote.data.TvPrefs
import com.example.samsungtvremote.ui.AppScreen
import com.example.samsungtvremote.ui.TvActions
import com.example.samsungtvremote.ui.TvUiState
import com.example.samsungtvremote.ui.theme.TvRemoteTheme

class MainActivity : ComponentActivity() {
    private lateinit var prefs: TvPrefs
    private lateinit var tv: TvClient
    private lateinit var discovery: TvDiscovery
    private lateinit var ui: TvUiState

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = TvPrefs(this)
        discovery = TvDiscovery(this)
        ui = TvUiState(prefs.ip, prefs.name, getString(R.string.status_idle))
        tv = TvClient(
            onStatus = { runOnUiThread { ui.status = it } },
            onToken = { prefs.token = it },
            onConnection = { runOnUiThread { ui.connected = it } },
        )

        val actions = TvActions(
            onConnect = ::connect,
            onScan = ::scan,
            onForget = ::forget,
            onKey = { tv.sendKey(it) },
            onApp = { tv.runApp(it) },
        )
        setContent {
            TvRemoteTheme { AppScreen(ui, actions) }
        }

        // Reconnect to the last TV automatically.
        if (prefs.ip.isNotBlank()) tv.connect(prefs.ip, prefs.token)
    }

    private fun connect(ip: String, name: String) {
        if (ip != prefs.ip) prefs.token = null // a token only works for the TV that issued it
        prefs.ip = ip
        prefs.name = name
        ui.activeIp = ip
        ui.activeName = name
        tv.connect(ip, prefs.token)
    }

    private fun scan() {
        if (ui.scanning) return
        ui.scanning = true
        ui.devices.clear()
        discovery.scan { found ->
            runOnUiThread {
                ui.devices.addAll(found)
                ui.scanning = false
                if (found.isEmpty()) ui.status = getString(R.string.no_tvs_found)
            }
        }
    }

    private fun forget() {
        tv.disconnect()
        prefs.clear()
        ui.activeIp = ""
        ui.activeName = ""
        ui.status = getString(R.string.status_idle)
    }

    override fun onDestroy() {
        tv.disconnect()
        super.onDestroy()
    }
}
