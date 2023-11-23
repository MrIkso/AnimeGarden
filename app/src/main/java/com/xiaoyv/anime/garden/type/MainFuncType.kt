package com.xiaoyv.anime.garden.type

import androidx.annotation.IntDef

/**
 * MainFuncType
 *
 * @author why
 * @since 11/18/23
 */
@IntDef(
    MainFuncType.TYPE_DETECT_ANIME,
    MainFuncType.TYPE_ANIME_INFO,
    MainFuncType.TYPE_ANIME_IMAGE,
    MainFuncType.TYPE_DETECT_CHARACTER,
)
@Retention(AnnotationRetention.SOURCE)
annotation class MainFuncType {
    companion object {
        const val TYPE_DETECT_ANIME = 1
        const val TYPE_DETECT_CHARACTER = 2
        const val TYPE_ANIME_INFO = 3
        const val TYPE_ANIME_IMAGE = 4
    }
}
