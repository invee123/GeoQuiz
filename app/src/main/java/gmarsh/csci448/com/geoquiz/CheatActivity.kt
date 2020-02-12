package gmarsh.csci448.com.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.security.AccessControlContext

private lateinit var answerTextView: TextView
private lateinit var showAnswerButton: Button
private lateinit var quizViewModel: QuizViewModel

private const val KEY_INDEX = "index"
private const val LOG_TAG = "448.CheatActivity"
private const val EXTRA_ANSWER_IS_TRUE = "CORRECT_ANSWER_KEY"
const val EXTRA_ANSWER_SHOWN = "ANSWER_SHOWN"
private var answerIsTrue = false
private var isCheater = false


class CheatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)
        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        Log.d(LOG_TAG, "onCreate() called")




        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.cheat_button)
        answerTextView.text = quizViewModel.currentQuestionAnswer
        showAnswerButton.setOnClickListener {
            answerTextView.visibility = View.VISIBLE
            setCheated(true)
        }

    }

    companion object {
        fun createIntent(packageContext: Context, answerIsTrue: String): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }

    fun setCheated(isAnswerShown: Boolean) {
        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
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
}