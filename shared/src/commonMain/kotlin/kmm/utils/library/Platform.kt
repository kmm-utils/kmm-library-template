package kmm.utils.library

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform