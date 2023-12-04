import Day04.part1
import Day04.part2
import kotlin.math.pow

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 13)
    check(part2(testInput) == 30)

    val input = readInput("Day04")
    println(part1(input)) // 26443
    println(part2(input)) // 6284877
}

object Day04 {
    fun part1(input: List<String>): Int {
        return input
            .map { parseGameCard(it) }
            .sumOf { it.getPoints() }
    }

    fun part2(input: List<String>): Int {
        val list = input
            .map { parseGameCard(it) }
            .map { Match(it.countWinningNumbers()) }

        for (idx in list.indices) {
            for (x in 1..list[idx].wins) {
                list[idx + x].copies += list[idx].copies
            }
        }

        return list.sumOf { it.copies }
    }

    private fun parseGameCard(line: String): GameCard {
        val tmp = line.split(":").last().split("|")
        val winning = tmp.first().drop(1).chunked(3).map { it.trim().toInt() }
        val numbers = tmp.last().drop(1).chunked(3).map { it.trim().toInt() }

        return GameCard(winning, numbers)
    }
}

data class GameCard(val winning: List<Int>, val numbers: List<Int>)
data class Match(val wins: Int, var copies: Int = 1)

fun GameCard.getPoints(): Int {
    val count = numbers.filter { it in winning }.size - 1
    return 2.0.pow(count).toInt()
}

fun GameCard.countWinningNumbers(): Int = numbers.filter { it in winning }.size
