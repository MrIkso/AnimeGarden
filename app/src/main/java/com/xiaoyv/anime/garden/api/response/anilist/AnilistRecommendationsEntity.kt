@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.api.response.anilist

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AnilistRecommendationsEntity(
    @SerializedName("nodes")
    var nodes: List<Node>? = null,
    @SerializedName("pageInfo")
    var pageInfo: AnilistPageEntity.PageInfo? = null
) : Parcelable {

    @Keep
    @Parcelize
    data class Node(
        @SerializedName("id")
        var id: Int = 0,
        @SerializedName("mediaRecommendation")
        var mediaRecommendation: AnilistMediaEntity? = null,
        @SerializedName("rating")
        var rating: Int = 0,
        @SerializedName("user")
        var user: AnilistUserEntity? = null,
        @SerializedName("userRating")
        var userRating: String? = null
    ) : Parcelable
}