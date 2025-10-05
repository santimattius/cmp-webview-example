package com.santimattius.kmp.skeleton.core.ui.components

import androidx.compose.runtime.staticCompositionLocalOf
import platform.UIKit.UIViewController

val LocalNativeViewFactory = staticCompositionLocalOf<NativeViewFactory> {
    error("LocalNativeViewFactory not provided")
}

interface NativeViewFactory {

    fun createWebView(urlString: String): UIViewController
}