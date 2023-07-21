package app.skapil.quizapp.retrofit

import app.skapil.quizapp.model.QuestionList
import retrofit2.Response
import retrofit2.http.GET

interface QuestionAPI {

    @GET("questionApi.php")
    suspend fun getQuestions():Response<QuestionList>

}