package com.santimattius.kmp.skeleton.core.data

import com.santimattius.kmp.skeleton.core.domain.Post
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

private fun PostDto.asDomain(): Post {
    return Post(
        id = id,
        name = name,
        description = description,
        imageUrl = imageUrl,
        url = url
    )
}

private fun List<PostDto>.asDomains(): List<Post> {
    return this.map { it.asDomain() }
}

class PostRepository(
    private val client: HttpClient,
) {

    suspend fun getPosts() = runCatching {
        val response = client.get("/live/medium")
        response.body<PostsResponse>().data.asDomains()
    }
}