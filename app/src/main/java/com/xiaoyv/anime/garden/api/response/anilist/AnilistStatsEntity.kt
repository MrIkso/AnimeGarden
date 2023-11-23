@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.api.response.anilist

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AnilistStatsEntity(
    @SerializedName("scoreDistribution")
    var scoreDistribution: List<ScoreDistribution>? = null,
    @SerializedName("statusDistribution")
    var statusDistribution: List<StatusDistribution>? = null
) : Parcelable {
    @Keep
    @Parcelize
    data class ScoreDistribution(
        @SerializedName("amount")
        var amount: Int = 0,
        @SerializedName("score")
        var score: Int = 0
    ) : Parcelable

    @Keep
    @Parcelize
    data class StatusDistribution(
        @SerializedName("amount")
        var amount: Int = 0,
        @SerializedName("status")
        var status: String? = null
    ) : Parcelable
}