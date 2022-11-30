package com.webproject.presentation

enum class Screen(val route: String) {
    WebViewScreen(
        "WebViewScreen",
    ),
    QuizScreen(
        "QuizScreen",
    ),
    QuizResultsScreen(
        "QuizResultsScreen",
    ),
    ;

    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}