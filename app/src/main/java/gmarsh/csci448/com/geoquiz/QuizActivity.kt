package gmarsh.csci448.com.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

private const val LOG_TAG = "448.QuizActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class QuizActivity : AppCompatActivity() {

    private lateinit var quizViewModel: QuizViewModel
    private lateinit var scoreTextView: TextView
    private lateinit var questionTextView: TextView
    private lateinit var cheatButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(LOG_TAG, "onCreate() called")
        setContentView(R.layout.activity_quiz)

        val factory = QuizViewModelFactory()
        quizViewModel = ViewModelProvider(this@QuizActivity, factory)
            .get(QuizViewModel::class.java)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX, 0) ?: 0
        quizViewModel.currentQuestionIndex = currentIndex

        scoreTextView = findViewById(R.id.score_text_view)
        questionTextView = findViewById(R.id.question_text_view)

        updateQuestion()

        //listener for true/false buttons

        val trueButton: Button = findViewById(R.id.true_button)
        val falseButton: Button = findViewById(R.id.false_button)
        trueButton.setOnClickListener {
            checkAnswer(true)
        }
        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        //listener for next/prev buttons

        val nextButton: Button = findViewById(R.id.next_button)
        val prevButton: Button = findViewById(R.id.prev_button)
        nextButton.setOnClickListener {
            moveToQuestion(1)
        }
        prevButton.setOnClickListener {
            moveToQuestion(-1)
        }

        cheatButton = findViewById(R.id.cheat_button)
        cheatButton.setOnClickListener{
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.createIntent(baseContext, answerIsTrue)
            startActivityForResult(intent, REQUEST_CODE_CHEAT)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode != Activity.RESULT_OK) {
            return
        }

        if(requestCode == REQUEST_CODE_CHEAT) {
            quizViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
    }

    private fun updateQuestion() {
        setCurrentScoreText()
        questionTextView.text = resources.getString(quizViewModel.currentQuestionTextId)
    }

    private fun setCurrentScoreText() {
        scoreTextView.text = quizViewModel.currentScore.toString()
    }


    private fun checkAnswer(isCorrect: Boolean) {
        if(quizViewModel.isCheater) {
            Toast.makeText(baseContext, R.string.judgement_toast, Toast.LENGTH_SHORT).show()
        } else {
            if (quizViewModel.isAnswerCorrect(isCorrect)) {
                Toast.makeText(
                    baseContext,
                    R.string.correct_toast,
                    Toast.LENGTH_SHORT
                ).show()
                setCurrentScoreText()
            } else {
                Toast.makeText(
                    baseContext,
                    R.string.incorrect_toast,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun moveToQuestion(qInt: Int) {
        quizViewModel.isCheater == false
        if(qInt < 0) {
            quizViewModel.moveToPreviousQuestion()
            updateQuestion()
        } else if (qInt > 0) {
            quizViewModel.moveToNextQuestion()
            updateQuestion()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.i(LOG_TAG, "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.i(LOG_TAG, "onResume()")

    }

    override fun onPause() {
        Log.i(LOG_TAG, "onPause()")
        super.onPause()
    }

    override fun onStop() {
        Log.i(LOG_TAG, "onStop()")
        super.onStop()
    }

    override fun onDestroy() {
        Log.i(LOG_TAG, "onDestroy()")
        super.onDestroy()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        Log.i(LOG_TAG, "onSaveInstanceState")
        savedInstanceState.putInt(KEY_INDEX, quizViewModel.currentQuestionIndex)

    }
}
