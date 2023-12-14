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
    val puzzle1ParalelStreamDuration = measureTime {
        println("Puzzle 1 ParallelStream ${puzzle1ParallelStream(input)}")
    }
    println("Puzzle 1 ParallelStream took $puzzle1ParalelStreamDuration")

//    println("Puzzle 2")
//    val puzzle2Duration = measureTime {
//        println("Puzzle 2 ${puzzle2(input)}")
//    }
//    println("Puzzle 2 took $puzzle1Duration")
}

fun puzzle1(input: List<String>): Int {
    return input.sumOf { l -> countArrangements(l) }
}

fun puzzle1ParallelStream(input: List<String>): Int {
    return input.stream().parallel().map { l -> countArrangements(l) }.toList().sum()
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
    tailrec fun generateCandidates(pattern: String, index: Int = 0, candidates: Collection<String> = emptySet()): Collection<String> {
        if (index == pattern.length) {
            return candidates
        }
        val character = pattern[index]
        val newCandidates = if (character == '?') {
            if (candidates.isEmpty()) {
                setOf(".", "#")
            } else {
                candidates.flatMap { listOf("$it.", "$it#") }
            }
        } else {
            if (candidates.isEmpty()) {
                setOf(character.toString())
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

fun countArrangements2(line: String): Long {
    val (p,checkSum) = parseLine(line).let {(l,c) ->
        "$l?$l?$l?$l?$l" to c + c + c + c + c
    }


    val minLenght = checkSum.sum() + checkSum.size - 1

    return if(p.last() == '.') {
        val first = findArrangements(p, checkSum).size.toLong()
        val additional = findArrangements("?$p", checkSum).size.toLong()
        first * additional * additional * additional * additional
    } else {
        val first = findArrangements("$p?", checkSum).size.toLong()
        val additional = findArrangements("?$p", checkSum).size.toLong()
        first * additional * additional * additional * additional
    }
}

fun puzzle2(input: List<String>): Long {
    return input.sumOf { l -> countArrangements2(l) }
}