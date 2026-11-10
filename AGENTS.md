# AutoPic — Project Rules

## Mission
AutoPic is a privacy-first Android photo app with on-device enhancement, on-device search, and a safe AI assistant. Core features must work offline and without altering originals.

## Required invariants
- Originals are never modified by automatic flows.
- Image-derived data stays on-device.
- The assistant only proposes actions and never executes them without explicit confirmation.
- Use deterministic local tooling and evidence-based validation before claiming success.
- No destructive user data changes without explicit consent through the platform flow.

## Build and validation
- Use JDK 21 for Android builds.
- Set `ANDROID_SDK_ROOT` or `local.properties` to `C:\Android\Sdk` when running Gradle locally.
- Validate with `./gradlew.bat assembleDebug`.

## Current status
- Core Android app scaffold and Compose UI are in place.
- The project compiles successfully in debug mode.
