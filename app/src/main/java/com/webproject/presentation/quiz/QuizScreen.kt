package com.webproject.presentation.quiz

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.navOptions
import com.skydoves.landscapist.glide.GlideImage
import com.webproject.R
import com.webproject.presentation.Screen
import com.webproject.presentation.components.ErrorTextHandler
import com.webproject.ui.theme.right_answer_color
import com.webproject.ui.theme.wrong_answer_color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    navController: NavController,
    viewModel: QuizScreenViewModel = hiltViewModel()
) {
    val state = viewModel.state.collectAsState().value
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        val context = LocalContext.current
        var isInternetAvailable by remember { mutableStateOf(isInternetAvailable(context)) }
        if(isInternetAvailable) {
            state.currentQuestion?.let { currentQuestion ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 10.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Image(
                        modifier = Modifier.size(60.dp),
                        painter = painterResource(id = R.drawable.ic_trainer_avatar),
                        contentDescription = null,
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        ElevatedCard(
                            modifier = Modifier
                                .height(25.dp)
                                .width(50.dp),
                            shape = TriangleShape(),
                        ) {}
                        var expanded by remember { mutableStateOf(false) }
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .animateContentSize()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 20.dp, vertical = 20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                GlideImage(
                                    circularRevealedEnabled = true,
                                    imageModel = currentQuestion.imageUrl,
                                    modifier = Modifier
                                        .padding(horizontal = 0.dp)
                                        .fillMaxWidth()
                                        .height(210.dp)
                                        .clip(RoundedCornerShape(20.dp))
                                    ,
                                    contentScale = ContentScale.FillBounds,
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    style = MaterialTheme.typography.titleSmall,
                                    text = currentQuestion.question,
                                    modifier = Modifier
                                        .padding(vertical = 10.dp),
                                    textAlign = TextAlign.Center
                                )
                                currentQuestion.help?.let {
                                    TextButton(
                                        modifier = Modifier
                                            .width(120.dp),
                                        onClick = {
                                            expanded = !expanded
                                        }
                                    ) {
                                        Text(
                                            text = stringResource(
                                                if(!expanded)
                                                    R.string.show_help_button_text
                                                else
                                                    R.string.hide_help_button_text
                                            )
                                        )
                                    }
                                    AnimatedVisibility(
                                        visible = expanded,
                                    ) {
                                        Card {
                                            Column(
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(20.dp)
                                            ) {
                                                Text(
                                                    text = stringResource(R.string.help_title),
                                                    style = MaterialTheme.typography.titleMedium,
                                                )
                                                Text(
                                                    modifier = Modifier
                                                        .padding(10.dp),
                                                    text = currentQuestion.help,
                                                )
                                                Spacer(Modifier.height(10.dp))
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        val rightAnswer = currentQuestion.rightAnswer
                        var currentAnswer by remember { mutableStateOf("") }
                        Spacer(modifier = Modifier.height(20.dp))
                        for(answer in currentQuestion.answers) {
                            val isButtonsEnabled = !state.isRightAnswerExpanded
                            ElevatedButton(
                                colors = if(currentAnswer == answer) {
                                    if(rightAnswer == answer)
                                        ButtonDefaults.elevatedButtonColors(
                                            containerColor = right_answer_color
                                        )
                                    else {
                                        ButtonDefaults.elevatedButtonColors(
                                            containerColor = wrong_answer_color
                                        )
                                    }
                                }
                                else if(!isButtonsEnabled){
                                    if (answer == rightAnswer)
                                        ButtonDefaults.elevatedButtonColors(
                                            containerColor = right_answer_color
                                        )
                                    else {
                                        ButtonDefaults.elevatedButtonColors()
                                    }
                                }
                                else
                                    ButtonDefaults.elevatedButtonColors(),
                                modifier = Modifier
                                    .fillMaxWidth(),
                                onClick = {
                                    if(!isButtonsEnabled)
                                        return@ElevatedButton
                                    currentAnswer = answer
                                    viewModel.onAnswerClicked(answer)
                                }
                            ) {
                                Text(
                                    text = answer
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = {
                                expanded = false
                                if(state.isLastQuestion) {
                                    navController.navigate(
                                        Screen.QuizResultsScreen.withArgs(
                                            state.rightAnswers.toString(),
                                            state.quizList.size.toString(),
                                        ),
                                        navOptions {
                                            popUpTo(
                                                route = Screen.QuizScreen.route,
                                            ) {
                                                inclusive = true
                                            }
                                        }
                                    )
                                }
                                else {
                                    currentAnswer = ""
                                    viewModel.onNextQuestionClick()
                                }
                            },
                            enabled = state.isRightAnswerExpanded
                        ) {
                            Text(
                                text =
                                if(state.isLastQuestion)
                                    stringResource(R.string.quiz_results_button)
                                else
                                    stringResource(R.string.next_question_button_text)
                            )
                        }
                    }
                }
            }
        }
        else {
            ErrorTextHandler(
                modifier = Modifier
                    .align(Alignment.Center),
                error = stringResource(id = R.string.no_internet_connection_text),
                onRefreshClick = {
                    isInternetAvailable = isInternetAvailable(context)
                }
            )
        }
    }
}

private fun isInternetAvailable(context: Context): Boolean {
    var result = false
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
        result = when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    } else {
        connectivityManager.run {
            connectivityManager.activeNetworkInfo?.run {
                result = when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
    }
    return result
}