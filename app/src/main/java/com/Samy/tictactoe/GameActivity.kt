package com.Samy.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.max
import kotlin.math.min
import android.content.Intent
import android.widget.*
import androidx.preference.PreferenceManager



data class Pair(var x:Int, var y:Int)

class GameActivity : AppCompatActivity() {


    private var board = arrayListOf(arrayListOf("-","-","-"),arrayListOf("-","-","-"),arrayListOf("-","-","-"))
    private var human ="o"
    private var computer ="x"
    private var gameOver = false
    private var activePlayer = human
    private var rounds = 0
    private var playFirst = true
    private var humanImage = R.drawable.circle
    private var computerImage = R.drawable.cross
    private var mode = "medium"
    val prefs = PreferenceManager.getDefaultSharedPreferences(SettingsActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       mode = intent.getStringExtra("mode")
      //  if (prefs.getString("symbol_list","<usnet>").toString() == "X"){
       //     human = "x"
       //     computer = "o"
       //     humanImage = R.drawable.cross
       //     computerImage = R.drawable.circle
       // }

    }


    fun buttonClick(view:View){


        val button = view as ImageView
        if(button.id == R.id.replay)
            reset()
        else {
            if (checkWinner() == null) {
                rounds++
                when (button.id) {
                    R.id.button1 -> board[0][0] = human
                    R.id.button2 -> board[0][1] = human
                    R.id.button3 -> board[0][2] = human
                    R.id.button4 -> board[1][0] = human
                    R.id.button5 -> board[1][1] = human
                    R.id.button6 -> board[1][2] = human
                    R.id.button7 -> board[2][0] = human
                    R.id.button8 -> board[2][1] = human
                    R.id.button9 -> board[2][2] = human
                }
                button.isEnabled = false
                button.isClickable = false
                button.setImageResource(humanImage)
                activePlayer = computer
                if (checkWinner() == null)
                    bestMoveCalc()
                val winner = checkWinner()
                if (winner != null) {
                    if (winner == computer) {
                        display_board.text = "Computer is the WINNER!!"
                    }
                    else if(winner == human){
                        display_board.text = "You are the WINNER!!"
                    }
                    else {
                        display_board.text = "The Game is TIE!!!"
                    }

                    when(winner){
                        "x" -> Toast.makeText(this, "The Computer is the WINNER!!!", Toast.LENGTH_LONG).show()
                        "y" -> Toast.makeText(this, "You are the WINNER!!!", Toast.LENGTH_LONG).show()
                        "tie" -> Toast.makeText(this, "The Game is TIE!!!", Toast.LENGTH_LONG).show()
                    }
                    checkWinnerDraw()
                }

            }
        }

    }

    private fun bestMoveCalc(){
        //The Computer's turn
        var bestMove = Pair(-1,-1)
        if(mode == "easy"){
            var numberList = arrayListOf<Pair>()
            for(i in 0.. 2){
                for(j in 0..2){
                    if(board[i][j] == "-"){
                        numberList.add(Pair(i,j))
                    }
                }
            }
            numberList.shuffle()
            bestMove = numberList[0]

        }
        else{
            var bestScore = Int.MIN_VALUE
            for (i in 0..2) {
                for (j in 0..2) {
                    //searching for the available positions in the board
                    if (board[i][j] == "-") {
                        board[i][j] = computer
                        val score = minimax(depth = 0, isMaximizing = false)
                        board[i][j] = "-"
                        if (score > bestScore) {
                            bestScore = score
                            bestMove.x = i
                            bestMove.y = j
                        }
                    }
                }
            }
        }

        board[bestMove.x][bestMove.y] = computer
        activePlayer = human

        when(bestMove.x*10 +bestMove.y){
            0 -> {
                button1.isEnabled = false
                button1.isClickable = false
                button1.setImageResource(computerImage)
            }
            1 -> {
                button2.isEnabled = false
                button2.isClickable = false
                button2.setImageResource(computerImage)
            }
            2 -> {
                button3.isEnabled = false
                button3.isClickable = false
                button3.setImageResource(computerImage)
            }
            10 -> {
                button4.isEnabled = false
                button4.isClickable = false
                button4.setImageResource(computerImage)
            }
            11 -> {
                button5.isEnabled = false
                button5.isClickable = false
                button5.setImageResource(computerImage)
            }
            12 -> {
                button6.isEnabled = false
                button6.isClickable = false
                button6.setImageResource(computerImage)
            }
            20 -> {
                button7.isEnabled = false
                button7.isClickable = false
                button7.setImageResource(computerImage)
            }
            21 -> {
                button8.isEnabled = false
                button8.isClickable = false
                button8.setImageResource(computerImage)
            }
            22 -> {
                button9.isEnabled = false
                button9.isClickable = false
                button9.setImageResource(computerImage)
            }
        }
    }

