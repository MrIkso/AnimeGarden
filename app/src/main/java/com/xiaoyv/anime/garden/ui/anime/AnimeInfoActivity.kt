package com.xiaoyv.anime.garden.ui.anime

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.LifecycleOwner
import com.google.android.material.tabs.TabLayoutMediator
import com.xiaoyv.anime.garden.api.response.anilist.AnilistMediaEntity
import com.xiaoyv.anime.garden.databinding.ActivityAnimeInfoBinding
import com.xiaoyv.anime.garden.kts.initNavBack
import com.xiaoyv.anime.garden.kts.loadImageAnimate
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModelActivity
import com.xiaoyv.blueprint.constant.NavKey
import com.xiaoyv.blueprint.kts.open
import com.xiaoyv.widget.kts.getParcelObj
import kotlin.math.abs

/**
 * Class: [AnimeInfoActivity]
 *
 * @author why
 * @since 11/19/23
 */
class AnimeInfoActivity : BaseViewModelActivity<ActivityAnimeInfoBinding, AnimeInfoViewModel>() {
    private val pageAdapter by lazy {
        AnimeInfoAdapter(this, viewModel.requireMediaEntity)
    }

    val vp2 get() = binding.vpContent

    override fun initIntentData(intent: Intent, bundle: Bundle, isNewIntent: Boolean) {
        viewModel.mediaId = bundle.getLong(NavKey.KEY_LONG, 0)
        viewModel.onMediaLiveData.value = bundle.getParcelObj(NavKey.KEY_PARCELABLE)
    }

    override fun initView() {
        binding.toolbar.initNavBack(this)
        binding.toolbarLayout.title = title

        binding.vpContent.offscreenPageLimit = pageAdapter.itemCount
        binding.vpContent.adapter = pageAdapter
    }

    override fun initData() {
        TabLayoutMediator(binding.tableLayout, binding.vpContent) { tab, i ->
            tab.setText(pageAdapter.tabs[i])
        }.attach()
    }

    override fun initListener() {
        binding.appBar.addOnOffsetChangedListener { appBarLayout, i ->
            val totalScrollRange = appBarLayout.totalScrollRange
            val scrollHeight = abs(i)
            val percent =
                if (scrollHeight == 0) 1f else 1 - scrollHeight / totalScrollRange.toFloat()

            binding.ivPoster.scaleX = percent
            binding.ivPoster.scaleY = percent
            binding.ivPoster.alpha = percent

            binding.tvTitle.alpha = 1 - percent
        }
    }

    override fun LifecycleOwner.initViewObserver() {
        viewModel.onMediaLiveData.observe(this) {
            val mediaEntity = it ?: return@observe

            binding.toolbarLayout.title = mediaEntity.title?.userPreferred.orEmpty()
            binding.tvTitle.text = mediaEntity.title?.userPreferred.orEmpty()
            binding.ivPoster.loadImageAnimate(mediaEntity.coverImageUrl)
            binding.ivBanner.loadImageAnimate(mediaEntity.bannerImageUrl)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.initNavBack(this)
        return super.onOptionsItemSelected(item)
    }

    companion object {
        @JvmStatic
        fun openSelf(animeId: Long) {
            AnimeInfoActivity::class.open(paramLong1 = animeId)
        }

        @JvmStatic
        fun openSelf(anime: AnilistMediaEntity) {
            AnimeInfoActivity::class.open(paramParcelable1 = anime)
        }
    }
}