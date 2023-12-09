package adventofcode2023.day9

import adventofcode2023.readInput
import kotlin.time.measureTime

fun main() {
    println("Day 9")
    val input = readInput("day9")
    val puzzle1Time = measureTime {
        println("Puzzle 1 ${puzzle1(input)}")
    }
    println("Puzzle 1 took $puzzle1Time")

    val puzzle2Time = measureTime {
        println("Puzzle 2 ${puzzle2(input)}")
    }
    println("Puzzle 2 took $puzzle2Time")
}

fun parseInput(input: List<String>): List<List<Int>> {
    return input.map { line -> line.split(' ')
        .filter { it.isNotEmpty() }
        .map { it.toInt() }
    }
}

fun predictNext(input: List<Int>): Int {
    val difference = input.windowed(size = 2).map { (f,s) -> s - f }
//    println("difference = $difference")
    return if (difference.all { it == 0 }) {
        input.last
    } else {
        val predicted = predictNext(difference)
        input.last() + predicted
    }
}

fun puzzle1(input: List<String>): Int =
    parseInput(input).sumOf { predictNext(it) }

fun predictPrevious(input: List<Int>): Int {
//    println("input = $input")
    val difference = input.windowed(size = 2).map { (f,s) -> s - f }
//    println("difference = $difference")
    return if (difference.all { it == 0 }) {
        input.first
    } else {
        val predicted = predictPrevious(difference)
        input.first - predicted
    }
}

fun puzzle2(input: List<String>): Int =
    parseInput(input).sumOf { predictPrevious(it) }