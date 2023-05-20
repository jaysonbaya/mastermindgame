// mastermind.py
// A non-GUI version of the Mastermind game
//
// Author: Jayson Baya
// Date: 5/11/23

import java.awt.Color
import kotlin.system.exitProcess

const val MAX_GUESSES = 10
const val SHOW_SECRET_CODE = true

class mastermind{

    fun printIntro(){
        // Introduce the game to the user.
        println("Welcome to Mastermind!\n")
        println("Here's how to play the game: \n")
        println("   - I create a secret code based on the following colors:")
        println("         Red (R)")
        println("         Orange (O)")
        println("         Yellow (Y)")
        println("         Green (G)")
        println("         Blue (B)")
        println("         Purple (P)\n")
        println("   - After each guess I give you hints!")
        println("       * For every color that you guess that is in exactly")
        println("         the right position, I will give you a black peg.")
        println("       * For every color that you guess that is in the code,")
        println("         but not in the right position, I will give you a white peg.\n")
        println("   - You will have 10 chances to guess the code.\n")
        println("   - Type \"exit\" or \"EXIT\" to stop the program.\n")
        println("Let's get started!")
    }

    fun giveHints(secretCode: ColorCode, userGuess: ColorCode){
        //Compares secretCode and userGuess and gives hints to the user.

        //Parameters:
        //secretCode -- ColorCode object holding the code to guess
        //userGuess -- ColorCode object holding the user's guess
        val numBlackPegs = checkedForBlackPegs(secretCode, userGuess)
        val numWhitePegs = checkedForWhitePegs(secretCode, userGuess)
        println("\nYour hints:")
        println("   black pegs: $numBlackPegs")
        println("   white pegs: $numWhitePegs")
        println()
    }

    private fun checkedForBlackPegs(secretCode: ColorCode, userGuess: ColorCode): Int {
        /*Looks for exact matches between secretCode and userGuess,
       and updates secretCode.matched and userGuess.matched.

        Parameters:
        secretCode -- ColorCode object holding the code to guess
        userGuess -- ColorCode object holding the user's guess

        Returns: Number of exact matches found (black pegs)*/

        var count = 0
        for (i in 0 until ColorCode.CODE_LENGTH){
            if (secretCode.code[i] == userGuess.code[i]) {
                count += 1
                secretCode.matched[i] = true
                userGuess.matched[i] = true
            }
        }
        return count
    }

    private fun checkedForWhitePegs(secretCode: ColorCode, userGuess: ColorCode): Int{
        /*Looks for inexact matches between secretCode and userGuess,
          and updates secretCode.matched and userGuess.matched.

          Parameters:
          secretCode -- ColorCode object holding the code to guess
          userGuess -- ColorCode object holding the user's guess

          Returns: Number of inexact matches found (white pegs) */
        var count = 0
        for (i in 0 until ColorCode.CODE_LENGTH){
            for (j in 0 until ColorCode.CODE_LENGTH){
                if (!(secretCode.matched[i] || userGuess.matched[j]) && secretCode.code[i] == userGuess.code[j]){
                    count += 1
                    secretCode.matched[i] = true
                    userGuess.matched[j] = true
                }
            }
        }
        return count
    }

    fun endGame(wonGame: Boolean, guesses: Int, secretCode: String ){
        /*Print an appropriate message and ends the game.

        Parameters:
        wonGame -- True or False, indicates whether user won the game
        guesses -- Number of guesses taken by the user */

        if (wonGame){
            when (guesses){
                1 -> println("\nWoohoo! You cracked the code on your first try!\n")
                in 2..4 ->  println("\nNice! You only took $guesses guesses to crack the code.\n")
                in 5..8 -> println("\nYou cracked the code in $guesses guesses. Good job!\n")
                else -> println("\nWhew! It took $guesses guesses, but you cracked the code.\n")
            }
        }
        else{
            println("\nAwww, too bad. You ran out of guesses $secretCode was the secret code.")
            println("Better luck next time!\n")
        }
        // End the game
        exitProcess(1)
    }
}

fun main() {
    val mastermindGame = mastermind()
    mastermindGame.printIntro()

    var secretCode = ColorCode("random")
    if (SHOW_SECRET_CODE) {
        println("secretCode (for debugging): ${secretCode.code}")
    }

    // Game Loop
    var guess: String?
    var numGuesses = 0
    print("\nWhat's your 1st guess? ")

    while (numGuesses < MAX_GUESSES) {
        guess = readlnOrNull()?.uppercase()
        if (guess == "exit" ||guess == "EXIT") {
            println("Thanks for playing!")
            exitProcess(1)
        }
        else if (guess.isNullOrEmpty() || guess.length != 4) {
            println("Your input is invalid!")
            print("\nInput a valid guess: ")
        }
        else {
            val userGuess = ColorCode(guess)
            if (userGuess.code == secretCode.code){
                numGuesses += 1
                mastermindGame.endGame(true, numGuesses, secretCode.code)
            }
            else{
                numGuesses += 1
                mastermindGame.giveHints(secretCode, userGuess)
            }
            secretCode.resetMatches()
            userGuess.resetMatches()
            if (numGuesses in 1..9){
                when (numGuesses){
                    1 -> print("What's your 2nd guess? ")
                    2 -> print("What's your 3rd guess? ")
                    in 3 until 10 -> print("What's your ${numGuesses + 1}th guess? ")
                }
            } else{
                mastermindGame.endGame(false, numGuesses, secretCode.code)
            }
        }
    }
}