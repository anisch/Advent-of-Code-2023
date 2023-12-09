package main

import (
	day "anisch.github.com/advent-of-code-2023/golang/day09"
	"anisch.github.com/advent-of-code-2023/golang/util"
)

func main() {
	input, err := util.ReadFile("Day09", "../../../")
	if err != nil {
		panic(err)
	}

	println(day.Part1(input)) // 1921197370
	println(day.Part2(input)) // 1124
}
