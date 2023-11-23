package com.xiaoyv.anime.garden.ui.detect.anime.result

import com.xiaoyv.anime.garden.api.response.AnimeSourceEntity
import com.xiaoyv.anime.garden.databinding.ActivityAnimeDetectResultItemBinding
import com.xiaoyv.anime.garden.kts.formatSecondsToTime
import com.xiaoyv.widget.binder.BaseQuickBindingAdapter
import com.xiaoyv.widget.binder.BaseQuickBindingHolder
import com.xiaoyv.widget.kts.loadImage

/**
 * AnimeDetectResultAdapter
 *
 * @author why
 * @since 11/18/23
 */
class AnimeDetectResultAdapter :
    BaseQuickBindingAdapter<AnimeSourceEntity.SourceResult, ActivityAnimeDetectResultItemBinding>() {
    override fun BaseQuickBindingHolder<ActivityAnimeDetectResultItemBinding>.converted(item: AnimeSourceEntity.SourceResult) {
        val from = item.from.formatSecondsToTime()
        val to = item.to.formatSecondsToTime()
        val episode = item.episode.orEmpty().ifBlank { "unknown" }

        binding.tvTitle.text = item.filename.orEmpty()
        binding.tvNo.text = String.format("Episode: %s", episode)
        binding.tvTime.text = String.format("%s - %s", from, to)
        binding.tvDesc.text = String.format("~%.2f%% Similarity", item.similarity * 100)
        binding.ivCover.loadImage(item.image)
    }
}