package day08_test

import (
	"testing"

	day "anisch.github.com/advent-of-code-2023/golang/day08"
	"anisch.github.com/advent-of-code-2023/golang/util"
	"github.com/stretchr/testify/assert"
)

func TestPart1(t *testing.T) {
	input, err := util.ReadFile("Day08_test1", "../../")
	if err != nil {
		t.Error(err)
	}
	actual := day.Part1(input)

	assert.Equal(t, 6, actual)
}

func TestPart2(t *testing.T) {
	input, err := util.ReadFile("Day08_test2", "../../")
	if err != nil {
		t.Error(err)
	}
	actual := day.Part2(input)

	assert.Equal(t, int64(6), actual)
}

func BenchmarkPart1(b *testing.B) {
	input, err := util.ReadFile("Day08", "../../")
	if err != nil {
		b.Error(err)
	}

	for n := 0; n < b.N; n++ {
		day.Part1(input)
	}
}

func BenchmarkPart2(b *testing.B) {
	input, err := util.ReadFile("Day08", "../../")
	if err != nil {
		b.Error(err)
	}

	for n := 0; n < b.N; n++ {
		day.Part2(input)
	}
}
