package com.xiaoyv.anime.garden.ui.search.anime

import com.xiaoyv.anime.garden.api.AnimeApiManager
import com.xiaoyv.anime.garden.api.request.anilist.AnilistGraphqlParam
import com.xiaoyv.anime.garden.api.request.anilist.AnilistListParam
import com.xiaoyv.anime.garden.api.response.anilist.AnilistMediaEntity
import com.xiaoyv.anime.garden.kts.currentYear
import com.xiaoyv.anime.garden.type.AnilistSearchOptions
import com.xiaoyv.anime.garden.ui.search.SearchViewModel
import com.xiaoyv.anime.garden.view.SearchOptionView
import com.xiaoyv.blueprint.kts.launchUI
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

    override val searchOptions by lazy {
        AnilistSearchOptions.buidlOptions(isAdult)
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