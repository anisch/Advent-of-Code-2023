import Day02.part1
import Day02.part2

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 8)
    check(part2(testInput) == 2286)

    val input = readInput("Day02")
    println(part1(input)) // 2105
    println(part2(input)) // 72422
}

object Day02 {
    fun part1(input: List<String>): Int {
        return input
            .map { parseGame(it) }
            .filter { game ->
                val r = game.graps.all { it.red.count <= 12 }
                val g = game.graps.all { it.green.count <= 13 }
                val b = game.graps.all { it.blue.count <= 14 }
                r && g && b
            }
            .sumOf { it.id }
    }

    fun part2(input: List<String>): Int {
        return input
            .map { parseGame(it) }
            .sumOf { game ->
                val r = game.graps.maxOf { it.red.count }
                val g = game.graps.maxOf { it.green.count }
                val b = game.graps.maxOf { it.blue.count }
                r * g * b
            }
    }

    private fun parseGame(s: String): Game {
        val h = s.split(":")

        val id = h[0].split(" ").last().toInt()
        val c = h[1].split(";")
        val grabs = c.map { parseGrabs(it) }
        return Game(id, grabs)
    }

    private fun parseGrabs(s: String): Grab {
        val h = s.trim()
            .split(",")
            .map { parseSets(it) }

        val r = h.firstOrNull { it.c == Color.RED } ?: Set(Color.RED, 0)
        val g = h.firstOrNull { it.c == Color.GREEN } ?: Set(Color.GREEN, 0)
        val b = h.firstOrNull { it.c == Color.BLUE } ?: Set(Color.BLUE, 0)

        return Grab(r, g, b)
    }

    private fun parseSets(s: String): Set {
        val h = s.trim().split(" ")
        return when {
            h[1] == "red" -> Set(Color.RED, h[0].toInt())
            h[1] == "green" -> Set(Color.GREEN, h[0].toInt())
            h[1] == "blue" -> Set(Color.BLUE, h[0].toInt())
            else -> throw Exception("something bad happen")
        }
    }
}

enum class Color { RED, GREEN, BLUE }
data class Set(val c: Color, val count: Int)
data class Grab(val red: Set, val green: Set, val blue: Set)
data class Game(val id: Int, val graps: List<Grab>)
