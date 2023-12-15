package day15

import (
	"slices"
	"strconv"
	"strings"
)

type Label struct {
	Label string
	Focal *int
	Hash  int
}

func Part1(input []string) int {
	list := strings.Split(input[0], ",")

	sum := 0
	for _, s := range list {
		sum += hash(s)
	}

	return sum
}

func Part2(input []string) int {
	list := strings.Split(input[0], ",")

	labels := []Label{}
	for _, s := range list {
		last := s[len(s)-1]

		var focal *int
		if last != '-' {
			i, err := strconv.Atoi(string(last))
			if err != nil {
				panic("Something bad happen!")
			}
			focal = &i
		}
		text := s[:len(s)-1]
		if focal != nil {
			text = s[:len(s)-2]
		}

		label := Label{
			Label: text,
			Focal: focal,
			Hash:  hash(text),
		}
		labels = append(labels, label)
	}

	hashMap := map[int][]Label{}
	for _, label := range labels {
		box := hashMap[label.Hash]
		if label.Focal == nil {
			box = slices.DeleteFunc(box, func(l Label) bool {
				return l.Label == label.Label
			})

		} else {
			idx := slices.IndexFunc(box, func(l Label) bool {
				return l.Label == label.Label
			})
			if 0 <= idx {
				box[idx] = label
			} else {
				box = append(box, label)
			}
		}
		hashMap[label.Hash] = box
	}

	sum := 0
	for box, labels := range hashMap {
		for i := 1; i <= len(labels); i++ {
			h := (i * *labels[i-1].Focal) * (box + 1)
			sum += h
		}
	}

	return sum
}

func hash(s string) int {
	h := 0
	for _, r := range s {
		h += int(r)
		h *= 17
		h %= 256
	}
	return h
}
