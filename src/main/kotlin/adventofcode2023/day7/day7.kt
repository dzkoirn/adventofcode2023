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

    val puzzle2Time = measureTime {
        println("Puzzle 2: ${puzzle2(input)}")
    }
    println("Puzzle 2 took $puzzle2Time")
}

data class Hand(
    val cards: String,
    val bid: Int
)

fun prepareInput(input: List<String>): List<Hand> =
    input.map { it.split(' ').filter { it.isNotEmpty() }.let { (f, s) -> Hand(cards = f, bid = s.toInt()) } }

fun sortHands(
    input: List<Hand>,
    toHandType: (String) -> HandType = ::toHandTypeV1,
    sortedCards: CharArray = sortedCardsv1
): List<Hand> =
    input.sortedWith { first, second -> compareHands(first.cards, second.cards, toHandType, sortedCards) }

val sortedCardsv1 = charArrayOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')

enum class HandType {
    HIGH_CARD,
    ONE_PAIR,
    TWO_PAIR,
    THREE_OF_KIND,
    FULL_HOUSE,
    FOUR_OF_KIND,
    FIVE_OF_KIND;
}

fun compareHands(
    first: String,
    second: String,
    toHandType: (String) -> HandType,
    sortedCards: CharArray
): Int {
    val firstHand = toHandType(first)
    val secondHand = toHandType(second)
    var comparison = firstHand.compareTo(secondHand)
    if (comparison == 0) {
        var index = 0
        do {
            comparison = compareCards(first[index], second[index], sortedCards)
            index++
        } while (comparison == 0 && index < 5)
    }
    return comparison
}

fun compareCards(first: Char, second: Char, sortedCards: CharArray): Int =
    with(sortedCards) {
        indexOf(second) - indexOf(first)
    }

fun toHandTypeV1(hand: String): HandType {
    val mapped = hand.groupBy { it }
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

val sortedCardsv2 = charArrayOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')

fun toHandTypeV2(hand: String): HandType {
    val mapped = hand.groupBy { it }
    if (mapped.keys.size == 1) {
        return HandType.FIVE_OF_KIND
    }
    val tempHand = if (mapped.keys.contains('J')) {
        val char = mapped.entries.filter { it.key != 'J' }.sortedWith { f, s ->
            val comparison = f.value.size - s.value.size
            if (comparison == 0) {
                compareCards(f.key, s.key, sortedCardsv2)
            } else {
                comparison
            }
        }.last.key
        hand.replace('J', char)
    } else {
        hand
    }
    return toHandTypeV1(tempHand)
}

fun puzzle2(input: List<String>): Long =
    sortHands(prepareInput(input), ::toHandTypeV2, sortedCardsv2).foldIndexed(0L) { index: Int, acc: Long, hand: Hand -> acc + hand.bid * (index + 1) }