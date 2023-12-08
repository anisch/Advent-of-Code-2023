package main

import (
	day "anisch.github.com/advent-of-code-2023/golang/day08"
	"anisch.github.com/advent-of-code-2023/golang/util"
)

func main() {
	input, err := util.ReadFile("Day08", "../../../")
	if err != nil {
		panic(err)
	}

	println(day.Part1(input)) // 12361
	println(day.Part2(input)) // 18215611419223
}
