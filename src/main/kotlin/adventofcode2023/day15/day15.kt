package adventofcode2023.day15

import adventofcode2023.readInput
import kotlin.time.measureTime

fun main() {
    println("Day 15")
    val input = readInput("day15")
    val time1 = measureTime {
        println("Puzzle 1 ${handleSequence(input.first)}")
    }
    println("Puzzle 1 took $time1")
}

fun calculateHash(s: String): Short {
    var currentValue = 0
    s.forEach { c ->
        currentValue += c.code
        currentValue *= 17
        currentValue %= 256
    }
    return currentValue.toShort()
}

fun handleSequence(line: String): Int =
    line.split(',').map { calculateHash(it) }.sum()