@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.type

import androidx.annotation.ArrayRes
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.Utils
import com.xiaoyv.anime.garden.R
import com.xiaoyv.anime.garden.api.request.anilist.AnilistListParam
import com.xiaoyv.anime.garden.api.response.anilist.AnilistOptionEntity
import com.xiaoyv.anime.garden.kts.currentYear
import com.xiaoyv.anime.garden.kts.fromJson
import com.xiaoyv.anime.garden.view.SearchOptionView
import kotlin.reflect.KProperty

/**
 * Class: [AnilistSearchOptions]
 *
 * @author why
 * @since 11/23/23
 */
object AnilistSearchOptions {

    private val localOptionEntity: AnilistOptionEntity by lazy {
        ResourceUtils.readAssets2String("json/options.json").fromJson() ?: AnilistOptionEntity()
    }

    private val totalSearchOptions by lazy {
        buidlOptions(null)
    }

    fun getOptionName(fieldName: KProperty<*>, value: String?): String {
        return totalSearchOptions
            .find { it.fieldName == fieldName.name }?.options.orEmpty()
            .find { it.value == value.orEmpty() }?.name ?: value.orEmpty()
    }

    fun buidlOptions(isAdult: Boolean? = null): List<SearchOptionView.Option> {
        return listOf(
            SearchOptionView.Option(
                name = "Genres",
                fieldName = AnilistListParam::genres.name,
                options = localOptionEntity.genres.orEmpty().map {
                    SearchOptionView.OptionItem(it, it)
                }
            ),
            SearchOptionView.Option(
                name = "Tags",
                fieldName = AnilistListParam::tags.name,
                options = localOptionEntity.tags.orEmpty()
                    .filter { if (isAdult == null) true else it.isAdult == isAdult }
                    .map {
                        SearchOptionView.OptionItem(it.name.orEmpty(), it.name.orEmpty())
                    }
            ),
            SearchOptionView.Option(
                name = "Year",
                fieldName = AnilistListParam::year.name,
                options = buildYearArray()
            ),
            SearchOptionView.Option(
                name = "Season",
                fieldName = AnilistListParam::season.name,
                options = buildFromArray(
                    R.array.array_value_anime_season,
                    R.array.array_option_value_season
                )
            ),
            SearchOptionView.Option(
                name = "Format",
                fieldName = AnilistListParam::format.name,
                options = buildFromArray(
                    R.array.array_value_anime_format,
                    R.array.array_option_value_format
                )
            ),
            SearchOptionView.Option(
                name = "Sort",
                fieldName = AnilistListParam::sort.name,
                options = buildFromArray(
                    R.array.array_value_anime_sort,
                    R.array.array_option_value_sort
                )
            ),
            SearchOptionView.Option(
                name = "Airing Status",
                fieldName = AnilistListParam::status.name,
                options = buildFromArray(
                    R.array.array_value_anime_airing_status,
                    R.array.array_option_value_status
                )
            ),
            SearchOptionView.Option(
                name = "Streaming On",
                fieldName = AnilistListParam::licensedBy.name,
                options = localOptionEntity.links.orEmpty().map {
                    SearchOptionView.OptionItem(it.site.orEmpty(), it.id.toString())
                }
            ),
            SearchOptionView.Option(
                name = "Country Of Origin",
                fieldName = AnilistListParam::countryOfOrigin.name,
                options = buildFromArray(
                    R.array.array_value_anime_country_of_origin,
                    R.array.array_option_value_country_of_origin
                )
            ),
            SearchOptionView.Option(
                name = "Source Material",
                fieldName = AnilistListParam::source.name,
                options = buildFromArray(
                    R.array.array_value_anime_source_material,
                    R.array.array_option_value_source_material
                )
            ),
            SearchOptionView.Option(
                name = "Doujin",
                fieldName = "",
                options = emptyList()
            )
        )
    }

    private fun buildFromArray(
        @ArrayRes nameArrayResId: Int,
        @ArrayRes valueArrayResId: Int
    ): List<SearchOptionView.OptionItem> {
        val names = Utils.getApp().resources.getStringArray(nameArrayResId)
        val values = Utils.getApp().resources.getStringArray(valueArrayResId)

        return names.mapIndexed { index, name ->
            SearchOptionView.OptionItem(name, values.getOrNull(index).orEmpty())
        }
    }

    private fun buildYearArray(): List<SearchOptionView.OptionItem> {
        val minYear = 1940
        return arrayListOf<SearchOptionView.OptionItem>().apply {
            for (year in (currentYear + 1) downTo minYear) {
                add(SearchOptionView.OptionItem(year.toString(), year.toString()))
            }
        }
    }
}