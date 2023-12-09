package day09

import (
	"strconv"
	"strings"
)

func Part1(input []string) int {

	list := stringListToIntList(input)

	result := 0
	for _, l := range list {
		tmp := [][]int{l}

		for x := 0; ; x++ {
			diffs := getDiffs(tmp[x])
			tmp = append(tmp, diffs)

			if all(diffs, func(i int) bool {
				return i == 0
			}) {
				break
			}
		}

		for x := len(tmp) - 2; x >= 0; x-- {
			h := tmp[x][len(tmp[x])-1] + tmp[x+1][len(tmp[x+1])-1]
			tmp[x] = append(tmp[x], h)
		}

		result += tmp[0][len(tmp[0])-1]
	}

	return result
}

func Part2(input []string) int {

	list := stringListToIntList(input)

	result := 0
	for _, l := range list {
		tmp := [][]int{l}

		for x := 0; ; x++ {
			diffs := getDiffs(tmp[x])
			tmp = append(tmp, diffs)

			if all(diffs, func(i int) bool {
				return i == 0
			}) {
				break
			}
		}

		for x := len(tmp) - 2; x >= 0; x-- {
			h := tmp[x][0] - tmp[x+1][0]
			tmp[x] = append([]int{h}, tmp[x]...)
		}

		result += tmp[0][0]
	}

	return result
}

func stringListToIntList(list []string) (result [][]int) {
	for _, line := range list {
		sNums := strings.Split(line, " ")

		tmp := []int{}
		for _, s := range sNums {
			i, err := strconv.Atoi(s)
			if err != nil {
				panic("Something bad happen!")
			}
			tmp = append(tmp, i)
		}
		result = append(result, tmp)
	}

	return result
}

func getDiffs(list []int) (result []int) {
	for x := 1; x < len(list); x++ {
		result = append(result, list[x]-list[x-1])
	}
	return result
}

func all[S ~[]E, E any](list S, pred func(e E) bool) bool {
	for _, elem := range list {
		if !pred(elem) {
			return false
		}
	}
	return true
}
