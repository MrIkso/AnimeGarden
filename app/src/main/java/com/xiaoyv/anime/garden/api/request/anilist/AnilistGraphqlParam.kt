@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.api.request.anilist

import android.os.Parcelable
import androidx.annotation.Keep
import com.blankj.utilcode.util.ResourceUtils
import com.google.gson.annotations.SerializedName
import com.xiaoyv.anime.garden.kts.toJsonMap
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue


/**
 * AnilistGraphqlParam
 *
 * @author why
 * @since 11/19/23
 */
@Keep
@Parcelize
data class AnilistGraphqlParam(
    @SerializedName("query")
    var query: String? = null,
    @SerializedName("variables")
    var variables: Map<String, @RawValue Any> = emptyMap()
) : Parcelable {

    companion object {
        fun buildIndexList(param: AnilistIndexParam): AnilistGraphqlParam {
            val query = ResourceUtils.readAssets2String("graph/anilist_media_index.graphql")
            return AnilistGraphqlParam(query, param.toJsonMap())
        }

        fun buildSearchList(param: AnilistListParam): AnilistGraphqlParam {
            val query = ResourceUtils.readAssets2String("graph/anilist_media_search.graphql")
            return AnilistGraphqlParam(query, param.toJsonMap())
        }
    }
}
