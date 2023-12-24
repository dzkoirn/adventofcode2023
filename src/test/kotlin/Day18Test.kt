import adventofcode2023.day18.puzzle2
import adventofcode2023.day18.parseHex
import adventofcode2023.day18.parseInputToGraph
import adventofcode2023.day18.puzzle1
import adventofcode2023.day18.visualizeGraph
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Day18Test {

    @Test
    fun testParseInputToGraph() {
        val graph = parseInputToGraph(exampleInput)
        println(graph.joinToString("\n") { it.toString() })
        assertEquals(
            """
                #######
                #.....#
                ###...#
                ..#...#
                ..#...#
                ###.###
                #...#..
                ##..###
                .#....#
                .######
            """.trimIndent(),
            visualizeGraph(graph).trimIndent()
        )
    }

    @Test
    fun testPuzzle1() {
        assertEquals(62, puzzle1(exampleInput))
    }

    @ParameterizedTest
    @MethodSource("hexParseSource")
    fun testParseHex(testCase: Pair<String, Pair<String, Int>>) {
        assertEquals(testCase.second, parseHex(testCase.first))
    }

    @Test
    fun testPuzzle2() {
        val actual = puzzle2(exampleInput)
        val expected = 952408144115
        assertEquals(expected, actual)
    }

    companion object {
        @JvmStatic
        fun hexParseSource() = listOf(
            Pair("#70c710", Pair("R", 461937)),
            Pair("#0dc571", Pair("D", 56407)),
            Pair("#5713f0", Pair("R", 356671)),
            Pair("#d2c081", Pair("D", 863240)),
            Pair("#59c680", Pair("R", 367720)),
            Pair("#411b91", Pair("D", 266681)),
            Pair("#8ceee2", Pair("L", 577262)),
            Pair("#caa173", Pair("U", 829975)),
            Pair("#1b58a2", Pair("L", 112010)),
            Pair("#caa171", Pair("D", 829975)),
            Pair("#7807d2", Pair("L", 491645)),
            Pair("#a77fa3", Pair("U", 686074)),
            Pair("#015232", Pair("L", 5411)),
            Pair("#7a21e3", Pair("U", 500254))
        )


        val exampleInput = """
            R 6 (#70c710)
            D 5 (#0dc571)
            L 2 (#5713f0)
            D 2 (#d2c081)
            R 2 (#59c680)
            D 2 (#411b91)
            L 5 (#8ceee2)
            U 2 (#caa173)
            L 1 (#1b58a2)
            U 2 (#caa171)
            R 2 (#7807d2)
            U 3 (#a77fa3)
            L 2 (#015232)
            U 2 (#7a21e3)
        """.trimIndent().lines()
    }
}