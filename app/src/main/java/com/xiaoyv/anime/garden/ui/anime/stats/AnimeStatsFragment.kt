package com.xiaoyv.anime.garden.ui.anime.stats

import com.xiaoyv.anime.garden.databinding.FragmentAnimeInfoStatsBinding
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModelFragment

/**
 * Class: [AnimeStatsFragment]
 *
 * @author why
 * @since 11/22/23
 */
class AnimeStatsFragment :
    BaseViewModelFragment<FragmentAnimeInfoStatsBinding, AnimeStatsViewModel>() {
    override fun initView() {

    }

    override fun initData() {

    }

    companion object {
        fun newInstance(): AnimeStatsFragment {
            return AnimeStatsFragment()
        }
    }
}