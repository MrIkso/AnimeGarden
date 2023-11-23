package com.xiaoyv.anime.garden.ui.detect

import android.net.Uri
import com.xiaoyv.anime.garden.view.SearchOptionView
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModel

/**
 * ImageDetectViewModel
 *
 * @author why
 * @since 11/18/23
 */
abstract class ImageDetectViewModel : BaseViewModel() {
    open val searchOptions: List<SearchOptionView.Option>
        get() = emptyList()

    abstract fun onImageSelected(imageUri: Uri, selectedOptions: List<SearchOptionView.Option>)
}