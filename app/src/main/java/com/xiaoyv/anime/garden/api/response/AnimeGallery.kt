package com.xiaoyv.anime.garden.api.response
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


/**
 * AnimeGallery
 *
 * @author why
 * @since 11/19/23
 */
@Keep
@Parcelize
data class AnimeGallery(
    @SerializedName("files")
    var files: List<String>? = null
) : Parcelable