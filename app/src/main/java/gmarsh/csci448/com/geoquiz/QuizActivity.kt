package gmarsh.csci448.com.geoquiz

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_quiz.*
import org.w3c.dom.Text

private const val LOG_TAG = "448.QuizActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class QuizActivity : AppCompatActivity() {

    private lateinit var quizViewModel: QuizViewModel
    private lateinit var scoreTextView: TextView
    private lateinit var questionTextView: TextView
    private lateinit var cheatButton: Button
    private lateinit var boolView: LinearLayout
    private lateinit var mcView: LinearLayout
    private lateinit var responseView: LinearLayout


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
            checkAnswer("true")
        }
        falseButton.setOnClickListener {
            checkAnswer("false")
        }

        //listener for Multiple Choice buttons
        val a_button: Button = findViewById(R.id.a_button)
        val b_button: Button = findViewById(R.id.b_button)
        val c_button: Button = findViewById(R.id.c_button)
        val d_button: Button = findViewById(R.id.d_button)
        a_button.setOnClickListener {
            checkAnswer(quizViewModel.ans1)
        }
        b_button.setOnClickListener {
            checkAnswer(quizViewModel.ans2)
        }
        c_button.setOnClickListener {
            checkAnswer(quizViewModel.ans3)
        }
        d_button.setOnClickListener {
            checkAnswer(quizViewModel.ans4)
        }

        //listener for Response question
        val submit_button: Button = findViewById(R.id.submit_button)
        var response_box = findViewById(R.id.text_box) as EditText
        submit_button.setOnClickListener {
            checkAnswer(response_box.text.toString())
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

        //listener for cheat button
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
        setCurrentQuestionType()
        questionTextView.text = resources.getString(quizViewModel.currentQuestionTextId)

    }

    private fun setCurrentScoreText() {
        scoreTextView.text = quizViewModel.currentScore.toString()
    }

    private fun setCurrentQuestionType() {
        val qType = quizViewModel.questionType
        boolView = findViewById(R.id.bool)
        mcView = findViewById(R.id.mc)
        responseView = findViewById(R.id.response)

        if(qType == "TF") {
            boolView.visibility = View.VISIBLE
            mcView.visibility = View.GONE
            responseView.visibility = View.GONE
        } else if(qType == "MC") {
            mcView.visibility = View.VISIBLE
            boolView.visibility = View.GONE
            responseView.visibility = View.GONE

            a_button.text = quizViewModel.ans1
            b_button.text = quizViewModel.ans2
            c_button.text = quizViewModel.ans3
            d_button.text = quizViewModel.ans4
        } else if(qType == "FREE") {
            responseView.visibility = View.VISIBLE
            boolView.visibility = View.GONE
            mcView.visibility = View.GONE
        }
    }


    private fun checkAnswer(isCorrect: String) {
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
