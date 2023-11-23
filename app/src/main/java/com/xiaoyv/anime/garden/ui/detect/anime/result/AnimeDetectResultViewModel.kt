package com.xiaoyv.anime.garden.ui.detect.anime.result

import androidx.lifecycle.MutableLiveData
import com.xiaoyv.anime.garden.api.response.AnimeSourceEntity
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModel

/**
 * AnimeDetectResultViewModel
 *
 * @author why
 * @since 11/18/23
 */
class AnimeDetectResultViewModel : BaseViewModel() {

    var animeSourceEntity: AnimeSourceEntity? = null

    internal val onAnimeSourceListLiveData = MutableLiveData<List<AnimeSourceEntity.SourceResult>>()

    override fun onViewCreated() {
        onAnimeSourceListLiveData.value = animeSourceEntity?.result.orEmpty()
    }
}