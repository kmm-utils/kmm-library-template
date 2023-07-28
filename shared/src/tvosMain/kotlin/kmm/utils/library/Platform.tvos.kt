package kmm.utils.library

import platform.UIKit.UIDevice

class TVOSPlatform : Platform {
    override val name: String =
        "Native Apple " + UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = TVOSPlatform()