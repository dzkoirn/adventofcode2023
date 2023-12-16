package adventofcode2023.day13

import adventofcode2023.readInput
import kotlin.math.min
import kotlin.time.measureTime

fun main() {
    println("Day 13")
    val input = readInput("day13")

    val puzzle2Duration = measureTime {
        println("Puzzle 2 ${puzzle2(input)}")
    }
    println("Puzzle 2 took $puzzle2Duration")
}

fun mutateInput(input: List<String>): List<List<String>> {
    return buildList {
        input.subList(0, input.lastIndex).forEachIndexed { index, s1 ->
            input.subList(index + 1, input.size).forEachIndexed { index2, s2 ->
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
                    fun Char.change() = if (this == '#') {
                        '.'
                    } else {
                        '#'
                    }
                    add(with(input.toMutableList()) {
                        set(index, String(s1.toCharArray().let { arr ->
                            arr.also {
                                it[diffI] = it[diffI].change()
                            }
                        }))
                        toList()
                    })
                    add(with(input.toMutableList()) {
                        set(index2 + index + 1, String(s2.toCharArray().let { arr ->
                            arr.also {
                                it[diffI] = it[diffI].change()
                            }
                        }))
                        toList()
                    })
                }
            }
        }
    }
//        .also {
//        println(
//            "Mutated candidates:\n${
//                it.joinToString("\n\n") { l -> l.asString() }
//            }")
//    }
}

fun findIndex2(input: List<String>): Set<Int> {
    val candidates = input.windowed(2).mapIndexedNotNull { index, (a, b) ->
//        println("findIndex: $index, $a, $b, ${a == b}")
        if (a == b) {
            if (index > 0) {
                val range = min(index, (input.lastIndex - (index + 1)))
                val isReflection = (0..range).all { i ->
//                    println("all $range, $index, $i, ${input[index - i]}, ${input[index + 1 + i]}, ${input[index - i] == input[index + 1 + i]}")
                    input[index - i] == input[index + 1 + i]
                }
//                println("isReflection = $isReflection, $index, $a, $b")
                if (isReflection) {
                    println("isReflection = $isReflection, $index, $a, $b")
                    return@mapIndexedNotNull index + 1
                }
            } else {
                println("isReflection = true, $index, $a, $b")
                return@mapIndexedNotNull index + 1
            }
        }
        null
    }
    return candidates.toSet()
//        .also {
//        println("Reflections: $it")
//    }
}

fun rotateInput(input: List<String>): List<String> {
    return buildList {
        input.first.indices.forEach { i ->
            add(buildString {
                input.forEach { l ->
                    append(l[i])
                }
            })
        }
    }
//        .also { newInput ->
//        println("Rotated Input:\n${newInput.joinToString(separator = "\n") { it }}")
//    }
}

fun findReflection2(input: List<String>): Int {
    val original = buildSet {
        findIndex2(input).forEach { add(it * 100) }
        findIndex2(rotateInput(input)).forEach { add(it) }
    }
    val calculated = buildSet {
        mutateInput(input).forEach { newInput ->
            findIndex2(newInput).forEach { add(it * 100) }
            findIndex2(rotateInput(newInput)).forEach { add(it) }
        }
        mutateInput(rotateInput(input)).forEach { newInput ->
            val newInputRotated = rotateInput(newInput)
            findIndex2(newInputRotated).forEach { add(it * 100)  }
            findIndex2(rotateInput(newInputRotated)).forEach { add(it) }
        }
    }
    return (calculated - original).sum()
}

fun List<String>.asString(): String {
    return this.mapIndexed { index, s -> String.format("%02d %s", index, s) }.joinToString("\n") { it }
}

fun puzzle2(source: List<String>): Int =
    parseInput(source).sumOf { input ->
        findReflection2(input).also {
            println("Input:\n${input.asString()}\nResult:$it")
        }
    }