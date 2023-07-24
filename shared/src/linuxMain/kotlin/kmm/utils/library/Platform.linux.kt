package kmm.utils.library

import kotlinx.cinterop.alloc
import kotlinx.cinterop.nativeHeap
import kotlinx.cinterop.ptr
import kotlinx.cinterop.toKString
import platform.posix.uname
import platform.posix.utsname

class LinuxPlatform : Platform {
    override val name: String
        get() {
            val osName: String
            val osVersion: String
            var utsnamePtr: utsname? = null

            try {
                utsnamePtr = nativeHeap.alloc<utsname> {
                    uname(this.ptr)
                }

                utsnamePtr.let {
                    osName = it.sysname.toKString()
                    osVersion = it.release.toKString()
                }

            } finally {
                utsnamePtr?.let {
                    nativeHeap.free(it.rawPtr)
                }
            }

            return "Native $osName ($osVersion)"
        }
}

actual fun getPlatform(): Platform = LinuxPlatform()