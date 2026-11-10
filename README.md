# AutoPic
AutoPic is a privacy-first Android photo app prototype focused on three core capabilities:
- on-device photo enhancement without modifying originals
- local gallery search and similarity matching
- a safe assistant that proposes actions instead of acting on its own

## Current implementation
This repository now contains an Android app scaffold built with Jetpack Compose. It includes:
- a gallery/library view
- a search experience
- an assistant tab with safe suggestions
- settings toggles for offline and consent-aware behavior

## Run locally
1. Install JDK 21.
2. Ensure Android SDK is available at `C:\Android\Sdk`.
3. From the project root, run:
   `./gradlew.bat assembleDebug`

## Notes
The current project is a working debug-build foundation aligned to the design brief in the master prompt. It is intentionally privacy-focused and keeps the assistant in proposal-only mode.
