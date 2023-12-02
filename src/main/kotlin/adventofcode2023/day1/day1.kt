package adventofcode2023.day1

import adventofcode2023.readInput

fun main() {
    val list = readInput("day1.txt")
    println("Day 1")
    println("Puzzle 1 : ${puzzle1(list)}")
    println("Puzzle 2 : ${puzzle2(list)}")
}

fun puzzle1(input: List<String>): Int {
    return input.sumOf { l -> l.first(Char::isDigit).digitToInt() * 10 + l.last(Char::isDigit).digitToInt() }
}

fun puzzle2(input: List<String>): Int = input.map(::processLine).sum()

private val wordsMapping = arrayOf(
    "one" to 1,
    "two" to 2,
    "three" to 3,
    "four" to 4,
    "five" to 5,
    "six" to 6,
    "seven" to 7,
    "eight" to 8,
    "nine" to 9
)

fun processLine(line: String): Int {
    var firstIndexWord = -1
    var firstValueWord = 0
    var lastIndexWord = -1
    var lastValueWord = 0
    wordsMapping.forEach { (w, v) ->
        val i = line.indexOf(w)
        if (i > -1 && (firstIndexWord == -1 || i < firstIndexWord)) {
            firstIndexWord = i
            firstValueWord = v
        }
        val li = line.lastIndexOf(w)
        if (li > -1 && (lastIndexWord == -1 || li > lastIndexWord)) {
            lastIndexWord = li
            lastValueWord = v
        }
    }

    val firstIndexDigit = line.indexOfFirst(Char::isDigit)

    val firstDigit = if (firstIndexDigit > -1 && (firstIndexWord == -1 || firstIndexDigit < firstIndexWord)) {
        line[firstIndexDigit].digitToInt()
    } else {
        firstValueWord
    }

    val lastIndexDigit = line.indexOfLast(Char::isDigit)

    val secondDigit = if (lastIndexDigit > lastIndexWord) {
        line[lastIndexDigit].digitToInt()
    } else {
        lastValueWord
    }

    return firstDigit * 10 + secondDigit
}