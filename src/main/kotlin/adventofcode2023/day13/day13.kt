package adventofcode2023.day13

import adventofcode2023.readInput
import java.lang.RuntimeException
import kotlin.math.min
import kotlin.time.measureTime

fun main() {
    println("Day 13")
    val input = readInput("day13")
    val puzzle1Duration = measureTime {
        println("Puzzle 1 ${puzzle1(input)}")
    }
    println("Puzzle 1 took $puzzle1Duration")

    println("\n================================================================\n")
}

fun parseInput(input: List<String>): Collection<List<String>> {
    return buildList {
        var list = mutableListOf<String>()
        input.forEach {
            if (it.isEmpty()) {
                add(list)
                list = mutableListOf()
            } else {
                list.add(it)
            }
        }
        add(list)
    }
}

fun findReflection(input: List<String>): Int {
    fun findIndex(input: List<String>): Int? {
         val candidates = input.windowed(2).mapIndexedNotNull { index, (a, b) ->
            println("findIndex: $index, $a, $b, ${a == b}")
            if (a == b) {
                if (index > 0) {
                    val range = min(index, (input.lastIndex - (index + 1)))
                    val isReflection = (0..range).all { i ->
                        println("all $range, $index, $i, ${input[index - i]}, ${input[index + 1 + i]}, ${input[index - i] == input[index + 1 + i]}")
                        input[index - i] == input[index + 1 + i]
                    }
                    println("isReflection = $isReflection, $index, $a, $b")
                    if (isReflection) {
                        return@mapIndexedNotNull index
                    }
                } else {
                    return@mapIndexedNotNull index
                }
            }
            null
        }
        return candidates.firstOrNull()
    }

    val row = findIndex(input)
    return if (row != null) {
        (row + 1) * 100
    } else {
        val column = findIndex(buildList {
            input.first.indices.forEach { i ->
                add(buildString {
                    input.forEach { l ->
                        try {
                            append(l[i])
                        } catch (ex: RuntimeException) {
                            println("$ex $l")
                            throw ex
                        }

                    }
                })
            }
        })
        if (column != null) {
            column + 1
        } else {
            0
        }
    }
}

fun puzzle1(input: List<String>): Int = parseInput(input).sumOf { findReflection(it) }