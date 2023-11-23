package com.xiaoyv.anime.garden.ui.detect.anime.result

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.LifecycleOwner
import com.xiaoyv.anime.garden.R
import com.xiaoyv.anime.garden.databinding.ActivityAnimeDetectResultBinding
import com.xiaoyv.anime.garden.kts.initNavBack
import com.xiaoyv.anime.garden.ui.anime.AnimeInfoActivity
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModelActivity
import com.xiaoyv.blueprint.constant.NavKey
import com.xiaoyv.blueprint.kts.open
import com.xiaoyv.widget.kts.getParcelObj

/**
 * Class: [AnimeDetectResultActivity]
 *
 * @author why
 * @since 11/18/23
 */
class AnimeDetectResultActivity :
    BaseViewModelActivity<ActivityAnimeDetectResultBinding, AnimeDetectResultViewModel>() {

    private val itemAdapter by lazy { AnimeDetectResultAdapter() }

    override fun initIntentData(intent: Intent, bundle: Bundle, isNewIntent: Boolean) {
        viewModel.animeSourceEntity = bundle.getParcelObj(NavKey.KEY_PARCELABLE)
    }

    override fun initView() {
        binding.toolbar.initNavBack(this)

        binding.rvItems.adapter = itemAdapter
    }

    override fun initData() {

    }

    override fun LifecycleOwner.initViewObserver() {
        viewModel.onAnimeSourceListLiveData.observe(this) {
            itemAdapter.submitList(it)
        }
    }

    override fun initListener() {
        itemAdapter.addOnItemChildClickListener(R.id.item_card) { adapter, _, position ->
            val item = adapter.getItem(position) ?: return@addOnItemChildClickListener
            AnimeInfoActivity::class.open(paramLong1 = item.anilist)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.initNavBack(this)
        return super.onOptionsItemSelected(item)
    }
}