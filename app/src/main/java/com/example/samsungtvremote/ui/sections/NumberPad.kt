package com.example.samsungtvremote.ui.sections

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.samsungtvremote.data.RemoteKeys
import com.example.samsungtvremote.ui.components.RemoteButton
import com.example.samsungtvremote.ui.theme.KeyGap

/** Number pad 0-9 with TTX and PRE-CH. */
@Composable
fun NumberPad(onKey: (String) -> Unit) {
    Column(verticalArrangement = KeyGap) {
        for (start in intArrayOf(1, 4, 7)) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = KeyGap) {
                for (d in start..start + 2) {
                    RemoteButton("$d", { onKey(RemoteKeys.digit(d)) }, Modifier.weight(1f))
                }
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = KeyGap) {
            RemoteButton("TTX", { onKey(RemoteKeys.TTX) }, Modifier.weight(1f))
            RemoteButton("0", { onKey(RemoteKeys.digit(0)) }, Modifier.weight(1f))
            RemoteButton("PRE-CH", { onKey(RemoteKeys.PRE_CH) }, Modifier.weight(1f))
        }
    }
}
