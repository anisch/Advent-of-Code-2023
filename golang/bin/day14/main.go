package main

import (
    day "anisch.github.com/advent-of-code-2023/golang/day14"
    "anisch.github.com/advent-of-code-2023/golang/util"
)

func main() {
    input, err := util.ReadFile("Day14", "../../../")
    if err != nil {
        panic(err)
    }

    println(day.Part1(input)) // 110090
    println(day.Part2(input)) // 95254
}
