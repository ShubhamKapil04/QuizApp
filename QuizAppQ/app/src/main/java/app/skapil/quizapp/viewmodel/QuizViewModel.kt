package app.skapil.quizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import app.skapil.quizapp.model.QuestionList
import app.skapil.quizapp.repository.QuizRepository

class QuizViewModel: ViewModel() {

    var repository: QuizRepository = QuizRepository()

    lateinit var questionLiveData: LiveData<QuestionList>


    init {
        questionLiveData = repository.getQuestionsFromAPI()

    }

    fun getQuestionsFromLiveData(): LiveData<QuestionList>{
        return questionLiveData
    }
}