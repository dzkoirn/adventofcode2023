package adventofcode2023.day4

import adventofcode2023.readInput

typealias Card = Pair<List<Int>, List<Int>>

val Card.winning: List<Int>
    get() = this.first

val Card.myNumbers: List<Int>
    get() = this.second

fun main() {
    val input = readInput("day4")
    println("Day 4")
    println("Puzzle 1: ${puzzle1(input)}")
}

fun parseLine(line: String): Card {
    fun String.toNumbers(): List<Int> = this.split(' ').filter { it.isNotEmpty() }.map { it.toInt() }
    return line.substring(line.indexOf(':') + 1).split('|').let {(left, right) ->
        Card(left.trim().toNumbers(), right.trim().toNumbers())
    }
}

fun findWinningNumber(card: Card): List<Int> = card.myNumbers.filter { it in card.winning }

fun calculatePoints(list: List<Int>): Int {
    if (list.isEmpty()) return 0
    var initial = 1
    repeat(list.size - 1) {
        initial *= 2
    }
    return initial
}

fun puzzle1(input: List<String>): Int = input.map { l -> parseLine(l) }
    .map { findWinningNumber(it) }
    .map { calculatePoints(it) }
    .sum()