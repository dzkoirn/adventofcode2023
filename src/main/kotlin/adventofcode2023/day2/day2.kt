package adventofcode2023.day2

import adventofcode2023.readInput
import kotlin.math.max

fun main() {
    val input = readInput("day2.txt")
    println("Day 2")
    println("Puzzle 1: ${puzzle1(input, GameRecord(red = 12, green = 13, blue = 14))}")
    println("Puzzle 2: ${puzzle2(input)}")
}

data class GameRecord(
    val green: Int = 0,
    val red: Int = 0,
    val blue: Int = 0
)

fun puzzle1(input: List<String>, initial: GameRecord): Int {
    return input.map { l -> dummyLineParsing(l) }
        .filter { isGamePossible(initial, it.second) }
        .sumOf { (id, _) -> id }
}

fun puzzle2(input: List<String>): Int {
    return input.map { l -> dummyLineParsing(l) }
        .map { (_, records) -> calculateMinimalGameAmount(records) }
        .map(::calculatePower)
        .sum()
}

fun dummyLineParsing(line: String): Pair<Int, List<GameRecord>> {
    val (gameIdStr, colorsStr) = line.split(":")
    val gameId = gameIdStr.substring(5).toInt()
    val gameRecords = colorsStr.split(";").map { s ->
        s.split(",").map {
            val (v, color) = it.trim().split(" ")
            color to v.toInt()
        }.toMap()

    }.map { m ->
        GameRecord(
            green = m.getOrDefault("green", 0),
            red = m.getOrDefault("red", 0),
            blue = m.getOrDefault("blue", 0)
        )
    }
    return Pair(gameId, gameRecords)
}

fun isGamePossible(initial: GameRecord, input: List<GameRecord>): Boolean =
    !input.any { gr -> gr.green > initial.green || gr.blue > initial.blue || gr.red > initial.red }

fun calculateMinimalGameAmount(input: List<GameRecord>): GameRecord =
    input.reduce { acc, gr ->
        GameRecord(red = max(acc.red, gr.red), green = max(acc.green, gr.green), blue = max(acc.blue, gr.blue))
    }

fun calculatePower(gameRecord: GameRecord): Int = gameRecord.red * gameRecord.blue * gameRecord.green
