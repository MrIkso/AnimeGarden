package com.xiaoyv.anime.garden.ui.anime.staff

import com.xiaoyv.anime.garden.databinding.FragmentAnimeInfoStaffBinding
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModelFragment

/**
 * Class: [AnimeStaffFragment]
 *
 * @author why
 * @since 11/22/23
 */
class AnimeStaffFragment :
    BaseViewModelFragment<FragmentAnimeInfoStaffBinding, AnimeStaffViewModel>() {
    override fun initView() {

    }

    override fun initData() {

    }

    companion object {
        fun newInstance(): AnimeStaffFragment {
            return AnimeStaffFragment()
        }
    }
}