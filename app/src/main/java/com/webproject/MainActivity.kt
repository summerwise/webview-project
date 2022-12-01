package com.webproject

import android.content.Context
import android.content.Context.TELEPHONY_SERVICE
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.webproject.presentation.Constants
import com.webproject.presentation.Screen
import com.webproject.presentation.quiz.QuizScreen
import com.webproject.presentation.quiz_results.QuizResultScreen
import com.webproject.presentation.webview.WebViewScreen
import com.webproject.ui.theme.WebProjectTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val context = LocalContext.current
            val isMock = checkIfMock(context)
            val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
            val configSettings = remoteConfigSettings {
                minimumFetchIntervalInSeconds = 5
            }
            remoteConfig.setConfigSettingsAsync(configSettings)
            var url by remember { mutableStateOf<String?>(null) }
            val savedUrl = remoteConfig.getString(Constants.PARAM_URL)
            if(savedUrl.isNotEmpty())
                url = savedUrl
            else {
                remoteConfig.fetchAndActivate()
                    .addOnCompleteListener(this) { task ->
                        url = remoteConfig.getString(Constants.PARAM_URL)
                    }
            }
            WebProjectTheme {
                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    url?.let { Navigation(navController, it, isMock) }
                }
            }
        }
    }
}

fun checkIfMock(context: Context): Boolean {
    val isGoogleBrand = checkIfGoogleBrand()
    val isSimAvailable = isSimAvailable(context)
    val isEmulator = checkIsEmu()
    return isGoogleBrand || !isSimAvailable || isEmulator
}

private fun checkIfGoogleBrand(): Boolean {
    return getDeviceName().contains("pixel", true)
}

private fun getDeviceName(): String {
    val manufacturer = Build.MANUFACTURER
    val model = Build.MODEL
    return if (model.lowercase(Locale.getDefault())
            .startsWith(manufacturer.lowercase(Locale.getDefault()))
    ) {
        capitalize(model)
    } else {
        capitalize(manufacturer) + " " + model
    }
}

private fun capitalize(s: String?): String {
    if (s == null || s.isEmpty()) {
        return ""
    }
    val first = s[0]
    return if (Character.isUpperCase(first)) {
        s
    } else {
        first.uppercaseChar().toString() + s.substring(1)
    }
}

fun isSimAvailable(context: Context): Boolean {
    val telMgr = context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
    var isAvailable = false
    when (telMgr.simState) {
        TelephonyManager.SIM_STATE_ABSENT -> {}
        TelephonyManager.SIM_STATE_NETWORK_LOCKED -> {}
        TelephonyManager.SIM_STATE_PIN_REQUIRED -> {}
        TelephonyManager.SIM_STATE_PUK_REQUIRED -> {}
        TelephonyManager.SIM_STATE_READY -> isAvailable = true
        TelephonyManager.SIM_STATE_UNKNOWN -> {}
    }
    return isAvailable
}

private fun checkIsEmu(): Boolean {
    if (BuildConfig.DEBUG) return false // when developer use this build on emulator
    val phoneModel = Build.MODEL
    val buildProduct = Build.PRODUCT
    val buildHardware = Build.HARDWARE
    var result = (Build.FINGERPRINT.startsWith("generic")
            || phoneModel.contains("google_sdk")
            || phoneModel.lowercase(Locale.getDefault()).contains("droid4x")
            || phoneModel.contains("Emulator")
            || phoneModel.contains("Android SDK built for x86")
            || Build.MANUFACTURER.contains("Genymotion")
            || buildHardware == "goldfish"
            || buildHardware == "vbox86"
            || buildProduct == "sdk"
            || buildProduct == "google_sdk"
            || buildProduct == "sdk_x86"
            || buildProduct == "vbox86p"
            || Build.BOARD.lowercase(Locale.getDefault()).contains("nox")
            || Build.BOOTLOADER.lowercase(Locale.getDefault()).contains("nox")
            || buildHardware.lowercase(Locale.getDefault()).contains("nox")
            || buildProduct.lowercase(Locale.getDefault()).contains("nox"))
    if (result)
        return true
    result = result or (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
    if (result)
        return true
    result = result or ("google_sdk" == buildProduct)
    return result
}


@Composable
fun Navigation(navController: NavHostController, url: String, isMock: Boolean)  {
    NavHost(
        navController = navController,
        modifier = Modifier,
        startDestination = if(url.isEmpty() || isMock)
            Screen.QuizScreen.route else
                Screen.WebViewScreen.route
    ) {
        composable(
            route = Screen.WebViewScreen.route
        ) {
            WebViewScreen(url)
        }
        composable(
            route = Screen.QuizScreen.route
        ) {
            QuizScreen(
                navController = navController
            )
        }
        composable(
            route = Screen.QuizResultsScreen.route +
            "/{${Constants.PARAM_RIGHT_ANSWERS_COUNT}}" +
            "/{${Constants.PARAM_QUESTIONS_COUNT}}",
            arguments = listOf(
                navArgument(Constants.PARAM_RIGHT_ANSWERS_COUNT) { type = NavType.IntType },
                navArgument(Constants.PARAM_QUESTIONS_COUNT) { type = NavType.IntType }
            )
        ) {
            QuizResultScreen(
                navController = navController
            )
        }
    }
}