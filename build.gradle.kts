plugins {
    kotlin("jvm") version "1.9.20"
}

repositories {
    mavenCentral()
}

tasks {
    sourceSets {
        main {
            java.srcDirs("src")
        }
    }

    wrapper {
        gradleVersion = "8.4"
    }
}

abstract class DayGeneratorTask : DefaultTask() {
    @get:Input
    @get:Option(option = "day", description = "set the day")
    abstract val day: Property<Int>

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
        targetFile.writeText(content)
    }
}

// Customize the greeting
tasks.register<DayGeneratorTask>("createDay") {
    day = 1
    outputDir = layout.projectDirectory.dir("src")
}
