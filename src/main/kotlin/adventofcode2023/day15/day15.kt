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

    val time2 = measureTime {
        println("Puzzle 2 ${calculateFocusingPower(handleSequence2(input.first))}")
    }
    println("Puzzle 2 took $time2")
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

fun handleSequence2(line: String): Array<LinkedHashMap<String, Int>> {
    val myMap = Array(256) { LinkedHashMap<String, Int>() }
    line.split(',').forEach { s ->
        val label = s.filter { it.isLetter() }
        val hash = calculateHash(label)
        val lenses = myMap[hash.toInt()]
        if (s.contains('-')) {
            lenses.remove(label)
        } else {
            s.split('=').let { (label, focus) -> lenses.put(label, focus.toInt()) }
        }
    }
    return myMap
}

fun calculateFocusingPower(boxes: Array<LinkedHashMap<String, Int>>): Long {
    val result = boxes.flatMapIndexed { boxIndex, lenses ->
        lenses.entries.mapIndexed { lenseIndex, mutableEntry ->
            ((boxIndex + 1) * (lenseIndex + 1) * mutableEntry.value).toLong()
        }
    }.sum()
    return result
}