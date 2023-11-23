@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.ui.anime

import androidx.lifecycle.MutableLiveData
import com.xiaoyv.anime.garden.api.response.anilist.AnilistMediaEntity
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModel
import com.xiaoyv.widget.kts.orEmpty

/**
 * Class: [AnimeInfoViewModel]
 *
 * @author why
 * @since 11/19/23
 */
class AnimeInfoViewModel : BaseViewModel() {
    internal var mediaId: Long = 0
    internal val onMediaLiveData = MutableLiveData<AnilistMediaEntity?>()

    private val requireMediaId: Long
        get() = mediaId.takeIf { it != 0L } ?: onMediaLiveData.value?.id.orEmpty()

    val requireMediaEntity: AnilistMediaEntity
        get() = onMediaLiveData.value ?: AnilistMediaEntity(id = requireMediaId)
}