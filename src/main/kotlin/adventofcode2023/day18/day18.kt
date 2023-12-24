package adventofcode2023.day18

import adventofcode2023.Point
import adventofcode2023.col
import adventofcode2023.line
import adventofcode2023.readInput
import java.awt.geom.Point2D
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.time.measureTime


data class Line(
    val start: Point,
    val end: Point
)

fun makeLine(prevPoint: Point, direction: String, steps: Int): Line {
    val newPoint = when (direction) {
        "U" -> prevPoint.copy(first = prevPoint.line - steps)
        "R" -> prevPoint.copy(second = prevPoint.col + steps)
        "D" -> prevPoint.copy(first = prevPoint.line + steps)
        "L" -> prevPoint.copy(second = prevPoint.col - steps)
        else -> throw IllegalArgumentException("Could not handle $direction")
    }
    return Line(prevPoint, newPoint)
}

fun parseInputToGraph(input: List<String>): List<Line> {
    val split = input.map { line -> line.split(' ') }
    val firstLine = split.first().let { (dir, steps, _) -> makeLine(Point(0, 0), dir, steps.toInt()) }
    return split.subList(1, split.size).fold(mutableListOf(firstLine)) { acc, (dir, steps, _) ->
        val newLine = makeLine(acc.last.end, dir, steps.toInt())
        acc.apply {
            add(newLine)
        }
    }
}

fun visualizeGraph(polygon: List<Line>): String {
    val startLine = polygon.minBy { it.start.line }.start.line
    val endLine = polygon.maxBy { it.end.line }.end.line
    val startCol = polygon.minBy { it.start.col }.start.col
    val endCol = polygon.maxBy { it.end.col }.end.col

    return buildString {
        for (i in startLine..endLine) {
            for (j in startCol..endCol) {
                val isBorder = isPointABorder(i, j, polygon)
                if (isBorder) {
                    append('#')
                } else {
                    append('.')
                }
            }
            append(System.lineSeparator())
        }
    }
}

fun isPointABorder(pLine: Int, pCol: Int, polygon: List<Line>): Boolean {
    return polygon.any { line ->
        pLine in (min(line.start.line, line.end.line)..max(line.start.line, line.end.line)) &&
                pCol in (min(line.start.col, line.end.col)..max(line.start.col, line.end.col))
    }
}

fun isPointInsidePolygon(pLine: Int, pCol: Int, polygon: List<Line>): Boolean {
    var inside = false

    for (line in polygon) {
        if ((line.start.col > pCol) != (line.end.col > pCol) &&
            pLine < (line.end.line - line.start.line) * (pCol - line.start.col) / (line.end.col - line.start.col) + line.start.line
        ) {
            inside = !inside
        }
    }

    return inside
}

fun countPointsInsidePolygon(polygon: List<Line>): Int {
    val startLine = polygon.minBy { it.start.line }.start.line
    val endLine = polygon.maxBy { it.end.line }.end.line
    val startCol = polygon.minBy { it.start.col }.start.col
    val endCol = polygon.maxBy { it.end.col }.end.col

    var count = 0
    for (i in startLine..endLine) {
        for (j in startCol..endCol) {
            val isBorder = isPointABorder(i, j, polygon)
            if (isBorder) {
                print('*')
                count++
                continue
            }
            val isInside = isPointInsidePolygon(i, j, polygon)
            if (isInside) {
                print('#')
                count++
            } else {
                print('.')
            }
        }
        println()
    }

    return count
}

fun puzzle1(input: List<String>): Int {
    val graph = parseInputToGraph(input)
    return countPointsInsidePolygon(graph)
}

fun parseHex(hex: String): Pair<String, Int> {
    return hex.substring(hex.indexOf('#') + 1).let {
        Pair(
            when (val c = it.last()) {
                '0' -> "R"
                '1' -> "D"
                '2' -> "L"
                '3' -> "U"
                else -> throw IllegalArgumentException("Illegal arrgument $c")
            },
            it.substring(0, 5).toInt(radix = 16)
        )
    }
}

fun parseInputToGraph2(input: List<String>): List<Line> {
    val split = input.map { line -> line.split(' ') }
    val firstLine = split.first().let { (_, _, hexNumber) ->
        val (dir, steps) = parseHex(hexNumber.substring(hexNumber.indexOf('(') + 1, hexNumber.indexOf(')') ))
        makeLine(Point(0, 0), dir, steps) }
    return split.subList(1, split.size).fold(mutableListOf(firstLine)) { acc, (_, _, hexNumber)  ->
        val (dir, steps) = parseHex(hexNumber.substring(hexNumber.indexOf('(') + 1, hexNumber.indexOf(')')))
        val newLine = makeLine(acc.last.end, dir, steps)
        acc.apply {
            add(newLine)
        }
    }
}

/**
 * Works like a charm!
 * Thanks https://www.reddit.com/r/adventofcode/comments/18l2nk2/2023_day_18_easiest_way_to_solve_both_parts/ and
 * https://github.com/wilkotom/Aoc2023/blob/main/day18/src/main.rs
 */
fun shoelaceFormula(lines: List<Line>): Long {
    val perimeter = lines.fold(0L) { p, l -> p + max(abs(l.end.line - l.start.line), abs(l.end.col - l.start.col))}

    val points = listOf(lines.first.start) + lines.map { it.end }

    val area = abs(points.windowed(size = 2).fold(0L) { area, (p1, p2) ->
        area + (p1.line.toLong() * p2.col.toLong() - p2.line.toLong() * p1.col.toLong())
    } / 2)

    return area + (perimeter / 2) + 1
}



fun puzzle2(input: List<String>): Long {
    val graph = parseInputToGraph2(input)
    return shoelaceFormula(graph)
}

fun main() {
    println("Day 18")
    val input = readInput("day18")
    val time1 = measureTime {
        println("Puzzle 1 ${puzzle1(input)}")
    }
    println("Puzzle 1 took $time1")
    val time2 = measureTime {
        println("Puzzle 2 ${puzzle2(input)}")
    }
    println("Puzzle 2 took $time2")
}
