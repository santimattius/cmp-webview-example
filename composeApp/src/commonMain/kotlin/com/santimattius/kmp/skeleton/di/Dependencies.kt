package com.santimattius.kmp.skeleton.di

import com.santimattius.kmp.skeleton.core.data.PostRepository
import com.santimattius.kmp.skeleton.core.network.ktorHttpClient
import com.santimattius.kmp.skeleton.features.home.HomeViewModel
import io.ktor.client.HttpClient
import org.koin.core.module.dsl.viewModelOf
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val sharedModules = module {
    single(qualifier(AppQualifiers.BaseUrl)) { "https://ts-mock-api.onrender.com" }
    single<HttpClient>(qualifier(AppQualifiers.Client)) {
        ktorHttpClient(
            baseUrl = get(
                qualifier = qualifier(
                    AppQualifiers.BaseUrl
                )
            )
        )
    }

    single { PostRepository(get<HttpClient>(qualifier(AppQualifiers.Client))) }
}

val homeModule = module {
    viewModelOf(::HomeViewModel)
}


fun applicationModules() = listOf(sharedModules, homeModule)