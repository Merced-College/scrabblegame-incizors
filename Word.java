// Garret Clark
// 7 - 5 - 25
// This program is a Scrabble game that will give the user four random letters to make into a word.

import java.util.Random;

public class Word {
    private String letters;

    // Constructor that generates a random 4-letter word
    public Word() {
        this.letters = generateRandomLetters(4);
    }

    // Optional constructor for manual word input
    public Word(String letters) {
        this.letters = letters.toUpperCase();
    }

    // Getter method
    public String getLetters() {
        return letters;
    }
    
    // Random letter generator
    public void reroll() {
        this.letters = generateRandomLetters(4);
    } 

    // Generates a random string of uppercase letters
    private String generateRandomLetters(int length) {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphabet.length());
            result.append(alphabet.charAt(index));
        }

        return result.toString();
    }

    @Override
    public String toString() {
        return letters;
    }
}
