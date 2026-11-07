package com.example.samsungtvremote.ui.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.samsungtvremote.data.RemoteKeys
import com.example.samsungtvremote.ui.components.RemoteButton
import com.example.samsungtvremote.ui.theme.Accent
import com.example.samsungtvremote.ui.theme.DPadKeyHeight
import com.example.samsungtvremote.ui.theme.KeyGap
import com.example.samsungtvremote.ui.theme.KeyGrey

/** Arrow keys + OK, plus Back / Home / Exit. */
@Composable
fun DPad(onKey: (String) -> Unit) {
    Column(verticalArrangement = KeyGap) {
        Row(Modifier.fillMaxWidth(), horizontalArrangement = KeyGap) {
            Spacer(Modifier.weight(1f))
            RemoteButton("▲", { onKey(RemoteKeys.UP) }, Modifier.weight(1f), height = DPadKeyHeight)
            Spacer(Modifier.weight(1f))
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = KeyGap) {
            RemoteButton("◄", { onKey(RemoteKeys.LEFT) }, Modifier.weight(1f), height = DPadKeyHeight)
            RemoteButton("OK", { onKey(RemoteKeys.ENTER) }, Modifier.weight(1f), Accent, DPadKeyHeight)
            RemoteButton("►", { onKey(RemoteKeys.RIGHT) }, Modifier.weight(1f), height = DPadKeyHeight)
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = KeyGap) {
            Spacer(Modifier.weight(1f))
            RemoteButton("▼", { onKey(RemoteKeys.DOWN) }, Modifier.weight(1f), height = DPadKeyHeight)
            Spacer(Modifier.weight(1f))
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = KeyGap) {
            RemoteButton("BACK", { onKey(RemoteKeys.RETURN) }, Modifier.weight(1f), KeyGrey)
            RemoteButton("HOME", { onKey(RemoteKeys.HOME) }, Modifier.weight(1f), KeyGrey)
            RemoteButton("EXIT", { onKey(RemoteKeys.EXIT) }, Modifier.weight(1f), KeyGrey)
        }
    }
}
