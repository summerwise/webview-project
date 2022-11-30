package com.webproject.domain.repository

import com.webproject.core.util.Resource
import com.webproject.data.local.dto.QuizDto
import kotlinx.coroutines.flow.Flow

interface QuizRepository {
    fun getQuizQuestions(): Flow<Resource<List<QuizDto>>>
}