// swift-tools-version:5.9
import PackageDescription

let package = Package(
    name: "shared",
    platforms: [.iOS(.v14)],
    products: [
        .library(name: "shared", targets: ["shared"])
    ],
    targets: [
        .binaryTarget(
            name: "shared",
            url: "https://github.com/ranab4b/kmp-shared-business-logic/releases/download/v1.0.0/shared.xcframework.zip",
            checksum: "265fe0ab692441af3dbd7ae033616b6e52854b7f32926371dc82ee9140808fd1"
        )
    ]
)
