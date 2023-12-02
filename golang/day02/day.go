package day02

import (
	"cmp"
	"slices"
	"strconv"
	"strings"

	"golang.org/x/exp/constraints"
)

type Set struct {
	Color string
	Count int
}

type Grab struct {
	Red   Set
	Green Set
	Blue  Set
}

type Game struct {
	Id    int
	Grabs []Grab
}

func Part1(input []string) int {

	games := []Game{}
	for _, line := range input {
		game := parseGame(line)
		games = append(games, game)
	}

	ids := []int{}
	for _, game := range games {
		r := all(game.Grabs, func(g Grab) bool {
			return g.Red.Count <= 12
		})
		g := all(game.Grabs, func(g Grab) bool {
			return g.Green.Count <= 13
		})
		b := all(game.Grabs, func(g Grab) bool {
			return g.Blue.Count <= 14
		})
		if r && g && b {
			ids = append(ids, game.Id)
		}
	}

	return sum(ids)
}

func Part2(input []string) int {

	games := []Game{}
	for _, line := range input {
		game := parseGame(line)
		games = append(games, game)
	}

	sum := 0
	for _, game := range games {
		r := slices.MaxFunc(game.Grabs, func(a, b Grab) int {
			return cmp.Compare(a.Red.Count, b.Red.Count)
		})
		g := slices.MaxFunc(game.Grabs, func(a, b Grab) int {
			return cmp.Compare(a.Green.Count, b.Green.Count)
		})
		b := slices.MaxFunc(game.Grabs, func(a, b Grab) int {
			return cmp.Compare(a.Blue.Count, b.Blue.Count)
		})
		sum += (r.Red.Count * g.Green.Count * b.Blue.Count)
	}

	return sum
}

func parseGame(str string) Game {
	h := strings.Split(str, ":")

	game := strings.Split(h[0], " ")
	id, err := strconv.Atoi(game[1])
	if err != nil {
		panic(err)
	}
	h = strings.Split(h[1], ";")

	grabs := []Grab{}
	for _, s := range h {
		g := parseGrab(s)
		grabs = append(grabs, g)
	}

	return Game{id, grabs}
}

func parseGrab(str string) Grab {
	str = strings.TrimSpace(str)
	h := strings.Split(str, ",")

	sets := []Set{}
	for _, s := range h {
		set := parseSet(s)
		sets = append(sets, set)
	}

	r := firstOrDefaultSet(sets, "red")
	g := firstOrDefaultSet(sets, "green")
	b := firstOrDefaultSet(sets, "blue")

	return Grab{r, g, b}
}

func parseSet(str string) Set {
	str = strings.TrimSpace(str)
	h := strings.Split(str, " ")
	i, err := strconv.Atoi(h[0])
	if err != nil {
		panic(err)
	}
	return Set{h[1], i}
}

func firstOrDefaultSet(sets []Set, color string) Set {
	for _, s := range sets {
		if s.Color == color {
			return s
		}
	}
	return Set{color, 0}
}

func all[S ~[]E, E any](list S, pred func(e E) bool) bool {
	for _, elem := range list {
		if pred(elem) == false {
			return false
		}
	}
	return true
}

func sum[S ~[]E, E constraints.Integer](list S) int {
	sum := 0
	for _, i := range list {
		sum += int(i)
	}
	return sum
}
