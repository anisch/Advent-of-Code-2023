package day04

import (
	"math"
	"strconv"
	"strings"
)

type GameCard struct {
	Winning []int
	Numbers []int
}

type Match struct {
	Wins   int
	Copies int
}

func (gc GameCard) getPoints() int {
	result := []int{}

	for _, n := range gc.Numbers {
		for _, w := range gc.Winning {
			if w == n {
				result = append(result, w)
			}
		}
	}

	return int(math.Pow(2, float64(len(result)-1)))
}

func (gc GameCard) countWinningNumbers() int {
	result := []int{}

	for _, n := range gc.Numbers {
		for _, w := range gc.Winning {
			if w == n {
				result = append(result, w)
			}
		}
	}

	return len(result)
}

func Part1(input []string) int {
	sum := 0

	for _, line := range input {
		sum += parseGameCard(line).getPoints()
	}

	return sum
}

func Part2(input []string) int {
	matches := []Match{}

	for _, line := range input {
		gc := parseGameCard(line)
		wins := gc.countWinningNumbers()
		matches = append(matches, Match{Wins: wins, Copies: 1})
	}

	for idx := range matches {
		for x := 1; x <= matches[idx].Wins; x++ {
			matches[idx+x].Copies += matches[idx].Copies
		}
	}

	sum := 0
	for _, m := range matches {
		sum += m.Copies
	}

	return sum
}

func parseGameCard(line string) GameCard {
	tmp := strings.Split(line, ":")
	tmp = strings.Split(tmp[1], "|")

	wins := mapToInt(chunked(tmp[0][1:], 3))
	nums := mapToInt(chunked(tmp[1][1:], 3))

	return GameCard{Winning: wins, Numbers: nums}
}

func chunked(t string, size int) []string {
	var result []string
	var tmp []rune

	for i := range t {
		tmp = append(tmp, rune(t[i]))
		if (i+1)%size == 0 || i == len(t)-1 {
			result = append(result, string(tmp))
			tmp = []rune{}
		}
	}

	return result
}

func mapToInt(list []string) []int {
	result := []int{}

	for _, s := range list {
		s = strings.TrimSpace(s)
		i, err := strconv.Atoi(s)
		if err != nil {
			panic(err)
		}
		result = append(result, i)
	}

	return result
}
