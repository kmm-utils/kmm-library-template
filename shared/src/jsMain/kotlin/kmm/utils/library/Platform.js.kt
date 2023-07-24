package kmm.utils.library

class JSlatform : Platform {
    override val name: String = "JavaScript"
}

actual fun getPlatform(): Platform = JSlatform()