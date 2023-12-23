package adventofcode2023.day17

import adventofcode2023.Point
import adventofcode2023.pointsAround

fun main() {
    val input = listOf(
        intArrayOf(1, 2, 3),
        intArrayOf(4, 5, 6),
        intArrayOf(7, 8, 9)
    )

    val minSumPath = findMinSumPath(input)
}

fun prepareInput(input: List<String>): List<IntArray> =
    input.map { l -> l.map { c -> "$c".toInt() }.toIntArray() }

fun sumPath(input: List<IntArray>, path: List<Point>): Int =
    path.sumOf { (line, row) -> input[line][row] }

fun visualize(input: List<IntArray>, path: List<Point>): String {
    val a = input.map { arr -> arr.joinToString("") { it.toString() }.toCharArray() }
    path.forEach { (line, row) -> a[line][row] = '#' }
    return a.map { it.joinToString("") { "$it" } }.joinToString("\n") { it }
}

fun isValidMove(x: Int, y: Int, rows: Int, cols: Int): Boolean {
    return x in 0 until rows && y in 0 until cols
}

fun findMinSumPath(input: List<IntArray>): List<Point> {
    val rows = input.size
    val cols = input[0].size
    val directions = listOf(Point(0, 1), Point(1, 0), Point(0, -1), Point(-1, 0))

    fun dfs(x: Int, y: Int, visited: Set<Point>, currentPath: List<Point>): List<Point> {
        if (x == rows - 1 && y == cols - 1) {
            return currentPath
        }

        var minPath: List<Point>? = null
        for (dir in directions) {
            val newX = x + dir.first
            val newY = y + dir.second

            if (isValidMove(newX, newY, rows, cols) && Point(newX, newY) !in visited) {
                val newPath = currentPath + Point(newX, newY)
                val newVisited = visited + Point(newX, newY)

                val resultPath = dfs(newX, newY, newVisited, newPath)
                println(resultPath)

                if (minPath == null || getPathSum(input, resultPath) < getPathSum(input, minPath)) {
                    minPath = resultPath
                }
            }
        }

        return minPath ?: emptyList()
    }

    return dfs(0, 0, setOf(Point(0, 0)), listOf(Point(0, 0)))
}

fun getPathSum(input: List<IntArray>, path: List<Point>): Int {
    return path.sumBy { (x, y) -> input[x][y] }
}



//fun findMinSumPath(input: List<IntArray>): List<Point> {
//    val rows = input.size
//    val cols = input[0].size
//
//    // Initialize a 2D array to store the minimum sum values
//    val minSum = Array(rows) { IntArray(cols) }
//
//    // Initialize the first cell with its own value
//    minSum[0][0] = input[0][0]
//
//    // Initialize the first row and column
//    for (i in 1 until rows) {
//        minSum[i][0] = minSum[i - 1][0] + input[i][0]
//    }
//    for (j in 1 until cols) {
//        minSum[0][j] = minSum[0][j - 1] + input[0][j]
//    }
//
//    // Fill the rest of the minSum array
//    for (i in 1 until rows) {
//        for (j in 1 until cols) {
//            minSum[i][j] = input[i][j] + minOf(minSum[i - 1][j], minSum[i][j - 1])
//        }
//    }
//
//    // Reconstruct the path
//    val path = mutableListOf<Point>()
//    var i = rows - 1
//    var j = cols - 1
//
//    while (i > 0 || j > 0) {
//        path.add(Point(i, j))
//        if (i == 0) {
//            j--
//        } else if (j == 0) {
//            i--
//        } else {
//            if (minSum[i - 1][j] < minSum[i][j - 1]) {
//                i--
//            } else {
//                j--
//            }
//        }
//    }
//
//    path.add(Point(0, 0))
//    path.reverse()
//
//    return path
//}

