package main

import (
	day "anisch.github.com/advent-of-code-2023/golang/day11"
	"anisch.github.com/advent-of-code-2023/golang/util"
)

func main() {
	input, err := util.ReadFile("Day11", "../../../")
	if err != nil {
		panic(err)
	}

	println(day.Part1(input))            // 9918828
	println(day.Part2(input, 1_000_000)) // 692506533832
}
