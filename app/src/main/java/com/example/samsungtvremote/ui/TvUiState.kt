package com.example.samsungtvremote.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.samsungtvremote.data.TvDevice

/** Everything the UI shows about the TV connection. */
class TvUiState(initialIp: String, initialName: String, initialStatus: String) {
    var status by mutableStateOf(initialStatus)
    var connected by mutableStateOf(false)

    /** The TV we are connected (or connecting) to; empty when none is saved. */
    var activeIp by mutableStateOf(initialIp)
    var activeName by mutableStateOf(initialName)

    var scanning by mutableStateOf(false)
    val devices = mutableStateListOf<TvDevice>()
}

/** Callbacks from the UI to the activity. */
class TvActions(
    val onConnect: (ip: String, name: String) -> Unit,
    val onScan: () -> Unit,
    val onForget: () -> Unit,
    val onKey: (String) -> Unit,
    val onApp: (String) -> Unit,
)
