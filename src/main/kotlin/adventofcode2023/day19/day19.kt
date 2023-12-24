package adventofcode2023.day19

import adventofcode2023.readInput
import kotlin.reflect.KProperty1
import kotlin.time.measureTime

fun main() {
    println("Day 19")
    val input = readInput("day19")
    val time1 = measureTime {
        println("Puzzle 1 ${puzzle1(input)}")
    }
    println("Puzzle 1 took $time1")
}

data class Part(
    val x: Int, val m: Int, val a:Int, val s:Int
) {
    fun sum(): Int = x + m + a + s
}

fun parseInput(input: List<String>): Pair<Map<String, WorkFlow>, List<Part>> {
    val emptyLineIndex = input.indexOfFirst { it.isEmpty() }

    val map = input.subList(0, emptyLineIndex).map { parseCommand(it) }
        .groupBy(keySelector = { (key, _) -> key }, valueTransform = { (_, v) -> v })
        .mapValues { it.value.first }

    val commands = input.subList(emptyLineIndex + 1, input.size).map {
        parsePart(it)
    }

    return Pair(map, commands)
}

data class WorkFlow(
    val rules: List<(Part) -> String?>,
    val final: String
) {
    fun invoke(p: Part): String {
        for (r in rules) {
            val res = r(p)
            if (res != null) {
                return res
            }
        }
        return final
    }
}

fun buildMoreRule(
    propertySupplier: KProperty1<Part, Int>,
    value: Int,
    label: String
): (Part) -> String? =
    fun (p: Part): String? {
        return if(propertySupplier.get(p) > value) {
            label
        } else {
            null
        }
    }

fun buildLessRule(
    propertySupplier: KProperty1<Part, Int>,
    value: Int,
    label: String
): (Part) -> String? =
    fun (p: Part): String? {
        return if(propertySupplier.get(p) < value) {
            label
        } else {
            null
        }
    }

fun parseCommand(line: String) : Pair<String, WorkFlow> {
    val openIndex = line.indexOf('{')
    val closesIndex = line.indexOf('}')
    val label = line.substring(0, openIndex)
    val rules = line.substring(openIndex + 1, closesIndex).split(',').map { it.trim() }

    val rulesList = rules.subList(0, rules.lastIndex).map { r ->
        r.split(':').let { (s1, ll) ->
            val (property, number) = s1.split('<', '>')
            val supplier = when(property) {
                "a" -> Part::a
                "x" -> Part::x
                "m" -> Part::m
                "s" -> Part::s
                else -> throw IllegalArgumentException("Could not handle $property")
            }
            if(s1.contains('<')) {
                buildLessRule(supplier, number.toInt(), ll)
            } else {
                buildMoreRule(supplier, number.toInt(), ll)
            }
        }
    }
    return Pair(label, WorkFlow(rulesList, rules.last))
}

fun parsePart(line: String): Part {
    val argMap = line.substring(line.indexOf('{') + 1, line.indexOf('}'))
        .split(',')
        .map { it.trim() }.associate { it.split('=').let { (name, value) -> name to value.toInt() } }
    return Part(a = argMap["a"]!!, x = argMap["x"]!!, m = argMap["m"]!!, s = argMap["s"]!!)
}

fun puzzle1(input: List<String>): Long {
    val (map, parts) = parseInput(input)

    return parts.filter { p ->
        var key = "in"
        do {
            key = map[key]!!.invoke(p)
        } while (key != "A" && key != "R")
        key == "A"
    }.sumOf { p -> p.sum().toLong() }
}