package com.xiaoyv.anime.garden.ui.search.anime

import androidx.annotation.ArrayRes
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ResourceUtils
import com.blankj.utilcode.util.Utils
import com.xiaoyv.anime.garden.R
import com.xiaoyv.anime.garden.api.AnimeApiManager
import com.xiaoyv.anime.garden.api.request.anilist.AnilistGraphqlParam
import com.xiaoyv.anime.garden.api.request.anilist.AnilistListParam
import com.xiaoyv.anime.garden.api.response.anilist.AnilistMediaEntity
import com.xiaoyv.anime.garden.api.response.anilist.AnilistOptionEntity
import com.xiaoyv.anime.garden.kts.currentYear
import com.xiaoyv.anime.garden.kts.fromJson
import com.xiaoyv.anime.garden.ui.search.SearchViewModel
import com.xiaoyv.anime.garden.view.SearchOptionView
import com.xiaoyv.blueprint.kts.launchUI
import com.xiaoyv.blueprint.kts.toJson
import com.xiaoyv.widget.kts.orEmpty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Class: [AnimeListViewModel]
 *
 * @author why
 * @since 11/22/23
 */
class AnimeListViewModel : SearchViewModel<AnilistMediaEntity>() {
    private val isAdult: Boolean = false

    private val queryParam = AnilistListParam(page = 1, type = "ANIME")

    private val localOptionEntity: AnilistOptionEntity by lazy {
        ResourceUtils.readAssets2String("json/options.json").fromJson() ?: AnilistOptionEntity()
    }

    @Suppress("SpellCheckingInspection")
    override val searchOptions by lazy {
        listOf(
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
                    .filter { it.isAdult == isAdult }
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

    override val isRefresh: Boolean
        get() = queryParam.page == 1

    override fun refresh(keyword: String, selectedOptions: List<SearchOptionView.Option>) {
        queryParam.page = 1
        fillOptionParam(keyword, selectedOptions)
        onLoadAnimeList()
    }

    override fun loadMore() {
        queryParam.page++
        onLoadAnimeList()
    }

    private fun onLoadAnimeList() {
        launchUI(
            stateView = loadingViewState,
            error = {
                it.printStackTrace()
                if (isRefresh.not()) queryParam.page--
            },
            block = {
                val listResponse = withContext(Dispatchers.IO) {
                    AnimeApiManager.animeApi.queryAnimeGraphql(
                        param = AnilistGraphqlParam.buildSearchList(queryParam)
                    )
                }

                val mediaList = listResponse.data?.page?.media.orEmpty()

                sendFetchedList(mediaList)
            }
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

    private fun fillOptionParam(keyword: String, selectedOptions: List<SearchOptionView.Option>) {
        queryParam.resetOption()
        queryParam.isAdult = isAdult
        queryParam.search = keyword.trim().ifBlank { null }

        // 当没有关键词和搜索选项时，填充默认 Index 的参数
        if (isRefresh && keyword.isBlank() && selectedOptions.isEmpty()) {
            queryParam.season = "FALL"
            queryParam.seasonYear = currentYear
            queryParam.nextYear = currentYear + 1
            queryParam.nextSeason = "WINTER"
            return
        }

        selectedOptions.forEach {
            val fieldName = it.fieldName
            val fieldValue = it.selected?.value
            val fieldIntValue = it.selected?.value?.toIntOrNull().orEmpty()
            if (fieldValue.isNullOrBlank()) return@forEach

            // 配置
            when (fieldName) {
                AnilistListParam::genres.name -> queryParam.genres = listOf(fieldValue)
                AnilistListParam::tags.name -> queryParam.tags = listOf(fieldValue)
                AnilistListParam::season.name -> queryParam.season = fieldValue
                AnilistListParam::format.name -> queryParam.format = listOf(fieldValue)
                AnilistListParam::sort.name -> queryParam.sort = fieldValue
                AnilistListParam::status.name -> queryParam.status = fieldValue
                AnilistListParam::licensedBy.name -> queryParam.licensedBy = listOf(fieldValue)
                AnilistListParam::countryOfOrigin.name -> queryParam.countryOfOrigin = fieldValue
                AnilistListParam::source.name -> queryParam.source = fieldValue
                AnilistListParam::year.name -> {
                    queryParam.year = "$fieldValue%"
                    queryParam.seasonYear = fieldIntValue
                }
            }
        }

        // 没选季节时，清空 seasonYear 参数
        if (queryParam.season == null) {
            queryParam.seasonYear = null
        }
        // 有选季节时，清空 year 参数
        else {
            queryParam.year = null
        }
    }
}