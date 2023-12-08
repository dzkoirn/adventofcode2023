package adventofcode2023.day8

import adventofcode2023.readInput
import kotlin.time.measureTime

fun main() {
    println("Day 8")
    val input = readInput("day8")
    val puzzle1Time = measureTime {
        val (commands, graph) = parseInput(input)
        println("Puzzle 1: ${walkThought(commands.toCharArray(), graph)}")
    }
    println("Puzzle 1 took $puzzle1Time")
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

fun walkThought(commands: CharArray, graph: Map<String, Pair<String, String>>): Int {
    var counter = 0
    var key = "AAA"
    val iterator = object : Iterator<Char> {
        private var index = 0

        override fun hasNext() = true

        override fun next(): Char {
            return commands[index].also {
                index = (index + 1) % commands.size
            }
        }
    }
    do {
        key = with(graph[key]!!) {
            if(iterator.next() == 'L') {
                first
            } else {
                second
            }.also {
                counter++
            }
        }
    } while (key != "ZZZ")
    return counter
}