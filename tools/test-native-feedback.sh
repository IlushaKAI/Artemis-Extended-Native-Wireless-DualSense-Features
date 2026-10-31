#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
output="$(mktemp -d)"
trap 'rm -rf "$output"' EXIT
if command -v javac >/dev/null; then compiler=(javac); else compiler=(java com.sun.tools.javac.Main); fi
"${compiler[@]}" -d "$output" app/src/main/java/com/limelight/dualsense/NativeFeedbackPolicy.java tests/NativeFeedbackPolicyTest.java
java -cp "$output" NativeFeedbackPolicyTest
