package com.xiaoyv.anime.garden.ui.search.magnet

import android.graphics.Color
import androidx.recyclerview.widget.DiffUtil
import com.blankj.utilcode.util.SpanUtils
import com.xiaoyv.anime.garden.api.response.MagnetListEntity
import com.xiaoyv.anime.garden.databinding.ViewMagnetItemBinding
import com.xiaoyv.anime.garden.kts.highlightText
import com.xiaoyv.anime.garden.type.AnimeGardenMagnetType
import com.xiaoyv.anime.garden.type.AnimeGardenTeamType
import com.xiaoyv.widget.binder.BaseQuickBindingHolder
import com.xiaoyv.widget.binder.BaseQuickDiffBindingAdapter

/**
 * AnimeMagnetListAdapter
 *
 * @author why
 * @since 11/18/23
 */
class AnimeMagnetListAdapter :
    BaseQuickDiffBindingAdapter<MagnetListEntity, ViewMagnetItemBinding>(MagnetListDiffCallback) {

    var keyword: String = ""

    override fun BaseQuickBindingHolder<ViewMagnetItemBinding>.converted(item: MagnetListEntity) {
        val teamName = AnimeGardenTeamType.getTypeName(item.tagId, item.tag).ifBlank { "unknown" }
        val kindName = AnimeGardenMagnetType.getTypeName(item.kind.orEmpty()).ifBlank { "unknown" }
        val publisher = item.publisher.orEmpty().ifBlank { "unknown" }
        val time = item.time.orEmpty().ifBlank { "unknown" }
        val size = item.size.orEmpty().ifBlank { "unknown" }

        binding.tvTitle.text = item.title
        binding.tvTitle.highlightText(keyword, Color.GREEN)

        SpanUtils.with(binding.tvDesc)
            .append("Team: $teamName\n")
            .append("Author: $publisher\n")
            .append("Time: $time\n")
            .append("Size: $size\n")
            .append("Kind: $kindName\n")
            .append("Download/Finish: ${item.download}/${item.finish}")
            .create()
    }

    object MagnetListDiffCallback : DiffUtil.ItemCallback<MagnetListEntity>() {
        override fun areItemsTheSame(
            oldItem: MagnetListEntity,
            newItem: MagnetListEntity
        ): Boolean {
            return oldItem.detailId == newItem.detailId
        }

        override fun areContentsTheSame(
            oldItem: MagnetListEntity,
            newItem: MagnetListEntity
        ): Boolean {
            return oldItem == newItem
        }
    }
}