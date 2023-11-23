package com.xiaoyv.anime.garden.ui.anime.overview

import androidx.annotation.ColorInt
import androidx.recyclerview.widget.DiffUtil
import com.xiaoyv.anime.garden.databinding.FragmentAnimeInfoOverviewItemBinding
import com.xiaoyv.anime.garden.kts.GoogleAttr
import com.xiaoyv.widget.binder.BaseQuickBindingHolder
import com.xiaoyv.widget.binder.BaseQuickDiffBindingAdapter
import com.xiaoyv.widget.kts.getAttrColor

/**
 * Class: [AnimeOverviewAdapter]
 *
 * @author why
 * @since 11/23/23
 */
class AnimeOverviewAdapter : BaseQuickDiffBindingAdapter<AnimeOverviewAdapter.OverviewItemEntity,
        FragmentAnimeInfoOverviewItemBinding>(OverviewDiffItemCallback) {

    override fun BaseQuickBindingHolder<FragmentAnimeInfoOverviewItemBinding>.converted(item: OverviewItemEntity) {
        binding.tvItemTitle.text = item.name
        binding.tvItemDesc.text = item.value
        binding.tvItemDesc.setTextColor(
            item.color ?: context.getAttrColor(GoogleAttr.colorOnSurfaceVariant)
        )
    }

    object OverviewDiffItemCallback : DiffUtil.ItemCallback<OverviewItemEntity>() {
        override fun areItemsTheSame(oldItem: OverviewItemEntity, newItem: OverviewItemEntity) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: OverviewItemEntity, newItem: OverviewItemEntity) =
            oldItem == newItem
    }

    data class OverviewItemEntity(
        var name: String,
        var value: String,
        @ColorInt
        var color: Int? = null
    )
}