# Artemis Android — personal development/testing builds

**Unofficial personal development/testing preview maintained by R3VALATiON.**

This fork and its builds are independent experimental work. They are not official releases of the original Artemis Android, its Extended fork, Moonlight or Sunshine. No affiliation with, approval by, or endorsement from the upstream maintainers is implied.

Features may be incomplete, unstable or unverified. Build/test status applies only to the exact revision identified with each artifact. Please report fork-specific issues to this personal fork; do not attribute its changes or regressions to the original projects.

Upstream authors retain credit and their original licence terms. These builds are for personal development and testing, not a production-ready release.

## Build labels

Every workflow run, downloaded artifact and build notice is labelled **Personal Development / Testing Preview — UNOFFICIAL**. Future release descriptions must repeat this disclaimer and be marked as pre-releases.

Initial prerelease: [v0.2.0-personal-preview.1](https://github.com/R3VALATiON/Artemis-Extended-Native-Wireless-DualSense-Features/releases/tag/v0.2.0-personal-preview.1), with the ARM64 APK, changelog, source revision, provenance and SHA-256 checksums.

## Current validation

The published ARM64 Android APK compiled successfully in GitHub Actions. Its signature and application metadata were checked during earlier artifact verification. Twelve targeted report-builder/startup tests and 23 connection-quality policy checks passed. Full upstream lint is not clean; remaining findings and hardware-test limitations are documented with the implementation.

Physical controller feedback, native game recognition, connection-quality switching and the matched Windows driver stack have not yet been validated on the user's hardware. Successful compilation is not proof that those hardware paths work.

## Development branch

Prepared implementation: [feature/native-dualsense-adaptive](https://github.com/R3VALATiON/Artemis-Extended-Native-Wireless-DualSense-Features/tree/feature/native-dualsense-adaptive).

Native PCM and bandwidth control require the matching personal Artemis/Apollo pair. An upstream binary must not be described as the matching personal build.

## Original project

[Upstream Extended source](https://github.com/Taveszfito/Artemis-Extended-Native-Wireless-DualSense-Features). Original authors, acknowledgements and licences remain intact. No upstream pull request is part of this work.
