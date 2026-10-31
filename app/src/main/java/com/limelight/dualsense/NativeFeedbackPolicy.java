// SPDX-License-Identifier: GPL-3.0-or-later
package com.limelight.dualsense;

/** Controls the extra native PCM stream without changing the virtual controller's identity. */
public final class NativeFeedbackPolicy {
    public static final String MODE_PREF = "native_feedback_traffic";
    public static final String NATIVE_CONTROLLER_PREF = "native_dualsense_emulation";
    public static final int MIN_VIDEO_KBPS = 12000;
    public static final long RECOVERY_MS = 20000;
    private long healthySince = -1;
    private boolean automaticEnabled;
    private String reason = "Waiting for connection";
    public boolean evaluate(String mode, boolean nativeEnabled, boolean audioRouteEnabled,
                            int videoKbps, boolean poor, int rttMs, long nowMs) {
        if (!nativeEnabled || !audioRouteEnabled || "off".equals(mode)) {
            healthySince = -1; automaticEnabled = false; reason = "Off"; return false;
        }
        if ("always".equals(mode)) { healthySince = -1; automaticEnabled = false; reason = "Always on"; return true; }
        // Unknown values fail to the conservative automatic policy.
        if (videoKbps < MIN_VIDEO_KBPS || poor || rttMs >= 150) {
            healthySince = -1; automaticEnabled = false;
            reason = videoKbps < MIN_VIDEO_KBPS ? "Paused: stream bitrate below 12 Mbps" : "Paused: connection quality";
            return false;
        }
        // Hysteresis: after a high-RTT event require RTT below 120 ms to recover.
        if (rttMs >= 120 && !automaticEnabled) { healthySince = -1; reason = "Paused: waiting for lower latency"; return false; }
        if (healthySince < 0 || nowMs < healthySince) healthySince = nowMs;
        if (nowMs - healthySince < RECOVERY_MS) { reason = "Waiting for 20 seconds of stable connection"; return false; }
        automaticEnabled = true; reason = "Automatic: native PCM enabled"; return true;
    }
    public String reason() { return reason; }
}
