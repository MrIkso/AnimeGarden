package com.xiaoyv.anime.garden.type

import androidx.annotation.StringDef
import com.xiaoyv.anime.garden.R

/**
 * AnimeGardenSortType
 *
 * @author why
 * @since 11/19/23
 */
@StringDef(
    AnimeGardenSortType.TYPE_DATE_ASC,
    AnimeGardenSortType.TYPE_DATE_DESC,
    AnimeGardenSortType.TYPE_REL
)
@Retention(AnnotationRetention.SOURCE)
annotation class AnimeGardenSortType {
    companion object {
        const val TYPE_DATE_DESC = "date-desc"
        const val TYPE_DATE_ASC = "date-asc"
        const val TYPE_REL = "rel"

        val typeTitleMap by lazy {
            mapOf(
                TYPE_DATE_DESC to R.string.magnet_sort_date_desc,
                TYPE_DATE_ASC to R.string.magnet_sort_date_asc,
                TYPE_REL to R.string.magnet_sort_rel,
            )
        }
    }
}
