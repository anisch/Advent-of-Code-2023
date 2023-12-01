import Day01.part1
import Day01.part2

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day01_test1")
    check(part1(testInput1) == 142)

    val testInput2 = readInput("Day01_test2")
    check(part2(testInput2) == 281)

    val input = readInput("Day01")
    println(part1(input)) // 54697
    println(part2(input)) // 54885
}

object Day01 {
    fun part1(input: List<String>): Int {
        return input
            .map { s -> s.filter { c -> c.isDigit() } }
            .map { it.first().toString() + it.last().toString() }
            .sumOf { it.toInt() }
    }

    fun part2(input: List<String>): Int {
        val regex = """(?=(one|two|three|four|five|six|seven|eight|nine|\d))""".toRegex()

        return input
            .map { line ->
                regex
                    .findAll(line)
                    .map { it.groupValues.last() }
                    .toList()
            }
            .map { it.map { s -> mapToInt(s) } }
            .map { it.first().toString() + it.last().toString() }
            .sumOf { it.toInt() }
    }

    private fun mapToInt(s: String): Int {
        return when (s) {
            "one" -> 1
            "two" -> 2
            "three" -> 3
            "four" -> 4
            "five" -> 5
            "six" -> 6
            "seven" -> 7
            "eight" -> 8
            "nine" -> 9
            else -> s.toInt()
        }
    }
}

