package com.xiaoyv.anime.garden.ui.search.anime

import androidx.core.view.updatePadding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.KeyboardUtils
import com.chad.library.adapter.base.layoutmanager.QuickGridLayoutManager
import com.chad.library.adapter.base.util.addOnDebouncedChildClick
import com.xiaoyv.anime.garden.R
import com.xiaoyv.anime.garden.api.response.anilist.AnilistMediaEntity
import com.xiaoyv.anime.garden.ui.anime.AnimeInfoActivity
import com.xiaoyv.anime.garden.ui.search.SearchActivity
import com.xiaoyv.blueprint.kts.open
import com.xiaoyv.widget.binder.BaseQuickDiffBindingAdapter
import com.xiaoyv.widget.kts.dpi

/**
 * Class: [AnimeListActivity]
 *
 * @author why
 * @since 11/22/23
 */
class AnimeListActivity : SearchActivity<AnilistMediaEntity, AnimeListViewModel>() {
    override val contentAdapter: BaseQuickDiffBindingAdapter<AnilistMediaEntity, *> by lazy {
        AnimeListAdapter()
    }

    override fun onConfigView() {
        binding.toolbar.title = "Search Anime"
        binding.etName.hint = "Search"

        binding.rvItems.updatePadding(left = 8.dpi, top = 8.dpi, right = 8.dpi, bottom = 8.dpi)

        contentAdapter.addOnDebouncedChildClick(R.id.iv_cover) { adapter, _, position ->
            val item = adapter.getItem(position) ?: return@addOnDebouncedChildClick
            AnimeInfoActivity::class.open(paramParcelable1 = item)
        }

        // 默认加载
        onRefreshList()
    }

    override fun onConfigLayoutManager(): RecyclerView.LayoutManager {
        return QuickGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
    }

    override fun allowEmptyKeyword(): Boolean {
        return true
    }

    override fun onWindowFirstFocus() {
        KeyboardUtils.showSoftInput(binding.etName)
    }
}