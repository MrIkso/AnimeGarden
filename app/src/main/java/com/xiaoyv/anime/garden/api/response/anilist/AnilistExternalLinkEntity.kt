@file:Suppress("unused", "SpellCheckingInspection")

package com.xiaoyv.anime.garden.api.response.anilist

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Class: [AnilistExternalLinkEntity]
 *
 * @author why
 * @since 11/23/23
 */
@Keep
@Parcelize
data class AnilistExternalLinkEntity(
    @SerializedName("color")
    var color: String? = null,
    @SerializedName("icon")
    var icon: String? = null,
    @SerializedName("id")
    var id: Long = 0,
    @SerializedName("isDisabled")
    var isDisabled: Boolean = false,
    @SerializedName("language")
    var language: String? = null,
    @SerializedName("site")
    var site: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("url")
    var url: String? = null
) : Parcelable
