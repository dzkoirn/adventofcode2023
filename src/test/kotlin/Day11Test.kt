import adventofcode2023.Point
import adventofcode2023.day11.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Day11Test {

    @Test
    fun testHandleCosmicExpansion() {
        val actual = handleCosmicExpansion(exampleInput)

        assertEquals(expandedCosmos, actual)
    }

    @Test
    fun testFindGalactics() {
        assertThat(findGalactics(expandedCosmos)).containsExactlyInAnyOrder(
            Point(0, 4), Point(1, 9), Point(2, 0), Point(5, 8), Point(6, 1), Point(7, 12), Point(10, 9), Point(11, 0), Point(11, 5)
        )
    }

    @ParameterizedTest
    @MethodSource("distanceTest")
    fun distanceTest(testCase: Triple<Point, Point, Int>) {
        assertEquals(testCase.third, calculateDistance(testCase.first, testCase.second))
    }

    @Test
    fun testPuzzle1() {
        assertEquals(374, puzzle1(exampleInput))
    }

    @Test
    fun testHandleCosmicExpansion2(){
        val oldVersion = findGalactics(handleCosmicExpansion(exampleInput))

        val newVersion = handleCosmicExpansion2(exampleInput, findGalactics(exampleInput), 2)

        assertThat(newVersion).containsExactlyInAnyOrderElementsOf(oldVersion)
    }

    @ParameterizedTest
    @MethodSource("puzzle2Source")
    fun testPuzzle2Example(testCase: Pair<Int, Long>) {
        assertEquals(testCase.second, puzzle2(exampleInput, testCase.first))
    }

    companion object {

        val exampleInput = """
            ...#......
            .......#..
            #.........
            ..........
            ......#...
            .#........
            .........#
            ..........
            .......#..
            #...#.....
        """.trimIndent().lines()

        val expandedCosmos = """
            ....#........
            .........#...
            #............
            .............
            .............
            ........#....
            .#...........
            ............#
            .............
            .............
            .........#...
            #....#.......
        """.trimIndent().lines()

        @JvmStatic
        fun distanceTest() = listOf(
            Triple(Point(6, 1), Point(11, 5), 9),
            Triple(Point(0, 4), Point(10, 9), 15),
            Triple(Point(2, 0), Point(7, 12), 17),
            Triple(Point(11, 0), Point(11, 5), 5)
        )

        @JvmStatic
        fun puzzle2Source() = listOf(
            Pair(2, 374L),
            Pair(10, 1030L),
            Pair(100, 8410L)
        )
    }
}