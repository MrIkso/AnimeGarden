package com.xiaoyv.anime.garden.ui.search

import androidx.lifecycle.MutableLiveData
import com.chad.library.adapter.base.loadState.LoadState
import com.xiaoyv.anime.garden.view.SearchOptionView
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModel
import com.xiaoyv.widget.kts.copyAddAll

/**
 * Class: [SearchViewModel]
 *
 * @author why
 * @since 11/22/23
 */
abstract class SearchViewModel<ItemEntity> : BaseViewModel() {
    internal val onListLiveData = MutableLiveData<List<ItemEntity>>()
    internal var loadingMoreState: LoadState = LoadState.None

    abstract val searchOptions: List<SearchOptionView.Option>

    abstract val isRefresh: Boolean

    abstract fun refresh(keyword: String, selectedOptions: List<SearchOptionView.Option>)

    abstract fun loadMore()

    open fun sendFetchedList(items: List<ItemEntity>) {
        if (isRefresh) {
            onListLiveData.value = items
        } else {
            onListLiveData.value = onListLiveData.value.copyAddAll(items)
        }

        loadingMoreState = if (isRefresh && items.isEmpty()) {
            LoadState.None
        } else {
            LoadState.NotLoading(items.isEmpty())
        }
    }
}