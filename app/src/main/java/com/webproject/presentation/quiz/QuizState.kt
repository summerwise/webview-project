package com.webproject.presentation.quiz

import com.webproject.data.local.dto.QuizDto

data class QuizState(
    val quizList: List<QuizDto> = emptyList(),
    val currentQuestion: QuizDto? = null,
    val currentQuestionIndex: Int = 0,
    val rightAnswers: Int = 0,
    val isRightAnswerExpanded: Boolean = false,
    val isUserAnswerRight: Boolean = false,
    val isLastQuestion: Boolean = false,
)