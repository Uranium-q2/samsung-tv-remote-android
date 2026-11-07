package com.example.samsungtvremote.ui.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.samsungtvremote.data.RemoteKeys
import com.example.samsungtvremote.ui.components.RemoteButton
import com.example.samsungtvremote.ui.theme.KeyGap
import com.example.samsungtvremote.ui.theme.TallKeyHeight

/** Volume, mute, menu and channels. */
@Composable
fun VolumeChannel(onKey: (String) -> Unit) {
    @Composable
    fun key(label: String, code: String, modifier: Modifier) =
        RemoteButton(label, { onKey(code) }, modifier.fillMaxWidth(), height = TallKeyHeight)

    Row(Modifier.fillMaxWidth(), horizontalArrangement = KeyGap) {
        Column(Modifier.weight(1f), verticalArrangement = KeyGap) {
            key("VOL +", RemoteKeys.VOL_UP, Modifier)
            key("VOL -", RemoteKeys.VOL_DOWN, Modifier)
        }
        Column(Modifier.weight(1f), verticalArrangement = KeyGap) {
            key("MUTE", RemoteKeys.MUTE, Modifier)
            key("MENU", RemoteKeys.MENU, Modifier)
        }
        Column(Modifier.weight(1f), verticalArrangement = KeyGap) {
            key("CH +", RemoteKeys.CH_UP, Modifier)
            key("CH -", RemoteKeys.CH_DOWN, Modifier)
        }
    }
}
