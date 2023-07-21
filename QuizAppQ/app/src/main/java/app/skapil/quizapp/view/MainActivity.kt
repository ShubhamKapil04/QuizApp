package app.skapil.quizapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView.FindListener
import android.widget.RadioButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import app.skapil.quizapp.R
import app.skapil.quizapp.databinding.ActivityMainBinding
import app.skapil.quizapp.model.Question
import app.skapil.quizapp.model.QuestionList
import app.skapil.quizapp.viewmodel.QuizViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var quizViewModel: QuizViewModel
    lateinit var questionList: List<Question>

    companion object{
        var result = 0
        var totalQuestions = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // Resetting the Score
        result = 0
        totalQuestions = 0

        // getting the response
        quizViewModel = ViewModelProvider(this)
            .get(QuizViewModel::class.java)


        // Display the First Question
        GlobalScope.launch(Dispatchers.Main) {
            quizViewModel.getQuestionsFromLiveData().observe(this@MainActivity, Observer {
                if(it.size > 0){
                    questionList = it
                    Log.i("TAGY", "This is 1st Question: ${questionList[0]}")

                    binding.apply {
                        txtQuestion.text = "Question 1 : "+ questionList!![0].question
                        radio1.text = questionList!![0].option1
                        radio2.text = questionList!![0].option2
                        radio3.text = questionList!![0].option3
                        radio4.text = questionList!![0].option4


                    }
                }
            })
        }

        // Adding functionality to get another question

        var i = 1
        binding.apply {
            btnNext.setOnClickListener {

                val selectedOption = radioGroup?.checkedRadioButtonId

                if (selectedOption != -1) {
                    val radbutton = findViewById<View>(selectedOption!!) as RadioButton

                    questionList.let {
                        if (i < it.size!!) {

                            // Getting 1 Question
                            totalQuestions = it.size

                            // Check if it is corect
                            if (radbutton.text.toString().equals(it[i - 1].correct_option)) {
                                result++
                                txtResult?.text = "Correct Answer : $result"
                            }

                            //Display the next Question
                            txtQuestion.text = "Question ${i + 1}: " + it[i].question
                            radio1.text = it[i].option1
                            radio2.text = it[i].option2
                            radio3.text = it[i].option3
                            radio4.text = it[i].option4

                            // Checking if it is the last question
                            if (i == it.size!!.minus(1)) {
                                btnNext.text = "FINISH"
                            }

                            radioGroup?.clearCheck()
                            i++
                        } else {
                            if (radbutton.text.toString().equals(it[i - 1].correct_option)) {
                                result++
                                txtResult?.text = "Correct Answer : $result"
                            } else {
                            }

                            val intent = Intent(this@MainActivity, ResultActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                    }
                } else {
                    Toast.makeText(
                        this@MainActivity, " Please select One Option",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }

    }
}