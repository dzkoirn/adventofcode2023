import adventofcode2023.day7.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Day7Test {

    @ParameterizedTest
    @MethodSource("comparisonCards")
    fun testCardsComparator(testCase: Triple<Char, Char, (Int)->Boolean>) {
        val actual = compareCards(testCase.first, testCase.second)
        assertTrue( { testCase.third(actual) }, "actual = $actual")
    }

    @ParameterizedTest
    @MethodSource("handsType")
    fun testToHandsTypeFunction(testCase: Pair<String, HandType>) {
        assertEquals(testCase.second, testCase.first.toHandType())
    }

    @ParameterizedTest
    @MethodSource("comparisonTest")
    fun testHandsComparator(testCase: Triple<String, String, (Int)->Boolean>) {
        val actual = compareHands(testCase.first, testCase.second)
        assertTrue({ testCase.third(actual) }, "actual = $actual")
    }

    @Test
    fun testSortInput() {
        assertThat(sortHands(prepareInput(inputExample.lines())))
            .containsExactly(
                Hand("32T3K", 765),
                Hand("KTJJT", 220),
                Hand("KK677", 28),
                Hand("T55J5", 684),
                Hand("QQQJA", 483)
            )
    }

    @Test
    fun testPuzzle1() {
        assertEquals(6440, puzzle1(inputExample.lines()))
    }

    companion object {
        @JvmStatic
        fun comparisonTest() = listOf(
            Triple("77888", "77788") { v: Int -> v > 0},
            Triple("32T3K", "T55J5") { v: Int -> v < 0},
            Triple("32T3K", "32T3K") { v: Int -> v == 0},
            Triple("KK677", "KTJJT") { v: Int -> v > 0}
        )

        @JvmStatic
        fun comparisonCards() = listOf(
            Triple('A', 'K') { v: Int -> v > 0 },
            Triple('K', 'Q') { v: Int -> v > 0 },
            Triple('Q', 'J') { v: Int -> v > 0 },
            Triple('J', 'T') { v: Int -> v > 0 },
            Triple('9', 'T') { v: Int -> v < 0 },
            Triple('9', '8') { v: Int -> v > 0 },
            Triple('7', '8') { v: Int -> v < 0 },
            Triple('7', '6') { v: Int -> v > 0 },
            Triple('5', '6') { v: Int -> v < 0 },
            Triple('5', '4') { v: Int -> v > 0 },
            Triple('3', '4') { v: Int -> v < 0 },
            Triple('3', '2') { v: Int -> v > 0 },
            Triple('2', '2') { v: Int -> v == 0 },
        )

        @JvmStatic
        fun handsType()= listOf(
            "AAAAA" to HandType.FIVE_OF_KIND,
            "AA8AA" to HandType.FOUR_OF_KIND,
            "23332" to HandType.FULL_HOUSE,
            "TTT98" to HandType.THREE_OF_KIND,
            "23432" to HandType.TWO_PAIR,
            "A23A4" to HandType.ONE_PAIR,
            "23456" to HandType.HIGH_CARD
        )

        val inputExample = """
            32T3K 765
            T55J5 684
            KK677 28
            KTJJT 220
            QQQJA 483
        """.trimIndent()
    }
}