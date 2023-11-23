@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.api.request.anilist

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Class: [AnilistMediaDetailParam]
 *
 * @author why
 * @since 11/22/23
 */
@Keep
@Parcelize
data class AnilistMediaDetailParam(
    @SerializedName("id")
    var id: Long,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("isAdult")
    var isAdult: Boolean = false
) : Parcelable