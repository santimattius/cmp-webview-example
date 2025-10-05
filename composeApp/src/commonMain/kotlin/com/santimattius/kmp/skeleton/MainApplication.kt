package com.santimattius.kmp.skeleton

import androidx.compose.runtime.Composable
import com.santimattius.kmp.skeleton.di.applicationModules
import com.santimattius.kmp.skeleton.navigation.Navigation
import org.koin.compose.KoinMultiplatformApplication
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.dsl.KoinConfiguration

@OptIn(KoinExperimentalAPI::class)
@Composable
fun MainApplication() {
    KoinMultiplatformApplication(config = KoinConfiguration {
        modules(applicationModules())
    }) {
        Navigation()
    }
}
