# Samsung TV Remote (Android)

An Android remote control for Samsung Smart TVs (Tizen, e.g. the 7 series) that works over WiFi.
Built with Kotlin and Jetpack Compose.

## Usage
1. Put the phone and the TV on the same WiFi network.
2. Enter the TV's IP address (TV: Settings > General > Network > Network Status).
3. Tap **Connect** and accept the request on the TV screen. The token is stored on the phone, so you only accept once.

## Project structure
```
app/src/main/java/com/example/samsungtvremote/
  MainActivity.kt          entry point
  data/                    TvClient (WebSocket), TvPrefs, RemoteKeys
  ui/RemoteScreen.kt       assembles the screen from sections
  ui/sections/             ConnectionBar, TopKeys, NumberPad, VolumeChannel, DPad, ColorMedia, AppShortcuts
  ui/components/           RemoteButton
  ui/theme/                Color, Dimens, Theme
```

## Build
Open the folder in Android Studio and run, or `./gradlew assembleDebug`.

## Limitations
Samsung TVs do not expose a screen stream over the network, so this app cannot show the TV picture.
It only sends remote key presses and launches apps.
