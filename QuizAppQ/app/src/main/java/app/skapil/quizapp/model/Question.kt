package app.skapil.quizapp.model

import java.util.Currency

data class Question(
    val correct_option: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val question: String
)