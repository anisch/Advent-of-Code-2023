import Day16.part1
import Day16.part2
import Direction.*

fun main() {
    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day16_test")
    check(part1(testInput) == 46)
    check(part2(testInput) == 51)

    val input = readInput("Day16")
    println(part1(input)) // 6514
    println(part2(input)) // 8089
}

object Day16 {
    fun part1(input: List<String>): Int {
        val tmp = input
            .map { line ->
                line
                    .chunked(1)
                    .map { MirrorField(it) }
            }

        beam(tmp, Vec2D(0, 0), RIGHT)

        return tmp.sumOf { line -> line.count { it.isBeam } }
    }

    fun part2(input: List<String>): Int {
        var tmp = input
            .map { line ->
                line
                    .chunked(1)
                    .map { MirrorField(it) }
            }

        var max = 0

        for (x in 0..tmp[0].lastIndex) {
            tmp = tmp.copy()
            beam(tmp, Vec2D(x, 0), DOWN)
            var sum = tmp.sumOf { line -> line.count { it.isBeam } }
            if (max < sum) max = sum

            tmp = tmp.copy()
            beam(tmp, Vec2D(x, tmp.lastIndex), UP)
            sum = tmp.sumOf { line -> line.count { it.isBeam } }
            if (max < sum) max = sum
        }

        for (y in 0..tmp.lastIndex) {
            tmp = tmp.copy()
            beam(tmp, Vec2D(0, y), RIGHT)
            var sum = tmp.sumOf { line -> line.count { it.isBeam } }
            if (max < sum) max = sum

            tmp = tmp.copy()
            beam(tmp, Vec2D(tmp[y].lastIndex, y), LEFT)
            sum = tmp.sumOf { line -> line.count { it.isBeam } }
            if (max < sum) max = sum
        }

        return max
    }

    private fun beam(mm: MirrorMap, v: Vec2D, d: Direction) {
        // if the path is not in rage, then it is finished
        if (v.x !in 0..mm[0].lastIndex) return
        if (v.y !in 0..mm.lastIndex) return

        val mf = mm[v.y][v.x]
        if (mf.field == "|" && mf.isBeam) return
        if (mf.field == "-" && mf.isBeam) return

        mf.isBeam = true

        if (mf.field == "|") {
            return when (d) {
                UP, DOWN -> beam(mm, v.next(d), d)

                else -> {
                    beam(mm, v.next(UP), UP)
                    beam(mm, v.next(DOWN), DOWN)
                }
            }
        }
        if (mf.field == "-") {
            return when (d) {
                LEFT, RIGHT -> beam(mm, v.next(d), d)

                else -> {
                    beam(mm, v.next(LEFT), LEFT)
                    beam(mm, v.next(RIGHT), RIGHT)
                }
            }
        }
        if (mf.field == "/") {
            return when (d) {
                UP -> beam(mm, v.next(RIGHT), RIGHT)
                DOWN -> beam(mm, v.next(LEFT), LEFT)
                LEFT -> beam(mm, v.next(DOWN), DOWN)
                RIGHT -> beam(mm, v.next(UP), UP)
            }
        }
        if (mf.field == "\\") {
            return when (d) {
                UP -> beam(mm, v.next(LEFT), LEFT)
                DOWN -> beam(mm, v.next(RIGHT), RIGHT)
                LEFT -> beam(mm, v.next(UP), UP)
                RIGHT -> beam(mm, v.next(DOWN), DOWN)
            }
        }

        return beam(mm, v.next(d), d)
    }
}

typealias MirrorMap = List<List<MirrorField>>

fun MirrorMap.copy() = map { row ->
    row.map { col -> col.copy(isBeam = false) }
}

data class MirrorField(val field: String, var isBeam: Boolean = false)

fun Vec2D.next(d: Direction): Vec2D = when (d) {
    UP -> Vec2D(x, y - 1)
    DOWN -> Vec2D(x, y + 1)
    LEFT -> Vec2D(x - 1, y)
    RIGHT -> Vec2D(x + 1, y)
}

enum class Direction {
    UP, DOWN, LEFT, RIGHT;
}
