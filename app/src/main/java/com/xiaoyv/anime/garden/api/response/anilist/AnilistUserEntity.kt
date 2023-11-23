@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.api.response.anilist

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AnilistUserEntity(
    @SerializedName("avatar")
    var avatar: AnilistImageEntity? = null,
    @SerializedName("id")
    var id: Long = 0,
    @SerializedName("name")
    var name: String? = null
) : Parcelable