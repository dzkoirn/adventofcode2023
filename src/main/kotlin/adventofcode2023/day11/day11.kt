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

    val puzzle2Duration = measureTime {
        println("Puzzle 2 ${puzzle2(input, 1000000)} ")
    }
    println("Puzzle 2 took $puzzle2Duration")
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

fun puzzle1(input: List<CharSequence>): Long {
    val points = findGalactics(handleCosmicExpansion(input)).toList()
    return calculateDistances(points)
}

private fun calculateDistances(points: List<Point>): Long {
    var distances = 0L

    for (i in 0 until points.size) {
        for (j in i + 1 until points.size) {
            val distance = calculateDistance(points[i], points[j])
            distances += distance
        }
    }

    return distances
}

fun handleCosmicExpansion2(input: List<CharSequence>, galaxies: Collection<Point>, multiplier: Int): List<Point> {
    val emptyLinesIndexes = buildList {
        input.forEachIndexed { line, charSequence ->
            if (charSequence.all { it == '.' }) {
                add(line)
            }
        }
    }
    val emptyRowsIndexes = buildList {
        input.first.indices.forEach { row ->
           if(input.all { it[row] == '.' }) {
               add(row)
           }
        }
    }
    return galaxies.map { (line,row) ->
        val newLine = emptyLinesIndexes.count { it < line }.let { line + it * multiplier - it }
        val newRow = emptyRowsIndexes.count { it < row }.let { row + it *multiplier - it }
        Point(newLine, newRow)
    }
}

fun puzzle2(input: List<CharSequence>, multiplier: Int): Long =
    calculateDistances(handleCosmicExpansion2(input, findGalactics(input), multiplier))