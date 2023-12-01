package main

import (
	day "anisch.github.com/advent-of-code-2023/golang/day01"
	"anisch.github.com/advent-of-code-2023/golang/util"
)

func main() {
	input, err := util.ReadFile("Day01", "../../../")
	if err != nil {
		panic(err)
	}

	println(day.Part1(input)) // 54697
	println(day.Part2(input)) // 54885
}
