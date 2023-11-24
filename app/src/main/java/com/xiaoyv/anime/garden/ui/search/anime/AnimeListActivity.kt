package com.xiaoyv.anime.garden.ui.search.anime

import android.view.Menu
import android.view.MenuItem
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.KeyboardUtils
import com.chad.library.adapter.base.layoutmanager.QuickGridLayoutManager
import com.chad.library.adapter.base.util.addOnDebouncedChildClick
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.xiaoyv.anime.garden.R
import com.xiaoyv.anime.garden.api.response.anilist.AnilistMediaEntity
import com.xiaoyv.anime.garden.kts.openInBrowser
import com.xiaoyv.anime.garden.ui.anime.AnimeInfoActivity
import com.xiaoyv.anime.garden.ui.search.SearchActivity
import com.xiaoyv.blueprint.kts.activity
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

    private val searchNameUrl: String
        get() = "https://www.bing.com/search?q=动漫${keyword}的原始名称是什么？"

    override fun onConfigView() {
        binding.toolbar.title = "Search Anime"
        binding.etName.hint = "Search"

        binding.rvItems.updatePadding(left = 8.dpi, top = 8.dpi, right = 8.dpi, bottom = 8.dpi)

        contentAdapter.addOnDebouncedChildClick(R.id.iv_cover) { adapter, _, position ->
            val item = adapter.getItem(position) ?: return@addOnDebouncedChildClick
            AnimeInfoActivity.openSelf(item)
        }

        // 默认加载
        onRefreshList()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add("Help")
            .setIcon(R.drawable.ic_help)
            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS)
            .setOnMenuItemClickListener {
                MaterialAlertDialogBuilder(activity)
                    .setTitle("搜索帮助")
                    .setMessage("搜索结果太少或为空？请尝试输入动漫或漫画的原始英文或日文名称进行搜索，不清楚可以去先搜索引擎查询。")
                    .setNegativeButton("我知道了", null)
                    .also {
                        if (keyword.isNotBlank()) {
                            it.setPositiveButton("搜索名称") { _, _ ->
                                openInBrowser(searchNameUrl)
                            }
                        }
                    }
                    .create()
                    .show()
                true
            }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onConfigLayoutManager(): RecyclerView.LayoutManager {
        return QuickGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
    }

    override fun allowEmptyKeyword(): Boolean {
        return true
    }

    override fun onEmptyResult() {
        if (keyword.isNotBlank()) {
            MaterialAlertDialogBuilder(activity)
                .setTitle("Tips")
                .setMessage("搜索结果为空，请尝试输入英文或日文名称，点击左侧按钮去搜索原始名称")
                .setPositiveButton("搜索名称") { _, _ ->
                    openInBrowser(searchNameUrl)
                }
                .create()
                .show()
        }
    }

    override fun onWindowFirstFocus() {
        KeyboardUtils.showSoftInput(binding.etName)
    }
}