@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.api.response.anilist

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Class: [AnilistStudiosEntity]
 *
 * @author why
 * @since 11/23/23
 */
@Keep
@Parcelize
data class AnilistStudiosEntity(
    @SerializedName("edges")
    var edges: List<Edge>? = null
) : Parcelable {
    @Keep
    @Parcelize
    data class Edge(
        @SerializedName("isMain")
        var isMain: Boolean = false,
        @SerializedName("node")
        var node: Node? = null
    ) : Parcelable {
        @Keep
        @Parcelize
        data class Node(
            @SerializedName("id")
            var id: Int = 0,
            @SerializedName("name")
            var name: String? = null
        ) : Parcelable
    }
}