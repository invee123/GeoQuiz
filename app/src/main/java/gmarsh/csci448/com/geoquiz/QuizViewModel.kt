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
        questionBank.add(Question(R.string.q1, "false", "TF"))
        questionBank.add(Question(R.string.q2, "true", "TF"))
        questionBank.add(Question(R.string.q3, "false", "TF"))
        questionBank.add(Question(R.string.q4, "false", "TF"))
        questionBank.add(Question(R.string.q5, "Grant", "MC", "Gran", "G-Money", "Grant", "Yolo" ))
        questionBank.add(Question(R.string.q6, "CS", "FREE"))
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel about to be destroyed")
    }

    private val currentQuestion: Question
        get() = questionBank[currentQuestionIndex]

    val currentQuestionTextId: Int
        get() = currentQuestion.textResId
    val currentQuestionAnswer: String
        get() = currentQuestion.isAnswerTrue
    val questionType: String
        get() = currentQuestion.questionType

    val ans1: String
        get() = currentQuestion.ans1
    val ans2: String
        get() = currentQuestion.ans2
    val ans3: String
        get() = currentQuestion.ans3
    val ans4: String
        get() = currentQuestion.ans4

    val currentScore: Int
        get() = score

    fun isAnswerCorrect(answer: String): Boolean {
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

    fun whatToDisplay() {
        if(questionType == "TF") {
            //make id bool visible
            //make all others gone
        } else if (questionType == "MC") {
            //make id MC visible
            //set tool.text in mc
            //make all others invisible
        } else {
            //make id response visible
            //make all others invisible
        }
    }


}
