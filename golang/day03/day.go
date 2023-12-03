package day03

import (
	"regexp"
	"strconv"
	"unicode"
)

type Vec2D struct {
	X int
	Y int
}

type PartNumber struct {
	Num    int
	XRange []int
	Y      int
}

func (pn PartNumber) touchesSymbol(vec Vec2D) bool {
	return inRange(pn.Y, vec.Y-1, vec.Y+1) &&
		inRange(vec.X, pn.XRange[0]-1, pn.XRange[len(pn.XRange)-1])
}

func (pn PartNumber) touchesSymbols(symbols []Vec2D) bool {
	for _, s := range symbols {
		if pn.touchesSymbol(s) {
			return true
		}
	}
	return false
}

func Part1(input []string) int {

	symbols := parseSymbols(input)
	nums := parseNums(input)

	result := []PartNumber{}
	for _, pn := range nums {
		if pn.touchesSymbols(symbols) {
			result = append(result, pn)
		}
	}

	sum := 0
	for _, pn := range result {
		sum += pn.Num
	}
	return sum
}

func Part2(input []string) int {
	symbols := parseSymbols(input)
	nums := parseNums(input)

	touch := []PartNumber{}
	for _, pn := range nums {
		if pn.touchesSymbols(symbols) {
			touch = append(touch, pn)
		}
	}

	result := [][]PartNumber{}
	for _, symbol := range symbols {
		tmp := []PartNumber{}
		for _, part := range touch {
			if part.touchesSymbol(symbol) {
				tmp = append(tmp, part)
			}
		}

		if len(tmp) == 2 {
			result = append(result, tmp)
		}
	}

	sum := 0
	for _, pns := range result {
		sum += (pns[0].Num * pns[1].Num)
	}

	return sum
}

func parseSymbols(input []string) []Vec2D {
	result := []Vec2D{}

	for y, line := range input {
		for x, c := range line {
			if c == '.' || unicode.IsDigit(c) {
				continue
			}
			result = append(result, Vec2D{x, y})
		}
	}

	return result
}

func parseNums(input []string) []PartNumber {
	regex := regexp.MustCompile(`\d+`)

	result := []PartNumber{}
	for y, line := range input {
		tmp := regex.FindAllStringIndex(line, -1)

		for _, xRange := range tmp {
			s := line[xRange[0]:xRange[1]]
			num, err := strconv.Atoi(s)
			if err != nil {
				panic(err)
			}

			result = append(result, PartNumber{
				Num:    num,
				XRange: xRange,
				Y:      y,
			})
		}
	}

	return result
}

func inRange(x, min, max int) bool {
	return min <= x && x <= max
}
