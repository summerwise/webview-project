package com.webproject.presentation.quiz_results

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.webproject.R
import com.webproject.presentation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizResultScreen(
    navController: NavController,
    viewModel: QuizResultViewModel = hiltViewModel(),
) {
    val state = viewModel.state.collectAsState().value
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if(state.successTextVariant == null
            || state.questionsCount == null
            || state.rightAnswersCount == null)
            return
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 20.dp)
        ) {
            ElevatedCard {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Text(
                        text = stringResource(R.string.quiz_result_title_text),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Text(
                        text = "${state.rightAnswersCount} correct answers out of ${state.questionsCount}",
                    )
                    Text(
                        text = state.successTextVariant.asString(),
                        style = MaterialTheme.typography.titleMedium,
                    )
                }
            }
            Button(
                modifier = Modifier
                    .padding(30.dp),
                onClick = {
                    val prevRoute = navController.currentDestination!!.route!!
                    navController.navigate(
                        Screen.QuizScreen.route,
                        navOptions {
                            popUpTo(
                                route = prevRoute,
                            ) {
                                inclusive = true
                            }
                        }
                    )
                },
            ) {
                Text(
                    text = stringResource(R.string.repeat_quiz_button_text),
                )
            }
        }
    }
}