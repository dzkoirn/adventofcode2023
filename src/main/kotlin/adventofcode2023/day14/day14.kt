package adventofcode2023.day14

import adventofcode2023.readInput
import kotlin.time.measureTime

fun main() {
    println("Day 14")
    val input = readInput("day14")
    println("Puzzle 1")
    val time1 = measureTime {
        println(calculateLoad(moveRocks(input.map { it.toCharArray() })))
    }
    println("puzzle 1 took $time1")

    println("Puzzle 2")
    val time2 = measureTime {
        println(calculateLoad(circle(input, 1000000000)))
    }
    println("puzzle 2 took $time2")
}

fun moveRocks(input: List<CharArray>): List<CharArray> {
    for (line in (1..input.lastIndex)) {
        input[line].indices.forEach { column ->
            if (input[line][column] == 'O') {
                var newLine = line
                while (newLine > 0 && input[newLine - 1][column] == '.') {
                    newLine--
                }
                if (newLine != line) {
                    input[newLine][column] = 'O'
                    input[line][column] = '.'
                }
            }
        }
    }
    return input
}

fun calculateLoad(input: List<CharArray>) : Int {
    var counter = 0
    input.forEachIndexed { index, chars ->
        counter += chars.count { it == 'O' } * (input.size - index)
    }
    return counter
}

fun List<CharArray>.rotate(): List<CharArray> {
    /*
        1 2 3     7 4 1
        4 5 6 =>  8 5 2
        7 8 9     9 6 3
     */
    return buildList {
        this@rotate.first.indices.forEach { index ->
            buildList {
                this@rotate.reversed().forEach { arr ->
                    add(arr[index])
                }
            }.toCharArray().let { add(it) }
        }
    }
}

fun circle(input: List<String>, n: Int): List<CharArray> {
    var preparedInput = input.map { it.toCharArray() }
    val cache = mutableMapOf<String, Int>()
    val cache2 = mutableMapOf<Int, List<CharArray>>()
    repeat(n) { iteration ->
        val buildString = preparedInput.joinToString("\n") { chars -> chars.joinToString("") { "$it" } }
        val oldValue = cache.put(buildString, iteration)
        if (oldValue != null) {
            val diff = iteration - oldValue
            val newIndex = oldValue + (n - oldValue) % diff
            return cache2[newIndex]!!
        }
        cache2[iteration] = buildList { addAll(preparedInput.map { it.copyOf() }) }
        val p1 = moveRocks(preparedInput)
        val p2 = moveRocks(p1.rotate())
        val p3 = moveRocks(p2.rotate())
        preparedInput = moveRocks(p3.rotate()).rotate()
        println("iteration $iteration")
    }
    return preparedInput
}