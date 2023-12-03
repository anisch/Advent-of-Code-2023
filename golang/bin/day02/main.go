package main

import (
	day "anisch.github.com/advent-of-code-2023/golang/day02"
	"anisch.github.com/advent-of-code-2023/golang/util"
)

func main() {
	input, err := util.ReadFile("Day02", "../../../")
	if err != nil {
		panic(err)
	}

	println(day.Part1(input)) // 2105
	println(day.Part2(input)) // 72422
}
