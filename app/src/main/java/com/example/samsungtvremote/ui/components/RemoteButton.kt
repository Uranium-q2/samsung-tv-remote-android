package com.example.samsungtvremote.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.samsungtvremote.ui.theme.KeyGrey
import com.example.samsungtvremote.ui.theme.KeyHeight

/** A single remote button. The caller sets its width through [modifier]. */
@Composable
fun RemoteButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = KeyGrey,
    height: Dp = KeyHeight,
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(height),
        colors = ButtonDefaults.buttonColors(containerColor = color, contentColor = Color.White),
        contentPadding = PaddingValues(0.dp),
    ) {
        Text(label, maxLines = 1)
    }
}
