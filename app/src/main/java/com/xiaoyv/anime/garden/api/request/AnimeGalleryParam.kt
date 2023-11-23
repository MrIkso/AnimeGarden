package com.xiaoyv.anime.garden.api.request

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * AnimeGalleryParam
 *
 * @author why
 * @since 11/19/23
 */
@Keep
@Parcelize
data class AnimeGalleryParam(
    var exclude: Array<String> = emptyArray()
) : Parcelable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AnimeGalleryParam

        return exclude.contentEquals(other.exclude)
    }

    override fun hashCode(): Int {
        return exclude.contentHashCode()
    }
}
