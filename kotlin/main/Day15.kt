import Day15.part1
import Day15.part2

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day15_test")
    check(part1(testInput) == 1320)
    check(part2(testInput) == 145)

    val input = readInput("Day15")
    println(part1(input)) // 519041
    println(part2(input)) // 260530
}

object Day15 {
    fun part1(input: List<String>): Int {
        return input
            .first()
            .split(",")
            .sumOf { it.hash() }
    }

    fun part2(input: List<String>): Int {
        val hashMap = mutableMapOf<Int, MutableList<Label>>()

        input
            .first()
            .split(",")
            .map { Label(it) }
            .forEach { label ->
                val box = hashMap[label.hashCode()] ?: mutableListOf()
                if (label.focalLength == null) {
                    box.remove(label)
                } else {
                    val idx = box.indexOf(label)
                    if (idx > -1) box[idx] = label
                    else box += label
                }
                hashMap[label.hashCode()] = box
            }

        return hashMap
            .map { (box, labels) ->
                (1..labels.size)
                    .sumOf { idx ->
                        idx * labels[idx - 1].focalLength!!
                    } * (box + 1)
            }
            .sum()
    }
}

class Label(val string: String) {

    val label = string.dropLast(if (string.last() == '-') 1 else 2)
    val focalLength: Int? = string.last().digitToIntOrNull()

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Label

        return label == other.label
    }

    override fun hashCode(): Int {
        var hash = 0
        for (c in label) {
            hash += c.code
            hash *= 17
            hash %= 256
        }
        return hash
    }
}

fun String.hash(): Int {
    var hash = 0
    for (c in this) {
        hash += c.code
        hash *= 17
        hash %= 256
    }
    return hash
}
