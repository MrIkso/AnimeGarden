@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.api.response.anilist

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

/**
 * Class: [AnilistPageEntity]
 *
 * @author why
 * @since 11/22/23
 */
@Keep
@Parcelize
data class AnilistPageEntity<T>(
    @SerializedName("media")
    var media: List<@RawValue T>? = null,
    @SerializedName("pageInfo")
    var pageInfo: PageInfo? = null
) : Parcelable {

    @Keep
    @Parcelize
    data class PageInfo(
        @SerializedName("currentPage")
        var currentPage: Int? = null,
        @SerializedName("hasNextPage")
        var hasNextPage: Boolean? = null,
        @SerializedName("lastPage")
        var lastPage: Int? = null,
        @SerializedName("perPage")
        var perPage: Int? = null,
        @SerializedName("total")
        var total: Int? = null
    ) : Parcelable
}