package day06

import (
	"regexp"
	"strconv"
	"strings"
)

func Part1(input []string) int {
	regex := regexp.MustCompile(`\s+`)

	times := []int{}
	distances := []int{}

	tmp := regex.Split(input[0], -1)
	for i, v := range tmp {
		if i == 0 {
			continue
		}
		t, err := strconv.Atoi(v)
		if err != nil {
			panic(err)
		}
		times = append(times, t)

	}

	tmp = regex.Split(input[1], -1)
	for i, v := range tmp {
		if i == 0 {
			continue
		}

		d, err := strconv.Atoi(v)
		if err != nil {
			panic(err)
		}
		distances = append(distances, d)
	}

	result := 1
	for idx, time := range times {
		count := 0
		for speed := 0; speed <= time; speed++ {
			if speed*(time-speed) > distances[idx] {
				count++
			}
		}
		result *= count
	}

	return result
}

func Part2(input []string) int {

	tmp := strings.Split(input[0], ":")
	tmpTime := strings.ReplaceAll(tmp[1], " ", "")
	tmp = strings.Split(input[1], ":")
	tmpDistance := strings.ReplaceAll(tmp[1], " ", "")

	time, err := strconv.ParseInt(tmpTime, 10, 64)
	if err != nil {
		panic(err)
	}

	distance, err := strconv.ParseInt(tmpDistance, 10, 64)
	if err != nil {
		panic(err)
	}

	count := 0
	for speed := int64(0); speed <= time; speed++ {
		if speed*(time-int64(speed)) > distance {
			count++
		}
	}

	return count
}
