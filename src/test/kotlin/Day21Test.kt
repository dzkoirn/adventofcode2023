import adventofcode2023.day21.walk
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day21Test {

    @Test
    fun testPuzzle1() {
        assertEquals(16, walk(exampleInput, 6).size)
    }

    companion object {
        val exampleInput = """
            ...........
            .....###.#.
            .###.##..#.
            ..#.#...#..
            ....#.#....
            .##..S####.
            .##..#...#.
            .......##..
            .##.#.####.
            .##..##.##.
            ...........
        """.trimIndent().lines()
    }

}