package com.santimattius.kmp.skeleton.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import androidx.compose.ui.viewinterop.UIKitViewController
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.UIKit.UIView
import platform.UIKit.UIViewAutoresizingFlexibleHeight
import platform.UIKit.UIViewAutoresizingFlexibleWidth
import platform.WebKit.WKWebView

@Composable
actual fun NativeWebView(modifier: Modifier, url: String) {
//    PlatformMKWebView(modifier, url)
    IOSWebView(modifier, url)
}


@Composable
private fun PlatformMKWebView(modifier: Modifier, url: String) {
    val webView = remember { WKWebView() }
    UIKitView(
        modifier = modifier,
        factory = {
            val container = UIView().apply {
                autoresizingMask =
                    UIViewAutoresizingFlexibleWidth or UIViewAutoresizingFlexibleHeight
            }
            webView.apply {
                autoresizingMask =
                    UIViewAutoresizingFlexibleWidth or UIViewAutoresizingFlexibleHeight
                loadRequest(NSURLRequest(uRL = NSURL(string = url)))
            }
            container.addSubview(webView)
            container
        },
    )
}

@Composable
private fun IOSWebView(modifier: Modifier, url: String) {
    val nativeViewFactory = LocalNativeViewFactory.current

    UIKitViewController(
        modifier = modifier,
        factory = { nativeViewFactory.createWebView(url) },
        update = {}
    )
}