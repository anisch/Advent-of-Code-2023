package util

func ChunkedList[T any](input []T, size int) [][]T {
	var result [][]T

	var tmp []T
	for i, v := range input {
		tmp = append(tmp, v)
		if (i+1)%size == 0 || i == len(input)-1 {
			result = append(result, tmp)
			tmp = []T{}
		}
	}

	return result
}
