package com.santimattius.kmp.skeleton.features.page

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import com.santimattius.kmp.skeleton.core.ui.components.AppBar
import com.santimattius.kmp.skeleton.core.ui.components.NativeWebView

@Composable
fun PageScreen(
    modifier: Modifier = Modifier,
    title: String,
    url: String,
    onBack: () -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            AppBar(title = title, navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back navigation"
                    )
                }
            })
        },
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(it),
            contentAlignment = Alignment.Center
        ) {
            NativeWebView(modifier = Modifier.fillMaxSize(), url = url)
            //LibraryWebView(modifier = Modifier.fillMaxSize(), url = url)
        }
    }
}

@Composable
private fun LibraryWebView(modifier: Modifier = Modifier, url: String) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        val state = rememberWebViewState(url)
        val loadingState = state.loadingState
        if (loadingState is LoadingState.Loading) {
            CircularProgressIndicator(
                progress = { loadingState.progress },
            )
        }
        WebView(
            state = state,
            modifier = Modifier.fillMaxSize()
        )
    }
}