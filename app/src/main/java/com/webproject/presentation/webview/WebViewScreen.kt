package com.webproject.presentation.webview

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.webkit.CookieManager
import android.webkit.WebSettings
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.webproject.R
import com.webproject.presentation.components.ErrorTextHandler

@Composable
fun WebViewScreen(
    url: String,
) {
    val state = rememberWebViewState(url)
    BackHandler(
        enabled = true,
        onBack  = {
        }
    )
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        val context = LocalContext.current
        var isInternetAvailable by remember { mutableStateOf(isInternetAvailable(context)) }
        if(isInternetAvailable) {
            WebView(
                state = state,
                modifier = Modifier.fillMaxSize(),
                onCreated = {
                    it.settings.javaScriptEnabled = true
                    it.settings.domStorageEnabled = true
                    it.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
                    it.settings.javaScriptCanOpenWindowsAutomatically = true
                    it.settings.loadWithOverviewMode = true
                    it.settings.useWideViewPort = true
                    it.settings.databaseEnabled = true
                    it.settings.setSupportZoom(false)
                    it.settings.allowFileAccess = true
                    it.settings.allowContentAccess = true

                    val cookieManager = CookieManager.getInstance()
                    cookieManager.setAcceptCookie(true)
                }
            )
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