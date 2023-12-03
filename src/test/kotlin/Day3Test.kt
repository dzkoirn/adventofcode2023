import adventofcode2023.day3.findAllIntersections
import adventofcode2023.day3.puzzle1
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day3Test {

    private val exampleInput = """
        467..114..
        ...*......
        ..35..633.
        ......#...
        617*......
        .....+.58.
        ..592.....
        ......755.
        ...$.*....
        .664.598..
    """.trimIndent()

    @Test
    fun testFundAllIntersections() {
        assertThat(findAllIntersections(exampleInput.lines())).containsExactlyInAnyOrder(
            "467", "35", "633", "617", "592", "755", "664", "598"
        )
    }

    @Test
    fun puzzle1Test() {
        assertEquals(4361, puzzle1(exampleInput.lines()))
    }

}