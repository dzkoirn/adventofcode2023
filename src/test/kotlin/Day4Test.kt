import adventofcode2023.day4.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Day4Test {

    @ParameterizedTest
    @MethodSource("parseLineSource")
    fun testParseLine(testCase: Pair<String, Card>) {
        assertEquals(testCase.second, parseLine(testCase.first))
    }

    @ParameterizedTest
    @MethodSource("findWinningNumberSource")
    fun testFindWinningNumber(testCase: Pair<String, Array<Int>>) {
        assertThat(findWinningNumber(parseLine(testCase.first))).containsExactlyInAnyOrder(*testCase.second)
    }

    @ParameterizedTest
    @MethodSource("calculatePointsSource")
    fun testCalculatePoint(testCase: Pair<List<Int>, Int>) {
        assertEquals(testCase.second, calculatePoints(testCase.first))
    }

    @Test
    fun testPuzzle1ExampleInput() {
        assertEquals(13, puzzle1(inputExample.lines()))
    }

    @Test
    fun testPuzzle2ExampleInput() {
        assertEquals(30, puzzle2(inputExample.lines()))
    }

    companion object {

        private val inputExample = """
            Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
            Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
            Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
            Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
            Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
            Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
        """.trimIndent()

        @JvmStatic
        fun parseLineSource(): List<Pair<String, Card>> = buildList {
            inputExample.lines().let { lines ->
                add(Pair(lines[0], Card(listOf(41, 48, 83, 86, 17), listOf(83, 86, 6, 31, 17, 9, 48, 53))))
                add(Pair(lines[1], Card(listOf(13, 32, 20, 16, 61), listOf(61, 30, 68, 82, 17, 32, 24, 19))))
                add(Pair(lines[2], Card(listOf(1, 21, 53, 59, 44), listOf(69, 82, 63, 72, 16, 21, 14, 1))))
                add(Pair(lines[3], Card(listOf(41, 92, 73, 84, 69), listOf(59, 84, 76, 51, 58, 5, 54, 83))))
                add(Pair(lines[4], Card(listOf(87, 83, 26, 28, 32), listOf(88, 30, 70, 12, 93, 22, 82, 36))))
                add(Pair(lines[5], Card(listOf(31, 18, 13, 56, 72), listOf(74, 77, 10, 23, 35, 67, 36, 11))))
            }
        }

        @JvmStatic
        fun findWinningNumberSource(): List<Pair<String, Array<Int>>> = buildList {
            inputExample.lines().let { lines ->
                add(Pair(lines[0], arrayOf(48, 83, 17, 86)))
                add(Pair(lines[1], arrayOf(32, 61)))
                add(Pair(lines[2], arrayOf(1, 21)))
                add(Pair(lines[3], arrayOf(84)))
                add(Pair(lines[4], emptyArray()))
                add(Pair(lines[5], emptyArray()))
            }
        }

        @JvmStatic
        fun calculatePointsSource(): List<Pair<List<Int>, Int>> = listOf(
            Pair(listOf(48, 83, 17, 86), 8),
            Pair(listOf(32, 61), 2),
            Pair(listOf(84), 1),
            Pair(emptyList(), 0),
        )
    }
}