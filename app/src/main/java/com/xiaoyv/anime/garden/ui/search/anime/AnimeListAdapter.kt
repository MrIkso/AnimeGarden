package com.xiaoyv.anime.garden.ui.search.anime

import android.graphics.Color
import androidx.recyclerview.widget.DiffUtil
import com.xiaoyv.anime.garden.api.response.anilist.AnilistMediaEntity
import com.xiaoyv.anime.garden.databinding.ViewAnimeItemBinding
import com.xiaoyv.anime.garden.kts.GoogleAttr
import com.xiaoyv.anime.garden.kts.loadImageAnimate
import com.xiaoyv.widget.binder.BaseQuickBindingHolder
import com.xiaoyv.widget.binder.BaseQuickDiffBindingAdapter
import com.xiaoyv.widget.kts.getAttrColor
import com.xiaoyv.widget.kts.orEmpty

/**
 * Class: [AnimeListAdapter]
 *
 * @author why
 * @since 11/22/23
 */
class AnimeListAdapter :
    BaseQuickDiffBindingAdapter<AnilistMediaEntity, ViewAnimeItemBinding>(AnimeListDiffCallback) {

    override fun BaseQuickBindingHolder<ViewAnimeItemBinding>.converted(item: AnilistMediaEntity) {
        val color = runCatching { Color.parseColor(item.coverImage?.color) }
            .getOrDefault(context.getAttrColor(GoogleAttr.colorOnSurfaceInverse))

        binding.tvTitle.text = item.title?.userPreferred
        binding.tvSource.text = item.averageScore.let { if (it == 0) "" else it.toString() }
        binding.tvTag.text = if (item.isFinished) {
            String.format("共%d话", item.episodes.orEmpty())
        } else {
            String.format("更新至%d话", item.currentEpisode)
        }

        binding.ivCover.setBackgroundColor((128 shl 24) or (color and 0x00FFFFFF))
        binding.ivCover.loadImageAnimate(item.coverImageUrl)
    }

    object AnimeListDiffCallback : DiffUtil.ItemCallback<AnilistMediaEntity>() {
        override fun areItemsTheSame(
            oldItem: AnilistMediaEntity,
            newItem: AnilistMediaEntity
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AnilistMediaEntity,
            newItem: AnilistMediaEntity
        ): Boolean {
            return oldItem == newItem
        }
    }

}