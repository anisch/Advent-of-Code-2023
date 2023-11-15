plugins {
    kotlin("jvm") version "1.9.20"
}

repositories {
    mavenCentral()
}

tasks {
    sourceSets {
        main {
            java.srcDirs("kotlin")
        }
    }

    wrapper {
        gradleVersion = "8.4"
    }
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
        val content =
            """
                fun main() {
                    fun part1(input: List<String>): Int {
                        return input.size
                    }

                    fun part2(input: List<String>): Int {
                        return input.size
                    }

                    // test if implementation meets criteria from the description, like:
                    val testInput = readInput("Day${txtDay}_test")
                    check(part1(testInput) == 1)

                    val input = readInput("Day$txtDay")
                    part1(input).println()
                    part2(input).println()
                }

            """.trimIndent()

        val targetFile = outputDir.file("Day$txtDay.kt").get().asFile
        if (!targetFile.exists() || force.get()) {
            targetFile.writeText(content)
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
        val contentDay =
            """
                package day$txtDay
    
                func Part1(input []string) int {
                    return 0
                }
    
                func Part2(input []string) int {
                    return 0
                }

            """.trimIndent()

        val contentDayTest =
            """
                package day${txtDay}_test
                
                import (
                	"testing"

                	day "anisch.github.com/advent-of-code-2023/golang/day$txtDay"
                	"anisch.github.com/advent-of-code-2023/golang/util"
                	"github.com/stretchr/testify/assert"
                )

                func TestPart1(t *testing.T) {
                	input, err := util.ReadFile("day_test")
                	if err != nil {
                		t.Error(err)
                	}
                	actual := day.Part1(input)

                	assert.Equal(t, 0, actual)
                }

            """.trimIndent()

        val contentMain =
            """
                package main

                import (
                	day "anisch.github.com/advent-of-code-2023/golang/day01"
                	"anisch.github.com/advent-of-code-2023/golang/util"
                )

                func main() {
                	input, err := util.ReadFile("day")
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
