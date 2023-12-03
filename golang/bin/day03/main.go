package main

import (
	day "anisch.github.com/advent-of-code-2023/golang/day03"
	"anisch.github.com/advent-of-code-2023/golang/util"
)

func main() {
	input, err := util.ReadFile("Day03", "../../../")
	if err != nil {
		panic(err)
	}

	println(day.Part1(input)) // 539433
	println(day.Part2(input)) // 75847567
}
