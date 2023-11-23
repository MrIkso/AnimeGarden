package com.xiaoyv.anime.garden.ui.search.magnet

import com.blankj.utilcode.util.StringUtils
import com.xiaoyv.anime.garden.api.AnimeApiManager
import com.xiaoyv.anime.garden.api.request.AnimeMagnetListParam
import com.xiaoyv.anime.garden.api.response.MagnetListEntity
import com.xiaoyv.anime.garden.type.AnimeGardenMagnetType
import com.xiaoyv.anime.garden.type.AnimeGardenSortType
import com.xiaoyv.anime.garden.type.AnimeGardenTeamType
import com.xiaoyv.anime.garden.ui.search.SearchViewModel
import com.xiaoyv.anime.garden.view.SearchOptionView
import com.xiaoyv.blueprint.kts.launchUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * AnimeMagnetListViewModel
 *
 * @author why
 * @since 11/18/23
 */
class AnimeMagnetListViewModel : SearchViewModel<MagnetListEntity>() {

    private val queryParam = AnimeMagnetListParam()

    override val isRefresh: Boolean
        get() = queryParam.page == 1

    override val searchOptions: List<SearchOptionView.Option> by lazy {
        listOf(
            SearchOptionView.Option(
                name = "分类",
                fieldName = AnimeMagnetListParam::kind.name,
                options = AnimeGardenMagnetType.typeTitleMap.entries.map {
                    SearchOptionView.OptionItem(
                        name = StringUtils.getString(it.value),
                        value = it.key
                    )
                }
            ),
            SearchOptionView.Option(
                name = "联盟",
                fieldName = AnimeMagnetListParam::team.name,
                options = AnimeGardenTeamType.types.map {
                    SearchOptionView.OptionItem(
                        name = it.label.orEmpty(),
                        value = it.value.orEmpty()
                    )
                }
            ),
            SearchOptionView.Option(
                name = "排序",
                fieldName = AnimeMagnetListParam::order.name,
                options = AnimeGardenSortType.typeTitleMap.map {
                    SearchOptionView.OptionItem(
                        name = StringUtils.getString(it.value),
                        value = it.key
                    )
                }
            )
        )
    }

    override fun refresh(keyword: String, selectedOptions: List<SearchOptionView.Option>) {
        queryParam.page = 1
        queryParam.keyword = keyword
        fillOptionParam(selectedOptions)
        onLoadMagnetList()
    }

    override fun loadMore() {
        queryParam.page++
        onLoadMagnetList()
    }

    private fun onLoadMagnetList() {
        launchUI(
            stateView = loadingViewState,
            error = {
                it.printStackTrace()
                if (isRefresh.not()) queryParam.page--
            },
            block = {
                val listResponse = withContext(Dispatchers.IO) {
                    AnimeApiManager.animeApi.queryAnimeList(queryParam.toQueryMap())
                }

                sendFetchedList(listResponse.data.orEmpty())
            }
        )
    }

    private fun fillOptionParam(selectedOptions: List<SearchOptionView.Option>) {
        val optionKind =
            selectedOptions.find { it.fieldName == AnimeMagnetListParam::kind.name }?.selected?.value
        if (optionKind != null) {
            queryParam.kind = optionKind
        }
        val optionOrder =
            selectedOptions.find { it.fieldName == AnimeMagnetListParam::order.name }?.selected?.value
        if (optionOrder != null) {
            queryParam.order = optionOrder
        }
        val optionTeam =
            selectedOptions.find { it.fieldName == AnimeMagnetListParam::team.name }?.selected?.value
        if (optionTeam != null) {
            queryParam.team = optionTeam
        }
    }
}