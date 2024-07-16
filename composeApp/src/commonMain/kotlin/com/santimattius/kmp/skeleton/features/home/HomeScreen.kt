package com.santimattius.kmp.skeleton.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.santimattius.kmp.skeleton.core.domain.Picture
import com.santimattius.kmp.skeleton.core.ui.components.AppBar
import com.santimattius.kmp.skeleton.core.ui.components.ErrorView
import com.santimattius.kmp.skeleton.core.ui.components.LoadingIndicator
import com.santimattius.kmp.skeleton.core.ui.components.NetworkImage
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun HomeScreen(
    screenModel: HomeViewModel = koinViewModel<HomeViewModel>(),
) {
    val state by screenModel.state.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            AppBar(title = "Gallery", actions = {
                Icon(
                    Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.clickable { screenModel.randomImage() }
                )
            })
        },
    ) {
        Box(
            modifier = Modifier.fillMaxSize().padding(it),
            contentAlignment = Alignment.Center
        ) {
            when {
                state.isLoading -> LoadingIndicator()

                state.data.isEmpty() || state.hasError -> {
                    ErrorView(message = "An error occurred while updating the image")
                }

                else -> {
                    ListOfImages(state.data)
                }
            }
        }
    }
}

@Composable
fun ListOfImages(data: List<Picture>, onImageClick: (Picture) -> Unit = {}) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 10.dp,
            bottom = 10.dp,
            start = 16.dp,
            end = 16.dp
        )
    ) {
        items(data, key = { it.id }) { picture ->
            Card(modifier = Modifier.clickable { onImageClick(picture) }
                .padding(top = 4.dp, bottom = 4.dp)) {
                NetworkImage(
                    imageUrl = picture.url,
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().background(Color.LightGray)
                        .aspectRatio(ratio = (16 / 8).toFloat()),
                )
            }
        }
    }
}
