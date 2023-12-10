package adventofcode2023.day10

import adventofcode2023.*
import kotlin.time.measureTime

fun main() {
    println("Day 10")
    val input = readInput("day10")
    val puzzle1Timing = measureTime {
        println("Puzzle 1 ${puzzle1(input)}")
    }
    println("Puzzle 1 took $puzzle1Timing")
}

fun findStart(field:List<CharSequence>): Point {
    var l: Int = 0
    var r: Int = 0
    field.forEachIndexed { lineIndex, line ->
        val rowIndex = line.indexOf('S')
        if (rowIndex > -1) {
            l = lineIndex
            r = rowIndex
        }
    }
    return Point(l, r)
}

tailrec fun findPath(field: List<CharSequence>, point: Point, path: MutableSet<Point> = mutableSetOf()): Set<Point> {
    path.add(point)
    val pointsAround = point.pointsAround().filter { (l, r) -> l >= 0 && r >= 0 && l <= field.lastIndex && r <= field[l].lastIndex }
    val connectedPoints = pointsAround.filter { isPointsConnected(field, point, it) }
    val nextPoint = connectedPoints.find { it !in path }
    if (nextPoint == null) {
        return path
    }
//    println("NextPoint $nextPoint, ${field[nextPoint.line][nextPoint.row]}")
    return findPath(field, nextPoint, path)
}

fun isPointsConnected(field: List<CharSequence>, first: Point, second: Point): Boolean {
    val f = field[first.line][first.row]
    val s = field[second.line][second.row]
    val diff = Pair(second.line - first.line, second.row - first.row)
    return when {
        diff.line == 0 && diff.row == -1 -> f in "S-7J" && s in "S-FL"
        diff.line == -1 && diff.row == 0 -> f in "SJ|L" && s in "SF|7"
        diff.line == 0 && diff.row == 1 -> f in "S-FL" && s in "S-J7"
        diff.line == 1 && diff.row == 0 -> f in "S|F7" && s in "S|JL"
        else -> false
    }
}

fun puzzle1(input: List<String>): Int {
    val startPoint = findStart(field = input)
    val path = findPath(input, startPoint)
    return path.size/2
}