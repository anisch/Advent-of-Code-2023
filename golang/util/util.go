package util

import (
	"bufio"
	"errors"
	"os"
)

func ReadFile(name string) ([]string, error) {
	fileName := name + ".txt"

	if _, err := os.Stat(fileName); err == nil {
		var result []string

		file, err := os.Open(fileName)
		if err != nil {
			return nil, err
		}

		defer file.Close()

		scanner := bufio.NewScanner(file)
		for scanner.Scan() {
			result = append(result, scanner.Text())
		}

		return result, nil
	} else if errors.Is(err, os.ErrNotExist) {
		// path/to/whatever does *not* exist
		return nil, err
	} else {
		return nil, err
	}
}
