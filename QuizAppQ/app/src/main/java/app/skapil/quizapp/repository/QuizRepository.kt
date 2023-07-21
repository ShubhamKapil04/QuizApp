package app.skapil.quizapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import app.skapil.quizapp.model.QuestionList
import app.skapil.quizapp.retrofit.QuestionAPI
import app.skapil.quizapp.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class QuizRepository {

    var questionApi: QuestionAPI

    init {
        questionApi = RetrofitInstance().getRetrofitInstance()
            .create(QuestionAPI::class.java)
    }

    fun getQuestionsFromAPI(): LiveData<QuestionList>{

        //Live Date
        var data = MutableLiveData<QuestionList>()

        var questionList: QuestionList

        GlobalScope.launch(Dispatchers.IO) {

            // Returning the live response
            val response = questionApi.getQuestions()

            if(response != null){

                //saving the data list
                questionList = response.body()!!

                data.postValue(questionList)

                Log.i("TAGY", " "+ data.value)
            }

        }
        return data
    }
}