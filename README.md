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
Requirements: Android Studio (or JDK 17+) and the Android SDK 37.

**Android Studio:** open the project folder, wait for the Gradle sync, connect a phone with USB debugging
(or start an emulator) and press **Run**.

**Command line:**
```
# Windows (PowerShell)
$env:JAVA_HOME="C:\Program Files\Android\Android Studio\jbr"   # if your default java is older than 17
.\gradlew.bat assembleDebug      # APK: app\build\outputs\apk\debug\app-debug.apk
.\gradlew.bat installDebug       # build and install on a connected phone

# macOS / Linux
./gradlew assembleDebug
./gradlew installDebug
```
Create a `local.properties` file with `sdk.dir=<path to your Android SDK>` if Gradle cannot find the SDK
(Android Studio creates it automatically).

Run the unit tests with `.\gradlew.bat testDebugUnitTest`.

## Install the APK without a computer
Copy `app-debug.apk` to the phone, open it and allow installing from unknown sources.

## Limitations
Samsung TVs do not expose a screen stream over the network, so this app cannot show the TV picture.
It only sends remote key presses and launches apps.
