package adventofcode2023.day21

import adventofcode2023.*
import kotlin.time.measureTime

fun main() {
    println("Day 21")
    val input = readInput("day21")
    val time1 = measureTime {
        val result = walk(input, 64)
        println("Puzzle 1 ${result.size}")
    }
    println("Puzzle 1 took $time1")
}

fun findStart(garden: List<CharSequence>): Point {
    for ((indexL, line) in garden.withIndex()) {
        for ((indexCol, c) in line.withIndex()) {
            if (c == 'S') {
                return Point(indexL, indexCol)
            }
        }
    }
    throw IllegalStateException("S not found")
}

tailrec fun walk(
    garden: List<CharSequence>,
    steps: Int,
    front: Set<Point> = setOf(findStart(garden)),
): Set<Point> {
    if (steps == 0) {
        return front
    }
    val newFront = front.asSequence()
        .flatMap { p -> p.pointsAround() }
        .filter { p -> p.line in (0..garden.size) && p.col in (0..garden.first.length) }
        .filter { p -> garden[p.line][p.col] != '#' }
        .toSet()
    return walk(garden, steps - 1, newFront)
}