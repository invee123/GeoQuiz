package gmarsh.csci448.com.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel : ViewModel(){
    private val questionBank: MutableList<Question> = mutableListOf()
    private var score = 0
    var currentQuestionIndex = 0
    var isCheater = false

    init {
        Log.d(TAG, "ViewModel instance created")
        questionBank.add(Question(R.string.q1, false))
        questionBank.add(Question(R.string.q2, true))
        questionBank.add(Question(R.string.q3, false))
        questionBank.add(Question(R.string.q4, false))
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel about to be destroyed")
    }

    private val currentQuestion: Question
        get() = questionBank[currentQuestionIndex]

    val currentQuestionTextId: Int
        get() = currentQuestion.textResId
    val currentQuestionAnswer: Boolean
        get() = currentQuestion.isAnswerTrue
    val currentScore: Int
        get() = score

    fun isAnswerCorrect(answer: Boolean): Boolean {
        if (answer == currentQuestionAnswer) {
            score = score + 1
            return true
        } else
            return false
        }

    fun moveToNextQuestion() {
        if(currentQuestionIndex == 3) {
            currentQuestionIndex = 0
        } else {
            currentQuestionIndex += 1
        }
    }

    fun moveToPreviousQuestion() {
        if(currentQuestionIndex == 0) {
            currentQuestionIndex = 3
        } else {
            currentQuestionIndex -= 1
        }
    }
}
