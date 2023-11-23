@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.ui.anime.staff

import androidx.recyclerview.widget.DiffUtil
import com.xiaoyv.anime.garden.api.response.anilist.AnilistStaffEntity
import com.xiaoyv.anime.garden.databinding.FragmentAnimeInfoStaffBinding
import com.xiaoyv.anime.garden.kts.loadImageAnimate
import com.xiaoyv.widget.binder.BaseQuickBindingHolder
import com.xiaoyv.widget.binder.BaseQuickDiffBindingAdapter

/**
 * Class: [AnimeStaffAdapter]
 *
 * @author why
 * @since 11/23/23
 */
class AnimeStaffAdapter :
    BaseQuickDiffBindingAdapter<AnilistStaffEntity.Edge, FragmentAnimeInfoStaffBinding>(
        AnilistStaffDiffCallback
    ) {

    override fun BaseQuickBindingHolder<FragmentAnimeInfoStaffBinding>.converted(item: AnilistStaffEntity.Edge) {
        binding.ivStaff.loadImageAnimate(item.node?.image?.avaivableUrl)
        binding.tvTitle.text = item.node?.name?.userPreferred
        binding.tvName.text = item.role
        binding.tvDesc.text = item.node?.language
    }

    object AnilistStaffDiffCallback : DiffUtil.ItemCallback<AnilistStaffEntity.Edge>() {
        override fun areItemsTheSame(
            oldItem: AnilistStaffEntity.Edge,
            newItem: AnilistStaffEntity.Edge
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AnilistStaffEntity.Edge,
            newItem: AnilistStaffEntity.Edge
        ): Boolean {
            return oldItem == newItem
        }
    }

}