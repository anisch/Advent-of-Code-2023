package day01

import (
	"regexp"
	"strconv"
	"unicode"
)

func Part1(input []string) int {
	sum := 0

	for _, line := range input {
		tmp := []rune{}
		for _, c := range line {
			if unicode.IsDigit(c) {
				tmp = append(tmp, c)
			}
		}

		first := tmp[0]
		last := tmp[len(tmp)-1]
		tmp = []rune{first, last}
		num, _ := strconv.Atoi(string(tmp))
		sum += num
	}

	return sum
}

func Part2(input []string) int {

	regex := regexp.MustCompile(`one|two|three|four|five|six|seven|eight|nine|[\d]`)

	sum := 0
	for _, line := range input {
		first := firstMatch(line, regex)
		last := lastMatch(line, regex)

		fi := stringToNum(first)
		li := stringToNum(last)

		sum += fi * 10 + li
	}
	return sum
}

func firstMatch(s string, r *regexp.Regexp) string {
	return r.FindString(s)
}

func lastMatch(s string, r *regexp.Regexp) string {
	i := len(s)
	j := 0
	for j <= i {
		tmp := r.FindString(s[i-j : i])
		if tmp != "" {
			return tmp
		}
		j++
	}
	return ""
}

func stringToNum(s string) int {
	switch s {
	case "one":
		return 1
	case "two":
		return 2
	case "three":
		return 3
	case "four":
		return 4
	case "five":
		return 5
	case "six":
		return 6
	case "seven":
		return 7
	case "eight":
		return 8
	case "nine":
		return 9
	default:
		i, _ := strconv.Atoi(s)
		return i
	}
}
