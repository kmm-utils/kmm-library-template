package kmm.utils.library

import kotlin.test.Test
import kotlin.test.assertTrue

class WasmGreetingTest {

    @Test
    fun testExample() {
        assertTrue(Greeting().greet().contains("WebAssembly"), "Check WebAssembly is mentioned")
    }
}