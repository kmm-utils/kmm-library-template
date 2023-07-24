package kmm.utils.library

import kotlin.test.Test
import kotlin.test.assertTrue

class LinuxGreetingTest {

    @Test
    fun testExample() {
        assertTrue(Greeting().greet().contains("Linux"), "Check Linux is mentioned")
    }
}