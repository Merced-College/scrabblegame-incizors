import java.util.*;
import java.io.*;

public class ScrabbleGame {

    private static ArrayList<String> validWords = new ArrayList<>();

    public static void main(String[] args) {
        // Load the dictionary words into memory from the file
        loadDictionary("CollinsScarbbleWords_2019.txt");

        Scanner scanner = new Scanner(System.in);
        Word randomWord = new Word();

        // === REROLL LETTERS LOOP ===
        // Prompt the user if they want to reroll their 4 letters until they say 'N'
        while (true) {
            System.out.println("Your letters are: " + randomWord);
            System.out.print("Would you like to reroll these letters? (Y/N): ");
            String rerollChoice = scanner.nextLine().trim().toUpperCase();

            if (rerollChoice.equals("Y")) {
                randomWord.reroll();  // Generate a new set of letters
            } else if (rerollChoice.equals("N")) {
                break;  // Exit loop and proceed with the game
            } else {
                System.out.println("Please enter 'Y' or 'N'.");  // Invalid input message
            }
        }

        // === MULTIPLE WORD INPUTS LOOP ===
        // Inform user how to stop inputting words
        System.out.println("Enter words using only those letters. Type 'QUIT' to stop.");

        // Continuously prompt the user for words until they type 'QUIT'
        int attempts = 0;  // Count how many tries user makes

        while (true) {
            System.out.print("Enter a word: ");
            String userInput = scanner.nextLine().toUpperCase().trim();

            if (userInput.equals("QUIT")) {
                System.out.println("Thanks for playing!");
                break;
            }

            attempts++;  // Increment attempts on each try

            if (!usesOnlyGivenLetters(userInput, randomWord.getLetters())) {
                System.out.println("Invalid: You used letters that were not in the original 4.");
            } else if (isValidWord(userInput)) {
                System.out.println("Valid word!");
                System.out.println("It took you " + attempts + " attempt" + (attempts > 1 ? "s" : "") + " to find a valid word.");
                break;  // Exit after correct word found
            } else {
                System.out.println("That word is not in the Scrabble dictionary.");
            }
        }

        scanner.close();
    }

    // Loads all valid Scrabble words from the dictionary file into an ArrayList
    private static void loadDictionary(String filename) {
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                validWords.add(fileScanner.nextLine().trim().toUpperCase());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: Could not find the dictionary file.");
        }
    }

    // Checks if the user's word uses only letters from the allowed set (repeats allowed)
    private static boolean usesOnlyGivenLetters(String word, String allowedLetters) {
        for (char c : word.toCharArray()) {
            if (!allowedLetters.contains(String.valueOf(c))) {
                return false;
            }
        }
        return true;
    }

    // Checks if the user's word exists in the loaded dictionary
    private static boolean isValidWord(String word) {
        return validWords.contains(word);
    }
}
