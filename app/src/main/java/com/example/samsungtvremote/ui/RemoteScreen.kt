package com.example.samsungtvremote.ui

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.samsungtvremote.R
import com.example.samsungtvremote.ui.sections.AppShortcuts
import com.example.samsungtvremote.ui.sections.ColorMedia
import com.example.samsungtvremote.ui.sections.DPad
import com.example.samsungtvremote.ui.sections.NumberPad
import com.example.samsungtvremote.ui.sections.TopKeys
import com.example.samsungtvremote.ui.sections.VolumeChannel
import com.example.samsungtvremote.ui.theme.Accent
import com.example.samsungtvremote.ui.theme.TextMuted

/** The remote control tab. Connection is handled in the settings tab. */
@Composable
fun RemoteScreen(state: TvUiState, actions: TvActions, modifier: Modifier = Modifier) {
    val view = LocalView.current
    fun buzz() { view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP) }
    val key: (String) -> Unit = { buzz(); actions.onKey(it) }
    val app: (String) -> Unit = { buzz(); actions.onApp(it) }

    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (state.connected) {
            Text(stringResource(R.string.connected_to, state.activeName.ifBlank { state.activeIp }), color = Accent)
        } else {
            Text(stringResource(R.string.not_connected_hint), color = TextMuted)
        }
        TopKeys(key)
        NumberPad(key)
        VolumeChannel(key)
        DPad(key)
        ColorMedia(key)
        AppShortcuts(app)
        Spacer(Modifier.height(24.dp))
    }
}
