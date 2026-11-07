package com.example.samsungtvremote.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.samsungtvremote.R
import com.example.samsungtvremote.ui.sections.DeviceList
import com.example.samsungtvremote.ui.sections.ManualConnect
import com.example.samsungtvremote.ui.theme.Accent
import com.example.samsungtvremote.ui.theme.TextMuted

/** Settings tab: find TVs on the network, connect, forget the saved TV. */
@Composable
fun SettingsScreen(state: TvUiState, actions: TvActions, modifier: Modifier = Modifier) {
    // Look for TVs as soon as the tab is opened.
    LaunchedEffect(Unit) { if (state.devices.isEmpty()) actions.onScan() }

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        Text(stringResource(R.string.tab_settings), style = MaterialTheme.typography.headlineSmall)

        // Connection status
        Text(
            if (state.connected) stringResource(R.string.connected_to, state.activeName.ifBlank { state.activeIp })
            else state.status,
            color = if (state.connected) Accent else TextMuted,
        )

        // Nearby TVs
        Text(stringResource(R.string.nearby_tvs), style = MaterialTheme.typography.titleMedium)
        DeviceList(state, onConnect = actions.onConnect)
        Button(onClick = actions.onScan, enabled = !state.scanning, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(if (state.scanning) R.string.scanning else R.string.scan_again))
        }

        // Manual fallback
        Text(stringResource(R.string.manual_title), style = MaterialTheme.typography.titleMedium)
        ManualConnect(initialIp = state.activeIp, onConnect = { ip -> actions.onConnect(ip, "") })

        if (state.activeIp.isNotBlank()) {
            OutlinedButton(onClick = actions.onForget, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.forget_tv))
            }
        }
    }
}
