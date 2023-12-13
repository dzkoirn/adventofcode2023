package adventofcode2023.day12

fun puzzle1(input: List<String>): Int {
    TODO()
}

fun countArrangements(line: String): Int {
    val (p,checkSum) = parseLine(line)
    TODO()
}

fun parseLine(line: String): Pair<String, IntArray> =
    line.split(' ').let { (first, second) ->
        Pair(
            first,
            second.split(',').map { it.toInt() }.toTypedArray().toIntArray()
        )
    }

fun findArrangements(pattern: String, checkSum: IntArray): Collection<String> {
    TODO()
}