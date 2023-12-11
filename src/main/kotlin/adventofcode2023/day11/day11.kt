package adventofcode2023.day11

import adventofcode2023.Point
import adventofcode2023.readInput
import kotlin.math.abs
import kotlin.time.measureTime

fun main() {
    println("Day 11")
    val input = readInput("day11")
    val puzzle1Duration = measureTime {
        println("Puzzle 1 ${puzzle1(input)}")
    }
    println("Puzzle 1 took $puzzle1Duration")
}

fun handleCosmicExpansion(input: List<CharSequence>): List<CharSequence> {
    fun List<CharSequence>.duplicateEmptyLine() =
        flatMap { line ->
            if(line.all { it == '.' }) {
                listOf(line, line)
            } else {
                listOf(line)
            }
        }
    fun List<CharSequence>.rotate(): List<CharSequence> =
        first.indices.map { index ->
            buildString {
                this@rotate.forEach {
                    append(it[index])
                }
            }
        }

    return input.duplicateEmptyLine()
        .rotate()
        .duplicateEmptyLine()
        .rotate()
}

fun findGalactics(input: List<CharSequence>): Set<Point> =
    buildSet {
        input.forEachIndexed { line, chars ->
            chars.forEachIndexed { row, c ->
                if(c == '#') {
                    add(Point(line, row))
                }
            }
        }
    }

fun calculateDistance(point1: Point, point2: Point): Int {
    val (x1, y1) = point1
    val (x2, y2) = point2
    return abs(x2 - x1) + abs(y2 - y1)
}

fun puzzle1(input: List<CharSequence>): Int {
    val points = findGalactics(handleCosmicExpansion(input)).toList()
    var distances = 0

    for (i in 0 until points.size) {
        for (j in i + 1 until points.size) {
            val distance = calculateDistance(points[i], points[j])
            distances+=distance
        }
    }

    return distances
}