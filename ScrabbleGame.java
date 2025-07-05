import java.util.*;
import java.io.*;

public class ScrabbleGame {
    private ArrayList<Word> dictionary = new ArrayList<>();

    public void loadWords(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine().trim();
                if (word.length() >= 2) {  // optional filter
                    dictionary.add(new Word(word));
                }
            }
            Collections.sort(dictionary);  // must be sorted for binary search
        } catch (FileNotFoundException e) {
            System.out.println("Dictionary file not found.");
        }
    }

    public void play() {
        // Generate 4 random letters
        Random rand = new Random();
        char[] letters = new char[4];
        for (int i = 0; i < 4; i++) {
            letters[i] = (char) ('A' + rand.nextInt(26));
        }

        System.out.println("Your letters: " + Arrays.toString(letters));
        System.out.print("Enter a word using these letters: ");

        Scanner input = new Scanner(System.in);
        String userWord = input.nextLine().toUpperCase();

        if (!isWordMadeFromLetters(userWord, letters)) {
            System.out.println("Invalid: your word uses letters not provided.");
            return;
        }

        boolean found = binarySearch(userWord);
        if (found) {
            System.out.println("Valid word!");
        } else {
            System.out.println("Not a valid Scrabble word.");
        }
    }

    private boolean isWordMadeFromLetters(String word, char[] letters) {
        Map<Character, Integer> available = new HashMap<>();
        for (char c : letters) {
            available.put(c, available.getOrDefault(c, 0) + 1);
        }

        for (char c : word.toCharArray()) {
            if (!available.containsKey(c) || available.get(c) == 0) {
                return false;
            }
            available.put(c, available.get(c) - 1);
        }
        return true;
    }

    private boolean binarySearch(String word) {
        int low = 0;
        int high = dictionary.size() - 1;
        Word key = new Word(word);

        while (low <= high) {
            int mid = (low + high) / 2;
            int cmp = dictionary.get(mid).compareTo(key);
            if (cmp == 0) {
                return true;
            } else if (cmp < 0) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        ScrabbleGame game = new ScrabbleGame();
        game.loadWords("CollinsScrabbleWords_2019.txt");
        game.play();
    }
}
