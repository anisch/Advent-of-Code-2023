import Day14.part1
import Day14.part2

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 136)
    check(part2(testInput) == 64)

    val input = readInput("Day14")
    println(part1(input)) // 110090
    println(part2(input)) // 95254
}

object Day14 {
    fun part1(input: List<String>): Int {
        val tmp = input
            .map { line -> line.chunked(1).toMutableList() }
            .toMutableList()

        tmp.moveAllRocksNorth()

        return tmp.sumUp()
    }

    fun part2(input: List<String>): Int {
        val tmp = input
            .map { line -> line.chunked(1).toMutableList() }
            .toMutableList()

        for (loop in 1..1000) {
            tmp.moveAllRocksNorth()
            tmp.moveAllRocksWest()
            tmp.moveAllRocksSouth()
            tmp.moveAllRocksEast()
        }

        return tmp.sumUp()
    }
}

typealias Field = MutableList<MutableList<String>>

fun Field.sumUp(): Int = mapIndexed { y, line -> (size - y) * line.count { it == "O" } }.sum()

fun Field.moveAllRocksNorth() {
    for (y in 1..lastIndex) {
        for (x in 0..this[y].lastIndex) {
            if (this[y][x] == "O") {
                moveRockNorth(y, x)
            }
        }
    }
}

fun Field.moveAllRocksSouth(): Field {
    for (y in lastIndex - 1 downTo 0) {
        for (x in 0..this[y].lastIndex) {
            if (this[y][x] == "O") {
                moveRockSouth(y, x)
            }
        }
    }
    return this
}

fun Field.moveAllRocksWest() {
    for (x in 1..this[0].lastIndex) {
        for (y in 0..lastIndex) {
            if (this[y][x] == "O") {
                moveRockWest(y, x)
            }
        }
    }
}

fun Field.moveAllRocksEast() {
    for (x in this[0].lastIndex - 1 downTo 0) {
        for (y in 0..lastIndex) {
            if (this[y][x] == "O") {
                moveRockEast(y, x)
            }
        }
    }
}

fun Field.moveRockNorth(y: Int, x: Int) {
    var tmpY = y
    while (0 < tmpY && this[tmpY - 1][x] == ".") {
        this[tmpY][x] = "."
        this[--tmpY][x] = "O"
    }
}

fun Field.moveRockSouth(y: Int, x: Int) {
    var tmpY = y
    while (tmpY < lastIndex && this[tmpY + 1][x] == ".") {
        this[tmpY][x] = "."
        this[++tmpY][x] = "O"
    }
}

fun Field.moveRockWest(y: Int, x: Int) {
    var tmpX = x
    while (0 < tmpX && this[y][tmpX - 1] == ".") {
        this[y][tmpX] = "."
        this[y][--tmpX] = "O"
    }
}

fun Field.moveRockEast(y: Int, x: Int) {
    var tmpX = x
    while (tmpX < this[y].lastIndex && this[y][tmpX + 1] == ".") {
        this[y][tmpX] = "."
        this[y][++tmpX] = "O"
    }
}
