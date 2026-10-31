# Native DualSense streaming fork — implementation notes

This is a personal development fork based on `Taveszfito/Artemis-Extended-Native-Wireless-DualSense-Features` at commit `46431c50600051f6f70960bddd38b65a28c66135`. The matching host base is `Taveszfito/Apollo-Extended` at `70ebba60a464e9769d1f800ac0ce669d62a0efdd`.

The Extended projects already supply native DualSense identification, Windows USB composite emulation through VIIPER, native controller PCM streaming and Bluetooth/USB output. Those implementations and their authors' licences/credits are retained. This fork adds explicit controller-emulation and bandwidth controls on top. It supersedes the earlier audio-derived-only prototype; the delivered native path does not generate effects from ordinary game sound.

## What changed

| Control | Behavior |
| --- | --- |
| Native DualSense emulation | On requests native DualSense for a detected DualSense. Off requests DualShock 4 compatibility for automatic/DS5 selection. An explicit Xbox choice is preserved. Restart the game/stream after changing controller type. |
| Native HD haptics traffic: Automatic | Allows the extra native controller PCM when the video bitrate target is at least 12 Mbps and the connection has been healthy for 20 seconds. |
| Native HD haptics traffic: Always on | Requests PCM regardless of the automatic budget/quality test. Master emulation-off and audio-route-off still win. |
| Native HD haptics traffic: Off | Stops the extra native PCM at the matching host, clears unsent data and silences the client's native audio path. |
| In-stream Quick Menu → Advanced | Cycles Automatic → Always on → Off without restarting or replacing the virtual controller. |

Controller speaker/headset audio and native waveform haptics share a four-channel 48 kHz PCM path (~3.072 Mbps of payload, plus packet overhead). The traffic switch pauses this path together. It does not remove the virtual controller or switch it to Xbox while a game is running. Buttons, sticks, touchpad, motion, conventional rumble, LEDs and adaptive-trigger messages retain their normal protocol paths. To disable native emulation altogether, use the master toggle before reconnecting.

Automatic pauses immediately on Apollo/Moonlight's poor-connection notification or RTT of at least 150 ms. It requires RTT below 120 ms to recover, then 20 stable seconds. When already enabled, RTT between 120 and 149 ms does not toggle it off. A video target below 12 Mbps pauses PCM. The video target is the stream's configured/negotiated bitrate, not an independent measurement of spare network capacity. The normal quality callback and measured RTT provide the live network signals. These are conservative initial thresholds for hardware tuning, not a guarantee that every connection above 12 Mbps can carry the added traffic.

Automatic uses the quality callback even when warning overlays are disabled. An unavailable RTT estimate does not by itself block recovery. The existing audio-route Off option also disables host PCM traffic in this matched pair.

## Host flow control

The Android fork adds JNI access to a versioned policy packet (`0x5506`) through the existing encrypted control stream. No extra listening port, pairing key, VPN or DSX relay is needed. The matching host acknowledges it before the client plays native PCM.

The client sends a six-second lease every two seconds. A disabled or expired lease prevents host PCM queuing/transmission and clears pending packets. Keeping the capture endpoint initialized avoids repeatedly tearing down the USB audio device. Unmodified Extended clients retain the host's original behavior if they never negotiate policy.

The acknowledgement is a marked metadata envelope in the existing adaptive-trigger callback and is intercepted before controller effects are processed. It is not sent to the physical controller as a trigger command. Invalid versions/sizes/reserved bytes/lease ranges are rejected, and the host requires input permission and encrypted control.

An older Apollo Extended binary does not implement this policy. It cannot be assumed to stop sending merely because the Android app is muted. The preview requires the matching host patch for native PCM playback and traffic control. Ordinary controller streaming remains available independently.

## Try the matched build

1. Build the patched Apollo Windows fork and review its pinned driver/runtime requirements. Keep the host's gamepad mode on Auto so it follows the client's controller request. A host-forced type overrides the client, as in upstream.
2. Install the ARM64 Android preview alongside your existing Artemis; its separate app ID is `com.r3valation.artemis.dualsense.noirdebug`.
3. Pair the DualSense to Android normally. Direct Android Bluetooth needs Android 12+ in the Extended implementation; USB and the optional HCI bridge are also present.
4. Enable native DualSense emulation, select Auto or DualSense for controller emulation, choose a controller-audio route other than Off, and temporarily choose Always on for the first validation.
5. Connect to the patched host. Verify a compatible native game sees a DualSense and produces native waveform/trigger effects. Check the upstream controller and host troubleshooting diagnostics.
6. Test Off and confirm that host PCM transmission stops while input remains responsive. Re-enable and confirm stale effects are not replayed.
7. Choose Automatic and test both a healthy connection and a constrained connection. Initial recovery/warmup takes 20 seconds.

The new APK is a debug preview. Fresh CI builds may use a different debug signing key; reinstalling a previous preview can require uninstalling it. Official/upstream updater shortcuts are disabled until this fork has its own release destination.

DSX remains a possible fallback if the integrated framework cannot run on the host. DSX's official 3.2 notes describe a virtual DualSense with audio, with DSX+ required for that virtual device. That setup has not been inspected on this user's PC. Steam Input/DSX remapping needs game-specific validation to avoid overlapping virtual devices; this work changes neither automatically.

## Verification and limits

- The Android ARM64 APK is compiled; final build/signature/test details accompany the source package.
- The standalone quality-policy tests check startup warmup, bitrate boundary, poor quality, RTT hysteresis, recovery, manual override, master/route Off, unknown RTT and clock reset.
- The host's standalone C++20 test checks packet parsing, malformed input, Off, renewal and lease expiry.
- Existing Bluetooth report-builder tests and a Robolectric startup test are run separately. The startup fixture now uses a Context-attached Robolectric Application.
- Recorder construction explicitly handles microphone permission revocation. Settings' deliberate finish-on-back behavior is documented for lint; it is not changed.
- Full lint is not clean in this upstream base: the report contains 782 errors on existing source lines, mostly invalid vector paths and Android API compatibility findings. No reported errors point to newly added source lines. The full report is retained; CI treats this existing lint debt as a separate diagnostic step, not a passed check.
- A full Windows binary, Windows driver behavior, real game recognition, native effects on a physical controller, bandwidth suppression and reconnect behavior have not been validated on hardware here. The Windows CI workflow is prepared but has not run.

## References reviewed

- MobinYengejehi/Artemis — initial requested base and ordinary streaming/controller behavior.
- awalol/DS5Dongle — Bluetooth report/state and haptic timing reference (MIT).
- TechAntohere/CloudPad-Android-Dualsense-Wireless — Android HID output architecture, also credited by Extended upstream.
- awalol/dualsense-bt-haptics — Windows four-channel endpoint capture and Bluetooth forwarding foundation (MIT).
- egormanga/SAxense — original public Bluetooth haptic PCM research; https://github.com/egormanga/SAxense and https://apps.sdore.me/SAxense . No SAxense source is copied into this patch.
- tonystyle13/Universal-Rumble-Haptics-Enabler-v1.0 — examined tree contains only README/changelog/licence, no reusable module implementation.
- Taveszfito's Artemis Extended and Apollo Extended — actual native streaming/backend base; their existing credit and licence notices are preserved.

No upstream PRs are created, and no remote PC drivers, DSX settings or Steam Input settings have been modified.
