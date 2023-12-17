import adventofcode2023.day15.calculateHash
import adventofcode2023.day15.handleSequence
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

    companion object {
        val exampleInput = "rn=1,cm-,qp=3,cm=2,qp-,pc=4,ot=9,ab=5,pc-,pc=6,ot=7".lines()
    }
}