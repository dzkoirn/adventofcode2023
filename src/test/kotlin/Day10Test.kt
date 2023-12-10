import adventofcode2023.Point
import adventofcode2023.day10.findPath
import adventofcode2023.day10.findStart
import adventofcode2023.day10.isPointsConnected
import adventofcode2023.day10.puzzle1
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Day10Test {

    @Test
    fun testFindStart() {
        assertEquals(Point(1, 1), findStart(exampleInput1.lines()))
        assertEquals(Point(2, 0), findStart(exampleInput2.lines()))
    }

    @ParameterizedTest
    @MethodSource("connectedTestSource")
    fun testIsConnected(testCase: Triple<Point, Point, Boolean>) {
        assertEquals(testCase.third, isPointsConnected(exampleInput1.lines(), testCase.first, testCase.second))
    }

    @ParameterizedTest
    @MethodSource("pathTestSource")
    fun testFindPath(testCase: Triple<List<CharSequence>, Point, List<Point>>) {
        val result = findPath(testCase.first, testCase.second)
        assertThat(result).containsExactlyInAnyOrderElementsOf(testCase.third)
    }

    @ParameterizedTest
    @MethodSource("puzzle1Cases")
    fun testPuzzle1(testCase: Pair<List<String>, Int>) {
        assertEquals(testCase.second, puzzle1(testCase.first))
    }

    companion object {
        @JvmStatic
        fun connectedTestSource() = listOf(
            Triple(Point(1, 1), Point(0, 1), false),
            Triple(Point(1, 1), Point(1, 0), false),
            Triple(Point(1, 1), Point(1, 2), true),
            Triple(Point(1, 1), Point(2, 1), true)
        )

        @JvmStatic
        fun pathTestSource() = listOf(
            Triple(
                exampleInput1.lines(),
                Point(1, 1),
                listOf(
                    Point(1, 1),
                    Point(1, 2),
                    Point(1, 3),
                    Point(2, 3),
                    Point(3, 3),
                    Point(3, 2),
                    Point(3, 1),
                    Point(2, 1)
                ),
            ),
            Triple(
                exampleInput2.lines(),
                Point(2, 0),
                listOf(
                    Point(2, 0),
                    Point(2, 1),
                    Point(1, 1),
                    Point(1, 2),
                    Point(0, 2),
                    Point(0, 3),
                    Point(1, 3),
                    Point(2, 3),
                    Point(2, 4),
                    Point(3, 4),
                    Point(3, 3),
                    Point(3, 2),
                    Point(3, 1),
                    Point(4, 1),
                    Point(4, 0),
                    Point(3, 0)
                ),
            )
        )

        @JvmStatic
        fun puzzle1Cases() = listOf(
            Pair(exampleInput1.lines(), 4),
            Pair(exampleInput2.lines(), 8)
        )

        val exampleInput1 = """
            -L|F7
            7S-7|
            L|7||
            -L-J|
            L|-JF
        """.trimIndent()

        val exampleInput2 = """
            7-F7-
            .FJ|7
            SJLL7
            |F--J
            LJ.LJ
        """.trimIndent()
    }
}