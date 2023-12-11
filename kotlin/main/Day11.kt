import Day11.part1
import Day11.part2
import Universe.EMPTY
import Universe.GALAXY

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 374L)
    check(part2(testInput, 100) == 8410L)

    val input = readInput("Day11")
    println(part1(input)) // 9918828
    println(part2(input, 1_000_000)) // 692506533832
}

object Day11 {
    fun part1(input: List<String>): Long {
        val universe = input
            .map { line ->
                line
                    .map { if (it == '.') EMPTY else GALAXY }
                    .toMutableList()
            }
            .toMutableList()

        val galaxys = universe.findGalaxys()
        val emptyX = universe.emptyX()
        val emptyY = universe.emptyY()

        return galaxys
            .getDistances(emptyX, emptyY, 2)
            .sum()
    }

    fun part2(input: List<String>, mul: Int): Long {
        val universe = input
            .map { line ->
                line
                    .map { if (it == '.') EMPTY else GALAXY }
                    .toMutableList()
            }
            .toMutableList()

        val galaxys = universe.findGalaxys()
        val emptyX = universe.emptyX()
        val emptyY = universe.emptyY()

        return galaxys
            .getDistances(emptyX, emptyY, mul)
            .sum()
    }
}

typealias Image = MutableList<MutableList<Universe>>

enum class Universe(private val text: String) {
    EMPTY("."),
    GALAXY("#");

    override fun toString(): String = text
}

fun Image.emptyY(): List<Int> {
    val result = mutableListOf<Int>()
    for (y in indices) {
        if (this[y].all { it == EMPTY }) {
            result += y
        }
    }
    return result
}

fun Image.emptyX(): List<Int> {
    val result = mutableListOf<Int>()
    var x = 0
    while (x < this[0].size) {
        var allEmpty = true
        for (y in indices) {
            if (this[y][x] == GALAXY) {
                allEmpty = false
                break
            }
        }
        if (allEmpty) {
            result += x
        }
        x++
    }
    return result
}

fun Image.findGalaxys(): List<Vec2D> {
    val result = mutableListOf<Vec2D>()
    forEachIndexed { y, line ->
        line.forEachIndexed { x, cell ->
            if (cell == GALAXY) result += Vec2D(x, y)
        }
    }
    return result
}

fun List<Vec2D>.getDistances(
    ex: List<Int>,
    ey: List<Int>,
    factor: Int,
): List<Long> {
    val result = mutableListOf<Long>()
    var a = 0
    var b = 1
    while (a < lastIndex) {
        while (b <= lastIndex) {
            var ax = this[a].x
            var bx = this[b].x
            var ay = this[a].y
            var by = this[b].y

            if (ax > bx) ax = bx.also { bx = ax }
            if (ay > by) ay = by.also { by = ay }

            val xd = bx - ax
            val xe = ex.count { it in ax..bx }
            val yd = by - ay
            val ye = ey.count { it in ay..by }

            val r = xd + xe * (factor - 1) + yd + ye * (factor - 1)

            result += r.toLong()
            b++
        }
        b = ++a + 1
    }
    return result
}
