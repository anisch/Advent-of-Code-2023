import Day03.part1
import Day03.part2

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    println(part1(input)) // 539433
    println(part2(input)) // 75847567
}

object Day03 {

    private val regex = """\d+""".toRegex()

    fun part1(input: List<String>): Int {

        val symbols = parseSymbols(input)
        val nums = parseNums(input)

        return nums
            .filter { it.touchesSymbol(symbols) }
            .sumOf { it.num }
    }

    fun part2(input: List<String>): Int {
        val symbols = parseSymbols(input)
        val nums = parseNums(input)

        val numsWithSymbols = nums.filter { it.touchesSymbol(symbols) }

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
                    if (c != '.' && !c.isDigit()) Symbol(x, y)
                    else null
                }
        }

    private fun parseNums(input: List<String>) = input
        .flatMapIndexed { y, text ->
            regex
                .findAll(text)
                .map { PartNumber(it.value.toInt(), it.range, y) }
        }
}

typealias Symbol = Vec2D

data class PartNumber(val num: Int, val xRange: IntRange, val y: Int)

fun PartNumber.touchesSymbol(symbol: Symbol): Boolean {
    val xRange = IntRange(xRange.first - 1, xRange.last + 1)
    val yRange = IntRange(y - 1, y + 1)
    return symbol.x in xRange && symbol.y in yRange
}

fun PartNumber.touchesSymbol(symbols: List<Symbol>): Boolean = symbols.any { touchesSymbol(it) }
