package com.xiaoyv.anime.garden.ui

import com.xiaoyv.anime.garden.R
import com.xiaoyv.anime.garden.databinding.ActivityMainBinding
import com.xiaoyv.anime.garden.type.MainFuncType
import com.xiaoyv.anime.garden.ui.detect.anime.ImageDetectAnimeActivity
import com.xiaoyv.anime.garden.ui.detect.character.ImageDetectCharacterActivity
import com.xiaoyv.anime.garden.ui.gallery.AnimeGalleryActivity
import com.xiaoyv.anime.garden.ui.search.anime.AnimeListActivity
import com.xiaoyv.anime.garden.ui.search.magnet.AnimeMagnetListActivity
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModelActivity
import com.xiaoyv.blueprint.kts.open
import com.xiaoyv.widget.callback.setOnFastLimitClickListener

/**
 * MainActivity
 *
 * @author why
 * @since 11/18/23
 */
class MainActivity : BaseViewModelActivity<ActivityMainBinding, MainViewModel>() {
    private val itemAdapter by lazy { MainAdapter() }

    override fun initView() {
        binding.rvFunc.adapter = itemAdapter
    }

    override fun initData() {
        itemAdapter.submitList(viewModel.mainFuncList)
    }

    override fun initListener() {
        binding.sbSearch.setOnFastLimitClickListener {
            AnimeMagnetListActivity::class.open()
        }

        itemAdapter.addOnItemChildClickListener(R.id.cl_item) { adapter, _, position ->
            val item = adapter.getItem(position) ?: return@addOnItemChildClickListener
            when (item.type) {
                MainFuncType.TYPE_DETECT_ANIME -> {
                    ImageDetectAnimeActivity::class.open()
                }

                MainFuncType.TYPE_DETECT_CHARACTER -> {
                    ImageDetectCharacterActivity::class.open()
                }

                MainFuncType.TYPE_ANIME_INFO -> {
                    AnimeListActivity::class.open()
                }

                MainFuncType.TYPE_ANIME_IMAGE -> {
                    AnimeGalleryActivity::class.open()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding.ivLogo.resumeAnimation()
        itemAdapter.resumeAnimation()
    }

    override fun onPause() {
        super.onPause()
        binding.ivLogo.pauseAnimation()
        itemAdapter.pauseAnimation()
    }
}