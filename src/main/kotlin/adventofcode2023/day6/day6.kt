package adventofcode2023.day6

import adventofcode2023.readInput
import kotlin.math.sqrt
import kotlin.time.measureTime

fun main() {
    val input = readInput("day6")
    println("Day 6")
    val duration1 = measureTime {
        println("Puzzle 1: ${puzzle1(input)}")
    }
    println("Puzzle 1 took $duration1")
}

data class Race(val time: Int, val distance: Int)

fun parseInput(lines:List<String>): List<Race> {
    val (timeLine, distanceLine) = lines
    val time = timeLine.substring("Time:".length).trim().split(' ').filter { it.isNotEmpty() }.map { it.toInt() }
    val distance = distanceLine.substring("Distance:".length).trim().split(' ').filter { it.isNotEmpty() }.map { it.toInt() }
    assert(time.size == distance.size)
    return time.indices.map { index ->
        Race(time[index], distance[index])
    }
}

fun findWinningStrategies(race: Race): List<Int> {
    val combinations = mutableListOf<Int>()

    for (x in 1 until race.time) {
        val y = race.time - x

        if (x * y > race.distance) {
            combinations.add(x)
        }
    }

    return combinations
}

fun findWinningStrategies2(race: Race): IntRange {
    val b = -race.time
    val c = race.distance

    val discriminant = b * b - 4 * 1.0 * c

    return if (discriminant <= 0.0) {
       throw IllegalStateException("Could not find solution")
    } else {
        val root1 = ((-b + sqrt(discriminant)) / (2 * 1.0))
        val root2 = ((-b - sqrt(discriminant)) / (2 * 1.0))
//        arrayOf(root1, root2).sortedArray().let { (first, second) ->
//            IntRange(first + 1, second - 1)
//        }
        TODO()
    }
}

fun puzzle1(input: List<String>): Int {
    return parseInput(input).map { race ->
        findWinningStrategies(race).size
    }.reduce { acc, i -> acc * i }
}