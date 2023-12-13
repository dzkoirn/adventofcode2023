package adventofcode2023.day12

import adventofcode2023.readInput
import kotlin.time.measureTime

fun main() {
    println("Day 12")
    val input = readInput("day12")
    val puzzle1Duration = measureTime {
        println("Puzzle 1 ${puzzle1(input)}")
    }
    println("Puzzle 1 took $puzzle1Duration")
}

fun puzzle1(input: List<String>): Int {
    return input.sumOf { l -> countArrangements(l) }
}

fun countArrangements(line: String): Int {
    val (p,checkSum) = parseLine(line)
    return findArrangements(p, checkSum).size
}

fun parseLine(line: String): Pair<String, IntArray> =
    line.split(' ').let { (first, second) ->
        Pair(
            first,
            second.split(',').map { it.toInt() }.toTypedArray().toIntArray()
        )
    }

fun findArrangements(pattern: String, checkSum: IntArray): Collection<String> {
    tailrec fun generateCandidates(pattern: String, index: Int = 0, candidates: List<String> = emptyList()): List<String> {
        if (index == pattern.length) {
            return candidates
        }
        val character = pattern[index]
        val newCandidates = if (character == '?') {
            if (candidates.isEmpty()) {
                listOf(".", "#")
            } else {
                candidates.flatMap { listOf("$it.", "$it#") }
            }
        } else {
            if (candidates.isEmpty()) {
                listOf(character.toString())
            } else {
                candidates.map { it + character }
            }
        }
        return generateCandidates(pattern, index + 1, newCandidates)
    }
    fun List<String>.checker(): Boolean {
        if(this.size != checkSum.size) return false
        for ((i,s) in this.withIndex()) {
            if(s.length != checkSum[i]) return false
        }
        return true
    }
    val candidates = generateCandidates(pattern)
    return candidates.filter { p ->
        p.split('.').filter { it.isNotEmpty() }.checker()
    }
}