package main

import (
	day "anisch.github.com/advent-of-code-2023/golang/day15"
	"anisch.github.com/advent-of-code-2023/golang/util"
)

func main() {
	input, err := util.ReadFile("Day15", "../../../")
	if err != nil {
		panic(err)
	}

	println(day.Part1(input)) // 519041
	println(day.Part2(input)) // 260530
}
