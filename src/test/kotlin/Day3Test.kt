import adventofcode2023.day3.findAllIntersections
import adventofcode2023.day3.findGears
import adventofcode2023.day3.puzzle1
import adventofcode2023.day3.puzzle2
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

    private val exampleFromInput = """
        840.......
        .....34...
        .518*.....
        ......$...
        .......739
        .....525..
        .....*....
        .756..855.
        ...708....
        .....*....
        ....478...
        ..878.569.
        .....*....
        ..........
        .....@981.
        ..........
        .......474
        ....../...
        ..........
    """.trimIndent()

    @Test
    fun testFundAllIntersections2() {
        assertThat(findAllIntersections(exampleFromInput.lines())).containsExactlyInAnyOrder(
            "518", "34", "739", "525", "855", "708", "478", "878", "569", "981", "474"
        )
    }

    @Test
    fun testFindGears() {
        val result = findGears(exampleInput.lines())
        assertThat(result.flatten()).containsExactlyInAnyOrder("467", "35", "755", "598")
    }

    @Test
    fun testPuzzle2() {
        assertEquals(467835, puzzle2(exampleInput.lines()))
    }

}