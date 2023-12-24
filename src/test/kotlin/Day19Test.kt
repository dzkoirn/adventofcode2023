import adventofcode2023.day19.Part
import adventofcode2023.day19.parsePart
import adventofcode2023.day19.puzzle1
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Day19Test {

    @Test
    fun testPuzzle1() {
        val actual = puzzle1(exampleInput)
        assertEquals(19114, actual)
    }

    @ParameterizedTest
    @MethodSource("partsSource")
    fun testParseParts(testCase: Pair<String, Part>) {
        assertEquals(testCase.second, parsePart(testCase.first))
    }

    companion object {
        @JvmStatic
        fun partsSource() = listOf(
            Pair("{x=787,m=2655,a=1222,s=2876}", Part(a = 1222, x = 787, m = 2655, s = 2876)),
            Pair("{x=1679,m=44,a=2067,s=496}", Part(a = 2067, x = 1679, m = 44, s=496)),
            Pair("{x=2036,m=264,a=79,s=2244}", Part(x=2036, m=264, a=79, s=2244)),
            Pair("{x=2461,m=1339,a=466,s=291}", Part(x=2461, m=1339, a=466, s=291)),
            Pair("{x=2127,m=1623,a=2188,s=1013}", Part(x=2127, m=1623, a=2188, s=1013))
        )

        val exampleInput = """
            px{a<2006:qkq,m>2090:A,rfg}
            pv{a>1716:R,A}
            lnx{m>1548:A,A}
            rfg{s<537:gd,x>2440:R,A}
            qs{s>3448:A,lnx}
            qkq{x<1416:A,crn}
            crn{x>2662:A,R}
            in{s<1351:px,qqz}
            qqz{s>2770:qs,m<1801:hdj,R}
            gd{a>3333:R,R}
            hdj{m>838:A,pv}

            {x=787,m=2655,a=1222,s=2876}
            {x=1679,m=44,a=2067,s=496}
            {x=2036,m=264,a=79,s=2244}
            {x=2461,m=1339,a=466,s=291}
            {x=2127,m=1623,a=2188,s=1013}
        """.trimIndent().lines()
    }
}