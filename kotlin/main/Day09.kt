import Day09.part1
import Day09.part2

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 114)
    check(part2(testInput) == 2)

    val input = readInput("Day09")
    println(part1(input)) // 1921197370
    println(part2(input)) // 1124
}

object Day09 {
    fun part1(input: List<String>): Int {
        return input
            .asSequence()
            .map { it.toIntList() }
            .map { mutableListOf(it.toMutableList()) }
            .map { list ->
                do {
                    val tmp = list.last()
                    list += tmp.getDiffs()
                } while (!tmp.all { it == 0 })
                list
            }
            .map { list ->
                for (x in list.lastIndex - 1 downTo 0) {
                    list[x] += list[x].last() + list[x + 1].last()
                }
                list[0].last()
            }
            .sum()
    }

    fun part2(input: List<String>): Int {
        return input
            .asSequence()
            .map { it.toIntList() }
            .map { mutableListOf(it.toMutableList()) }
            .map { list ->
                do {
                    val tmp = list.last()
                    list += tmp.getDiffs()
                } while (!tmp.all { it == 0 })
                list
            }
            .map { list ->
                for (x in list.lastIndex - 1 downTo 0) {
                    list[x].add(0, list[x][0] - list[x + 1][0])
                }
                list[0][0]
            }
            .sum()
    }
}

fun String.toIntList() = split(" ").map { it.toInt() }

fun List<Int>.getDiffs(): MutableList<Int> {
    val h = mutableListOf<Int>()
    for (x in 1..lastIndex) {
        h += this[x] - this[x - 1]
    }
    return h
}
