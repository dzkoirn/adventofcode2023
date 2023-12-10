import adventofcode2023.Point
import adventofcode2023.day10.*
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
    fun testIsPointsConnected(testCase: Triple<Point, Point, Boolean>) {
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

    @ParameterizedTest
    @MethodSource("insideTestCases")
    fun testIsPointInsidePath(testCase: Triple<List<String>, Point, Boolean>) {
        assertEquals(testCase.third, isPointInsidePath(testCase.second, findPath(testCase.first, findStart(testCase.first)).toList()))
    }

    @ParameterizedTest
    @MethodSource("puzzle2Source")
    fun testPuzzle2(testCase: Pair<List<String>, Int>) {
        assertEquals(testCase.second, puzzle2(testCase.first))
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

        @JvmStatic
        fun insideTestCases() = listOf(
            Triple(exampleInput3.lines(), Point(3, 3), false),
            Triple(exampleInput3.lines(), Point(6, 5), false),
            Triple(exampleInput3.lines(), Point(6, 2), true),
            Triple(exampleInput3.lines(), Point(6, 7), true),
            Triple(exampleInput4.lines(), Point(2, 3), false),
        )

        @JvmStatic
        fun puzzle2Source() = listOf(
            Pair(exampleInput3.lines(), 4),
            Pair(exampleInput4.lines(), 8),
            Pair(exampleInput5.lines(), 10)
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

        val exampleInput3 = """
            ...........
            .S-------7.
            .|F-----7|.
            .||OOOOO||.
            .||OOOOO||.
            .|L-7OF-J|.
            .|II|O|II|.
            .L--JOL--J.
            .....O.....
        """.trimIndent()

        val exampleInput4: String = """
            OF----7F7F7F7F-7OOOO
            O|F--7||||||||FJOOOO
            O||OFJ||||||||L7OOOO
            FJL7L7LJLJ||LJIL-7OO
            L--JOL7IIILJS7F-7L7O
            OOOOF-JIIF7FJ|L7L7L7
            OOOOL7IF7||L7|IL7L7|
            OOOOO|FJLJ|FJ|F7|OLJ
            OOOOFJL-7O||O||||OOO
            OOOOL---JOLJOLJLJOOO
        """.trimIndent()

        val exampleInput5: String = """
            FF7FSF7F7F7F7F7F---7
            L|LJ||||||||||||F--J
            FL-7LJLJ||||||LJL-77
            F--JF--7||LJLJIF7FJ-
            L---JF-JLJIIIIFJLJJ7
            |F|F-JF---7IIIL7L|7|
            |FFJF7L7F-JF7IIL---7
            7-L-JL7||F7|L7F-7F7|
            L.L7LFJ|||||FJL7||LJ
            L7JLJL-JLJLJL--JLJ.L
        """.trimIndent()
    }
}