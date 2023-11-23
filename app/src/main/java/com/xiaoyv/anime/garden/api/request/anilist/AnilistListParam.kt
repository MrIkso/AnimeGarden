@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.api.request.anilist

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AnilistListParam(
    @SerializedName("page")
    var page: Int,
    @SerializedName("type")
    var type: String,
    @SerializedName("year")
    var year: String? = null,
    @SerializedName("search")
    var search: String? = null,
    @SerializedName("countryOfOrigin")
    var countryOfOrigin: String? = null,
    @SerializedName("format")
    var format: List<String>? = null,
    @SerializedName("genres")
    var genres: List<String>? = null,
    @SerializedName("tags")
    var tags: List<String>? = null,
    @SerializedName("licensedBy")
    var licensedBy: List<String>? = null,
    @SerializedName("season")
    var season: String? = null,
    @SerializedName("seasonYear")
    var seasonYear: Int? = null,
    @SerializedName("nextSeason")
    var nextSeason: String? = null,
    @SerializedName("nextYear")
    var nextYear: Int? = null,
    @SerializedName("sort")
    var sort: String? = null,
    @SerializedName("source")
    var source: String? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("isAdult")
    var isAdult: Boolean = false,
) : Parcelable {

    /**
     * 重置搜索选项（不会重置 [page]、[type]、[isAdult]）
     */
    fun resetOption() {
        countryOfOrigin = null
        format = null
        genres = null
        tags = null
        licensedBy = null
        season = null
        seasonYear = null
        nextSeason = null
        nextYear = null
        sort = null
        source = null
        status = null
    }
}