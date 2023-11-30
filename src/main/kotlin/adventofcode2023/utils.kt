package adventofcode2023

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import kotlin.streams.toList

fun openResourseAsStream(resourceName: String): InputStream =
    requireNotNull(object {}.javaClass.classLoader.getResourceAsStream(resourceName))

fun InputStream.asReader() =
    BufferedReader(InputStreamReader(BufferedInputStream(this)))

fun readInput(resourceName: String): List<String> =
    openResourseAsStream(resourceName).asReader().use { reader ->
        reader.lines().toList()
    }