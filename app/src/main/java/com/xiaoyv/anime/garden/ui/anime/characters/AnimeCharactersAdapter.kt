@file:Suppress("SpellCheckingInspection")

package com.xiaoyv.anime.garden.ui.anime.characters

import androidx.recyclerview.widget.DiffUtil
import com.xiaoyv.anime.garden.api.response.anilist.AnilistCharacterEntity
import com.xiaoyv.anime.garden.databinding.FragmentAnimeInfoCharactersBinding
import com.xiaoyv.anime.garden.kts.loadImageAnimate
import com.xiaoyv.widget.binder.BaseQuickBindingHolder
import com.xiaoyv.widget.binder.BaseQuickDiffBindingAdapter

/**
 * Class: [AnimeCharactersAdapter]
 *
 * @author why
 * @since 11/23/23
 */
class AnimeCharactersAdapter :
    BaseQuickDiffBindingAdapter<AnilistCharacterEntity.Edge, FragmentAnimeInfoCharactersBinding>(
        AnilistStaffDiffCallback
    ) {

    override fun BaseQuickBindingHolder<FragmentAnimeInfoCharactersBinding>.converted(item: AnilistCharacterEntity.Edge) {
        val voiceActor = item.voiceActors?.firstOrNull()
        val media = item.node

        binding.ivCharacter.loadImageAnimate(media?.image?.avaivableUrl)
        binding.ivAv.loadImageAnimate(voiceActor?.image?.avaivableUrl)

        binding.tvCharacterTitle.text = media?.name?.userPreferred.orEmpty()
        binding.tvCharacterDesc.text = item.role.orEmpty()
        binding.tvAvTitle.text = voiceActor?.name?.userPreferred.orEmpty()
        binding.tvAvDesc.text = voiceActor?.language.orEmpty()
    }

    object AnilistStaffDiffCallback : DiffUtil.ItemCallback<AnilistCharacterEntity.Edge>() {
        override fun areItemsTheSame(
            oldItem: AnilistCharacterEntity.Edge,
            newItem: AnilistCharacterEntity.Edge
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: AnilistCharacterEntity.Edge,
            newItem: AnilistCharacterEntity.Edge
        ): Boolean {
            return oldItem == newItem
        }
    }

}