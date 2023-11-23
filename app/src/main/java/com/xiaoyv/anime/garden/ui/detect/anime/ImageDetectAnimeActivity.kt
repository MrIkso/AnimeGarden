package com.xiaoyv.anime.garden.ui.detect.anime

import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import com.xiaoyv.anime.garden.R
import com.xiaoyv.anime.garden.ui.detect.ImageDetectActivity
import com.xiaoyv.anime.garden.ui.detect.anime.result.AnimeDetectResultActivity
import com.xiaoyv.blueprint.kts.activity
import com.xiaoyv.blueprint.kts.open

/**
 * ImageDetectAnimeActivity
 *
 * @author why
 * @since 11/18/23
 */
class ImageDetectAnimeActivity : ImageDetectActivity<ImageDetectAnimeViewModel>() {
    override val searchEngineUrl: String
        get() = "https://trace.moe"

    override fun onConfigView() {
        binding.toolbar.title = getString(R.string.anime_search_title)
        binding.tvUploadTitle.text = getString(R.string.anime_search_subtitle)
        binding.tvUploadDesc.text = getString(R.string.anime_search_desc)
        binding.tvUploadCardTitle.text = getString(R.string.anime_search_upload_image)
        binding.tvUploadCardLimit.text = getString(R.string.anime_search_upload_limit)
        binding.tvUploadCardBtn.text = getString(R.string.anime_search_upload_btn)
    }

    override fun LifecycleOwner.initViewObserver() {
        viewModel.onAnimeSourceLiveData.observe(this) {
            if (it == null) {
                Toast.makeText(activity, getString(R.string.detect_failed), Toast.LENGTH_SHORT)
                    .show()
                return@observe
            }
            AnimeDetectResultActivity::class.open(paramParcelable1 = it)
        }
    }
}