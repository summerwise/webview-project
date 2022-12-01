package com.webproject.data.local

import com.webproject.data.local.dto.QuizDto

class QuizApi {
    companion object {
        val quizList = listOf(
            QuizDto(
                imageUrl = "https://i.imgur.com/wtaUXzj.jpg",
                question = "What sport is this simulator used for?",
                answers = listOf("Boxing", "Wrestling", "Sambo", "Judo"),
                help = "Answer contains a letter \"B\"",
                rightAnswer = "Boxing"
            ),
            QuizDto(
                imageUrl = "https://i.imgur.com/DYRFWV1.jpg",
                question = "What is the name of the boxing court?",
                answers = listOf("Area", "Ring", "Court", "Platform"),
                help = null,
                rightAnswer = "Ring"
            ),
            QuizDto(
                imageUrl = "https://i.imgur.com/UEYN0Tp.jpg",
                question = "Which country won the World Cup 2018?",
                answers = listOf("England", "Belgium", "Croatia", "France"),
                help = "After cruising through qualifying, Germany crashed out of the 2018 FIFA World Cup Russia at the group stages, falling at the first hurdle of their title defense.",
                rightAnswer = "France"
            ),
            QuizDto(
                imageUrl = "https://i.imgur.com/0xtvy6y.jpg",
                question = "What is the classic marathon distance?",
                answers = listOf("40 km", "34,5 km", "42,195 km", "There is no correct answer"),
                help = "The answer options have the correct distance",
                rightAnswer = "42,195 km"
            ),
            QuizDto(
                imageUrl = "https://i.imgur.com/9WkF3lf.jpg",
                question = "Who was the first player to score five goals in one match at the World Cup?",
                answers = listOf("Artem Dziuba", "Oleg Salenko", "Oleg Shatov", "Andrey Arshavin"),
                help = "After one of the meetings, this footballer had a choice - to continue playing for Ukraine or to play in the Russian team. In the end, he chose the last option.",
                rightAnswer = "Oleg Salenko"
            ),
            QuizDto(
                imageUrl = "https://i.imgur.com/6bMGiMW.jpg",
                question = "How many players are there on a baseball team?",
                answers = listOf("9 players", "10 players", "8 players", "7 players"),
                help = "Odd number of players.",
                rightAnswer = "9 players"
            ),
            QuizDto(
                imageUrl = "https://i.imgur.com/lzq5hVo.jpg",
                question = "What is Muhammad Ali’s real name?",
                answers = listOf("Angelo Dundee", "Cassius Ali", "Muhammad Ali", "Cassius Clay"),
                help = "His mother's name is Odessa Lee Clay",
                rightAnswer = "Cassius Clay"
            ),
            QuizDto(
                imageUrl = "https://i.imgur.com/r6jefHk.jpg",
                question = "What is Usain Bolt’s blistering 100m world record time?",
                answers = listOf("9.58 seconds", "9.69 seconds", "10.72  seconds", "10.03 seconds"),
                help = "Less than 10 seconds",
                rightAnswer = "9.58 seconds"
            ),
            QuizDto(
                imageUrl = "https://i.imgur.com/Ur5Acng.jpg",
                question = "Which Formula 1 driver has won the most races in the history of the sport?",
                answers = listOf("Sebastian Vettel", "Michael Schumacher", "Lewis Hamilton", "Fernando Alonso"),
                help = "This driver recently surpassed Michael Schumacher’s record with 100 race wins in his career",
                rightAnswer = "Lewis Hamilton"
            ),
            QuizDto(
                imageUrl = "https://i.imgur.com/7uePdCm.jpg",
                question = "What five colors make up the Olympic rings?",
                answers = listOf("Red, white, green, black, yellow.", "Blue, white, green, black, yellow.", "Blue, white, green, red, yellow.", "Blue, black, green, red, yellow."),
                help = "Right answer contains black color",
                rightAnswer = "Blue, black, green, red, yellow."
            )
        )
    }
}