package kmm.utils.library

import kotlin.test.Test
import kotlin.test.assertTrue

class JsGreetingTest {

    @Test
    fun testExample() {
        assertTrue(Greeting().greet().contains("JavaScript"), "Check JavaScript is mentioned")
    }
}