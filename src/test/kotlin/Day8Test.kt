import adventofcode2023.day8.parseInput
import adventofcode2023.day8.walkThought
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day8Test {

    @Test
    fun testParseInput1() {
        val actual  = parseInput(exampleInput1.lines())
        val expected = Pair(
            "RL",
            mapOf(
                "AAA" to Pair("BBB", "CCC"),
                "BBB" to Pair("DDD", "EEE"),
                "CCC" to Pair("ZZZ", "GGG"),
                "DDD" to Pair("DDD", "DDD"),
                "EEE" to Pair("EEE", "EEE"),
                "GGG" to Pair("GGG", "GGG"),
                "ZZZ" to Pair("ZZZ", "ZZZ")
            )
        )

        assertEquals(expected, actual)
    }

    @Test
    fun testParseInput2() {
        val actual  = parseInput(exampleInput2.lines())
        val expected = Pair(
            "LLR",
            mapOf(
                "AAA" to Pair("BBB", "BBB"),
                "BBB" to Pair("AAA", "ZZZ"),
                "ZZZ" to Pair("ZZZ", "ZZZ")
            )
        )

        assertEquals(expected, actual)
    }

    @Test
    fun testWalkThrought1() {
        val parsedInput = parseInput(exampleInput1.lines())
        val actual = walkThought(parsedInput.first.toCharArray(), parsedInput.second)
        assertEquals(2, actual)
    }

    @Test
    fun testWalkThrought2() {
        val parsedInput = parseInput(exampleInput2.lines())
        val actual = walkThought(parsedInput.first.toCharArray(), parsedInput.second)
        assertEquals(6, actual)
    }

    companion object {

        val exampleInput1 = """
            RL

            AAA = (BBB, CCC)
            BBB = (DDD, EEE)
            CCC = (ZZZ, GGG)
            DDD = (DDD, DDD)
            EEE = (EEE, EEE)
            GGG = (GGG, GGG)
            ZZZ = (ZZZ, ZZZ)
        """.trimIndent()


        val exampleInput2 = """
            LLR

            AAA = (BBB, BBB)
            BBB = (AAA, ZZZ)
            ZZZ = (ZZZ, ZZZ)
        """.trimIndent()
    }
}