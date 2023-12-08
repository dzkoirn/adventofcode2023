package adventofcode2023.day8

import adventofcode2023.readInput

fun main() {
    println("Day 8")
    val input = readInput("day8")
}


fun parseInput(input:List<String>): Pair<String, Map<String, Pair<String, String>>> {
    val commands = input[0]

    val map = buildMap<String, Pair<String, String>> {
        input.drop(2).forEach { line ->
            line.split('=').filter { it.isNotEmpty() }.let { (key, directions) ->
                val pair = directions.trim().substring(directions.indexOf('('), directions.indexOf(')') - 1)
                    .split(',')
                    .filter { it.isNotEmpty() }
                    .let { (left, right) -> left.trim() to right.trim() }
                put(key.trim(), pair)
            }
        }
    }

    return Pair(commands, map)
}

fun walkThought(commands: String, graph: Map<String, Pair<String, String>>): Int {
    TODO()
}