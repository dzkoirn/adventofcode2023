package adventofcode2023.day5

import adventofcode2023.readInput

fun main() {
    val input = readInput("day5")
    println("Day 5")
    println("Puzzle 1: ${puzzle1(input)}")
}

data class ParsedInput(
    val seeds: List<Long>,
    val mappers: List<Mapper>,
) {

    data class Mapper(
        val records: List<MapRecord>
    ) {
        data class MapRecord(
            val destinationIndex: Long,
            val sourceIndex: Long,
            val range: Int
        )
    }
}

fun parseInput(input: List<String>): ParsedInput {
    val seeds = input.first().split(':')
        .let { (_, numbers) ->
            numbers.split(' ')
                .filter { it.isNotEmpty() }
                .map { it.toLong() }
        }
    val mappers: List<ParsedInput.Mapper> = buildList {
        var records: MutableList<ParsedInput.Mapper.MapRecord> = mutableListOf()
        input.drop(2).forEach { line ->
            when {
                line.isEmpty() -> {
                    add(ParsedInput.Mapper(records))
                }

                line.first().isLetter() -> {
                    records = mutableListOf()
                }

                else -> {
                    line.split(' ')
                        .filter { it.isNotEmpty() }
                        .map { it.toLong() }
                        .let { (dI, sI, r) ->
                            ParsedInput.Mapper.MapRecord(
                                destinationIndex = dI,
                                sourceIndex = sI,
                                range = r.toInt()
                            )
                        }.let { records.add(it) }
                }
            }
        }
        add(ParsedInput.Mapper(records))
    }
    return ParsedInput(seeds, mappers)
}

fun ParsedInput.Mapper.map(value: Long): Long {
    val mapRecord = records.find { value in LongRange(it.sourceIndex, it.sourceIndex + it.range) }
    return mapRecord?.let { it.destinationIndex + (value - it.sourceIndex) } ?: value
}

fun puzzle1(input: List<String>): Long {
    val parsedInput = parseInput(input)
    return parsedInput.seeds.map { s ->
        parsedInput.mappers.fold(s) { s, mapper -> mapper.map(s) }
    }.min()
}