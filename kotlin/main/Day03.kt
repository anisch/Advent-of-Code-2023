import Day03.part1
import Day03.part2

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

object Day03 {

    private val regex = """\d+""".toRegex()

    fun part1(input: List<String>): Int {

        val symbols = parseSymbols(input)
        val nums = parseNums(input)

        return nums
            .filter { part ->
                part.touchesSymbols(symbols)
            }
            .sumOf { it.num }
    }

    fun part2(input: List<String>): Int {
        val symbols = parseSymbols(input)
        val nums = parseNums(input)

        val numsWithSymbols = nums.filter { it.touchesSymbols(symbols) }

        return symbols
            .mapNotNull { symbol ->
                val touches = numsWithSymbols.filter { it.touchesSymbol(symbol) }
                if (touches.size == 2) touches else null
            }
            .sumOf { it.first().num * it.last().num }
    }

    private fun parseSymbols(input: List<String>) = input
        .flatMapIndexed { y, text ->
            text
                .mapIndexedNotNull { x, c ->
                    if (c != '.' && !c.isDigit()) x else null
                }
                .map { x -> Vec2D(x, y) }
        }

    private fun parseNums(input: List<String>) = input
        .flatMapIndexed { y, text ->
            regex
                .findAll(text)
                .map {
                    PartNumber(it.value.toInt(), it.range, y)
                }
        }
}


data class PartNumber(val num: Int, val xRange: IntRange, val y: Int)

fun PartNumber.touchesSymbol(s: Vec2D): Boolean {
    if (y !in s.y - 1..s.y + 1) {
        return false
    }
    val xRange = IntRange(xRange.first - 1, xRange.last + 1)
    return s.x in xRange
}

fun PartNumber.touchesSymbols(symbols: List<Vec2D>): Boolean {
    for (s in symbols) {
        if (y !in s.y - 1..s.y + 1) {
            continue
        }
        val xRange = IntRange(xRange.first - 1, xRange.last + 1)
        if (s.x in xRange) {
            return true
        }
    }
    return false
}