    private fun checkEqual(s1:String, s2:String, s3:String): Boolean {
        return s1==s2 && s2==s3 && s1 != "-"
    }
    private fun checkWinner(): String? {
        var winner:String? = null
        //checking for a winner in the rows
        for(i in 0..2)
                if(checkEqual(board[i][0],board[i][1],board[i][2])) {
                    winner = board[i][0]
                    gameOver = true
                }
        //checking for  a winner in the columns
        for(i in 0..2)
            if(checkEqual(board[0][i],board[1][i],board[2][i])) {
                winner = board[0][i]
                gameOver = true
            }

        // checking for a winner in the diagonals
        if(checkEqual(board[0][0],board[1][1],board[2][2])){
            winner = board[0][0]
            gameOver = true
        }
        if(checkEqual(board[0][2],board[1][1],board[2][0])){
            winner = board[1][1]
            gameOver = true
        }
        var available = 0

        for(i in 0..2){
            for(j in 0..2){
                if (board[i][j]=="-") {
                    available++
                }
            }
        }

        if(winner==null && available==0) {
            winner = "tie"
            gameOver = true
        }
        return winner

    }

    private fun minimax(depth:Int, isMaximizing:Boolean): Int {
        val medium:Boolean = mode == "medium"
        val winner = checkWinner()
        if (winner != null){
            when(winner){
                "x"   -> return  10
                "o"   -> return  -10
                "tie" -> return 0
            }
        }
        var alphaTemp:Int
        var betaTemp:Int
        if(isMaximizing){
            //Computer's turn
            var bestScore = Int.MIN_VALUE
            /* looping for available positions */
            for (i in 0..2){
                for (j in 0..2){
                    if (board[i][j]=="-"){
                        board[i][j] = computer
                        val score = minimax(depth+1,false)
                        board[i][j] = "-"
                        if(medium)
                            bestScore = score - depth
                        else
                            bestScore = max(bestScore,score) - depth
                        /*if(mode == "medium") {
                            alphaTemp = max(alpha,score)
                            if(beta<=alphaTemp)
                              break
                        }*/
                    }
                }
            }
            return bestScore
        }
        else{
            //human' turn
            var bestScore = Int.MAX_VALUE
            var betaTemp:Int = 0
            for(i in 0..2){
                for (j in 0..2){
                    if(board[i][j]=="-"){
                        board[i][j] = human
                        val score = minimax(depth+1,true)
                        board [i][j] = "-"
                        if(medium)
                            bestScore = score + depth
                        else
                            bestScore = min(bestScore,score) + depth
                        /*if(mode == "medium") {
                            if(score<beta)
                                betaTemp = min(score,beta)
                            if(betaTemp<=alpha)
                                break
                        }*/
                    }
                }
            }
            return bestScore
        }
    }

    private fun checkWinnerDraw() {
        //checking for a winner in the rows
        for(i in 0..2)
            if(checkEqual(board[i][0],board[i][1],board[i][2])) {
                when (i) {
                    0 -> top_horizontal.visibility = View.VISIBLE
                    1 -> center_horizontal.visibility = View.VISIBLE
                    else -> bottom_horizontal.visibility = View.VISIBLE
                }
            }
        //checking for  a winner in the columns
        for(i in 0..2)
            if(checkEqual(board[0][i],board[1][i],board[2][i])) {
                when (i) {
                    0 -> left_vertical.visibility = View.VISIBLE
                    1 -> center_vertical.visibility = View.VISIBLE
                    else -> right_vertical.visibility = View.VISIBLE
                }
            }

        // checking for a winner in the diagonals
        if(checkEqual(board[0][0],board[1][1],board[2][2])){
                left_right_diagonal.visibility = View.VISIBLE
        }
        if(checkEqual(board[0][2],board[1][1],board[2][0])){
            right_left_diagonal.visibility = View.VISIBLE
        }

    }

    private fun reset(){
        for(i in 0..2){
            for (j in 0..2){
                board[i][j] = "-"
            }
        }
        button1.setImageResource(R.color.app_background)
        button1.isEnabled = true
        button1.isClickable = true

        button2.setImageResource(R.color.app_background)
        button2.isEnabled = true
        button2.isClickable = true

        button3.setImageResource(R.color.app_background)
        button3.isEnabled = true
        button3.isClickable = true

        button4.setImageResource(R.color.app_background)
        button4.isEnabled = true
        button4.isClickable = true

        button5.setImageResource(R.color.app_background)
        button5.isEnabled = true
        button5.isClickable = true

        button6.setImageResource(R.color.app_background)
        button6.isEnabled = true
        button6.isClickable = true

        button7.setImageResource(R.color.app_background)
        button7.isEnabled = true
        button7.isClickable = true

        button8.setImageResource(R.color.app_background)
        button8.isEnabled = true
        button8.isClickable = true

        button9.setImageResource(R.color.app_background)
        button9.isEnabled = true
        button9.isClickable = true

        top_horizontal.visibility = View.INVISIBLE
        center_horizontal.visibility = View.INVISIBLE
        bottom_horizontal.visibility = View.INVISIBLE
        left_vertical.visibility = View.INVISIBLE
        center_vertical.visibility = View.INVISIBLE
        right_vertical.visibility = View.INVISIBLE
        right_left_diagonal.visibility = View.INVISIBLE
        left_right_diagonal.visibility = View.INVISIBLE


        display_board.text = "It's your turn Now!!"

    }

}