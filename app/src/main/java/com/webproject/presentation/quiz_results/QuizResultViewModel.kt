package com.webproject.presentation.quiz_results

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webproject.R
import com.webproject.core.util.UiText
import com.webproject.presentation.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizResultViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
): ViewModel() {
    private val _state = MutableStateFlow(QuizResultState())
    val state = _state.asStateFlow()
    private val rightAnswersCount = savedStateHandle.get<Int>(Constants.PARAM_RIGHT_ANSWERS_COUNT)
    private val questionsCount = savedStateHandle.get<Int>(Constants.PARAM_QUESTIONS_COUNT)

    init {
        loadQuizResults()
    }

    private fun loadQuizResults() {
        if(rightAnswersCount == null || questionsCount == null)
            return
        viewModelScope.launch {
            val rightAnswersPercent = 100F / questionsCount * rightAnswersCount
            val successTextVariant =
                if(rightAnswersPercent < 33.33) {
                    UiText.StringResource(R.string.success_low_variant_text)
                }
                else if(rightAnswersPercent < 66.66)
                    UiText.StringResource(R.string.success_medium_variant_text)
                else {
                    UiText.StringResource(R.string.success_high_variant_text)
                }

            _state.update {
                it.copy(
                    rightAnswersCount = rightAnswersCount,
                    questionsCount = questionsCount,
                    successTextVariant = successTextVariant
                )
            }
        }
    }
}