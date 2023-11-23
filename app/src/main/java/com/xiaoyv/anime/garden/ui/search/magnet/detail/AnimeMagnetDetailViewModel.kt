package com.xiaoyv.anime.garden.ui.search.magnet.detail

import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ResourceUtils
import com.xiaoyv.anime.garden.api.AnimeApiManager
import com.xiaoyv.anime.garden.api.response.AnimeDetailEntity
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModel
import com.xiaoyv.blueprint.kts.launchUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * AnimeMagnetDetailViewModel
 *
 * @author why
 * @since 11/18/23
 */
class AnimeMagnetDetailViewModel : BaseViewModel() {

    internal var animeId: String = ""

    internal val onAnimeDetailLiveData = MutableLiveData<AnimeDetailEntity?>()

    internal val htmlTemp by lazy {
        ResourceUtils.readAssets2String("html/detail.html")
    }

    override fun onViewCreated() {
        queryAnimeDetail()
    }

    internal fun queryAnimeDetail() {
        launchUI(
            stateView = loadingViewState,
            error = { onAnimeDetailLiveData.value = null },
            block = {
                onAnimeDetailLiveData.value = withContext(Dispatchers.IO) {
                    AnimeApiManager.animeApi.queryAnimeDetail(animeId).data
                }
            }
        )
    }
}