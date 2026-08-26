import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        int round = 1;
        int totalWins = 0;
        String playAgain;

        System.out.println("======================================");
        System.out.println("       NUMBER GUESSING GAME");
        System.out.println("======================================");

        do {

            // Generate a random number between 1 and 100
            int secretNumber = random.nextInt(100) + 1;

            int maxAttempts = 7;
            int attempts = 0;
            boolean correct = false;

            System.out.println("\nRound " + round);
            System.out.println("I have selected a number between 1 and 100.");
            System.out.println("You have " + maxAttempts + " attempts.");
            System.out.println("--------------------------------------");

            // Allow the user to guess up to 7 times
            while (attempts < maxAttempts && !correct) {

                System.out.print("Enter your guess: ");
                int guess = scanner.nextInt();

                // Validate the guess
                if (guess < 1 || guess > 100) {
                    System.out.println("Please enter a number between 1 and 100.");
                    continue;
                }

                attempts++;

                System.out.println("Attempt " + attempts + " of " + maxAttempts);

                // Compare the guess with the secret number
                if (guess > secretNumber) {
                    System.out.println("Too High!");

                } else if (guess < secretNumber) {
                    System.out.println("Too Low!");

                } else {
                    System.out.println("Correct!");
                    System.out.println(
                            "Congratulations! You guessed the number in "
                                    + attempts + " attempts.");

                    correct = true;
                    totalWins++;
                }

                System.out.println();
            }

            // If the user did not guess correctly
            if (!correct) {
                System.out.println("You Lost!");
                System.out.println("The correct number was: " + secretNumber);
            }

            // Display round summary
            System.out.println("--------------------------------------");

            if (correct) {
                System.out.println(
                        "Round " + round + " — guessed in "
                                + attempts + " attempts");
            } else {
                System.out.println(
                        "Round " + round + " — Not guessed");
            }

            System.out.println("Total Rounds Won: " + totalWins);
            System.out.println("--------------------------------------");

            // Ask whether the user wants another round
            System.out.print("Do you want to Play Again? (yes/no): ");
            playAgain = scanner.next();

            round++;

        } while (playAgain.equalsIgnoreCase("yes"));

        // Final game summary
        System.out.println("\n======================================");
        System.out.println("          GAME OVER");
        System.out.println("======================================");
        System.out.println("Total Rounds Played: " + (round - 1));
        System.out.println("Total Rounds Won: " + totalWins);
        System.out.println("Thank you for playing!");
        System.out.println("======================================");

        scanner.close();
    }
}
