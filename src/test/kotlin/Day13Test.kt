import adventofcode2023.day13.findReflection
import adventofcode2023.day13.parseInput
import adventofcode2023.day13.puzzle1
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Day13Test {

    @Test
    fun testParseInput() {
        val actual = parseInput(exampleInput)
        assertThat(actual).containsExactly(
            """
                #.##..##.
                ..#.##.#.
                ##......#
                ##......#
                ..#.##.#.
                ..##..##.
                #.#.##.#.
            """.trimIndent().lines(),
            """
                #...##..#
                #....#..#
                ..##..###
                #####.##.
                #####.##.
                ..##..###
                #....#..#
            """.trimIndent().lines()
        )
    }

    @ParameterizedTest
    @MethodSource("reflection")
    fun testFindReflection(testCase: Pair<List<String>, Int>) {
        assertEquals(testCase.second, findReflection(testCase.first))
    }

    @Test
    fun testPuzzle1() {
        assertEquals(405, puzzle1(exampleInput))
    }

    companion object {
        @JvmStatic
        fun reflection() = listOf(
            Pair("""
                #.##..##.
                ..#.##.#.
                ##......#
                ##......#
                ..#.##.#.
                ..##..##.
                #.#.##.#.
            """.trimIndent().lines(), 5),
            Pair("""
                #...##..#
                #....#..#
                ..##..###
                #####.##.
                #####.##.
                ..##..###
                #....#..#
            """.trimIndent().lines(), 400),
            Pair("""
                #.#.....#.##.##.#
                #.#.....#.##.##.#
                .....#...#####...
                ...#.###....#...#
                .###..####.#..#.#
                #.###.#.#..###..#
                ..####...##.#.##.
                ..####...##.#.##.
                #.#.#.#.#..###..#
                .###..####.#..#.#
                ...#.###....#...#
                .....#...#####...
                #.#.....#.##.##.#
            """.trimIndent().lines(),
                100
            )
        )

        val exampleInput = """
            #.##..##.
            ..#.##.#.
            ##......#
            ##......#
            ..#.##.#.
            ..##..##.
            #.#.##.#.

            #...##..#
            #....#..#
            ..##..###
            #####.##.
            #####.##.
            ..##..###
            #....#..#
        """.trimIndent().lines()
    }
}