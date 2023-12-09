import adventofcode2023.day9.parseInput
import adventofcode2023.day9.predictNext
import adventofcode2023.day9.puzzle1
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Day9Test {

    @ParameterizedTest
    @MethodSource("testSource1")
    fun testPrediction(testCase: Pair<List<Int>, Int>) {
        assertEquals(testCase.second, predictNext(testCase.first))
    }

    @Test
    fun testParseInput() {
        assertEquals(
            listOf(
                listOf(0, 3, 6, 9, 12, 15),
                listOf(1, 3, 6, 10, 15, 21),
                listOf(10, 13, 16, 21, 30, 45)
            ),
            parseInput(exampleInput.lines())
        )
    }

    @Test
    fun testPuzzle1() {
        assertEquals(114, puzzle1(exampleInput.lines()))
    }

    companion object {
        @JvmStatic
        fun testSource1() = listOf(
            Pair(listOf(0, 3, 6, 9, 12, 15), 18),
            Pair(listOf(1, 3, 6, 10, 15, 21), 28),
            Pair(listOf(10, 13, 16, 21, 30, 45), 68)
        )

        val exampleInput = """
            0 3 6 9 12 15
            1 3 6 10 15 21
            10 13 16 21 30 45
        """.trimIndent()
    }
}