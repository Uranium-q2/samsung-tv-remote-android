package com.example.samsungtvremote.ui.sections

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.samsungtvremote.data.TvApps
import com.example.samsungtvremote.ui.components.RemoteButton
import com.example.samsungtvremote.ui.theme.KeyGap

/** Shortcuts to apps on the TV. */
@Composable
fun AppShortcuts(onApp: (String) -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = KeyGap) {
        TvApps.forEach { app ->
            RemoteButton(app.name, { onApp(app.id) }, Modifier.weight(1f))
        }
    }
}
