# KMP Shared Business Logic (demo)

A standalone Kotlin Multiplatform library — one loan EMI (equated monthly
installment) calculator, shared between two **separate, pre-existing**
Android and iOS repos. It isn't part of either app's codebase and doesn't
assume a monorepo — it's published and versioned like any third-party
dependency.

This repo is one half of a two-repo demo. The other half —
[`kmp-adoption-before-after`](https://github.com/ranab4b/kmp-adoption-before-after) —
shows a minimal Android app and a minimal iOS app *before* adopting this
module (each with its own hand-written, independently-drifting copy of the
same formula) and *after* (both calling into this shared implementation
instead). The full step-by-step migration guide lives there, in
`docs/MIGRATION-GUIDE.md`.

## What's here

- `shared/src/commonMain/kotlin/com/example/shared/domain/` — the domain
  layer: `LoanInput`, `LoanResult`, `LoanEmiCalculator`. Pure Kotlin, zero
  Android or iOS framework imports. Formatting (currency, dates) stays
  native on each platform; only the calculation is shared.
- `shared/src/commonTest/kotlin/` — a table of golden test cases
  (principal, rate, tenure → expected EMI/total payment/total interest)
  that both platforms are checked against for parity.
- `Package.swift` — a `binaryTarget` pointing at the zipped, checksummed
  XCFramework attached to this repo's GitHub Release.

## Consume it

**Android (JitPack):**
```kotlin
// settings.gradle.kts
dependencyResolutionManagement {
    repositories {
        maven("https://jitpack.io")
    }
}

// app/build.gradle.kts
dependencies {
    implementation("com.github.ranab4b:kmp-shared-business-logic:1.0.0")
}
```
Build status for the tagged release: https://jitpack.io/#ranab4b/kmp-shared-business-logic

**iOS (Swift Package Manager):**
In Xcode: **File → Add Package Dependencies…** → paste this repo's URL
(`https://github.com/ranab4b/kmp-shared-business-logic`) → select the
`shared` product. SPM resolves `Package.swift`, which points at the
XCFramework zip attached to the `v1.0.0` release, verified against its
published checksum.

## Build it yourself

```bash
# Android artifact / run the parity test suite
./gradlew :shared:testDebugUnitTest

# iOS XCFramework (device + simulator in one artifact)
./gradlew :shared:assembleXCFramework
# output: shared/build/XCFrameworks/release/shared.xcframework
```

## Stack

Kotlin Multiplatform (`androidTarget`, `iosArm64`, `iosSimulatorArm64`),
the Gradle `XCFramework()` export DSL, JitPack, Swift Package Manager
binary targets.
