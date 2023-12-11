import adventofcode2023.Point
import adventofcode2023.day11.calculateDistance
import adventofcode2023.day11.findGalactics
import adventofcode2023.day11.handleCosmicExpansion
import adventofcode2023.day11.puzzle1
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
    }
}