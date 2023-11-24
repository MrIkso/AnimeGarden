@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.api.response.anilist

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Class: [AnilistNameEntity]
 *
 * @author why
 * @since 11/23/23
 */
@Keep
@Parcelize
data class AnilistNameEntity(
    @SerializedName("userPreferred")
    var userPreferred: String? = null,
    @SerializedName("romaji")
    var romaji: String? = null,
    @SerializedName("english")
    var english: String? = null,
    @SerializedName("native")
    var native: String? = null,
) : Parcelable
