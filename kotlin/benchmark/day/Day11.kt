package day

import Day11.part1
import Day11.part2
import org.openjdk.jmh.annotations.*
import readInput
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
class TestBenchmarkDay11 {

    private lateinit var content: List<String>

    @Setup
    fun setUp() {
        content = readInput("Day11_test")
    }

    @Benchmark
    fun benchPart1(): Long {
        return part1(content)
    }

    @Benchmark
    fun benchPart2(): Long {
        return part2(content, 1000000)
    }
}
