@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.api.response.anilist

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.xiaoyv.anime.garden.api.response.base.BaseResponse
import kotlinx.parcelize.Parcelize

/**
 * BaseAnimeListResponse
 *
 * @author why
 * @since 11/18/23
 */
@Keep
@Parcelize
class AnilistResponse<T> : BaseResponse<AnilistResponse.WrapEntity<T>>() {
    @Keep
    @Parcelize
    data class WrapEntity<T>(
        @SerializedName("Page")
        var page: AnilistPageEntity<T>? = null,
        @SerializedName("Media")
        var media: AnilistMediaEntity? = null
    ) : Parcelable
}
