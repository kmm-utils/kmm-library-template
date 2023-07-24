package kmm.utils.library

import kotlin.test.Test
import kotlin.test.assertTrue

class MingwGreetingTest {

    @Test
    fun testExample() {
        assertTrue(Greeting().greet().contains("Windows"), "Check Windows is mentioned")
    }
}