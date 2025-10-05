package com.santimattius.kmp.skeleton.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.santimattius.kmp.skeleton.core.data.PostRepository
import com.santimattius.kmp.skeleton.core.domain.Post
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class HomeUiState(
    val isLoading: Boolean = false,
    val hasError: Boolean = false,
    val data: List<Post> = emptyList()
)

class HomeViewModel(
    private val repository: PostRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(HomeUiState())
    val state: StateFlow<HomeUiState> = _state.onStart {
        fetchPosts()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState()
    )

    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _state.update { it.copy(isLoading = false, hasError = true) }
    }

    fun fetchPosts() {
        _state.update { it.copy(isLoading = true, hasError = false) }
        viewModelScope.launch(exceptionHandler) {
            repository.getPosts().onSuccess { picture ->
                _state.update { it.copy(isLoading = false, data = picture) }
            }.onFailure {
                _state.update { it.copy(isLoading = false, hasError = true) }
            }
        }
    }
}