package adventofcode2023.day13

import adventofcode2023.readInput
import kotlin.math.min
import kotlin.time.measureTime

fun main() {
    println("Day 13")
    val input = readInput("day13")
    val puzzle1Duration = measureTime {
        println("Puzzle 1 ${puzzle1(input)}")
    }
    println("Puzzle 1 took $puzzle1Duration")
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
        return input.windowed(2).mapIndexedNotNull { index, (a, b) ->
            println("findIndex: $index, $a, $b, ${a == b}")
            if (a == b) {
                if(index > 0) {
                    val range = min(index, (input.lastIndex - (index + 1)))
                    val isReflection = index > 0 && (0..range).all { i ->
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
        }.maxOrNull()
    }

    val row = findIndex(input)
    return if (row != null) {
        (row + 1) * 100
    } else {
        val column = findIndex(buildList {
            input.first.indices.forEach { i ->
                add(buildString {
                    input.forEach { l ->
                        append(l[i])
                    }
                })
            }
        })
        if (column != null) {
            column!! + 1
        } else {
            throw IllegalStateException(input.joinToString(separator = "\n") { it })
        }

    }

//    val row = input.windowed(2).indexOfFirst { (a, b) -> a == b }
//    val range = min(row, (input.lastIndex - (row + 1)))
//    val isReflection = row > 0 && (0..range).all { i -> input[row - i] == input[row + 1 + i] }
//    return if (isReflection) {
//        (row + 1) * 100
//    } else {
//        val column = buildList {
//            input.first.indices.forEach { i ->
//                add(buildString {
//                    input.forEach { l ->
//                        append(l[i])
//                    }
//                })
//            }
//        }.windowed(2).indexOfFirst { (a, b) -> a == b }
//        column + 1
//    }
}

fun puzzle1(input: List<String>): Int = parseInput(input).sumOf { findReflection(it) }