package com.webproject.presentation.quiz_results

import com.webproject.core.util.UiText

data class QuizResultState(
    val rightAnswersCount: Int? = null,
    val questionsCount: Int? = null,
    val successTextVariant: UiText? = null
)