package com.webproject.domain.usecase

import com.webproject.core.util.Resource
import com.webproject.data.local.dto.QuizDto
import com.webproject.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetQuizQuestionsUseCase @Inject constructor(
    private val repository: QuizRepository
) {

    operator fun invoke(): Flow<Resource<List<QuizDto>>> {
        return repository.getQuizQuestions()
    }
}