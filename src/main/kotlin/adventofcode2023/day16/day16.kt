package adventofcode2023.day16

import adventofcode2023.Point
import adventofcode2023.line
import adventofcode2023.readInput
import adventofcode2023.col
import kotlin.time.measureTime


fun main() {
    println("day 16")
    val input = readInput("day16")
    val time1 = measureTime {
        val output = findEnergizedPoints(input.map { it.toCharArray() })
        println("Puzzle 1 ${output.size}")
    }
    println("Puzzle took $time1")

    println("Puzzle 2")
    val time2 = measureTime {
        val output = findBestStartPoint(input)
        println("Puzzle 2 $output")
    }
    println("Puzzle 2 took $time2")
}

data class RayHeader(
    val point: Point,
    val direction: Direction
) {
    enum class Direction {
        UP, LEFT, DOWN, RIGHT
    }
}

fun handlePoint(rayHeader: RayHeader, char: Char): List<RayHeader> {
    return when (char) {
        '\\' -> listOf(
            with(rayHeader.point) {
                when(rayHeader.direction) {
                    RayHeader.Direction.UP -> RayHeader(copy(second = col - 1), RayHeader.Direction.LEFT)
                    RayHeader.Direction.LEFT -> RayHeader(copy(first = line - 1), RayHeader.Direction.UP)
                    RayHeader.Direction.DOWN -> RayHeader(copy(second = col + 1), RayHeader.Direction.RIGHT)
                    RayHeader.Direction.RIGHT -> RayHeader(copy(first = line + 1), RayHeader.Direction.DOWN)
                }
            }
        )
        '/' -> listOf(
            with(rayHeader.point) {
                when(rayHeader.direction) {
                    RayHeader.Direction.UP -> RayHeader(copy(second = col + 1), RayHeader.Direction.RIGHT)
                    RayHeader.Direction.LEFT -> RayHeader(copy(first = line + 1), RayHeader.Direction.DOWN)
                    RayHeader.Direction.DOWN -> RayHeader(copy(second = col - 1), RayHeader.Direction.LEFT)
                    RayHeader.Direction.RIGHT -> RayHeader(copy(first = line - 1), RayHeader.Direction.UP)
                }
            }
        )
        '-' -> with(rayHeader.point) {
            buildList {
                when(rayHeader.direction) {
                    RayHeader.Direction.LEFT -> add(RayHeader(copy(second = col - 1), RayHeader.Direction.LEFT))
                    RayHeader.Direction.RIGHT -> add(RayHeader(copy(second = col + 1), RayHeader.Direction.RIGHT))
                    RayHeader.Direction.DOWN, RayHeader.Direction.UP -> {
                        add(RayHeader(copy(second = col - 1), RayHeader.Direction.LEFT))
                        add(RayHeader(copy(second = col + 1), RayHeader.Direction.RIGHT))
                    }
                }
            }
        }
        '|' -> with(rayHeader.point) {
            buildList {
                when(rayHeader.direction) {
                    RayHeader.Direction.UP -> add(RayHeader(copy(first = line - 1), RayHeader.Direction.UP))
                    RayHeader.Direction.DOWN -> add(RayHeader(copy(first = line + 1), RayHeader.Direction.DOWN))
                    RayHeader.Direction.LEFT,
                    RayHeader.Direction.RIGHT -> {
                        add(RayHeader(copy(first = line - 1), RayHeader.Direction.UP))
                        add(RayHeader(copy(first = line + 1), RayHeader.Direction.DOWN))
                    }
                }
            }
        }
        else -> listOf(
            RayHeader(
                with(rayHeader.point) {
                    when(rayHeader.direction) {
                        RayHeader.Direction.UP -> copy(first = line - 1)
                        RayHeader.Direction.LEFT -> copy(second = col - 1)
                        RayHeader.Direction.DOWN -> copy(first = line + 1)
                        RayHeader.Direction.RIGHT -> copy(second = col + 1)
                    }
                },
                rayHeader.direction
            )
        )
    }
}

tailrec fun findEnergizedPoints(
    input: List<CharArray>,
    rayHeaders: List<RayHeader> = listOf(RayHeader(Point(0,0), RayHeader.Direction.RIGHT)),
    visitedPoints: MutableSet<RayHeader> = mutableSetOf()
): Set<Point> {
    if (rayHeaders.isEmpty()) return visitedPoints.map { (p,_) -> p }.toSet()
    visitedPoints.addAll(rayHeaders)
    val newRayHeaders = rayHeaders.flatMap { rh ->
        val ch = input[rh.point.line][rh.point.col]
        handlePoint(rh, ch)
    }.filter { (p, _) -> p.line in (0..input.lastIndex) && p.col in (0..input.first.lastIndex) }
    .filter { it !in visitedPoints }
    return findEnergizedPoints(input, newRayHeaders, visitedPoints)
}

fun visualize(input: List<CharArray>, points: Set<Point>): List<CharArray> {
    points.forEach { (line, row) ->
        input[line][row] = '#'
    }
    return input
}

fun findBestStartPoint(input: List<String>): Int {
    val preparedInput = input.map { it.toCharArray() }
    return listOf(
        Array(input.size) { index -> RayHeader(Point(index, 0), RayHeader.Direction.RIGHT) },
        Array(input.size) { index -> RayHeader(Point(index, input.first.lastIndex), RayHeader.Direction.LEFT) },
        Array(input.first.length) { index -> RayHeader(Point(0, index), RayHeader.Direction.DOWN) },
        Array(input.first.length) { index -> RayHeader(Point(input.lastIndex, index), RayHeader.Direction.UP) },
    ).flatMap { it.toList() }
    .stream().parallel().map { start ->
        findEnergizedPoints(preparedInput, listOf(start)).size
    }.max(Comparator.naturalOrder()).get()
}