@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.type

import androidx.annotation.StringDef

/**
 * Class: [AnimeGalleryUrlType]
 *
 * @author why
 * @since 11/21/23
 */
@StringDef(
    AnimeGalleryUrlType.TYPE_WAIFU_SFW_PICS,
    AnimeGalleryUrlType.TYPE_WAIFU_NSFW_PICS,
    AnimeGalleryUrlType.TYPE_PIC_RE
)
@Retention(AnnotationRetention.SOURCE)
annotation class AnimeGalleryUrlType {
    companion object {
        const val TYPE_WAIFU_SFW_PICS = "https://api.waifu.pics/many/sfw/waifu"
        const val TYPE_WAIFU_NSFW_PICS = "https://api.waifu.pics/many/nsfw/waifu"
        const val TYPE_PIC_RE = "https://pic.re"
    }
}
