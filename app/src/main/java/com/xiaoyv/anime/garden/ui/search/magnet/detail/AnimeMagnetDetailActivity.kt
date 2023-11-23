package com.xiaoyv.anime.garden.ui.search.magnet.detail

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.ActivityUtils
import com.xiaoyv.anime.garden.databinding.ActivityMagnetDetailBinding
import com.xiaoyv.anime.garden.kts.GoogleAttr
import com.xiaoyv.anime.garden.kts.initNavBack
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModelActivity
import com.xiaoyv.blueprint.constant.NavKey
import com.xiaoyv.widget.kts.getAttrColor
import com.xiaoyv.widget.webview.listener.OnWindowListener

/**
 * AnimeMagnetDetailActivity
 *
 * @author why
 * @since 11/18/23
 */
class AnimeMagnetDetailActivity :
    BaseViewModelActivity<ActivityMagnetDetailBinding, AnimeMagnetDetailViewModel>() {

    override fun initIntentData(intent: Intent, bundle: Bundle, isNewIntent: Boolean) {
        viewModel.animeId = bundle.getString(NavKey.KEY_STRING).orEmpty()
    }

    override fun initView() {
        binding.toolbar.initNavBack(this)
        binding.srlRefresh.isRefreshing = true
    }

    override fun initData() {
        binding.srlRefresh.initRefresh()
        binding.srlRefresh.setColorSchemeColors(getAttrColor(GoogleAttr.colorPrimary))
    }

    override fun initListener() {
        binding.srlRefresh.setOnRefreshListener {
            viewModel.queryAnimeDetail()
        }

        binding.webView.multipleWindows = true
        binding.webView.onWindowListener = object : OnWindowListener {
            override fun openNewWindow(url: String) {
                ActivityUtils.startActivity(Intent.parseUri(url, Intent.URI_ALLOW_UNSAFE))
            }
        }
    }

    override fun LifecycleOwner.initViewObserver() {
        viewModel.onAnimeDetailLiveData.observe(this) {
            val contentHtml = it?.detailContent.orEmpty()
            val targetHtml = viewModel.htmlTemp.replace("HTML_CONTENT", contentHtml)

            binding.webView.loadHtml(targetHtml)

            binding.srlRefresh.isRefreshing = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.initNavBack(this)
        return super.onOptionsItemSelected(item)
    }
}