package com.example.samsungtvremote.ui.sections

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.example.samsungtvremote.R
import com.example.samsungtvremote.ui.theme.KeyGap
import com.example.samsungtvremote.ui.theme.TextMuted

/** IP field, Connect button and connection status. */
@Composable
fun ConnectionBar(initialIp: String, status: String, onConnect: (String) -> Unit) {
    var ip by remember { mutableStateOf(initialIp) }
    Row(horizontalArrangement = KeyGap, verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = ip,
            onValueChange = { ip = it },
            label = { Text(stringResource(R.string.ip_label)) },
            singleLine = true,
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
        )
        Button(onClick = { if (ip.isNotBlank()) onConnect(ip.trim()) }) {
            Text(stringResource(R.string.connect))
        }
    }
    Text(status, color = TextMuted)
}
