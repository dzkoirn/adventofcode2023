package adventofcode2023.day3

import adventofcode2023.readInput

fun main() {
    val input = readInput("day3")
    println("Day 3")
    println("Puzzle 1: ${puzzle1(input)}")
}

fun puzzle1(list: List<CharSequence>): Int {
    return findAllIntersections(list).sumOf { it.toInt() }
}

fun findAllIntersections(list: List<CharSequence>): List<String> {
    val foundedDigits = mutableListOf<String>()

    fun lineSearch(lineIndex: Int, rowIndex: Int) {
        var startC = if (rowIndex > 1) {
            rowIndex - 1
        } else {
            rowIndex
        }
        while (startC > 0 && list[lineIndex][startC].isDigit()) {
            startC--
        }
        var endC = if (rowIndex < list[lineIndex].lastIndex) {
            rowIndex + 1
        } else {
            rowIndex
        }
        while (endC < list[lineIndex].lastIndex && list[lineIndex][endC].isDigit()) {
            endC++
        }
        list[lineIndex].substring(startC, endC)
            .split('.')
            .filter { it.isNotEmpty() }
            .let { foundedDigits.addAll(it) }
    }

    list.forEachIndexed { indexL, l ->
        l.forEachIndexed { indexC, c ->
            if(!c.isDigit() && c != '.') {
                if (indexC > 0 && list[indexL][indexC - 1].isDigit()) {
                    var indexCC = indexC - 1
                    while (indexCC > 0 && list[indexL][indexCC].isDigit()) {
                        indexCC--
                    }
                    foundedDigits.add(list[indexL].substring(indexCC, indexC))
                }
                if (indexC < l.length && list[indexL][indexC + 1].isDigit()) {
                    var indexCC = indexC + 1
                    while (indexCC < l.length && list[indexL][indexCC].isDigit()) {
                        indexCC++
                    }
                    foundedDigits.add(list[indexL].substring(indexC + 1, indexCC))
                }
                if (indexL > 0) {
                    lineSearch(indexL - 1, indexC)
                }
                if (indexL < list.size) {
                    lineSearch(indexL + 1, indexC)
                }
            }
        }
    }
    return foundedDigits
}