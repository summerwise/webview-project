package com.webproject.presentation.webview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.webproject.presentation.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WebViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
): ViewModel() {
    //val url = savedStateHandle.get<String>(Constants.PARAM_URL)
}