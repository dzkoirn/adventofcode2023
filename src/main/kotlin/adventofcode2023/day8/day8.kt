package adventofcode2023.day8

import adventofcode2023.readInput
import kotlin.math.abs
import kotlin.time.measureTime

fun main() {
    println("Day 8")
    val input = readInput("day8")
    val puzzle1Time = measureTime {
        val (commands, graph) = parseInput(input)
        println("Puzzle 1: ${puzzle1(commands.toCharArray(), graph)}")
    }
    println("Puzzle 1 took $puzzle1Time")
    val puzzle2Time = measureTime {
        val (commands, graph) = parseInput(input)
        println("Puzzle 2: ${puzzle2(commands.toCharArray(), graph)}")
    }
    println("Puzzle 2 took $puzzle2Time")
}


fun parseInput(input: List<String>): Pair<String, Map<String, Pair<String, String>>> {
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

class LoopedIterator(
    private val commands: CharArray
) : Iterator<Char> {
    private var index = 0

    override fun hasNext() = true

    override fun next(): Char {
        return commands[index].also {
            index = (index + 1) % commands.size
        }
    }
}

fun walkThought(
    commands: CharArray,
    graph: Map<String, Pair<String, String>>,
    startKey: String,
    predicate: (String) -> Boolean
): Int {
    var counter = 0
    var key = startKey
    val iterator = LoopedIterator(commands)
    do {
        key = with(graph[key]!!) {
            if (iterator.next() == 'L') {
                first
            } else {
                second
            }.also {
                counter++
            }
        }
    } while (predicate(key))
    return counter
}

fun puzzle1(commands: CharArray, graph: Map<String, Pair<String, String>>) =
    walkThought(commands, graph, "AAA") { endKey -> endKey != "ZZZ"}

//TODO NOK
fun puzzle2(commands: CharArray, graph: Map<String, Pair<String, String>>): Long {
     return graph.keys.stream().filter { it.last() == 'A' }
        .parallel()
        .map { start -> walkThought(commands, graph, start) { it.last() != 'Z' } }
        .map { it.toLong() }
        .reduce(::findLCM)
        .get()
}

private fun findLCM(a: Long, b: Long): Long {
    if (a == 0L || b == 0L) {
        return 0
    }

    return abs(a * b) / findGCD(a, b)
}

private fun findGCD(a: Long, b: Long): Long {
    var num1 = a
    var num2 = b

    while (num2 != 0L) {
        val temp = num2
        num2 = num1 % num2
        num1 = temp
    }

    return num1
}