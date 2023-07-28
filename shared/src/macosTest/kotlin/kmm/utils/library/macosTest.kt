package kmm.utils.library

import kotlin.test.Test
import kotlin.test.assertTrue

class MacosGreetingTest {

    @Test
    fun testExample() {
        val greeting = Greeting().greet()
        assertTrue(greeting.contains("macOS"), "Check macOS is mentioned in '$greeting'")
    }
}