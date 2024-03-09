import java.util.Random;
import java.util.Scanner;

public class NUMBER_GUESSING_GAME {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();
        int lowerBound = 1;
        int upperBound = 100;
        int attempts = 0;
        int maxAttempts = 10;
        int score = 0;

        System.out.println("Welcome to Guess the Number game!");
        System.out.println("I'm thinking of a number between " + lowerBound + " and " + upperBound);

        int randomNumber = random.nextInt(upperBound - lowerBound + 1) + lowerBound;

        while (attempts < maxAttempts) {
            System.out.print("Enter your guess: ");
            int guess = scanner.nextInt();
            attempts++;

            if (guess == randomNumber) {
                System.out.println("Congratulations! You guessed the number correctly in " + attempts + " attempts.");
                score = (maxAttempts - attempts + 1) * 10; // Score calculation based on attempts
                break;
            } else if (guess < randomNumber) {
                System.out.println("your guess is lesser than the number! Try again.");
            } else {
                System.out.println("your guess is greater than the number! Try again.");
            }
        }

        if (attempts == maxAttempts) {
            System.out.println("Sorry, you've reached the maximum number of attempts. The number was " + randomNumber);
        }

        System.out.println("Your score: " + score);
    }
}
