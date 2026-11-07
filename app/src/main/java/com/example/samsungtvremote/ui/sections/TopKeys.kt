package com.example.samsungtvremote.ui.sections

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.samsungtvremote.data.RemoteKeys
import com.example.samsungtvremote.ui.components.RemoteButton
import com.example.samsungtvremote.ui.theme.KeyGap
import com.example.samsungtvremote.ui.theme.PowerRed

/** Power / Source / Info / Guide. */
@Composable
fun TopKeys(onKey: (String) -> Unit) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = KeyGap) {
        RemoteButton("POWER", { onKey(RemoteKeys.POWER) }, Modifier.weight(1f), PowerRed)
        RemoteButton("SOURCE", { onKey(RemoteKeys.SOURCE) }, Modifier.weight(1f))
        RemoteButton("INFO", { onKey(RemoteKeys.INFO) }, Modifier.weight(1f))
        RemoteButton("GUIDE", { onKey(RemoteKeys.GUIDE) }, Modifier.weight(1f))
    }
}
