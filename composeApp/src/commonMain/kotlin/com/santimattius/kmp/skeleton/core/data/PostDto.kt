package com.santimattius.kmp.skeleton.core.data


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostDto(
    @SerialName("id") val id: String,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String,
    @SerialName("url") val url: String,
    @SerialName("imageUrl") val imageUrl: String,
)

@Serializable
data class PostsResponse(
    @SerialName("data") val data: List<PostDto>
)