package com.xiaoyv.anime.garden.ui.search

import android.view.MenuItem
import androidx.annotation.CallSuper
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.KeyboardUtils
import com.chad.library.adapter.base.QuickAdapterHelper
import com.chad.library.adapter.base.loadState.LoadState
import com.chad.library.adapter.base.loadState.trailing.TrailingLoadStateAdapter
import com.xiaoyv.anime.garden.databinding.ActivitySearchBinding
import com.xiaoyv.anime.garden.kts.GoogleAttr
import com.xiaoyv.anime.garden.kts.initNavBack
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModelActivity
import com.xiaoyv.widget.binder.BaseQuickDiffBindingAdapter
import com.xiaoyv.widget.kts.getAttrColor

/**
 * Class: [SearchActivity]
 *
 * @author why
 * @since 11/22/23
 */
abstract class SearchActivity<ItemEntity, ViewModel : SearchViewModel<ItemEntity>> :
    BaseViewModelActivity<ActivitySearchBinding, ViewModel>() {

    abstract val contentAdapter: BaseQuickDiffBindingAdapter<ItemEntity, *>

    internal val keyword get() = binding.etName.text.toString().trim()

    private val adapterHelper: QuickAdapterHelper by lazy {
        QuickAdapterHelper.Builder(contentAdapter)
            .setTrailingLoadStateAdapter(object : TrailingLoadStateAdapter.OnTrailingListener {
                override fun onFailRetry() {
                    viewModel.loadMore()
                }

                override fun onLoad() {
                    viewModel.loadMore()
                }

                override fun isAllowLoading(): Boolean {
                    if (binding.srlRefresh.isRefreshing) {
                        return false
                    }

                    if (allowEmptyKeyword().not() && keyword.isBlank()) {
                        return false
                    }

                    return true
                }
            })
            .build()
    }

    @CallSuper
    override fun initView() {
        binding.toolbar.initNavBack(this)
        binding.rvItems.layoutManager = onConfigLayoutManager()
        binding.rvItems.adapter = adapterHelper.adapter
        binding.rvOptions.bindView(binding.ivAdvance)
    }

    @CallSuper
    override fun initData() {
        binding.srlRefresh.initRefresh()
        binding.srlRefresh.setColorSchemeColors(getAttrColor(GoogleAttr.colorPrimary))

        binding.rvOptions.options = viewModel.searchOptions
        binding.rvOptions.onOptionSelectedChange = {
            onRefreshList()
        }

        onConfigView()
    }


    @CallSuper
    override fun initListener() {
        binding.srlRefresh.setOnRefreshListener {
            onRefreshList()
        }

        binding.etName.doAfterTextChanged {
            adapterHelper.trailingLoadState = LoadState.None
            contentAdapter.submitList(emptyList())
        }

        binding.etName.setOnEditorActionListener { _, _, _ ->
            onRefreshList()
            true
        }
    }

    override fun LifecycleOwner.initViewObserver() {
        viewModel.onListLiveData.observe(this) {
            contentAdapter.submitList(it) {
                adapterHelper.trailingLoadState = viewModel.loadingMoreState

                if (viewModel.isRefresh && it.isEmpty()) {
                    onEmptyResult()
                }
            }
        }
    }

    abstract fun onConfigLayoutManager(): RecyclerView.LayoutManager

    abstract fun onConfigView()

    abstract fun allowEmptyKeyword(): Boolean

    open fun onEmptyResult() {

    }

    @CallSuper
    open fun onRefreshList() {
        if (keyword.isBlank() && !allowEmptyKeyword()) {
            binding.srlRefresh.isRefreshing = false
            return
        }

        KeyboardUtils.hideSoftInput(binding.etName)

        adapterHelper.trailingLoadState = LoadState.None
        binding.srlRefresh.isRefreshing = true

        // 根据关键词和参数选项刷新
        viewModel.refresh(keyword, binding.rvOptions.selectedOptions)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.initNavBack(this)
        return super.onOptionsItemSelected(item)
    }
}