package day

import Day04.part1
import Day04.part2
import org.openjdk.jmh.annotations.*
import readInput
import java.math.BigInteger
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
class TestBenchmarkDay04 {

    private lateinit var content: List<String>

    @Setup
    fun setUp() {
        content = readInput("Day04_test")
    }

    @Benchmark
    fun benchPart1(): Int {
        return part1(content)
    }

    @Benchmark
    fun benchPart2(): Int {
        return part2(content)
    }
}
