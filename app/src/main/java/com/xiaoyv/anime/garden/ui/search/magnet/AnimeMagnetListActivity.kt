package com.xiaoyv.anime.garden.ui.search.magnet

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ClipboardUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.VibrateUtils
import com.xiaoyv.anime.garden.R
import com.xiaoyv.anime.garden.api.response.MagnetListEntity
import com.xiaoyv.anime.garden.ui.search.SearchActivity
import com.xiaoyv.anime.garden.ui.search.magnet.detail.AnimeMagnetDetailActivity
import com.xiaoyv.blueprint.kts.open

/**
 * AnimeMagnetListActivity
 *
 * @author why
 * @since 11/18/23
 */
class AnimeMagnetListActivity : SearchActivity<MagnetListEntity, AnimeMagnetListViewModel>() {

    override val contentAdapter by lazy {
        AnimeMagnetListAdapter()
    }

    override fun onConfigView() {
        binding.toolbar.title = "Search Anime Magnet"
        binding.etName.hint = "Search"

        contentAdapter.addOnItemChildClickListener(R.id.item_card) { adapter, _, position ->
            val entity = adapter.getItem(position)

            AnimeMagnetDetailActivity::class.open(paramString1 = entity?.detailId.orEmpty())
        }

        contentAdapter.addOnItemChildLongClickListener(R.id.item_card) { adapter, _, position ->
            val entity = adapter.getItem(position)
            ClipboardUtils.copyText(entity?.magnet.orEmpty())
            VibrateUtils.vibrate(10)
            true
        }
    }

    override fun onRefreshList() {
        super.onRefreshList()

        contentAdapter.keyword = binding.etName.text.toString()
    }

    override fun allowEmptyKeyword(): Boolean {
        return false
    }

    override fun onConfigLayoutManager(): RecyclerView.LayoutManager {
        return LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onWindowFirstFocus() {
        KeyboardUtils.showSoftInput(binding.etName)
    }
}