package kmm.utils.library

import kotlinx.cinterop.alloc
import kotlinx.cinterop.convert
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.ptr
import kotlinx.cinterop.sizeOf
import platform.windows.GetVersionExW
import platform.windows.OSVERSIONINFOW

class WindowsPlatform : Platform {
    override val name: String
        get() {
            val osVersion: String
            var osVersionInfoPtr: OSVERSIONINFOW? = null

            try {

                osVersionInfoPtr = nativeHeap.alloc<OSVERSIONINFOW> {
                    dwOSVersionInfoSize = sizeOf<OSVERSIONINFOW>().convert()

                    GetVersionExW(this.ptr)
                }

                osVersion =
                    "${osVersionInfoPtr.dwMajorVersion}.${osVersionInfoPtr.dwMinorVersion}.${osVersionInfoPtr.dwBuildNumber}"
            } finally {
                osVersionInfoPtr?.let {
                    nativeHeap.free(it.rawPtr)
                }
            }

            return "Windows ($osVersion)"
        }
}

actual fun getPlatform(): Platform = WindowsPlatform()