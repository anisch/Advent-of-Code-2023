package day11

const (
	EMPTY  = "."
	GALAXY = "#"
)

type Vec2D struct {
	X int
	Y int
}

type Image [][]string

func (i Image) emptyY() (result []int) {
	for y, line := range i {
		if all(line, func(s string) bool {
			return s == EMPTY
		}) {
			result = append(result, y)
		}
	}
	return result
}

func (i Image) emptyX() (result []int) {
	for x := 0; x < len(i[0]); x++ {
		allEmpty := true
		for y := 0; y < len(i); y++ {
			if i[y][x] == GALAXY {
				allEmpty = false
				break
			}
		}
		if allEmpty {
			result = append(result, x)
		}
	}
	return result
}

func (i Image) findGalaxys() (result []Vec2D) {
	for y := range i {
		for x, s := range i[y] {
			if s == GALAXY {
				result = append(result, Vec2D{
					X: x,
					Y: y,
				})
			}
		}
	}
	return result
}

func Part1(input []string) int64 {
	universe := Image{}
	for _, line := range input {
		tmp := chunked(line, 1)
		universe = append(universe, tmp)
	}

	galaxys := universe.findGalaxys()
	emptyX := universe.emptyX()
	emptyY := universe.emptyY()

	distances := getDistances(galaxys, emptyX, emptyY, 2)
	return sum(distances)
}

func Part2(input []string, factor int) int64 {
	universe := Image{}
	for _, line := range input {
		tmp := chunked(line, 1)
		universe = append(universe, tmp)
	}

	galaxys := universe.findGalaxys()
	emptyX := universe.emptyX()
	emptyY := universe.emptyY()

	distances := getDistances(galaxys, emptyX, emptyY, factor)
	return sum(distances)
}

func getDistances(vec []Vec2D, ex, ey []int, factor int) (result []int64) {
	b := 1
	for a := 0; a < len(vec)-1; a++ {
		for ; b < len(vec); b++ {
			ax := vec[a].X
			bx := vec[b].X
			ay := vec[a].Y
			by := vec[b].Y

			if ax > bx {
				ax, bx = bx, ax
			}
			if ay > by {
				ay, by = by, ay
			}
			xd := bx - ax
			xe := count(ex, ax, bx)
			yd := by - ay
			ye := count(ey, ay, by)
			r := xd + xe*(factor-1) + yd + ye*(factor-1)
			result = append(result, int64(r))
		}
		b = a + 2
	}
	return result
}

func count(list []int, start, end int) int {
	count := 0
	for _, v := range list {
		if start < v && v < end {
			count++
		}
	}
	return count
}

func sum(list []int64) int64 {
	result := int64(0)
	for _, v := range list {
		result += v
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
