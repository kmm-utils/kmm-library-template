package kmm.utils.library

import kotlin.test.Test
import kotlin.test.assertTrue

class WatchosGreetingTest {

    @Test
    fun testExample() {
        assertTrue(Greeting().greet().contains("watchOS"), "Check watchOS is mentioned")
    }
}