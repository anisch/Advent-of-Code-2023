package main

import (
	day "anisch.github.com/advent-of-code-2023/golang/day06"
	"anisch.github.com/advent-of-code-2023/golang/util"
)

func main() {
	input, err := util.ReadFile("Day06", "../../../")
	if err != nil {
		panic(err)
	}

	println(day.Part1(input)) // 1710720
	println(day.Part2(input)) // 35349468
}
