package com.xiaoyv.anime.garden.api.response

import androidx.annotation.Keep

/**
 * AnimeDetailEntity
 *
 * @author why
 * @since 11/18/23
 */
@Keep
data class AnimeDetailEntity(
    var title: String? = null,
    var kind: List<String>? = null,
    var publishTime: String? = null,
    var modifiedTime: String? = null,
    var size: String? = null,
    var detailContent: String? = null,
    var resource: List<ResourceLink>? = null
) {

    @Keep
    data class ResourceLink(
        var type: String? = null,
        var typeId: Int = 0,
        val link: String? = null,
        val title: String? = null,
    )
}