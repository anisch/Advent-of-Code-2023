package util

func Chunked[T ~[]A, A any](t T, size int) []T {
	var result []T

	var tmp T
	for i := range t {
		tmp = append(tmp, t[i])
		if (i+1)%size == 0 || i == len(t)-1 {
			result = append(result, tmp)
			tmp = T{}
		}
	}

	return result
}
