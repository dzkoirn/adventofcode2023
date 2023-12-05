package adventofcode2023.day5

import adventofcode2023.readInput
import java.util.stream.LongStream
import kotlin.time.measureTime

fun main() {
    val input = readInput("day5")
    println("Day 5")
    val duration1 = measureTime {
        println("Puzzle 1: ${puzzle1(input)}")
    }
    println(duration1)

    println("Puzzle 2 started")
    val duration2 = measureTime {
        println("Puzzle 2: ${puzzle2dummy(input)}")
    }
    println(duration2)

    println("Puzzle 2 started")
    val duration2Parallel = measureTime {
        println("Puzzle 2 Parallel: ${puzzle2dummyParallel(input)}")
    }
    println(duration2Parallel)
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

fun puzzle2dummy(input: List<String>): Long {
    val parsedInput = parseInput(input)
    var minimalValue = Long.MAX_VALUE
    parsedInput.seeds.windowed(size = 2, step = 2).forEach { (s, r) ->
        (s..(s + r)).forEach {
            val v = parsedInput.mappers.fold(it) { s, mapper -> mapper.map(s) }
            if (minimalValue > v) {
                minimalValue = v
            }
        }
    }
    return minimalValue
}

fun puzzle2dummyParallel(input: List<String>): Long {
    val parsedInput = parseInput(input)
    return parsedInput.seeds.windowed(size = 2, step = 2)
        .stream()
        .parallel()
        .flatMapToLong { (s, r) -> LongStream.range(s, s + r) }
        .map { parsedInput.mappers.fold(it) { s, mapper -> mapper.map(s) } }
        .min()
        .asLong
}

