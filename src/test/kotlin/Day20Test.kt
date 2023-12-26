import adventofcode2023.day20.parseInput
import adventofcode2023.day20.pushButton
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day20Test {

    @Test
    fun testExample1() {
        val result = pushButton(1, parseInput(exampleInput1))
        assertEquals(Pair(8, 4), result)
    }

    @Test
    fun testExample1_2() {
        val result = pushButton(1000, parseInput(exampleInput1))
        assertEquals(Pair(8000, 4000), result)
    }

    @Test
    fun testExample2() {
        val result = pushButton(1000, parseInput(exampleInput2))
        assertEquals(Pair(4250, 2750), result)
    }

    companion object {
        val exampleInput1 = """
            broadcaster -> a, b, c
            %a -> b
            %b -> c
            %c -> inv
            &inv -> a
        """.trimIndent().lines()

        val exampleInput2 = """
            broadcaster -> a
            %a -> inv, con
            &inv -> b
            %b -> con
            &con -> output
        """.trimIndent().lines()
    }
}