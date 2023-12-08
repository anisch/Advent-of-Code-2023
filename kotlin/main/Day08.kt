import Day08.part1
import Day08.part2

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput1 = readInput("Day08_test1")
    val testInput2 = readInput("Day08_test2")
    check(part1(testInput1) == 6)
    check(part2(testInput2) == 6L)

    val input = readInput("Day08")
    println(part1(input)) // 12361
    println(part2(input)) // 18215611419223
}

object Day08 {
    fun part1(input: List<String>): Int {
        val navigation = input.first().chunked(1)
        val nodes = input
            .drop(2)
            .associate { line ->
                val key = line.substring(0..2)
                val left = line.substring(7..9)
                val right = line.substring(12..14)
                key to Node(left, right)
            }

        var start = "AAA"
        var navIdx = 0
        var count = 0
        while (start != "ZZZ") {
            val dir = navigation[navIdx]

            start = if (dir == "L") nodes[start]!!.left
            else nodes[start]!!.right

            if (++navIdx > navigation.lastIndex) {
                navIdx = 0
            }
            count++
        }

        return count
    }

    fun part2(input: List<String>): Long {
        val navigation = input.first().chunked(1)
        val nodes = input
            .drop(2)
            .associate { line ->
                val key = line.substring(0..2)
                val left = line.substring(7..9)
                val right = line.substring(12..14)
                key to Node(left, right)
            }

        val startNodes = nodes.keys
            .filter { it[2] == 'A' }
            .toMutableList()

        val countList = mutableListOf<Int>()
        for (node in startNodes) {
            var start = node
            var navIdx = 0
            var count = 0
            while (start[2] != 'Z') {
                val dir = navigation[navIdx]

                start = if (dir == "L") nodes[start]!!.left
                else nodes[start]!!.right

                if (++navIdx > navigation.lastIndex) {
                    navIdx = 0
                }
                count++
            }

            countList += count
        }

        var result = countList[0].toLong()
        for (x in 1..countList.lastIndex) {
            var a = result
            var b = countList[x].toLong()

            while (a != b) {
                if (a < b) a += result
                else b += countList[x].toLong()
            }
            result = a
        }

        return result
    }
}

data class Node(val left: String, val right: String)
