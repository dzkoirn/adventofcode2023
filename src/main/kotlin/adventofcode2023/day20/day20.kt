package adventofcode2023.day20

import adventofcode2023.readInput
import java.util.LinkedList
import java.util.Queue
import kotlin.time.measureTime

data class Signal(
    val level: SignalLevel,
    val source: String,
    val destination: String
) {
    enum class SignalLevel {
        LOW, HIGH;
    }
}

abstract class Module(
    val name: String,
    val destinations: Array<String>
) {
    open fun addSource(name: String) {}
    abstract fun sendSignal(signal: Signal): Collection<Signal>
}

class Broadcaster(
    name: String,
    destinations: Array<String>
) : Module(name, destinations) {
    override fun sendSignal(signal: Signal): Collection<Signal> {
        return destinations.map { Signal(signal.level, name, it) }
    }
}

class FlipFlop(
    name: String,
    destinations: Array<String>
) : Module(name, destinations) {

    private var isOn: Boolean = false

    override fun sendSignal(signal: Signal): Collection<Signal> {
        return when (signal.level) {
            Signal.SignalLevel.HIGH -> emptyList()
            Signal.SignalLevel.LOW -> {
                isOn = !isOn
                val outLevel = if (isOn) {
                    Signal.SignalLevel.HIGH
                } else {
                    Signal.SignalLevel.LOW
                }
                destinations.map { Signal(outLevel, name, it) }
            }
        }
    }
}

class Conjunction(
    name: String,
    destinations: Array<String>,
) : Module(name, destinations) {

    private val sources = mutableMapOf<String, Signal.SignalLevel>()
    override fun addSource(name: String) {
        sources[name] = Signal.SignalLevel.LOW
    }

    override fun sendSignal(signal: Signal): Collection<Signal> {
        sources[signal.source] = signal.level
        val outLevel = if (sources.values.all { it == Signal.SignalLevel.HIGH }) {
            Signal.SignalLevel.LOW
        } else {
            Signal.SignalLevel.HIGH
        }
        return destinations.map { Signal(outLevel, name, it) }
    }
}

fun parseInput(input: List<String>): Map<String, Module> {
    return input.associate { line ->
        line.split("->").map { s -> s.trim() }.let { (module, destinationsStr) ->
            val destinations = destinationsStr.split(",").map { s -> s.trim() }.toTypedArray()
            if (module == "broadcaster") {
                "broadcaster" to Broadcaster("broadcaster", destinations)
            } else if (module.startsWith("%")) {
                val key = module.substring(1)
                key to FlipFlop(key, destinations)
            } else {
                val key = module.substring(1)
                key to Conjunction(key, destinations)
            }
        }
    }.also { map ->
        map.values.forEach { m -> m.destinations.forEach { d -> map[d]?.addSource(m.name) } }
    }
}

fun pushButton(times: Int, schema: Map<String, Module>): Pair<Int, Int> {
    val queue: Queue<Signal> = LinkedList()

    var lowCounter: Int = 0
    var highCounter: Int = 0

    repeat(times) {
        queue.add(Signal(Signal.SignalLevel.LOW, "button", "broadcaster"))

        while (queue.isNotEmpty()) {
            val signal = queue.poll().also {
                if (it.level == Signal.SignalLevel.LOW) {
                    lowCounter++
                } else {
                    highCounter++
                }
            }
            val module = schema[signal.destination]
            if (module != null) {
                val out = module.sendSignal(signal)
                queue.addAll(out)
            }
        }
    }

    return Pair(lowCounter, highCounter)
}

fun puzzle1(input: List<String>): Int {
    val scheme = parseInput(input)
    return pushButton(1000, scheme).let { (low, high) -> low * high }
}

fun main() {
    println("Day 20")
    val input = readInput("day20")
    val time1 = measureTime {
        println("Puzzle 1 ${puzzle1(input)}")
    }
    println("Puzzle 1 took $time1")
}