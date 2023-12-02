package day02_test

import (
    "testing"

    day "anisch.github.com/advent-of-code-2023/golang/day02"
    "anisch.github.com/advent-of-code-2023/golang/util"
    "github.com/stretchr/testify/assert"
)

const rel = "../../"

func TestPart1(t *testing.T) {
    input, err := util.ReadFile("Day02_test", rel)
    if err != nil {
        t.Error(err)
    }
    actual := day.Part1(input)

    assert.Equal(t, 8, actual)
}

func TestPart2(t *testing.T) {
    input, err := util.ReadFile("Day02_test", rel)
    if err != nil {
        t.Error(err)
    }
    actual := day.Part2(input)

    assert.Equal(t, 2286, actual)
}

func BenchmarkPart1(b *testing.B) {
    input, err := util.ReadFile("Day02", rel)
    if err != nil {
        b.Error(err)
    }

    for n := 0; n < b.N; n++ {
        day.Part1(input)
    }
}

func BenchmarkPart2(b *testing.B) {
    input, err := util.ReadFile("Day02", rel)
    if err != nil {
        b.Error(err)
    }

    for n := 0; n < b.N; n++ {
        day.Part2(input)
    }
}
