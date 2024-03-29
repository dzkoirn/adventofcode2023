import adventofcode2023.day16.findBestStartPoint
import adventofcode2023.day16.findEnergizedPoints
import adventofcode2023.day16.visualize
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day16Test {

    @Test
    fun testFindEnergizedPoints() {
        val result = findEnergizedPoints(exampleInput.map { it.toCharArray() })
        println(
            visualize(
                exampleInput.map { it.toCharArray() },
                result
            ).joinToString("\n") { chars -> chars.joinToString("") { "$it" } }
        )
        assertEquals(46, result.size)
    }

    @Test
    fun testFindBestStartPoint() {
        val result = findBestStartPoint(exampleInput)
        assertEquals(51, result)
    }

    companion object {
        val exampleInput = """
            .|...\....
            |.-.\.....
            .....|-...
            ........|.
            ..........
            .........\
            ..../.\\..
            .-.-/..|..
            .|....-|.\
            ..//.|....
        """.trimIndent().lines()
    }
}