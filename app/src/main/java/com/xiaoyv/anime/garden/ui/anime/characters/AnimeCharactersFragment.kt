package com.xiaoyv.anime.garden.ui.anime.characters

import com.xiaoyv.anime.garden.databinding.FragmentAnimeInfoCharactersBinding
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModelFragment

/**
 * Class: [AnimeCharactersFragment]
 *
 * @author why
 * @since 11/22/23
 */
class AnimeCharactersFragment :
    BaseViewModelFragment<FragmentAnimeInfoCharactersBinding, AnimeCharactersViewModel>() {
    override fun initView() {

    }

    override fun initData() {

    }

    companion object {
        fun newInstance(): AnimeCharactersFragment {
            return AnimeCharactersFragment()
        }
    }
}