package com.santimattius.kmp.skeleton.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.multiplatform.webview.web.LoadingState
import com.multiplatform.webview.web.WebView
import com.multiplatform.webview.web.rememberWebViewState
import com.santimattius.kmp.skeleton.core.domain.Post
import com.santimattius.kmp.skeleton.core.ui.components.AppBar
import com.santimattius.kmp.skeleton.core.ui.components.Center
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
            AppBar(title = "My Medium Posts", actions = {
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
                    ErrorView(message = "An error occurred while updating the posts")
                }

                else -> {
                    var select by remember { mutableStateOf<Post?>(null) }
                    ListOfPosts(data = state.data) { post -> select = post }

                    if (select != null) {
                        BottomSheet(post = select!!) { select = null }
                    }
                }
            }
        }
    }
}

@Composable
fun ListOfPosts(
    data: List<Post>,
    onPostClick: (Post) -> Unit = {}
) {
    LazyColumn(
        contentPadding = PaddingValues(
            top = 10.dp,
            bottom = 10.dp,
            start = 16.dp,
            end = 16.dp
        )
    ) {
        items(data, key = { it.id }) { post ->
            PostItem(post, onPostClick)
        }
    }
}

@Composable
fun PostItem(post: Post, onPostClick: (Post) -> Unit) {
    Card(modifier = Modifier.clickable { onPostClick(post) }
        .fillMaxWidth()
        .padding(top = 4.dp, bottom = 4.dp)) {
        ListItem(
            leadingContent = {
                NetworkImage(
                    imageUrl = post.imageUrl,
                    contentDescription = "Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(110.dp)
                        .background(Color.LightGray)
                        .aspectRatio(ratio = (16 / 8).toFloat()),
                )
            },
            headlineContent = {
                Text(text = post.name, maxLines = 2, overflow = TextOverflow.Ellipsis)
            },
            supportingContent = {
                Text(text = post.description, maxLines = 3, overflow = TextOverflow.Ellipsis)
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(post: Post, onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Center(modifier = Modifier.verticalScroll(rememberScrollState())) {
            val state = rememberWebViewState(post.url)
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
}