package kmm.utils.library

import platform.Foundation.NSProcessInfo

class MacOSPlatform : Platform {
    override val name: String =
        "Native Apple macOS ${NSProcessInfo.processInfo().operatingSystemVersionString}"
}

actual fun getPlatform(): Platform = MacOSPlatform()