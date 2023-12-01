import adventofcode2023.puzzle1
import adventofcode2023.puzzle2
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Day1Test {

    private val exampleInput = """
        1abc2
        pqr3stu8vwx
        a1b2c3d4e5f
        treb7uchet
    """.trimIndent()

    @Test
    fun testPuzzle1() {
        assertEquals(142, puzzle1(exampleInput.lines()))
    }

    private val exampleInput2 = """
        two1nine
        eightwothree
        abcone2threexyz
        xtwone3four
        4nineeightseven2
        zoneight234
        7pqrstsixteen
    """.trimIndent()

    @Test
    fun testPuzzle2() {
        assertEquals(281, puzzle2(exampleInput2.lines()))
    }

    @ParameterizedTest
    @MethodSource("linesSource")
    fun testProcessLine(testCase: Pair<String, Int>) {
        assertEquals(testCase.second, puzzle2(listOf(testCase.first)))
    }

    companion object {

        @JvmStatic
        fun linesSource() = listOf(
            "two1nine" to 29,
            "eightwothree" to 83,
            "abcone2threexyz" to 13,
            "xtwone3four" to 24,
            "4nineeightseven2" to 42,
            "zoneight234" to 14,
            "7pqrstsixteen" to 76,
            "41" to 41
        )

    }


}