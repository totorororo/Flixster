package com.example.flixster

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class SearchNewsResponse(
    @SerialName("response")
    val response: BaseResponse?
)

@Keep
@Serializable
data class BaseResponse(
    @SerialName("docs")
    val docs: List<Movies>?
)
@android.support.annotation.Keep
@Serializable
data class Movies(
    @SerialName("abstract")
    val abstract: String?,
    @SerialName("headline")
    val headline: HeadLine?,
    @SerialName("multimedia")
    val multimedia: List<MultiMedia>?,
): java.io.Serializable {
    val mediaImageUrl = "https://image.tmdb.org/t/p/w500${multimedia?.firstOrNull { it.url != null }?.url ?: ""}"
}

@android.support.annotation.Keep
@Serializable
data class HeadLine(
    @SerialName("main")
    val main: String
) : java.io.Serializable

@android.support.annotation.Keep
@Serializable
data class MultiMedia(
    @SerialName("url")
    val url: String?
) : java.io.Serializable