package adventofcode2023

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

fun openResourseAsStream(resourceName: String): InputStream =
    requireNotNull(object {}.javaClass.classLoader.getResourceAsStream(resourceName))

fun InputStream.asReader() =
    BufferedReader(InputStreamReader(BufferedInputStream(this)))

fun readInput(resourceName: String): List<String> =
    openResourseAsStream(resourceName).asReader().use { reader ->
        reader.lines().toList()
    }

typealias Point = Pair<Int, Int>

val Point.line: Int
    get() = this.first

val Point.row: Int
    get() = this.second

fun Point.pointsAround(): List<Point> =
    listOf(
        Point(line, row - 1),
        Point(line - 1, row),
        Point(line, row + 1),
        Point(line + 1, row)
    )
