import adventofcode2023.day5.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class Day5Test {

    @Test
    fun testInputParsing() {
        val actual = parseInput(inputExample.lines())

        val expected = ParsedInput(
            seeds = listOf(79, 14, 55, 13),
            mappers = listOf(
                /*seed-to-soil map:
                50 98 2
                52 50 48*/
                ParsedInput.Mapper(
                    records = listOf(
                        ParsedInput.Mapper.MapRecord(
                            destinationIndex = 50,
                            sourceIndex = 98,
                            range = 2
                        ),
                        ParsedInput.Mapper.MapRecord(
                            destinationIndex = 52,
                            sourceIndex = 50,
                            range = 48
                        )
                    )
                ),
                /*soil-to-fertilizer map:
                0 15 37
                37 52 2
                39 0 15*/
                ParsedInput.Mapper(
                    records = listOf(
                        ParsedInput.Mapper.MapRecord(
                            destinationIndex = 0,
                            sourceIndex = 15,
                            range = 37
                        ),
                        ParsedInput.Mapper.MapRecord(
                            destinationIndex = 37,
                            sourceIndex = 52,
                            range = 2
                        ),
                        ParsedInput.Mapper.MapRecord(
                            destinationIndex = 39,
                            sourceIndex = 0,
                            range = 15
                        )
                    )
                ),
                /*fertilizer-to-water map:
                49 53 8
                0 11 42
                42 0 7
                57 7 4*/
                ParsedInput.Mapper(
                    records = listOf(
                        ParsedInput.Mapper.MapRecord(
                            destinationIndex = 49,
                            sourceIndex = 53,
                            range = 8
                        ),
                        ParsedInput.Mapper.MapRecord(
                            destinationIndex = 0,
                            sourceIndex = 11,
                            range = 42
                        ),
                        ParsedInput.Mapper.MapRecord(
                            destinationIndex = 42,
                            sourceIndex = 0,
                            range = 7
                        ),
                        ParsedInput.Mapper.MapRecord(
                            destinationIndex = 57,
                            sourceIndex = 7,
                            range = 4
                        )
                    )
                ),
                /*water-to-light map:
                88 18 7
                18 25 70*/
                ParsedInput.Mapper(
                    records = listOf(
                        ParsedInput.Mapper.MapRecord(
                            destinationIndex = 88,
                            sourceIndex = 18,
                            range = 7
                        ),
                        ParsedInput.Mapper.MapRecord(
                            destinationIndex = 18,
                            sourceIndex = 25,
                            range = 70
                        )
                    )
                ),
                /*light-to-temperature map:
                45 77 23
                81 45 19
                68 64 13*/
                ParsedInput.Mapper(
                    records = listOf(
                        ParsedInput.Mapper.MapRecord(
                            destinationIndex = 45,
                            sourceIndex = 77,
                            range = 23
                        ),
                        ParsedInput.Mapper.MapRecord(
                            destinationIndex = 81,
                            sourceIndex = 45,
                            range = 19
                        ),
                        ParsedInput.Mapper.MapRecord(
                            destinationIndex = 68,
                            sourceIndex = 64,
                            range = 13
                        )
                    )
                ),
                /*temperature-to-humidity map:
                0 69 1
                1 0 69*/
                ParsedInput.Mapper(
                    records = listOf(
                        ParsedInput.Mapper.MapRecord(
                            destinationIndex = 0,
                            sourceIndex = 69,
                            range = 1
                        ),
                        ParsedInput.Mapper.MapRecord(
                            destinationIndex = 1,
                            sourceIndex = 0,
                            range = 69
                        )
                    )
                ),
                /*humidity-to-location map:
                60 56 37
                56 93 4*/
                ParsedInput.Mapper(
                    records = listOf(
                        ParsedInput.Mapper.MapRecord(
                            destinationIndex = 60,
                            sourceIndex = 56,
                            range = 37,
                        ),
                        ParsedInput.Mapper.MapRecord(
                            destinationIndex = 56,
                            sourceIndex = 93,
                            range = 4
                        )
                    )
                )
            )
        )

        assertEquals(expected, actual)
    }

    @ParameterizedTest
    @MethodSource("mapperSource")
    fun testMapperMap(testCase: Pair<Long, Long>) {
        val mapper = ParsedInput.Mapper(
            records = listOf(
                ParsedInput.Mapper.MapRecord(
                    destinationIndex = 50,
                    sourceIndex = 98,
                    range = 2
                ),
                ParsedInput.Mapper.MapRecord(
                    destinationIndex = 52,
                    sourceIndex = 50,
                    range = 48
                )
            )
        )
        assertEquals(testCase.second, mapper.map(testCase.first))
    }

    @Test
    fun puzzle1Test() {
        assertEquals(35, puzzle1(inputExample.lines()))
    }

    @Test
    fun puzzle2Test() {
        assertEquals(46, puzzle2dummy(inputExample.lines()))
    }

    @Test
    fun puzzle2ParallelTest() {
        assertEquals(46, puzzle2dummyParallel(inputExample.lines()))
    }

    companion object {
        @JvmStatic
        fun mapperSource(): Stream<Pair<Long, Long>> = Stream.of(
            0L to 0L,
            1L to 1L,
            2L to 2L,
            48L to 48L,
            49L to 49L,
            50L to 52L,
            51L to 53L,
            52L to 54L,
            66L to 68L,
            96L to 98L,
            97L to 99L,
            98L to 50L,
            99L to 51L
        )


        private val inputExample = """
            seeds: 79 14 55 13

            seed-to-soil map:
            50 98 2
            52 50 48

            soil-to-fertilizer map:
            0 15 37
            37 52 2
            39 0 15

            fertilizer-to-water map:
            49 53 8
            0 11 42
            42 0 7
            57 7 4

            water-to-light map:
            88 18 7
            18 25 70

            light-to-temperature map:
            45 77 23
            81 45 19
            68 64 13

            temperature-to-humidity map:
            0 69 1
            1 0 69

            humidity-to-location map:
            60 56 37
            56 93 4
        """.trimIndent()

    }

}