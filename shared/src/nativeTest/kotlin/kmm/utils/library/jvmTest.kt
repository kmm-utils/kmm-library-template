package kmm.utils.library

import kotlin.test.Test
import kotlin.test.assertTrue

class JvmGreetingTest {

    @Test
    fun testExample() {
        assertTrue(Greeting().greet().contains("Native"), "Check Native is mentioned")
    }
}