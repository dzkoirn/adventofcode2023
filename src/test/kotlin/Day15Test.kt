import adventofcode2023.day15.calculateFocusingPower
import adventofcode2023.day15.calculateHash
import adventofcode2023.day15.handleSequence
import adventofcode2023.day15.handleSequence2
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day15Test {

    @Test
    fun testHash() {
        assertEquals(52, calculateHash("HASH"))
    }

    @Test
    fun testSequence() {
        assertEquals(1320, handleSequence(exampleInput.first()))
    }

    @Test
    fun testHandleSequence2() {
        val result = handleSequence2(exampleInput.first())
        println(result)
        assertThat(result[0].entries.map { Pair(it.key, it.value) }).containsExactly(Pair("rn", 1), Pair("cm", 2))
        assertThat(result[3].entries.map { Pair(it.key, it.value) }).containsExactly(Pair("ot", 7), Pair("ab", 5), Pair("pc", 6))
    }

    @Test
    fun testPuzzle2Example() {
        assertEquals(145, calculateFocusingPower(handleSequence2(exampleInput.first())))
    }

    companion object {
        val exampleInput = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7".lines()
    }
}