package adventofcode2023.day19

import adventofcode2023.readInput
import kotlin.math.max
import kotlin.math.min
import kotlin.reflect.KProperty1
import kotlin.time.measureTime

fun main() {
    println("Day 19")
    val input = readInput("day19")
    val time1 = measureTime {
        println("Puzzle 1 ${puzzle1(input)}")
    }
    println("Puzzle 1 took $time1")

    val time2 = measureTime {
        println("Puzzle 2 ${puzzle2(input)}")
    }
    println("Puzzle 2 took $time2")
}

data class Part(
    val x: Int, val m: Int, val a: Int, val s: Int
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
    fun(p: Part): String? {
        return if (propertySupplier.get(p) > value) {
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
    fun(p: Part): String? {
        return if (propertySupplier.get(p) < value) {
            label
        } else {
            null
        }
    }

fun parseCommand(line: String): Pair<String, WorkFlow> {
    val openIndex = line.indexOf('{')
    val closesIndex = line.indexOf('}')
    val label = line.substring(0, openIndex)
    val rules = line.substring(openIndex + 1, closesIndex).split(',').map { it.trim() }

    val rulesList = rules.subList(0, rules.lastIndex).map { r ->
        r.split(':').let { (s1, ll) ->
            val (property, number) = s1.split('<', '>')
            val supplier = when (property) {
                "a" -> Part::a
                "x" -> Part::x
                "m" -> Part::m
                "s" -> Part::s
                else -> throw IllegalArgumentException("Could not handle $property")
            }
            if (s1.contains('<')) {
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

    return parts.filter { p -> verify(p, map) }.sumOf { p -> p.sum().toLong() }
}

fun verify(p: Part, flows: Map<String, WorkFlow>): Boolean {
    var key = "in"
    do {
        key = flows[key]!!.invoke(p)
    } while (key != "A" && key != "R")
    return key == "A"
}

fun puzzle2(input: List<String>): Long {
    val workflows = input.subList(0, input.indexOfFirst { it.isEmpty() }).map { parseCommand2(it) }
        .groupBy(keySelector = { (key, _) -> key }, valueTransform = { (_, v) -> v })
        .mapValues { it.value.first }

    return count(workflows)
}

data class Workflow2(
    val rules: List<Rule>,
    val final: String
) {
    data class Rule(
        val name: String,
        val action: String,
        val number: Int,
        val label: String
    )
}

fun parseCommand2(line: String): Pair<String, Workflow2> {
    val openIndex = line.indexOf('{')
    val closesIndex = line.indexOf('}')
    val label = line.substring(0, openIndex)
    val rules = line.substring(openIndex + 1, closesIndex).split(',').map { it.trim() }

    val rulesList = rules.subList(0, rules.lastIndex).map { r ->
        r.split(':').let { (s1, ll) ->
            val (property, number) = s1.split('<', '>')
            val symbol = if (s1.contains('<')) {
                "<"
            } else {
                ">"
            }
            Workflow2.Rule(property, symbol, number.toInt(), ll)
        }
    }
    return Pair(label, Workflow2(rulesList, rules.last))
}

fun Map<String, IntRange>.calculate() =
    this.values.fold(1L) { acc, intRange ->
        acc * (intRange.last - intRange.first + 1)
    }

fun count(
    workflows: Map<String, Workflow2>,
    data: Map<String, IntRange> = mapOf(
        "x" to IntRange(1, 4000), "m" to IntRange(1, 4000), "a" to IntRange(1, 4000), "s" to IntRange(1, 4000)
    ),
    key: String = "in"
): Long {
    if (key == "R") {
        return 0
    }
    if (key == "A") {
        val result = data.calculate()
        println("$data $result")
        return result
    }

    var total = 0L

    val currentData = data.toMutableMap()

    val (rules, final ) = workflows[key]!!
    for ((name, action, number, label) in rules) {
        val r = currentData[name]!!
        val (f, s) =  if (action == "<") {
            Pair(
                IntRange(r.first, min(r.last, number - 1)),
                IntRange(max(r.first, number), r.last)
            )
        } else {
            Pair(
                IntRange(max(r.first, number + 1), r.last),
                IntRange(r.first, min(r.last, number))
            )
        }
        if (f.first < f.last) {
            val newData = currentData.toMutableMap().apply {
                this[name] = f
            }
            total += count(workflows, newData, label)
        }
        if (s.first < s.last) {
            currentData[name] = s
        }
    }
    total += count(workflows, currentData, final)

    return total
}