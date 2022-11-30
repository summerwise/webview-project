package com.webproject.presentation.quiz

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.webproject.core.util.Resource
import com.webproject.domain.usecase.GetQuizQuestionsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizScreenViewModel @Inject constructor(
    private val getQuizQuestionsUseCase: GetQuizQuestionsUseCase,
): ViewModel() {
    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()

    init {
        getQuizQuestions()
    }

    fun onNextQuestionClick() {
        viewModelScope.launch {
            val isLastQuestion = _state.value.currentQuestionIndex == _state.value.quizList.lastIndex - 1
            _state.update {
                it.copy(
                    isLastQuestion = isLastQuestion,
                    isRightAnswerExpanded = false,
                    currentQuestionIndex = it.currentQuestionIndex + 1,
                    currentQuestion = it.quizList[it.currentQuestionIndex + 1],
                )
            }
        }
    }

    fun onAnswerClicked(answer: String) {
        if(state.value.currentQuestion == null)
            return
        viewModelScope.launch {
            val isUserRight = state.value.currentQuestion!!.rightAnswer == answer
            _state.update {
                it.copy(
                    isRightAnswerExpanded = true,
                    isUserAnswerRight = isUserRight,
                    rightAnswers = if(isUserRight) it.rightAnswers + 1 else it.rightAnswers
                )
            }
        }
    }

    private fun getQuizQuestions() {
        viewModelScope.launch {
            getQuizQuestionsUseCase().collect { result ->
                when(result) {
                    is Resource.Success -> {
                        _state.update {
                            it.copy(
                                quizList = result.data,
                                currentQuestion = result.data[0]
                            )
                        }
                    }
                    is Resource.Error -> {
                    }
                }
            }
        }
    }
}