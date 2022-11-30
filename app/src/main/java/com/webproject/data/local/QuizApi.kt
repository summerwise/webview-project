package com.webproject.data.local

import com.webproject.data.local.dto.QuizDto

class QuizApi {
    companion object {
        val quizList = listOf(
            QuizDto(
                imageUrl = "https://i.imgur.com/wtaUXzj.jpg",
                question = "Для какого вида спорта используется этот тренажер?",
                answers = listOf("Бокс", "Вольная борьба", "Самбо", "Дзюдо"),
                help = "В ответе есть буква \"Б\"",
                rightAnswer = "Бокс"
            ),
            QuizDto(
                imageUrl = "https://i.imgur.com/DYRFWV1.jpg",
                question = "Как называется боксерский корт?",
                answers = listOf("Площадка", "Ринг", "Кортеж"),
                help = null,
                rightAnswer = "Ринг"
            ),
            QuizDto(
                imageUrl = "https://i.imgur.com/UEYN0Tp.jpg",
                question = "Какая страна выиграла ЧМ-2018?",
                answers = listOf("Англия", "Бельгия", "Хорватия", "Франция"),
                help = "На этом ЧМ впервые в истории чемпионатов мира произошёл невыход сборной Германии из группы",
                rightAnswer = "Франция"
            ),
            QuizDto(
                imageUrl = "https://i.imgur.com/0xtvy6y.jpg",
                question = "Какова классическая длина марафонской дистанции?",
                answers = listOf("40 км", "34,5 км", "42,195 км", "Нет правильного ответа"),
                help = "В вариантах ответа есть правильная дистанция",
                rightAnswer = "42,195 км"
            ),
            QuizDto(
                imageUrl = "https://i.imgur.com/9WkF3lf.jpg",
                question = "Кто из футболистов впервые забил пять голов за один матч на чемпионате мира?",
                answers = listOf("Артем Дзюба", "Олег Саленко", "Олег Шатов", "Андрей Аршавин"),
                help = "После одной из встреч перед этим футболистом встал выбор - продолжать играть за Украину или выступать в сборной России. В итоге он выбрал последний вариант",
                rightAnswer = "Олег Саленко"
            )
        )
    }
}