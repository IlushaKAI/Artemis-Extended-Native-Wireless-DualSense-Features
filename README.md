# Artemis Android — Personal Development & Testing Fork

> **This branch, `prospero-ps5`, is tuned for streaming from a PS5 with [ProsperoSunshine](https://github.com/IlushaKAI/ProsperoSunshine/tree/firmware-9.60).**
> It is based on R3VALATiON's `feature/native-dualsense-adaptive` and is unofficial too, not an official Moonlight, Artemis or Sony product.
>
> - Sets 1080p60, HEVC, 30 Mbit/s, no HDR and balanced frame pacing once, at the first start. All of it can be changed afterwards.
> - Turns the DualSense's trigger and rumble power down by 3 of 8 steps. The console does this by its own setting; the client sent full power.
>
> Tested by one person, on a Galaxy S23 with Android 17 and a DualSense over Bluetooth. Rumble there still needs **Emulate rumble support with
> vibration** turned on, for a reason not understood yet.
>
> Build: `gradlew -Pandroid.injected.build.abi=arm64-v8a -Pandroid.injected.testOnly=false assembleNonRoot_gameDebug`. Without the second
> option Android refuses to install the APK. The result is in `app/build/intermediates/apk/`.

> [!WARNING]
> **UNOFFICIAL PERSONAL DEVELOPMENT / TESTING BUILD — R3VALATiON**
>
> This is an independent experimental fork. Its builds are **not official releases of the original project or the Extended upstream project**, and no upstream endorsement is implied. Features may be incomplete or unverified. Please report issues introduced by this fork here, not as regressions in the main project.

See [personal build descriptions and validation status](BUILD_NOTES.md) and the [notice included with build artifacts](BUILD_NOTICE.md).

The personal implementation is maintained on [feature/native-dualsense-adaptive](https://github.com/R3VALATiON/Artemis-Extended-Native-Wireless-DualSense-Features/tree/feature/native-dualsense-adaptive). The upstream documentation below describes the upstream work; it does not certify the validation status of this personal fork.

---

## Upstream reference documentation

# Artemis Android Extended

This fork is based on [Artemis Android](https://github.com/ClassicOldSong/moonlight-android) and adds native DualSense streaming, advanced controller features, input customization, microphone forwarding, and quality-of-life improvements.

---

## 🎮 Native DualSense Streaming

Artemis Android Extended supports native end-to-end DualSense functionality through [Apollo Extended](https://github.com/Taveszfito/Apollo-Extended).

On **Android 12 and newer**, a DualSense can connect directly to the Android device over Bluetooth. No external Bluetooth adapter is required anymore.

DualSense functionality is available through both wired and wireless connections:

- Adaptive triggers
- Native HD haptics
- Conventional rumble
- Lightbar control
- Player LED control
- Multi-touch touchpad input
- Gyroscope and motion input
- Built-in controller-speaker audio
- Automatic headset-jack detection
- Stream-audio routing to a headset connected to the controller
- Automatic return to the controller speaker after unplugging the headset
- DualSense mute button support as a global client microphone mute switch
- In-stream microphone mute and unmute status feedback

The controller maintains its lighting, trigger, audio, haptic, rumble, touchpad, and motion state simultaneously while streaming.

### Connection support

- **Android 12+ Bluetooth:** Connect the DualSense directly to the Android device without an external adapter.
- **Wired USB:** Connect the controller directly through USB.
- The optional USB Bluetooth HCI bridge remains part of Artemis for compatible external Bluetooth adapters and setups that require it.

### Current limitation

The DualSense built-in microphone is not currently available when the controller is connected directly through Android Bluetooth.

DualSense microphone forwarding currently requires either:

- A wired USB connection, or
- The optional USB Bluetooth HCI bridge

The DualSense mute button and in-stream microphone status feedback work in every connection mode. This limitation applies only to forwarding audio from the controller’s built-in microphone. Other native DualSense features remain available through the direct Android Bluetooth connection.

## 🎙️ Microphone Forwarding

Forward microphone audio from the Android client to the host as a Steam Streaming Microphone device.

- Choose the Android device microphone or an available DualSense microphone as the source
- Receive in-stream feedback when client microphone forwarding is enabled or disabled
- Automatically use a headset microphone when supported by the active DualSense connection

Microphone forwarding requires **Apollo Extended** on the host.

The Android device microphone can be used with a directly connected wireless DualSense. The DualSense built-in microphone requires a wired USB connection or the optional USB Bluetooth HCI bridge.

## 🎯 Gyro Aim

Use controller gyro for aiming by blending it into **right-stick input**, or convert it directly into **mouse input** in KBM Mode.

Gyro behavior, sensitivity, activation, and controls are fully customizable.

## ⌨️ Controller KBM Mode

Convert controller input directly into fully customizable **keyboard and mouse controls**, including:

- Controller buttons
- Analog sticks
- Triggers
- Paddles
- Touchpad
- Gyroscope

Custom mappings can be saved, imported, and exported as presets.

## 🕹️ Quick Menu

A fullscreen Quick Menu designed for complete controller navigation.

The menu layout and activation shortcut are customizable, and all controls can be used without touching the screen.

## 🔎 Settings Search

Quickly find and access settings without browsing through individual categories.

## 🔊 Android or Windows Volume Control

Choose whether the device volume buttons control **Android volume** or **Windows volume** while streaming.

Windows volume control requires a corresponding [PowerToys Keyboard Manager](https://learn.microsoft.com/windows/powertoys/keyboard-manager) mapping.

---

## 🖥️ Apollo Extended Host

[Apollo Extended](https://github.com/Taveszfito/Apollo-Extended) provides **Xbox 360, DualShock 4, and native DualSense host emulation** for Artemis Android Extended.

Together, Artemis Android Extended and Apollo Extended provide native end-to-end DualSense streaming while preserving:

- Adaptive triggers
- Native HD haptics
- Controller-speaker audio
- Lightbar and player LEDs
- Touchpad input
- Gyroscope and motion input
- Conventional rumble
- Controller-specific microphone controls

Download the host installer from the [Apollo Extended releases](https://github.com/Taveszfito/Apollo-Extended/releases) page.

Standard Sunshine and Apollo hosts remain compatible, but native DualSense emulation and Extended controller selection require Apollo Extended.

---

### Credits

Based on [Artemis Android](https://github.com/ClassicOldSong/moonlight-android).

Thanks to **Idkiamaguy645** and the [CloudPad-Android-Dualsense-Wireless](https://github.com/TechAntohere/CloudPad-Android-Dualsense-Wireless) project for demonstrating the Android wireless HID approach used to enable full-featured DualSense operation without an external Bluetooth adapter.

The Windows client is also available: [Moonlight Extended for Windows](https://github.com/Taveszfito/moonlight-extended).
