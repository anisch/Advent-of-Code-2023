import Day06.part1
import Day06.part2

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 288)
    check(part2(testInput) == 71503)

    val input = readInput("Day06")
    println(part1(input)) // 1710720
    println(part2(input)) // 35349468
}

object Day06 {
    fun part1(input: List<String>): Int {
        val split = "\\s+".toRegex()

        val times = input[0]
            .split(split)
            .drop(1)
            .map { it.toInt() }

        val distances = input[1]
            .split(split)
            .drop(1)
            .map { it.toInt() }

        return times
            .mapIndexed { idx, time ->
                (0..time)
                    .map { speed -> speed * (time - speed) }
                    .filter { it > distances[idx] }
            }
            .map { it.size }
            .reduce { acc, i -> acc * i }
    }

    fun part2(input: List<String>): Int {
        val time = input[0]
            .split(":")
            .drop(1)
            .map { it.replace(" ", "").toLong() }
            .first()

        val distance = input[1]
            .split(":")
            .drop(1)
            .map { it.replace(" ", "").toLong() }
            .first()

        return (0..time)
            .asSequence()
            .map { speed -> speed * (time - speed) }
            .count { it > distance }
    }
}
