package com.santimattius.kmp.skeleton.core.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import com.santimattius.kmp.skeleton.core.domain.Picture as DomainPicture

private fun Picture.asDomain(): DomainPicture {
    return DomainPicture(this.id, this.author, this.downloadURL)
}

private fun List<Picture>.asDomains(): List<DomainPicture> {
    return this.map { it.asDomain() }
}

class PictureRepository(
    private val client: HttpClient,
) {
    suspend fun random() = runCatching {
        val response = client.get("/random")
        response.body<Picture>().asDomain()
    }

    suspend fun getPictures() = runCatching {
        val response = client.get("/pictures")
        response.body<List<Picture>>().asDomains()
    }
}