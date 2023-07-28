package kmm.utils.library

import platform.WatchKit.WKInterfaceDevice

class WatchOSPlatform : Platform {
    override val name: String =
        "Native Apple " + WKInterfaceDevice.currentDevice().systemName + " " + WKInterfaceDevice.currentDevice().systemVersion
}

actual fun getPlatform(): Platform = WatchOSPlatform()