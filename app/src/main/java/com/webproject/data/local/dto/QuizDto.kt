package com.webproject.data.local.dto

data class QuizDto(
    val imageUrl: String,
    val question: String,
    val answers: List<String>,
    val help: String?,
    val rightAnswer: String,
)