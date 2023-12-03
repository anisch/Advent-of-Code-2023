import kotlinx.benchmark.gradle.JvmBenchmarkTarget

plugins {
    kotlin("jvm") version "1.9.21"
    kotlin("plugin.allopen") version "1.9.21"
    id("org.jetbrains.kotlinx.benchmark") version "0.4.9"
}

tasks {
    wrapper {
        gradleVersion = "8.4"
    }
}

repositories {
    mavenCentral()
}

// how to apply plugin to a specific source set?
allOpen {
    annotation("org.openjdk.jmh.annotations.State")
}

sourceSets {
    main {
        java.srcDirs("kotlin/main")
        resources.srcDir("kotlin/resources")
    }
    test {
        java.srcDirs("kotlin/test")
    }
}

benchmark {
    targets {
        register("main") {
            this as JvmBenchmarkTarget
            jmhVersion = "1.21"
        }
    }
    sourceSets {
        main {
            java.srcDirs("kotlin/benchmark")
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-benchmark-runtime:0.4.9")
}

abstract class KotlinDayGenerator : DefaultTask() {
    @get:Input
    @get:Option(option = "day", description = "day to create")
    abstract val day: Property<Int>

    @get:Input
    @get:Option(option = "force", description = "force - overrides the current file(s)")
    abstract val force: Property<Boolean>

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun createFile() {
        val txtDay = String.format("%02d", day.get())
        val mainContent = """
            import Day${txtDay}.part1
            import Day${txtDay}.part2
            
            fun main() {
                // test if implementation meets criteria from the description, like:
                val testInput = readInput("Day${txtDay}_test")
                check(part1(testInput) == 1)

                val input = readInput("Day$txtDay")
                println(part1(input))
                println(part2(input))
            }
            
            object Day${txtDay} {
                fun part1(input: List<String>): Int {
                    return input.size
                }
            
                fun part2(input: List<String>): Int {
                    return input.size
                }
            }

        """.trimIndent()

        val benchmarkContent = """
            package day

            import Day${txtDay}.part1
            import Day${txtDay}.part2
            import org.openjdk.jmh.annotations.*
            import readInput
            import java.util.concurrent.TimeUnit

            @State(Scope.Benchmark)
            @Fork(1)
            @Warmup(iterations = 5)
            @Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
            class TestBenchmarkDay${txtDay} {

                private lateinit var content: List<String>

                @Setup
                fun setUp() {
                    content = readInput("Day${txtDay}_test")
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

        """.trimIndent()

        val targetMainFile = outputDir.file("main/Day$txtDay.kt").get().asFile
        if (!targetMainFile.exists() || force.get()) {
            targetMainFile.writeText(mainContent)
        }

        val targetBenchmarkFile = outputDir.file("benchmark/day/Day$txtDay.kt").get().asFile
        if (!targetBenchmarkFile.exists() || force.get()) {
            targetBenchmarkFile.writeText(benchmarkContent)
        }
    }
}

abstract class GolangDayGenerator : DefaultTask() {
    @get:Input
    @get:Option(option = "day", description = "day to create")
    abstract val day: Property<Int>

    @get:Input
    @get:Option(option = "force", description = "force - overrides the current file(s)")
    abstract val force: Property<Boolean>

    @get:OutputDirectory
    abstract val outputDir: DirectoryProperty

    @TaskAction
    fun createFile() {
        val txtDay = String.format("%02d", day.get())
        val contentDay = """
            package day$txtDay

            func Part1(input []string) int {
                return 0
            }

            func Part2(input []string) int {
                return 0
            }

        """.trimIndent()

        val contentDayTest = """
            package day${txtDay}_test
            
            import (
                "testing"

                day "anisch.github.com/advent-of-code-2023/golang/day$txtDay"
                "anisch.github.com/advent-of-code-2023/golang/util"
                "github.com/stretchr/testify/assert"
            )

            func TestPart1(t *testing.T) {
                input, err := util.ReadFile("Day${txtDay}_test")
                if err != nil {
                    t.Error(err)
                }
                actual := day.Part1(input)

                assert.Equal(t, 0, actual)
            }
            
            func TestPart2(t *testing.T) {
                input, err := util.ReadFile("Day${txtDay}_test")
                if err != nil {
                    t.Error(err)
                }
                actual := day.Part2(input)

                assert.Equal(t, 0, actual)
            }

            func BenchmarkPart1(b *testing.B) {
                input, err := util.ReadFile("Day${txtDay}")
                if err != nil {
                    b.Error(err)
                }

                for n := 0; n < b.N; n++ {
                    day.Part1(input)
                }
            }

            func BenchmarkPart2(b *testing.B) {
                input, err := util.ReadFile("Day${txtDay}")
                if err != nil {
                    b.Error(err)
                }

                for n := 0; n < b.N; n++ {
                    day.Part2(input)
                }
            }

        """.trimIndent()

        val contentMain = """
            package main

            import (
                day "anisch.github.com/advent-of-code-2023/golang/day${txtDay}"
                "anisch.github.com/advent-of-code-2023/golang/util"
            )

            func main() {
                input, err := util.ReadFile("Day${txtDay}")
                if err != nil {
                    panic(err)
                }

                println(day.Part1(input))
                println(day.Part2(input))
            }

        """.trimIndent()

        outputDir.file("day$txtDay").get().asFile.mkdirs()
        outputDir.file("bin/day$txtDay").get().asFile.mkdirs()

        val targetFileDay = outputDir.file("day$txtDay/day.go").get().asFile
        if (!targetFileDay.exists() || force.get()) {
            targetFileDay.writeText(contentDay)
        }

        val targetFileDayTest = outputDir.file("day$txtDay/day_test.go").get().asFile
        if (!targetFileDayTest.exists() || force.get()) {
            targetFileDayTest.writeText(contentDayTest)
        }

        val targetFileMain = outputDir.file("bin/day$txtDay/main.go").get().asFile
        if (!targetFileMain.exists() || force.get()) {
            targetFileMain.writeText(contentMain)
        }
    }
}

tasks.register<KotlinDayGenerator>("kotlinCreateDay") {
    day = 1
    outputDir = layout.projectDirectory.dir("kotlin")
    force = false
}

tasks.register<GolangDayGenerator>("goCreateDay") {
    day = 1
    outputDir = layout.projectDirectory.dir("golang")
    force = false
}
