package kmm.utils.library

class JVMlatform : Platform {
    override val name: String =
        "JVM " + System.getProperty("os.name") + "" + System.getProperty("os.version")
}

actual fun getPlatform(): Platform = JVMlatform()