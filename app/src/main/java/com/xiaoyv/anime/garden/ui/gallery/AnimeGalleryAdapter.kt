package com.xiaoyv.anime.garden.ui.gallery

import androidx.recyclerview.widget.DiffUtil
import com.xiaoyv.anime.garden.databinding.ActivityAnimeGalleryItemBinding
import com.xiaoyv.anime.garden.kts.loadImageAnimate
import com.xiaoyv.widget.binder.BaseQuickBindingHolder
import com.xiaoyv.widget.binder.BaseQuickDiffBindingAdapter

/**
 * AnimeGalleryAdapter
 *
 * @author why
 * @since 11/19/23
 */
class AnimeGalleryAdapter :
    BaseQuickDiffBindingAdapter<AnimeGalleryAdapter.GalleryEntity, ActivityAnimeGalleryItemBinding>(
        DiffCallback
    ) {

    override fun BaseQuickBindingHolder<ActivityAnimeGalleryItemBinding>.converted(item: GalleryEntity) {
        binding.ivImage.loadImageAnimate(item.url)
    }

    data class GalleryEntity(
        var url: String,
        var uuid: String
    )

    object DiffCallback : DiffUtil.ItemCallback<GalleryEntity>() {
        override fun areItemsTheSame(oldItem: GalleryEntity, newItem: GalleryEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: GalleryEntity, newItem: GalleryEntity): Boolean {
            return oldItem == newItem
        }
    }
}