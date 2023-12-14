package day14

type field [][]rune

func Part1(input []string) int {
	tmp := field{}
	for _, line := range input {
		tmp = append(tmp, []rune(line))
	}

	moveAllRocksNorth(tmp)

	return sumUp(tmp)
}

func Part2(input []string) int {
	f := field{}
	for _, line := range input {
		f = append(f, []rune(line))
	}

	for i := 1; i <= 1000; i++ {
		moveAllRocksNorth(f)
		moveAllRocksWest(f)
		moveAllRocksSouth(f)
		moveAllRocksEast(f)
	}

	return sumUp(f)
}

func sumUp(s field) int {
	size := len(s)

	sum := 0
	for y := 0; y < size; y++ {
		sum += (size - y) * count(s[y])
	}

	return sum
}

func count(s []rune) int {
	count := 0
	for _, c := range s {
		if c == 'O' {
			count++
		}
	}
	return count
}

func moveAllRocksNorth(f field) {
	for y := 1; y < len(f); y++ {
		for x := 0; x < len(f[0]); x++ {
			if f[y][x] == 'O' {
				moveRockNorth(f, y, x)
			}
		}
	}
}

func moveAllRocksSouth(f field) {
	for y := len(f) - 2; 0 <= y; y-- {
		for x := 0; x < len(f[y]); x++ {
			if f[y][x] == 'O' {
				moveRockSouth(f, y, x)
			}
		}
	}
}

func moveAllRocksWest(f field) {
	for x := 1; x < len(f[0]); x++ {
		for y := 0; y < len(f); y++ {
			if f[y][x] == 'O' {
				moveRockWest(f, y, x)
			}
		}
	}
}

func moveAllRocksEast(f field) {
	for x := len(f[0]) - 2; 0 <= x; x-- {
		for y := 0; y < len(f); y++ {
			if f[y][x] == 'O' {
				moveRockEast(f, y, x)
			}
		}
	}
}

func moveRockNorth(f field, y, x int) {
	for ty := y; 0 < ty && f[ty-1][x] == '.'; ty-- {
		f[ty][x] = '.'
		f[ty-1][x] = 'O'
	}
}

func moveRockSouth(f field, y, x int) {
	for ty := y; ty < len(f)-1 && f[ty+1][x] == '.'; ty++ {
		f[ty][x] = '.'
		f[ty+1][x] = 'O'
	}
}

func moveRockWest(f field, y, x int) {
	for tx := x; 0 < tx && f[y][tx-1] == '.'; tx-- {
		f[y][tx] = '.'
		f[y][tx-1] = 'O'
	}
}

func moveRockEast(f field, y, x int) {
	for tx := x; tx < len(f[y])-1 && f[y][tx+1] == '.'; tx++ {
		f[y][tx] = '.'
		f[y][tx+1] = 'O'
	}
}
