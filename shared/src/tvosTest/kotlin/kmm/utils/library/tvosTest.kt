package kmm.utils.library

import kotlin.test.Test
import kotlin.test.assertTrue

class TvosGreetingTest {

    @Test
    fun testExample() {
        assertTrue(Greeting().greet().contains("tvOS"), "Check tvOS is mentioned")
    }
}