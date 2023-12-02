package day

import Day02.part1
import Day02.part2
import org.openjdk.jmh.annotations.*
import readInput
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 5)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
class TestBenchmarkDay02 {

    private lateinit var content: List<String>

    @Setup
    fun setUp() {
        content = readInput("Day02_test")
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
