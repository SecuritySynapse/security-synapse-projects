package main

import (
	"fmt"
	"sort"
	"strings"
)

// Function to encrypt the plaintext using a columnar transposition cipher
func encrypt(plaintext, key string) string {
	// Remove spaces from plaintext
	plaintext = strings.ReplaceAll(plaintext, " ", "")
	keyLength := len(key)
	numRows := (len(plaintext) + keyLength - 1) / keyLength // ceil division to calculate rows

	// Create a 2D slice to hold the table
	grid := make([][]rune, numRows)
	for i := range grid {
		grid[i] = make([]rune, keyLength)
	}

	// Fill the grid with the plaintext
	for i, char := range plaintext {
		row := i / keyLength
		col := i % keyLength
		grid[row][col] = char
	}

	// Pad the last row if necessary
	for i := len(plaintext); i < numRows*keyLength; i++ {
		grid[i/keyLength][i%keyLength] = 'X'
	}

	// Create a mapping from the key characters to their sorted positions
	type pair struct {
		char  rune
		index int
	}
	pairs := make([]pair, keyLength)
	for i, char := range key {
		pairs[i] = pair{char, i}
	}
	sort.Slice(pairs, func(i, j int) bool {
		return pairs[i].char < pairs[j].char
	})

	// Read the columns based on the sorted key
	var ciphertext strings.Builder
	for _, p := range pairs {
		for row := 0; row < numRows; row++ {
			ciphertext.WriteRune(grid[row][p.index])
		}
	}

	return ciphertext.String()
}

// Function to decrypt the ciphertext using a columnar transposition cipher
func decrypt(ciphertext, key string) string {
	keyLength := len(key)
	numRows := (len(ciphertext) + keyLength - 1) / keyLength

	// Create a mapping from the key characters to their sorted positions
	type pair struct {
		char  rune
		index int
	}
	pairs := make([]pair, keyLength)
	for i, char := range key {
		pairs[i] = pair{char, i}
	}
	sort.Slice(pairs, func(i, j int) bool {
		return pairs[i].char < pairs[j].char
	})

	// Create an empty grid to hold the rows
	grid := make([][]rune, numRows)
	for i := range grid {
		grid[i] = make([]rune, keyLength)
	}

	// Fill the columns based on the sorted key
	idx := 0
	for _, p := range pairs {
		for row := 0; row < numRows; row++ {
			grid[row][p.index] = rune(ciphertext[idx])
			idx++
		}
	}

	// Read the rows to get the original plaintext
	var plaintext strings.Builder
	for row := 0; row < numRows; row++ {
		for col := 0; col < keyLength; col++ {
			plaintext.WriteRune(grid[row][col])
		}
	}

	return strings.TrimRight(plaintext.String(), "X") // Remove padding if any
}

func main() {
	plaintext := "WE ARE DISCOVERED FLEE AT ONCE"
	key := "ZEBRA"

	// Output 1: Plain text before encryption
	fmt.Println("Plain text before encryption:", plaintext)

	// Encrypt the plaintext
	encrypted := encrypt(plaintext, key)

	// Output 2: Cipher text after encryption
	fmt.Println("Cipher text after encryption:", encrypted)

	// Decrypt the cipher text
	decrypted := decrypt(encrypted, key)

	// Output 3: Plain text after decryption
	fmt.Println("Plain text after decryption:", decrypted)
}
