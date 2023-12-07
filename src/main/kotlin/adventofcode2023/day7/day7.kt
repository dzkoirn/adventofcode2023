package adventofcode2023.day7

import adventofcode2023.readInput
import kotlin.time.measureTime

fun main() {
    val input = readInput("day7")
    println("Day 7")
    val puzzle1Time = measureTime {
        println("Puzzle 1: ${puzzle1(input)}")
    }
    println("Puzzle 1 took $puzzle1Time")
}

data class Hand(
    val cards: String,
    val bid: Int
)

fun prepareInput(input: List<String>): List<Hand> =
    input.map { it.split(' ').filter { it.isNotEmpty() }.let { (f, s) -> Hand(cards = f, bid = s.toInt()) } }

fun sortHands(input: List<Hand>): List<Hand> = input.sortedWith { first, second -> compareHands(first.cards, second.cards) }

private val sortedCard = arrayOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')

enum class HandType {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_KIND,
    FULL_HOUSE,
    FOUR_OF_KIND,
    FIVE_OF_KIND;
}

fun compareHands(first: String, second: String): Int {
    val firstHand = first.toHandType()
    val secondHand = second.toHandType()
    var comparison = firstHand.compareTo(secondHand)
    if (comparison == 0) {
        var index = 0
        do {
            comparison = compareCards(first[index], second[index])
            index++
        } while(comparison == 0 && index < 5)
    }
    return comparison
}

fun compareCards(first: Char, second: Char): Int = sortedCard.indexOf(second) - sortedCard.indexOf(first)

fun String.toHandType(): HandType {
    val mapped = this.groupBy { it }
    return when (mapped.keys.size) {
        5 -> HandType.HIGH_CARD
        4 -> HandType.ONE_PAIR
        3 -> {
            if (mapped.values.any { it.size == 3 }) {
                HandType.THREE_OF_KIND
            } else {
                HandType.TWO_PAIR
            }
        }
        2 -> {
            if (mapped.values.any { it.size == 4 }) {
                HandType.FOUR_OF_KIND
            } else {
                HandType.FULL_HOUSE
            }
        }
        1 -> HandType.FIVE_OF_KIND
        else -> {
            throw IllegalStateException()
        }
    }
}

fun puzzle1(input: List<String>): Long =
    sortHands(prepareInput(input)).foldIndexed(0L) { index: Int, acc: Long, hand: Hand -> acc + hand.bid * (index + 1) }