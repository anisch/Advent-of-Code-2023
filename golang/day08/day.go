package day08

type Node struct {
	Left  string
	Right string
}

func Part1(input []string) int {
	navigation := chunked(input[0], 1)

	nodes := map[string]Node{}
	for _, line := range input[2:] {
		key := line[0:3]
		left := line[7:10]
		right := line[12:15]

		nodes[key] = Node{Left: left, Right: right}
	}

	start := "AAA"
	navIdx := 0
	count := 0

	for ; start != "ZZZ"; count++ {
		dir := navigation[navIdx]

		if dir == "L" {
			start = nodes[start].Left
		} else {
			start = nodes[start].Right
		}

		navIdx++
		if navIdx >= len(navigation) {
			navIdx = 0
		}
	}

	return count
}

func Part2(input []string) int64 {
	navigation := chunked(input[0], 1)

	nodes := map[string]Node{}
	for _, line := range input[2:] {
		key := line[0:3]
		left := line[7:10]
		right := line[12:15]

		nodes[key] = Node{Left: left, Right: right}
	}

	startNods := []string{}
	for node := range nodes {
		if node[2] == 'A' {
			startNods = append(startNods, node)
		}
	}

	countList := []int{}
	for _, node := range startNods {
		start := node
		navIdx := 0
		count := 0

		for ; start[2] != 'Z'; count++ {
			dir := navigation[navIdx]

			if dir == "L" {
				start = nodes[start].Left
			} else {
				start = nodes[start].Right
			}

			navIdx++
			if navIdx >= len(navigation) {
				navIdx = 0
			}
		}
		countList = append(countList, count)
	}

	result := int64(countList[0])
	for x := 1; x < len(countList); x++ {
		a := result
		b := int64(countList[x])

		for a != b {
			if a < b {
				a += result
			} else {
				b += int64(countList[x])
			}
		}
		result = a
	}

	return result
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
