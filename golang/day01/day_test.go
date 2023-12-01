package day01_test

import (
	"testing"

	day "anisch.github.com/advent-of-code-2023/golang/day01"
	"anisch.github.com/advent-of-code-2023/golang/util"
	"github.com/stretchr/testify/assert"
)

const rel = "../../"

func TestPart1(t *testing.T) {
	input, err := util.ReadFile("Day01_test1", rel)
	if err != nil {
		t.Error(err)
	}
	actual := day.Part1(input)

	assert.Equal(t, 142, actual)
}

func TestPart2(t *testing.T) {
	input, err := util.ReadFile("Day01_test2", rel)
	if err != nil {
		t.Error(err)
	}
	actual := day.Part2(input)

	assert.Equal(t, 281, actual)
}

func BenchmarkPart1(b *testing.B) {
	input, err := util.ReadFile("Day01", rel)
	if err != nil {
		b.Error(err)
	}

	for n := 0; n < b.N; n++ {
		day.Part1(input)
	}
}

func BenchmarkPart2(b *testing.B) {
	input, err := util.ReadFile("Day01", rel)
	if err != nil {
		b.Error(err)
	}

	for n := 0; n < b.N; n++ {
		day.Part2(input)
	}
}
