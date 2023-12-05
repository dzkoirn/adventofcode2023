package adventofcode2023.day5

import java.util.stream.Stream

fun main() {
    println("Day 5")
}

data class ParsedInput(
    val seeds: List<Long>,
    val mappers: List<Map<Long, Long>>,
)

fun parseInput(input: List<String>): ParsedInput {
    val seeds = input.first().split(':')
        .let { (_, numbers) -> numbers.split(' ')
            .filter { it.isNotEmpty() }
            .map { it.toLong() }
        }
    val mappers: List<Map<Long, Long>> = buildList {
        var map: MutableMap<Long, Long> = mutableMapOf()
        input.drop(2).forEach { line ->
            when {
                line.isEmpty() -> { add(map) }
                line.first().isLetter() -> { map = mutableMapOf() }
                else -> { line.split(' ').filter { it.isNotEmpty() }.map { it.toLong() } }
            }
        }
    }
    return ParsedInput(seeds, mappers)
}