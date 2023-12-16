import adventofcode2023.day14.calculateLoad
import adventofcode2023.day14.moveRocks
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day14Test {

    @Test
    fun testMoveRocks() {
        assertThat(moveRocks(exampleInput)).containsExactly(
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

    @Test
    fun testCalculateLoad() {
        assertEquals(136, calculateLoad(moveRocks(exampleInput)))
    }

    companion object {

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