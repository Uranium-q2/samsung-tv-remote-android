package com.example.samsungtvremote.ui.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.samsungtvremote.data.RemoteKeys
import com.example.samsungtvremote.ui.components.RemoteButton
import com.example.samsungtvremote.ui.theme.KeyBlue
import com.example.samsungtvremote.ui.theme.KeyGap
import com.example.samsungtvremote.ui.theme.KeyGreen
import com.example.samsungtvremote.ui.theme.KeyRed
import com.example.samsungtvremote.ui.theme.KeyYellow

/** Colour keys A-D and playback controls. */
@Composable
fun ColorMedia(onKey: (String) -> Unit) {
    Column(verticalArrangement = KeyGap) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = KeyGap) {
            RemoteButton("A", { onKey(RemoteKeys.RED) }, Modifier.weight(1f), KeyRed)
            RemoteButton("B", { onKey(RemoteKeys.GREEN) }, Modifier.weight(1f), KeyGreen)
            RemoteButton("C", { onKey(RemoteKeys.YELLOW) }, Modifier.weight(1f), KeyYellow)
            RemoteButton("D", { onKey(RemoteKeys.CYAN) }, Modifier.weight(1f), KeyBlue)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = KeyGap) {
            RemoteButton("⏪", { onKey(RemoteKeys.REWIND) }, Modifier.weight(1f))
            RemoteButton("⏯", { onKey(RemoteKeys.PLAY) }, Modifier.weight(1f))
            RemoteButton("⏸", { onKey(RemoteKeys.PAUSE) }, Modifier.weight(1f))
            RemoteButton("⏹", { onKey(RemoteKeys.STOP) }, Modifier.weight(1f))
            RemoteButton("⏩", { onKey(RemoteKeys.FF) }, Modifier.weight(1f))
        }
    }
}
