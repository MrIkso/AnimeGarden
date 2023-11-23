@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.api.response.anilist

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


/**
 * Class: [AnilistOptionEntity]
 *
 * @author why
 * @since 11/22/23
 */
@Keep
@Parcelize
data class AnilistOptionEntity(
    @SerializedName("genres")
    var genres: List<String>? = null,
    @SerializedName("links")
    var links: List<Link>? = null,
    @SerializedName("tags")
    var tags: List<AnilistMediaEntity.Tag>? = null
) : Parcelable {

    @Keep
    @Parcelize
    data class Link(
        @SerializedName("color")
        var color: String? = null,
        @SerializedName("icon")
        var icon: String? = null,
        @SerializedName("id")
        var id: Long = 0,
        @SerializedName("language")
        var language: String? = null,
        @SerializedName("site")
        var site: String? = null,
        @SerializedName("type")
        var type: String? = null,
        @SerializedName("url")
        var url: String? = null
    ) : Parcelable
}