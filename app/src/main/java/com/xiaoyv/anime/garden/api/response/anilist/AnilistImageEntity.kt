@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.api.response.anilist

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AnilistImageEntity(
    @SerializedName("color")
    var color: String? = null,
    @SerializedName("extraLarge")
    var extraLarge: String? = null,
    @SerializedName("large")
    var large: String? = null
) : Parcelable {

    val avaivableUrl: String
        get() = extraLarge ?: large.orEmpty()
}