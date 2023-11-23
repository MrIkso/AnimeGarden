package com.xiaoyv.anime.garden.api.response

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

/**
 * Class: [ListParcelableWrapEntity]
 *
 * @author why
 * @since 11/21/23
 */
@Keep
@Parcelize
data class ListParcelableWrapEntity<T>(var list: List<@RawValue T>) : Parcelable
