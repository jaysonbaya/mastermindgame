// colorcode.py
//
// Class used by mastermind.py to represent the colors and
// keep track of matches in a secret code or user guess.
//
// Author: Jayson Baya
// Date: 5/11/23

import kotlin.random.*

class ColorCode(colors: String){
    public companion object{
        val ALL_COLORS = arrayOf("R", "O", "G", "B", "P")
        val CODE_LENGTH = 4
    }

    var matched = mutableListOf(false, false, false, false)
    val code: String = if (colors == "random") genRandomCode() else colors

    private fun genRandomCode(): String{
        var randCode = ""
        for (i in 0 until CODE_LENGTH){
            randCode += ALL_COLORS.random()
        }
        return randCode
    }

    fun resetMatches() {
        for (i in 0 until CODE_LENGTH){
            matched[i] = false
        }
    }
}