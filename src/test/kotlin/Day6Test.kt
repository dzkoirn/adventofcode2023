import adventofcode2023.day6.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Day6Test {

    @ParameterizedTest
    @MethodSource("solutionsSource")
    fun testFindWinningStrategies(testCase: Pair<Race, IntRange>) {
        assertTrue { findWinningStrategies(testCase.first).all { it in testCase.second } }
    }

//    @ParameterizedTest
//    @MethodSource("solutionsSource")
//    fun testFindWinningStrategies2(testCase: Pair<Race, IntRange>) {
//        val result = findWinningStrategies2(testCase.first)
//        assertEquals(testCase.second, result)
//    }


    @Test
    fun testParsing() {
        assertEquals(
            listOf(Race(7, 9), Race(15, 40), Race(30, 200)),
            parseInput(exampleInput.lines())
        )
    }

    @Test
    fun testPuzzle1() {
        assertEquals(288, puzzle1(exampleInput.lines()))
    }

    companion object {
        @JvmStatic
        fun solutionsSource(): List<Pair<Race, IntRange>> = listOf(
            Race(7,9) to IntRange(2, 5),
            Race(15, 40) to IntRange(4, 11),
            Race(30, 200) to IntRange(11, 19)
        )

        val exampleInput = """
            Time:      7  15   30
            Distance:  9  40  200
        """.trimIndent()

    }
}