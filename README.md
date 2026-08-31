# Task 2 – Number Guessing Game 🎯

## 📌 Project Overview

The Number Guessing Game is a simple Java-based console application developed as part of my Oasis Infobyte Internship.

In this game, the computer randomly generates a number within a specified range, and the player has to guess the number within a limited number of attempts. After each guess, the program provides a hint indicating whether the guessed number is too high or too low.

## ✨ Features

- Generates a random number automatically.
- Allows the user to enter guesses using the keyboard.
- Provides "Too High" and "Too Low" hints.
- Displays "Correct" when the user guesses the number.
- Keeps track of the number of attempts.
- Provides different difficulty levels.
- Limits the number of attempts based on the selected difficulty.
- Allows the player to play multiple rounds.
- Displays a game summary after playing.

## 🎮 Difficulty Levels

| Level | Number Range | Maximum Attempts |
|-------|--------------|------------------|
| Easy | 1 – 50 | 10 |
| Medium | 1 – 100 | 7 |
| Hard | 1 – 200 | 5 |

## 🛠️ Technologies Used

- Java
- Java `Scanner`
- Java `Random`
- VS Code

## ▶️ How to Run

1. Make sure Java JDK is installed on your computer.
2. Open the project in VS Code.
3. Open the terminal.
4. Compile the program:

```bash
javac NumberGuessingGame.java
5. Run the program
java NumberGuessingGame
📷 Sample Gameplay

The program asks the user to select a difficulty level and enter guesses.

Example:
Select Difficulty:
1. Easy   (1-50, 10 attempts)
2. Medium (1-100, 7 attempts)
3. Hard   (1-200, 5 attempts)

Enter choice: 2

Enter your guess: 50
Too Low!

Enter your guess: 75
Too High!

Enter your guess: 63
Correct!

You guessed it in 3 attempts.
🎯 Learning Outcomes

Through this project, I practiced:

Java programming fundamentals
Variables and data types
Conditional statements
Loops
Switch statements
Random number generation
User input using Scanner
Basic problem-solving and logic building
