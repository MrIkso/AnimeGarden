@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.ui.anime.overview

import androidx.recyclerview.widget.DiffUtil
import com.xiaoyv.anime.garden.api.response.anilist.AnilistRelationsEntity
import com.xiaoyv.anime.garden.databinding.FragmentAnimeInfoOverviewRelationBinding
import com.xiaoyv.anime.garden.kts.loadImageAnimate
import com.xiaoyv.widget.binder.BaseQuickBindingHolder
import com.xiaoyv.widget.binder.BaseQuickDiffBindingAdapter

/**
 * Class: [AnimeOverviewRelationAdapter]
 *
 * @author why
 * @since 11/23/23
 */
class AnimeOverviewRelationAdapter : BaseQuickDiffBindingAdapter<AnilistRelationsEntity.Edge,
        FragmentAnimeInfoOverviewRelationBinding>(AnilistRelationsDiffCallback) {

    override fun BaseQuickBindingHolder<FragmentAnimeInfoOverviewRelationBinding>.converted(item: AnilistRelationsEntity.Edge) {
        binding.ivCover.loadImageAnimate(item.node?.coverImageUrl)
        binding.tvTitle.text = item.relationType
        binding.tvName.text = item.node?.title?.userPreferred
        binding.tvDesc.text = buildString {
            append(item.node?.format)
            append(" · ")
            append(item.node?.status)
        }
    }

    object AnilistRelationsDiffCallback : DiffUtil.ItemCallback<AnilistRelationsEntity.Edge>() {
        override fun areItemsTheSame(
            oldItem: AnilistRelationsEntity.Edge,
            newItem: AnilistRelationsEntity.Edge
        ): Boolean {
            return oldItem.id == newItem.id

        }

        override fun areContentsTheSame(
            oldItem: AnilistRelationsEntity.Edge,
            newItem: AnilistRelationsEntity.Edge
        ): Boolean {
            return oldItem == newItem
        }
    }
}
