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
    val duration1Optimized = measureTime {
        println("Puzzle Optimized 1: ${puzzle1Optimized(input)}")
    }
    println("Puzzle 1 Solved took $duration1Optimized")
    val duration1Solved = measureTime {
        println("Puzzle Solved 1: ${puzzle1Solved(input)}")
    }
    println("Puzzle 1 Solved took $duration1Solved")

    val duration2Optimized = measureTime {
        println("Puzzle 2 Optimized: ${puzzle2Optimized(input)}")
    }
    println("Puzzle 2 Optimized took $duration2Optimized")
    val duration2Dummy = measureTime {
        println("Puzzle 2 Dummy: ${puzzle2Dummy(input)}")
    }
    println("Puzzle 2 Dummy took $duration2Dummy")

}

data class Race(val time: Long, val distance: Long)

fun parseInput(lines:List<String>): List<Race> {
    val (timeLine, distanceLine) = lines
    val time = timeLine.substring("Time:".length).trim().split(' ').filter { it.isNotEmpty() }.map { it.toLong() }
    val distance = distanceLine.substring("Distance:".length).trim().split(' ').filter { it.isNotEmpty() }.map { it.toLong() }
    assert(time.size == distance.size)
    return time.indices.map { index ->
        Race(time[index], distance[index])
    }
}

fun findWinningStrategiesDummy(race: Race): List<Long> {
    val combinations = mutableListOf<Long>()

    for (x in 1 until race.time) {
        val y = race.time - x

        if (x * y > race.distance) {
            combinations.add(x)
        }
    }

    return combinations
}

fun findWinningStrategiesDummyOptimized(race: Race) =
    (1 until race.time).count { x -> x * (race.time - x) > race.distance }

fun findWinningStrategiesSolves(race: Race): Int {
    val b = -race.time
    val c = race.distance

    val discriminant = b * b - 4 * 1.0 * c

    return if (discriminant <= 0.0) {
       throw IllegalStateException("Could not find solution")
    } else {
        val root1 = ((-b + sqrt(discriminant)) / (2 * 1.0))
        val root2 = ((-b - sqrt(discriminant)) / (2 * 1.0))
            val correctedEnd = if (root1 % 1 > 0) {
                root1.toInt()
            } else {
                root1.toInt() -1
            }
        correctedEnd - root2.toInt()
//            IntRange(start.toInt() + 1, correctedEnd)
    }
}

fun puzzle1(input: List<String>): Int {
    return parseInput(input).map { race ->
        findWinningStrategiesDummy(race).size
    }.reduce { acc, i -> acc * i }
}

fun puzzle1Optimized(input: List<String>): Int {
    return parseInput(input).map { race ->
        findWinningStrategiesDummyOptimized(race)
    }.reduce { acc, i -> acc * i }
}

fun puzzle1Solved(input: List<String>): Int {
    return parseInput(input).map { race ->
        findWinningStrategiesSolves(race)
    }.reduce { acc, i -> acc * i }
}

fun parseInput2(lines:List<String>): Race {
    val (timeLine, distanceLine) = lines
    val time = timeLine.substring("Time:".length).trim().split(' ').filter { it.isNotEmpty() }.joinToString(separator = "").toLong()
    val distance = distanceLine.substring("Distance:".length).trim().split(' ').filter { it.isNotEmpty() }.joinToString(separator = "").toLong()
    return Race(time, distance)
}

fun puzzle2Dummy(input: List<String>): Int {
    return parseInput2(input).let { race ->
        findWinningStrategiesDummy(race).size
    }
}

fun puzzle2Optimized(input: List<String>): Int {
    return findWinningStrategiesDummyOptimized(parseInput2(input))
}

fun puzzle2Solved(input: List<String>): Int {
    return findWinningStrategiesSolves(parseInput2(input))
}