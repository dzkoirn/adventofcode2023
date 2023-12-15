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

    val puzzle2Duration = measureTime {
        println("Puzzle 2 ${puzzle2(input)}")
    }
    println("Puzzle 2 took $puzzle2Duration")
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
        return if (candidates.size != 1) {
            null
        } else {
            candidates.first()
        }

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

fun findMutationCandidates(input: List<String>): List<Triple<Int, String, Int>> {
    return buildList {
        input.subList(0, input.lastIndex - 1).forEachIndexed { index, s1 ->
            input.subList(index + 1, input.lastIndex).forEachIndexed { index2, s2 ->
                var difference = 1
                var diffI = 0
                for ((i, c) in s1.withIndex()) {
                    if (s2[i] != c) {
                        diffI = i
                        difference--
                    }
                    if (difference < 0) {
                        break
                    }
                }
                if (difference == 0) {
                    println("findMutationCandidates = $index, $s1, $diffI, ${s1[diffI]}")
                    println("findMutationCandidates = ${(index2 + index + 1)}, $s2, $diffI, ${s2[diffI]}")
                    add(Triple(index, s1, diffI))
                    add(Triple(index2 + index + 1, s2, diffI))
                }
            }
        }
    }
}

fun findMutationReflection(input: List<String>, candidates: List<Triple<Int, String, Int>>): Int {
    var result = 0
    for ((index, s, charIndex) in candidates) {
        val newInput = input.toMutableList().let { list ->
            val newS = s.toCharArray().let { arr ->
                val character = arr[charIndex]
                arr[charIndex] = if (character == '#') {
                    '.'
                } else {
                    '#'
                }
                String(arr)
            }
            println("newS = $newS")
            list[index] = newS
            list.toList()
        }
        println("New input:\n${newInput.joinToString(separator = "\n") { it }}")
        result = findReflection(newInput)
        println("result = $result")
        if (result > 0) {
            break
        }
    }
    return result
}

fun puzzle2(source: List<String>): Int =
    parseInput(source).sumOf { input ->
        println("Input:\n${input.joinToString(separator = "\n") { it }}")
        val candidates = findMutationCandidates(input)
        if (candidates.isNotEmpty()) {
            findMutationReflection(input, candidates)
        } else {
            val newInput = buildList {
                input.first.indices.forEach { i ->
                    add(buildString {
                        input.forEach { l ->
                            append(l[i])
                        }
                    })
                }
            }
            val newCandidates = findMutationCandidates(newInput)
            findMutationReflection(newInput, newCandidates) / 100
        }

    }