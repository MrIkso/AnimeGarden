package com.xiaoyv.anime.garden.ui.detect

import android.view.MenuItem
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.blankj.utilcode.util.ScreenUtils
import com.xiaoyv.anime.garden.R
import com.xiaoyv.anime.garden.databinding.ActivityImageDetectBinding
import com.xiaoyv.anime.garden.kts.GoogleAttr
import com.xiaoyv.anime.garden.kts.initNavBack
import com.xiaoyv.anime.garden.kts.openInBrowser
import com.xiaoyv.anime.garden.view.AnimeLoadingDialog
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModelActivity
import com.xiaoyv.widget.callback.setOnFastLimitClickListener
import com.xiaoyv.widget.dialog.UiDialog
import com.xiaoyv.widget.kts.getAttrColor

/**
 * ImageDetectActivity
 *
 * @author why
 * @since 11/18/23
 */
abstract class ImageDetectActivity<ViewModel : ImageDetectViewModel> :
    BaseViewModelActivity<ActivityImageDetectBinding, ViewModel>() {

    private val selectImageLauncher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
            if (it != null) viewModel.onImageSelected(it, binding.optionView.selectedOptions)
        }

    open val optionItemWidth: Float
        get() = ScreenUtils.getAppScreenWidth() * 0.75f

    abstract val searchEngineUrl: String

    override fun initView() {
        binding.toolbar.initNavBack(this)
        binding.tvEngine.text = getString(R.string.anime_search_engine, searchEngineUrl)

        onConfigView()
    }

    override fun initData() {
        binding.optionView.titleColor = getAttrColor(GoogleAttr.colorOnSurfaceVariant)
        binding.optionView.itemWidth = ScreenUtils.getScreenWidth() / 1.5f
        binding.optionView.options = viewModel.searchOptions
    }

    override fun initListener() {
        binding.llUpload.setOnFastLimitClickListener {
            selectImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        binding.tvEngine.setOnFastLimitClickListener {
            openInBrowser(searchEngineUrl)
        }
    }

    abstract fun onConfigView()

    override fun onCreateLoadingDialog(): UiDialog {
        return AnimeLoadingDialog(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.initNavBack(this)
        return super.onOptionsItemSelected(item)
    }
}