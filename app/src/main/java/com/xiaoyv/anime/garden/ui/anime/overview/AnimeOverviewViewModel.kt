package com.xiaoyv.anime.garden.ui.anime.overview

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ResourceUtils
import com.xiaoyv.anime.garden.api.AnimeApiManager
import com.xiaoyv.anime.garden.api.request.anilist.AnilistGraphqlParam
import com.xiaoyv.anime.garden.api.request.anilist.AnilistMediaDetailParam
import com.xiaoyv.anime.garden.api.response.anilist.AnilistMediaEntity
import com.xiaoyv.anime.garden.kts.GoogleAttr
import com.xiaoyv.anime.garden.kts.toJsonMap
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
                "Format",
                mediaEntity.format.orEmpty()
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                "Episodes",
                mediaEntity.episodes.toString()
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                "Episode Duration",
                mediaEntity.duration.toString() + " Min's"
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                "Status",
                mediaEntity.status.orEmpty()
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                "Start Date",
                mediaEntity.startDate?.formatLocalDate().orEmpty()
            ),
            AnimeOverviewAdapter.OverviewItemEntity(
                "End Date",
                mediaEntity.endDate?.formatLocalDate().orEmpty()
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
        }
    }
}