@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.api.response.anilist

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Class: [AnilistCharacterEntity]
 *
 * @author why
 * @since 11/23/23
 */
@Keep
@Parcelize
data class AnilistCharacterEntity(
    @SerializedName("edges")
    var edges: List<Edge>? = null
) : Parcelable {
    @Keep
    @Parcelize
    data class Edge(
        @SerializedName("id")
        var id: Long = 0,
        @SerializedName("name")
        var name: String? = null,
        @SerializedName("node")
        var node: Node? = null,
        @SerializedName("role")
        var role: String? = null,
        @SerializedName("voiceActors")
        var voiceActors: List<VoiceActor>? = null
    ) : Parcelable {
        @Keep
        @Parcelize
        data class Node(
            @SerializedName("id")
            var id: Long = 0,
            @SerializedName("image")
            var image: AnilistImageEntity? = null,
            @SerializedName("name")
            var name: AnilistNameEntity? = null
        ) : Parcelable

        @Keep
        @Parcelize
        data class VoiceActor(
            @SerializedName("id")
            var id: Long = 0,
            @SerializedName("image")
            var image: AnilistImageEntity? = null,
            @SerializedName("language")
            var language: String? = null,
            @SerializedName("name")
            var name: AnilistNameEntity? = null
        ) : Parcelable
    }
}