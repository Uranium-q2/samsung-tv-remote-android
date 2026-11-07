package com.example.samsungtvremote.ui.sections

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.samsungtvremote.R
import com.example.samsungtvremote.data.TvDevice
import com.example.samsungtvremote.ui.TvUiState
import com.example.samsungtvremote.ui.theme.Accent
import com.example.samsungtvremote.ui.theme.KeyGrey
import com.example.samsungtvremote.ui.theme.TextMuted

/** TVs found by the network scan. Tap one to connect. */
@Composable
fun DeviceList(state: TvUiState, onConnect: (ip: String, name: String) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        when {
            state.scanning -> Row(verticalAlignment = Alignment.CenterVertically) {
                CircularProgressIndicator(Modifier.padding(end = 12.dp), strokeWidth = 2.dp)
                Text(stringResource(R.string.scanning), color = TextMuted)
            }
            state.devices.isEmpty() -> Text(stringResource(R.string.no_tvs_found), color = TextMuted)
        }
        state.devices.forEach { device -> DeviceRow(device, state, onConnect) }
    }
}

@Composable
private fun DeviceRow(device: TvDevice, state: TvUiState, onConnect: (String, String) -> Unit) {
    val active = state.connected && state.activeIp == device.ip
    Surface(
        color = KeyGrey,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth().clickable { onConnect(device.ip, device.name) },
    ) {
        Column(Modifier.padding(14.dp)) {
            Text(device.name, style = MaterialTheme.typography.titleMedium)
            Text(
                listOf(device.model, device.ip).filter { it.isNotBlank() }.joinToString(" · "),
                color = TextMuted,
            )
            if (active) Text(stringResource(R.string.connected), color = Accent)
        }
    }
}
