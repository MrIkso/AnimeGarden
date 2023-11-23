@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.api.request.anilist

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


/**
 * Class: [AnilistIndexParam]
 *
 * @author why
 * @since 11/22/23
 */
@Keep
@Parcelize
data class AnilistIndexParam(
    @SerializedName("season")
    var season: String,
    @SerializedName("seasonYear")
    var seasonYear: Int,
    @SerializedName("nextSeason")
    var nextSeason: String,
    @SerializedName("nextYear")
    var nextYear: Int,
    @SerializedName("type")
    var type: String
) : Parcelable
