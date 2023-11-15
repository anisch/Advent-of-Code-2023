package day01_test

import (
	"testing"

	day "anisch.github.com/advent-of-code-2023/golang/day01"
	"anisch.github.com/advent-of-code-2023/golang/util"
	"github.com/stretchr/testify/assert"
)

func TestPart1(t *testing.T) {
	input, err := util.ReadFile("day_test")
	if err != nil {
		t.Error(err)
	}
	actual := day.Part1(input)

	assert.Equal(t, 0, actual)
}
