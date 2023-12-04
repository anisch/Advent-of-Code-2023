package main

import (
    day "anisch.github.com/advent-of-code-2023/golang/day04"
    "anisch.github.com/advent-of-code-2023/golang/util"
)

func main() {
    input, err := util.ReadFile("Day04", "../../../")
    if err != nil {
        panic(err)
    }

    println(day.Part1(input)) // 26443
    println(day.Part2(input)) // 6284877
}
