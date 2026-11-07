package com.example.samsungtvremote.ui

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import com.example.samsungtvremote.ui.sections.AppShortcuts
import com.example.samsungtvremote.ui.sections.ColorMedia
import com.example.samsungtvremote.ui.sections.ConnectionBar
import com.example.samsungtvremote.ui.sections.DPad
import com.example.samsungtvremote.ui.sections.NumberPad
import com.example.samsungtvremote.ui.sections.TopKeys
import com.example.samsungtvremote.ui.sections.VolumeChannel
import com.example.samsungtvremote.ui.theme.Background

@Composable
fun RemoteScreen(
    initialIp: String,
    status: String,
    onConnect: (String) -> Unit,
    onKey: (String) -> Unit,
    onApp: (String) -> Unit,
) {
    val view = LocalView.current
    fun buzz() { view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP) }
    val key: (String) -> Unit = { buzz(); onKey(it) }
    val app: (String) -> Unit = { buzz(); onApp(it) }

    Column(
        Modifier
            .fillMaxWidth()
            .background(Background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(14.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        ConnectionBar(initialIp, status, onConnect)
        TopKeys(key)
        NumberPad(key)
        VolumeChannel(key)
        DPad(key)
        ColorMedia(key)
        AppShortcuts(app)
        Spacer(Modifier.height(24.dp))
    }
}
