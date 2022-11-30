package com.webproject.data.repository

import com.webproject.core.util.Resource
import com.webproject.data.local.QuizApi
import com.webproject.data.local.dto.QuizDto
import com.webproject.domain.repository.QuizRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class QuizRepositoryImpl @Inject constructor(

): QuizRepository {
    override fun getQuizQuestions(): Flow<Resource<List<QuizDto>>> {
        return flow {
            val data = QuizApi.quizList
            emit(Resource.Success(data))
        }
    }
}