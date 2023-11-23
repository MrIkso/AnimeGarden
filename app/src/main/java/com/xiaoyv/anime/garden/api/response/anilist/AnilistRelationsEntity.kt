@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.api.response.anilist

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AnilistRelationsEntity(
    @SerializedName("edges")
    var edges: List<Edge>? = null
) : Parcelable {
    @Keep
    @Parcelize
    data class Edge(
        @SerializedName("id")
        var id: Long = 0,
        @SerializedName("node")
        var node: AnilistMediaEntity? = null,
        @SerializedName("relationType")
        var relationType: String? = null
    ) : Parcelable
}