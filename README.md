# Samsung TV Remote (Android)

An Android remote control for Samsung Smart TVs (Tizen, e.g. the 7 series) that works over WiFi.
Built with Kotlin and Jetpack Compose.

## Usage
1. Put the phone and the TV on the same WiFi network.
2. Open the **Settings** tab. The app searches the network for Samsung TVs (SSDP) and lists them.
3. Tap your TV and accept the request on the TV screen. The token is stored on the phone, so you only accept once, and the app reconnects automatically next time.
4. If the TV is not found, enter its IP manually (TV: Settings > General > Network > Network Status).

## Project structure
```
app/src/main/java/com/example/samsungtvremote/
  MainActivity.kt          entry point
  data/                    TvClient (WebSocket), TvDiscovery (SSDP scan), TvPrefs, RemoteKeys
  ui/AppScreen.kt          bottom navigation (Remote / Settings)
  ui/RemoteScreen.kt       remote tab, assembled from sections
  ui/SettingsScreen.kt     TV search, manual IP, forget TV
  ui/sections/             ManualConnect, DeviceList, TopKeys, NumberPad, VolumeChannel, DPad, ColorMedia, AppShortcuts
  ui/components/           RemoteButton
  ui/theme/                Color, Dimens, Theme
```

## Build
Open the folder in Android Studio and run, or `./gradlew assembleDebug`.

## Limitations
Samsung TVs do not expose a screen stream over the network, so this app cannot show the TV picture.
It only sends remote key presses and launches apps.
