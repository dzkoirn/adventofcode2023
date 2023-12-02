import adventofcode2023.day2.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Day2Test {

    private val inputExample = """
        Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
        Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue
        Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red
        Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red
        Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green
    """.trimIndent()

    @Test
    fun puzzle1Test() {
        assertEquals(8, puzzle1(inputExample.lines(), GameRecord(red = 12, green = 13, blue = 14)))
    }

    @Test
    fun puzzle2Test() {
        assertEquals(2286, puzzle2(inputExample.lines()))
    }

    @ParameterizedTest
    @MethodSource("parsingSource")
    fun testLineParsing(testCase: Pair<String, Pair<Int, List<GameRecord>>>) {
        assertEquals(testCase.second, dummyLineParsing(testCase.first))
    }

    @ParameterizedTest
    @MethodSource("possibleSource")
    fun testisGamePossible(testCase: Pair<String, Boolean>) {
        assertEquals(
            testCase.second,
            isGamePossible(
                initial = GameRecord(red = 12, green = 13, blue = 14),
                dummyLineParsing(testCase.first).second
            )
        )
    }

    @ParameterizedTest
    @MethodSource("minimalGameAmount")
    fun testMinimalGameAmount(testCase: Pair<String, GameRecord>) {
        assertEquals(testCase.second, calculateMinimalGameAmount(dummyLineParsing(testCase.first).second))
    }

    companion object {

        @JvmStatic
        fun parsingSource() = listOf(
            Pair(
                "Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green",
                Pair(
                    1,
                    listOf(
                        GameRecord(blue = 3, red = 4),
                        GameRecord(red = 1, green = 2, blue = 6),
                        GameRecord(green = 2)
                    )
                )
            ),
            Pair(
                "Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue",
                Pair(
                    2,
                    listOf(
                        GameRecord(blue = 1, green = 2),
                        GameRecord(green = 3, blue = 4, red = 1),
                        GameRecord(green = 1, blue = 1)
                    )
                )
            ),
            Pair(
                "Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red",
                Pair(
                    3,
                    listOf(
                        GameRecord(green = 8, blue = 6, red = 20),
                        GameRecord(blue = 5, red = 4, green = 13),
                        GameRecord(green = 5, red = 1)
                    )
                )
            ),
            Pair(
                "Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red",
                Pair(
                    4,
                    listOf(
                        GameRecord(green = 1, red = 3, blue = 6),
                        GameRecord(green = 3, red = 6),
                        GameRecord(green = 3, blue = 15, red = 14)
                    )
                )
            ),
            Pair(
                "Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green",
                Pair(
                    5,
                    listOf(
                        GameRecord(red = 6, blue = 1, green = 3),
                        GameRecord(blue = 2, red = 1, green = 2)
                    )
                )
            )
        )


        @JvmStatic
        fun possibleSource() = listOf(
            Pair("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green", true),
            Pair("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue", true),
            Pair("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red", false),
            Pair("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red", false),
            Pair("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green", true)
        )

        @JvmStatic
        fun minimalGameAmount() = listOf(
            Pair("Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green", GameRecord(red = 4, green = 2, blue = 6)),
            Pair("Game 2: 1 blue, 2 green; 3 green, 4 blue, 1 red; 1 green, 1 blue", GameRecord(red = 1, green = 3, blue = 4)),
            Pair("Game 3: 8 green, 6 blue, 20 red; 5 blue, 4 red, 13 green; 5 green, 1 red", GameRecord(red = 20, green = 13, blue = 6)),
            Pair("Game 4: 1 green, 3 red, 6 blue; 3 green, 6 red; 3 green, 15 blue, 14 red", GameRecord(red = 14, green = 3, blue = 15)),
            Pair("Game 5: 6 red, 1 blue, 3 green; 2 blue, 1 red, 2 green", GameRecord(red = 6, green = 3, blue = 2))
        )
    }
}