package adventofcode2023.day14

import adventofcode2023.readInput
import kotlin.time.measureTime

fun main() {
    println("Day 14")
    val input = readInput("day14")
    println("Puzzle 1")
    val time1 = measureTime {
        println(calculateLoad(moveRocks(input)))
    }
    println("puzzle 1 took $time1")
}

fun moveRocks(input: List<String>): List<CharArray> {
    val newInput = input.map { it.toCharArray() }
    for (line in (1..input.lastIndex)) {
        input[line].indices.forEach { column ->
            if (newInput[line][column] == 'O') {
                var newLine = line
                while (newLine > 0 && newInput[newLine - 1][column] == '.') {
                    newLine--
                }
                if (newLine != line) {
                    newInput[newLine][column] = 'O'
                    newInput[line][column] = '.'
                }
            }
        }
    }
    return newInput
}

fun calculateLoad(input: List<CharArray>) : Int {
    var counter = 0
    input.forEachIndexed { index, chars ->
        counter += chars.count { it == 'O' } * (input.size - index)
    }
    return counter
}