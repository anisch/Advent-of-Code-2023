package util

func ChunckedString(s string, size int) []string {
	var result []string

	var tmp []rune
	for i, c := range s {
		tmp = append(tmp, c)
		if (i+1)%size == 0 || i == len(s)-1 {
			result = append(result, string(tmp))
			tmp = []rune{}
		}
	}

	return result
}
