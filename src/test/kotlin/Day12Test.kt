import adventofcode2023.day12.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Day12Test {

    @Test
    fun testPuzzle() {
        assertEquals(21, puzzle1(exampleInput))
    }

    @Test
    fun testFindArrangements() {
        val (p, cs) = parseLine("?###???????? 3,2,1")
        val actual = findArrangements(p, cs)
        assertThat(actual).containsExactlyInAnyOrder(
            ".###.##.#...", ".###.##..#..", ".###.##...#.", ".###.##....#",  ".###..##.#..",
            ".###..##..#.", ".###..##...#", ".###...##.#.", ".###...##..#", ".###....##.#"
        )
    }

    @ParameterizedTest
    @MethodSource("arrangementsTestSource")
    fun testCountArrangements(testCase: Pair<String, Int>) {
        assertEquals(testCase.second, countArrangements(testCase.first))
    }

//    @ParameterizedTest
//    @MethodSource("arrangementsTestSource2")
//    fun testCountArrangements2(testCase: Pair<String, Int>) {
//        assertEquals(testCase.second, countArrangements2(testCase.first))
//    }

//    @Ignore
//    @Test
//    fun testPuzzle2() {
//        assertEquals(525152, puzzle2(exampleInput))
//    }

    companion object {
        @JvmStatic
        fun arrangementsTestSource() = listOf(
            Pair("???.### 1,1,3", 1),
            Pair(".??..??...?##. 1,1,3", 4),
            Pair("?#?#?#?#?#?#?#? 1,3,1,6", 1),
            Pair("????.#...#... 4,1,1", 1),
            Pair("????.######..#####. 1,6,5", 4),
            Pair("?###???????? 3,2,1", 10)
        )

        @JvmStatic
        fun arrangementsTestSource2() = listOf(
            Pair("???.### 1,1,3", 1L),
            Pair(".??..??...?##. 1,1,3", 16384L),
            Pair("?#?#?#?#?#?#?#? 1,3,1,6", 1L),
            Pair("????.#...#... 4,1,1", 16L),
            Pair("????.######..#####. 1,6,5", 2500L),
            Pair("?###???????? 3,2,1", 506250L)
        )

        val exampleInput = """
            ???.### 1,1,3
            .??..??...?##. 1,1,3
            ?#?#?#?#?#?#?#? 1,3,1,6
            ????.#...#... 4,1,1
            ????.######..#####. 1,6,5
            ?###???????? 3,2,1
        """.trimIndent().lines()
    }
}