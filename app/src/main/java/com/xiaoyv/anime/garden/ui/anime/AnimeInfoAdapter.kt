package com.xiaoyv.anime.garden.ui.anime

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.xiaoyv.anime.garden.api.response.anilist.AnilistMediaEntity
import com.xiaoyv.anime.garden.ui.anime.characters.AnimeCharactersFragment
import com.xiaoyv.anime.garden.ui.anime.overview.AnimeOverviewFragment
import com.xiaoyv.anime.garden.ui.anime.staff.AnimeStaffFragment
import com.xiaoyv.anime.garden.ui.anime.stats.AnimeStatsFragment

/**
 * Class: [AnimeInfoAdapter]
 *
 * @author why
 * @since 11/22/23
 */
class AnimeInfoAdapter(fragmentActivity: FragmentActivity, private val media: AnilistMediaEntity) :
    FragmentStateAdapter(fragmentActivity) {

    internal val tabs = listOf(
        "Overview",
        "Characters",
        "Staff",
        "Stats"
    )

    override fun getItemCount() = tabs.count()

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AnimeOverviewFragment.newInstance(media)
            1 -> AnimeCharactersFragment.newInstance()
            2 -> AnimeStaffFragment.newInstance()
            3 -> AnimeStatsFragment.newInstance()
            else -> throw IllegalArgumentException()
        }
    }
}