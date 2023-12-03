package adventofcode2023.day3

import adventofcode2023.readInput

fun main() {
    val input = readInput("day3")
    println("Day 3")
    println("Puzzle 1: ${puzzle1(input)}")
    println("Puzzle 2: ${puzzle2(input)}")
}

fun puzzle1(list: List<CharSequence>): Int {
    return findAllIntersections(list).sumOf { it.toInt() }
}

fun puzzle2(list: List<CharSequence>): Int {
    return findGears(list).map { pair -> pair.map { it.toInt() } }
        .map { (f, s) -> f * s }
        .sum()
}

fun findAllIntersections(list: List<CharSequence>): List<String> {
    return buildList {
        list.forEachIndexed { indexL, l ->
            l.forEachIndexed { indexC, c ->
                if (!c.isDigit() && c != '.') {
                    addAll(findIntersection(list, indexC, indexL))
                }
            }
        }
    }
}

fun findGears(list: List<CharSequence>): List<List<String>> {
    return buildList {
        list.forEachIndexed { indexL, l ->
            l.forEachIndexed { indexC, c ->
                if(c == '*') {
                    val intersections = findIntersection(list, indexC, indexL)
                    if (intersections.size == 2) {
                        add(intersections)
                    }
                }
            }
        }
    }
}

private fun findIntersection(
    list: List<CharSequence>,
    indexC: Int,
    indexL: Int,
): List<String> {
    val foundedDigits = mutableListOf<String>()

    fun lineSearch(lineIndex: Int, rowIndex: Int) {
        var startC = if (rowIndex > 0) {
            rowIndex - 1
        } else {
            rowIndex
        }
        if (startC > 0 && list[lineIndex][startC].isDigit()) {
            while (startC - 1 >= 0 && list[lineIndex][startC - 1].isDigit()) {
                startC--
            }
        }
        var endC = if (rowIndex < list[lineIndex].lastIndex) {
            rowIndex + 1
        } else {
            rowIndex
        }
        if (endC < list[lineIndex].lastIndex && list[lineIndex][endC].isDigit()) {
            while (endC + 1 <= list[lineIndex].lastIndex && list[lineIndex][endC + 1].isDigit()) {
                endC++
            }
        }
        list[lineIndex].substring(startC, endC + 1)
            .split('.')
            .filter { it.isNotEmpty() }
            .let { foundedDigits.addAll(it) }
    }


    if (indexC > 0 && list[indexL][indexC - 1].isDigit()) {
        var indexCC = indexC - 1
        while (indexCC >= 0 && list[indexL][indexCC].isDigit()) {
            indexCC--
        }
        list[indexL].substring(indexCC + 1, indexC)
            .takeIf { it.isNotEmpty() }?.let { foundedDigits.add(it) }
    }
    if (indexC < list[indexL].length && list[indexL][indexC + 1].isDigit()) {
        var indexCC = indexC + 1
        while (indexCC < list[indexL].length && list[indexL][indexCC].isDigit()) {
            indexCC++
        }
        list[indexL].substring(indexC + 1, indexCC)
            .takeIf { it.isNotEmpty() }?.let { foundedDigits.add(it) }
    }
    if (indexL > 0) {
        lineSearch(indexL - 1, indexC)
    }
    if (indexL < list.lastIndex) {
        lineSearch(indexL + 1, indexC)
    }
    return foundedDigits
}
