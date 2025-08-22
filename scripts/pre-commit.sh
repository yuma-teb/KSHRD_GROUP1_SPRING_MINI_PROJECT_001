#!/bin/sh
set -eu

echo "Running Spotless…"
./gradlew spotlessApply

# Stage only tracked files that changed (safer than `.`)
git add -u

echo "Pre-commit formatting done."
