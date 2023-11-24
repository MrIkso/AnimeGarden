@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.ui.anime.overview

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ResourceUtils
import com.xiaoyv.anime.garden.api.AnimeApiManager
import com.xiaoyv.anime.garden.api.request.anilist.AnilistGraphqlParam
import com.xiaoyv.anime.garden.api.request.anilist.AnilistListParam
import com.xiaoyv.anime.garden.api.request.anilist.AnilistMediaDetailParam
import com.xiaoyv.anime.garden.api.response.anilist.AnilistMediaEntity
import com.xiaoyv.anime.garden.kts.GoogleAttr
import com.xiaoyv.anime.garden.kts.toJsonMap
import com.xiaoyv.anime.garden.type.AnilistSearchOptions
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModel
import com.xiaoyv.blueprint.kts.launchUI
import com.xiaoyv.widget.kts.getAttrColor
import com.xiaoyv.widget.kts.orEmpty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Class: [AnimeOverviewViewModel]
 *
 * @author why
 * @since 11/22/23
 */
class AnimeOverviewViewModel : BaseViewModel() {
    internal val onMediaLiveData = MutableLiveData<AnilistMediaEntity?>()

    private val requireMediaId: Long
        get() = onMediaLiveData.value?.id.orEmpty()

    private val requireMediaType: String?
        get() = onMediaLiveData.value?.type

    private val requireIsAdult: Boolean
        get() = onMediaLiveData.value?.isAdult == true

    private val querySql by lazy {
        ResourceUtils.readAssets2String("graph/anilist_media_detail.graphql")
    }

    override fun onViewCreated() {
        queryAnimeInfo()
    }

    private fun queryAnimeInfo() {
        launchUI(
            stateView = loadingViewState,
            error = {
                it.printStackTrace()
                onMediaLiveData.value = null
            },
            block = {
                onMediaLiveData.value = withContext(Dispatchers.IO) {
                    val variables = AnilistMediaDetailParam(
                        id = requireMediaId,
                        type = requireMediaType,
                        isAdult = requireIsAdult
                    ).toJsonMap()

                    val param = AnilistGraphqlParam(querySql, variables)

                    AnimeApiManager.animeApi.queryAnimeGraphql(param = param).data?.media
                        ?: throw IllegalStateException()
                }
            }
        )
    }

    fun buildOverviewItem(mediaEntity: AnilistMediaEntity): List<AnimeOverviewAdapter.OverviewItemEntity> {
        return mutableListOf(
            AnimeOverviewAdapter.OverviewItemEntity(
                name = "Format",
                AnilistSearchOptions.getOptionName(AnilistListParam::format, mediaEntity.format)
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                name = "Episodes",
                mediaEntity.episodes.toString()
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                name = "Episode Duration",
                mediaEntity.duration.toString() + " Min's"
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                name = "Status",
                AnilistSearchOptions.getOptionName(AnilistListParam::status, mediaEntity.status)
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                name = "Start Date",
                mediaEntity.startDate?.formatLocalDate().orEmpty()
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                name = "End Date",
                mediaEntity.endDate?.formatLocalDate().orEmpty()
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                name = "Season",
                buildString {
                    append(
                        AnilistSearchOptions.getOptionName(
                            AnilistListParam::season, mediaEntity.season
                        )
                    )
                    append(" ")
                    append(mediaEntity.seasonYear)
                }
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                name = "Average Score",
                mediaEntity.averageScore.toString()
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                name = "Mean Score",
                mediaEntity.meanScore.toString()
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                name = "Popularity",
                mediaEntity.popularity.toString()
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                name = "Favourites",
                mediaEntity.favourites.toString()
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                name = "Studios",
                mediaEntity.studios?.edges.orEmpty()
                    .filter { it.isMain }
                    .joinToString(", ") { it.node?.name.orEmpty() }
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                name = "Producers",
                mediaEntity.studios?.edges.orEmpty()
                    .filter { !it.isMain }
                    .joinToString(", ") { it.node?.name.orEmpty() }
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                name = "Source",
                AnilistSearchOptions.getOptionName(AnilistListParam::source, mediaEntity.source)
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                name = "Hashtag",
                mediaEntity.hashtag.orEmpty()
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                name = "Genres",
                mediaEntity.genres.orEmpty().joinToString(", ")
            )
        ).apply {
            if (!mediaEntity.isFinished) {
                add(
                    0, AnimeOverviewAdapter.OverviewItemEntity(
                        name = "Airing",
                        value = mediaEntity.nextAiringEpisodeDesc(),
                        color = context.getAttrColor(GoogleAttr.colorPrimary)
                    )
                )
            }
        }.filter { it.value.isNotBlank() }
    }
}