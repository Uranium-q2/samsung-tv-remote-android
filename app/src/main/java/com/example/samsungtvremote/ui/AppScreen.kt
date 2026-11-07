package com.example.samsungtvremote.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.samsungtvremote.R
import com.example.samsungtvremote.ui.theme.Background

private enum class Tab(val labelRes: Int, val icon: String) {
    Remote(R.string.tab_remote, "🎮"),
    Settings(R.string.tab_settings, "⚙"),
}

/** Root screen: bottom navigation with the Remote and Settings tabs. */
@Composable
fun AppScreen(state: TvUiState, actions: TvActions) {
    var tab by rememberSaveable { mutableStateOf(Tab.Remote) }

    Scaffold(
        containerColor = Background,
        bottomBar = {
            NavigationBar {
                Tab.entries.forEach { t ->
                    NavigationBarItem(
                        selected = tab == t,
                        onClick = { tab = t },
                        icon = { Text(t.icon) },
                        label = { Text(stringResource(t.labelRes)) },
                    )
                }
            }
        },
    ) { padding ->
        val content = Modifier.padding(padding)
        when (tab) {
            Tab.Remote -> RemoteScreen(state, actions, content)
            Tab.Settings -> SettingsScreen(state, actions, content)
        }
    }
}
