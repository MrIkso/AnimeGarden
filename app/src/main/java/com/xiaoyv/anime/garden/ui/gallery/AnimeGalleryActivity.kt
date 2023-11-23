package com.xiaoyv.anime.garden.ui.gallery

import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.LifecycleOwner
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.DebouncingUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.QuickAdapterHelper
import com.chad.library.adapter.base.loadState.LoadState
import com.chad.library.adapter.base.loadState.trailing.TrailingLoadStateAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.gyf.immersionbar.ImmersionBar
import com.xiaoyv.anime.garden.R
import com.xiaoyv.anime.garden.databinding.ActivityAnimeGalleryBinding
import com.xiaoyv.anime.garden.databinding.ActivityAnimeGalleryDialogBinding
import com.xiaoyv.anime.garden.kts.GoogleAttr
import com.xiaoyv.anime.garden.kts.initNavBack
import com.xiaoyv.anime.garden.kts.loadImageAnimate
import com.xiaoyv.anime.garden.type.AnimeGalleryUrlType
import com.xiaoyv.anime.garden.view.AnimeLoadingDialog
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModelActivity
import com.xiaoyv.blueprint.kts.activity
import com.xiaoyv.widget.dialog.UiDialog
import com.xiaoyv.widget.kts.dpi
import com.xiaoyv.widget.kts.getAttrColor
import com.xiaoyv.widget.kts.getParcelObj
import com.xiaoyv.widget.stateview.StateViewLiveData


/**
 * AnimeGalleryActivity
 *
 * @author why
 * @since 11/19/23
 */
class AnimeGalleryActivity :
    BaseViewModelActivity<ActivityAnimeGalleryBinding, AnimeGalleryViewModel>() {

    private val galleryAdapter by lazy {
        AnimeGalleryAdapter()
    }

    private val adapterHelper by lazy {
        QuickAdapterHelper.Builder(galleryAdapter)
            .setTrailingLoadStateAdapter(object : TrailingLoadStateAdapter.OnTrailingListener {
                override fun isAllowLoading(): Boolean {
                    return binding.srlRefresh.isRefreshing.not()
                }

                override fun onFailRetry() {
                    viewModel.loadMore()
                }

                override fun onLoad() {
                    viewModel.loadMore()
                }
            })
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 恢复RecyclerView状态信息
        if (savedInstanceState != null) {
            val recyclerViewState: Parcelable? = savedInstanceState.getParcelObj("recycler_state")
            if (recyclerViewState != null) {
                binding.rvGallery.layoutManager?.onRestoreInstanceState(recyclerViewState)
            }
        }
    }

    override fun initBarConfig() {
        ImmersionBar.with(this)
            .transparentStatusBar()
            .transparentNavigationBar()
            .statusBarDarkFont(true)
            .init()
    }

    override fun initView() {
        val offsetStart = BarUtils.getStatusBarHeight()

        binding.toolbar.initNavBack(this)

        binding.srlRefresh.initRefresh()
        binding.srlRefresh.setColorSchemeColors(getAttrColor(GoogleAttr.colorPrimary))
        binding.srlRefresh.setProgressViewOffset(true, offsetStart, offsetStart + 40.dpi)

        galleryAdapter.setItemAnimation(BaseQuickAdapter.AnimationType.ScaleIn)

        binding.rvGallery.adapter = adapterHelper.adapter
    }

    override fun initData() {

    }


    override fun initListener() {
        binding.srlRefresh.setOnRefreshListener {
            adapterHelper.trailingLoadState = LoadState.None
            viewModel.refresh()
        }

        // 长按菜单
        galleryAdapter.addOnItemChildLongClickListener(R.id.iv_image) { adapter, _, position ->
            val item = adapter.getItem(position) ?: return@addOnItemChildLongClickListener true
            showActionDialog(item.url)
            true
        }

        // 单击预览
        galleryAdapter.addOnItemChildClickListener(R.id.iv_image) { adapter, v, position ->
            val item = adapter.getItem(position) ?: return@addOnItemChildClickListener
            if (DebouncingUtils.isValid(v)) {
                viewModel.previewImage(item.url)
            }
        }
    }


    override fun LifecycleOwner.initViewObserver() {
        viewModel.onGalleryLiveData.observe(this) {
            galleryAdapter.submitList(it.orEmpty()) {
                adapterHelper.trailingLoadState = viewModel.loadingMoreState

                // 页面重构时恢复
                val recyclerViewState = viewModel.recycleState
                if (recyclerViewState != null && viewModel.recreate) {
                    binding.rvGallery.layoutManager?.onRestoreInstanceState(recyclerViewState)
                    viewModel.recycleState = null
                }
            }
        }

        viewModel.loadingViewState.observe(this) {
            if (it.type == StateViewLiveData.StateType.STATE_LOADING && viewModel.isRefresh) {
                binding.srlRefresh.isRefreshing = true
            }
        }

        viewModel.previewImageFile.observe(this) { file ->
            val model = file ?: return@observe
            val binding = ActivityAnimeGalleryDialogBinding.inflate(layoutInflater)
            binding.ivPreview.loadImageAnimate(model, centerCrop = false)

            MaterialAlertDialogBuilder(activity)
                .setView(binding.root)
                .create()
                .show()
        }
    }

    private fun showActionDialog(url: String) {
        MaterialAlertDialogBuilder(this)
            .setItems(viewModel.shareMenus) { _, which ->
                viewModel.downloadWallpaperFromUrl(url, viewModel.shareMenus[which])
            }
            .create()
            .show()
    }

    override fun onCreateLoadingDialog(): UiDialog {
        return AnimeLoadingDialog(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        // 保存当前的滚动位置
        viewModel.recycleState = binding.rvGallery.layoutManager?.onSaveInstanceState()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add("Setting")
            .setIcon(R.drawable.ic_setting_gear)
            .setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS)
            .setOnMenuItemClickListener {

                @Suppress("SpellCheckingInspection")
                val menus = mapOf(
                    "waifu.pic - sfw" to AnimeGalleryUrlType.TYPE_WAIFU_SFW_PICS,
                    "waifu.pic - nsfw" to AnimeGalleryUrlType.TYPE_WAIFU_NSFW_PICS,
                    "pic.re" to AnimeGalleryUrlType.TYPE_PIC_RE
                )

                MaterialAlertDialogBuilder(activity)
                    .setTitle("选取图片源")
                    .setItems(menus.keys.toTypedArray()) { _, which ->
                        binding.srlRefresh.isRefreshing = true

                        viewModel.galleryUrlType = menus.values.toList()[which]
                        viewModel.refresh()
                    }
                    .create()
                    .show()
                true
            }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item.initNavBack(this)
        return super.onOptionsItemSelected(item)
    }
}