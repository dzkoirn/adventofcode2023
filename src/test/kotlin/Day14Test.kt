import adventofcode2023.day14.calculateLoad
import adventofcode2023.day14.circle
import adventofcode2023.day14.moveRocks
import adventofcode2023.day14.rotate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Day14Test {

    @Test
    fun testMoveRocks() {
        assertThat(moveRocks(exampleInput.map { it.toCharArray() })).containsExactly(
            charArrayOf('O', 'O', 'O', 'O', '.', '#', '.', 'O', '.', '.'),
            charArrayOf('O', 'O', '.', '.', '#', '.', '.', '.', '.', '#'),
            charArrayOf('O', 'O', '.', '.', 'O', '#', '#', '.', '.', 'O'),
            charArrayOf('O', '.', '.', '#', '.', 'O', 'O', '.', '.', '.'),
            charArrayOf('.', '.', '.', '.', '.', '.', '.', '.', '#', '.'),
            charArrayOf('.', '.', '#', '.', '.', '.', '.', '#', '.', '#'),
            charArrayOf('.', '.', 'O', '.', '.', '#', '.', 'O', '.', 'O'),
            charArrayOf('.', '.', 'O', '.', '.', '.', '.', '.', '.', '.'),
            charArrayOf('#', '.', '.', '.', '.', '#', '#', '#', '.', '.'),
            charArrayOf('#', '.', '.', '.', '.', '#', '.', '.', '.', '.'),
        )
    }

    @ParameterizedTest
    @MethodSource("circle")
    fun testCircle(testCase: Pair<Int, List<CharArray>>) {
        assertThat(circle(exampleInput, testCase.first)).containsExactly(
            *testCase.second.toTypedArray()
        )
    }

    @Test
    fun testCalculateLoad() {
        assertEquals(136, calculateLoad(moveRocks(exampleInput.map { it.toCharArray() })))
    }

    @Test
    fun testRotate() {
        val input = listOf(
            charArrayOf('1', '2', '3'),
            charArrayOf('4', '5', '6'),
            charArrayOf('7', '8', '9')
        )
        val output = input.rotate()
        assertThat(output).containsExactly(
            charArrayOf('7', '4', '1'),
            charArrayOf('8', '5', '2'),
            charArrayOf('9', '6', '3')
        )
    }

    @Test
    fun testPuzzle2Example() {
        assertEquals(64, calculateLoad(circle(exampleInput, 1000000000)))
    }

    companion object {
        @JvmStatic
        fun circle() = listOf(
            Pair(1, """
                .....#....
                ....#...O#
                ...OO##...
                .OO#......
                .....OOO#.
                .O#...O#.#
                ....O#....
                ......OOOO
                #...O###..
                #..OO#....
            """.trimIndent().lines().map { it.toCharArray() }),
            Pair(2, """
                .....#....
                ....#...O#
                .....##...
                ..O#......
                .....OOO#.
                .O#...O#.#
                ....O#...O
                .......OOO
                #..OO###..
                #.OOO#...O
            """.trimIndent().lines().map { it.toCharArray() }),
            Pair(3, """
                .....#....
                ....#...O#
                .....##...
                ..O#......
                .....OOO#.
                .O#...O#.#
                ....O#...O
                .......OOO
                #...O###.O
                #.OOO#...O
            """.trimIndent().lines().map { it.toCharArray() })
        )

        val exampleInput = """
            O....#....
            O.OO#....#
            .....##...
            OO.#O....O
            .O.....O#.
            O.#..O.#.#
            ..O..#O..O
            .......O..
            #....###..
            #OO..#....
        """.trimIndent().lines()
    }
}