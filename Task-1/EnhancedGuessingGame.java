import java.util.*;
import java.io.*;

public class EnhancedGuessingGame {
    private static final int EASY_MAX_ATTEMPTS = 10;
    private static final int MEDIUM_MAX_ATTEMPTS = 7;
    private static final int HARD_MAX_ATTEMPTS = 5;
    private static Map<String, Integer> leaderboard = new HashMap<>();

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        loadLeaderboard();
        
        System.out.println("Welcome to the Enhanced Number Guessing Game!");

        boolean playAgain = true;
        
        while (playAgain) {
            System.out.println("Choose difficulty level (easy, medium, hard): ");
            String difficulty = scanner.nextLine().toLowerCase();
            int maxAttempts = getMaxAttempts(difficulty);
            
            System.out.println("Enter number of players: ");
            int numPlayers = scanner.nextInt();
            scanner.nextLine();  // Consume the newline

            String[] players = new String[numPlayers];
            int[] scores = new int[numPlayers];
            for (int i = 0; i < numPlayers; i++) {
                System.out.println("Enter player " + (i + 1) + " name:");
                players[i] = scanner.nextLine();
            }

            int round = 1;
            boolean gameOver = false;
            
            while (!gameOver) {
                System.out.println("Round " + round);
                int numberToGuess = random.nextInt(100) + 1;
                
                for (int i = 0; i < numPlayers; i++) {
                    System.out.println(players[i] + ", it's your turn. You have " + maxAttempts + " attempts to guess.");
                    int attempts = 0;
                    boolean guessedCorrectly = false;

                    while (attempts < maxAttempts) {
                        System.out.print("Enter your guess: ");
                        int guess = scanner.nextInt();
                        attempts++;
                        
                        if (guess == numberToGuess) {
                            System.out.println("Correct! " + players[i] + " guessed it in " + attempts + " attempts.");
                            scores[i] += calculateScore(attempts, maxAttempts);
                            guessedCorrectly = true;
                            break;
                        } else if (guess > numberToGuess) {
                            giveHint("high", guess, numberToGuess);
                        } else {
                            giveHint("low", guess, numberToGuess);
                        }

                        if (attempts == maxAttempts) {
                            System.out.println("Out of attempts! The correct number was: " + numberToGuess);
                        }
                    }

                    if (guessedCorrectly) break; // Move to next round
                }

                System.out.print("Do you want to play another round? (yes/no): ");
                scanner.nextLine();  // Consume the newline
                playAgain = scanner.nextLine().equalsIgnoreCase("yes");
                if (!playAgain) {
                    gameOver = true;
                }
                round++;
            }

            // Display Final Scores
            displayScores(players, scores);
            
            // Save leaderboard
            updateLeaderboard(players, scores);
            saveLeaderboard();

            System.out.print("Do you want to start a new game? (yes/no): ");
            playAgain = scanner.nextLine().equalsIgnoreCase("yes");
        }

        System.out.println("Thanks for playing!");
        scanner.close();
    }

    private static int getMaxAttempts(String difficulty) {
        switch (difficulty) {
            case "easy":
                return EASY_MAX_ATTEMPTS;
            case "medium":
                return MEDIUM_MAX_ATTEMPTS;
            case "hard":
                return HARD_MAX_ATTEMPTS;
            default:
                System.out.println("Invalid difficulty! Defaulting to Medium.");
                return MEDIUM_MAX_ATTEMPTS;
        }
    }

    private static void giveHint(String direction, int guess, int numberToGuess) {
        int difference = Math.abs(guess - numberToGuess);
        if (direction.equals("high")) {
            if (difference > 20) {
                System.out.println("You're way too high! Try a much smaller number.");
            } else if (difference > 10) {
                System.out.println("Too high! You're getting closer.");
            } else {
                System.out.println("Slightly too high! Very close.");
            }
        } else {
            if (difference > 20) {
                System.out.println("You're way too low! Try a much higher number.");
            } else if (difference > 10) {
                System.out.println("Too low! You're getting closer.");
            } else {
                System.out.println("Slightly too low! Very close.");
            }
        }
    }

    private static int calculateScore(int attempts, int maxAttempts) {
        return (maxAttempts - attempts) * 10;
    }

    private static void displayScores(String[] players, int[] scores) {
        System.out.println("\nFinal Scores:");
        for (int i = 0; i < players.length; i++) {
            System.out.println(players[i] + ": " + scores[i] + " points");
        }
    }

    private static void loadLeaderboard() throws IOException {
        File file = new File("leaderboard.txt");
        if (file.exists()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(":");
                leaderboard.put(data[0], Integer.parseInt(data[1]));
            }
            reader.close();
        }
    }

    private static void updateLeaderboard(String[] players, int[] scores) {
        for (int i = 0; i < players.length; i++) {
            leaderboard.put(players[i], leaderboard.getOrDefault(players[i], 0) + scores[i]);
        }
    }

    private static void saveLeaderboard() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("leaderboard.txt"));
        for (Map.Entry<String, Integer> entry : leaderboard.entrySet()) {
            writer.write(entry.getKey() + ":" + entry.getValue());
            writer.newLine();
        }
        writer.close();
    }
}
