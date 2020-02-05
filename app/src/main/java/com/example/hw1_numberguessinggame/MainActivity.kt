package com.example.hw1_numberguessinggame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    var counter: Int = 0
    var randomNumber: Int = pickRandomNumber()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        counter = num_trials.text.toString().toInt()
        setOnActionListener()
    }

    fun setOnActionListener() {
        user_guess.setOnKeyListener { view, actionId, event ->
            if (event.action == KeyEvent.ACTION_DOWN &&
                actionId == KeyEvent.KEYCODE_ENTER) {
                if(user_guess == null || user_guess.text.isEmpty()) {
                    Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show()
                    false
                } else {
                    checkUserInput()
                    true
                }
            }
            false
        }
    }

    fun checkUserInput() {
        val userGuess = user_guess.text.toString().toInt()
        println("User Guess $userGuess")

        if(userGuess > randomNumber) {
            user_hint.text = "Hint: Down!"
            counter--
            num_trials.text = counter.toString()
        } else if(userGuess < randomNumber) {
            user_hint.text = "Hint: Up!"
            counter--
            num_trials.text = counter.toString()
        } else {
            user_hint.text = "Congrats! $userGuess is correct!"
            reset_button.visibility = View.VISIBLE
        }
        checkIfEndGame()
    }

    fun resetGame(v: View) {
        Toast.makeText(this, "Restarting Game", Toast.LENGTH_SHORT).show()
        counter = 5
        randomNumber = pickRandomNumber()
        num_trials.text = counter.toString()
        println("Random Number $randomNumber")
        user_hint.text = ""
        reset_button.visibility = View.INVISIBLE
        user_guess.isFocusable = true
    }

    fun checkIfEndGame() {
        if(counter == 0 ) {
            reset_button.visibility = View.VISIBLE
            user_hint.text = "You Lost :( Correct answer is $randomNumber!"
            user_guess.editableText.clear()
            user_guess.isFocusable = false
        }
    }

    fun pickRandomNumber(): Int {
        return Random().nextInt(100)
    }
}
