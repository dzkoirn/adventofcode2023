package adventofcode2023.day18

import adventofcode2023.Point
import adventofcode2023.line
import adventofcode2023.col
import adventofcode2023.readInput
import kotlin.math.max
import kotlin.math.min
import kotlin.time.measureTime

data class Line(
    val start: Point,
    val end: Point,
    val color: String
)

fun makeLine(prevPoint: Point, direction: String, steps: Int, color: String): Line {
    val newPoint = when(direction) {
        "U" -> prevPoint.copy(first = prevPoint.line - steps)
        "R" -> prevPoint.copy(second = prevPoint.col + steps)
        "D" -> prevPoint.copy(first = prevPoint.line + steps)
        "L" -> prevPoint.copy(second = prevPoint.col - steps)
        else -> throw IllegalArgumentException("Could not handle $direction")
    }
    return Line(prevPoint, newPoint, color)
}

fun parseInputToGraph(input: List<String>): List<Line> {
    val split = input.map { line -> line.split(' ') }
    val firstLine = split.first().let { (dir, steps, color) -> makeLine(Point(0, 0), dir, steps.toInt(), color) }
    return split.subList(1, split.size).fold(mutableListOf(firstLine)) { acc, (dir, steps, color)  ->
        val newLine = makeLine(acc.last.end, dir, steps.toInt(), color)
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
        pLine in (min(line.start.line,line.end.line)..max(line.start.line,line.end.line)) &&
        pCol in (min(line.start.col,line.end.col)..max(line.start.col, line.end.col))
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

fun main() {
    println("Day 18")
    val input = readInput("day18")
    val time1 = measureTime {
        println("Puzzle 1 ${puzzle1(input)}")
    }
    println("Puzzle 1 took $time1")
}
