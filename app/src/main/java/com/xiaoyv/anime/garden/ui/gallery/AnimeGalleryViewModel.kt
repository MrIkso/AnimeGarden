package com.xiaoyv.anime.garden.ui.gallery

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Parcelable
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ImageUtils
import com.blankj.utilcode.util.IntentUtils
import com.blankj.utilcode.util.StringUtils
import com.blankj.utilcode.util.Utils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.GlideException
import com.chad.library.adapter.base.loadState.LoadState
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import com.xiaoyv.anime.garden.R
import com.xiaoyv.anime.garden.api.AnimeApiManager
import com.xiaoyv.anime.garden.kts.requireStoragePermission
import com.xiaoyv.anime.garden.type.AnimeGalleryUrlType
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModel
import com.xiaoyv.blueprint.kts.launchIO
import com.xiaoyv.blueprint.kts.launchUI
import com.xiaoyv.widget.kts.copyAddAll
import com.xiaoyv.widget.kts.listener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.File
import java.util.UUID

/**
 * AnimeGalleryViewModel
 *
 * @author why
 * @since 11/19/23
 */
class AnimeGalleryViewModel : BaseViewModel() {

    internal val onGalleryLiveData = MutableLiveData<List<AnimeGalleryAdapter.GalleryEntity>?>()
    internal var loadingMoreState: LoadState = LoadState.None

    internal val previewImageFile = UnPeekLiveData<File?>()

    internal var recycleState: Parcelable? = null
    internal var recreate = false

    @AnimeGalleryUrlType
    internal var galleryUrlType: String = AnimeGalleryUrlType.TYPE_WAIFU_SFW_PICS

    internal val shareMenus by lazy {
        arrayOf(
            StringUtils.getString(R.string.anime_gallery_action_save),
            StringUtils.getString(R.string.anime_gallery_action_share),
            StringUtils.getString(R.string.anime_gallery_action_wallpaper)
        )
    }

    internal var isRefresh = false

    override fun onViewCreated() {
        if (onGalleryLiveData.value.isNullOrEmpty()) {
            refresh()
        } else {
            recreate = true
        }
    }

    fun refresh() {
        isRefresh = true
        queryAnimeGallery()
    }

    fun loadMore() {
        isRefresh = false
        queryAnimeGallery()
    }

    private fun queryAnimeGallery() {
        launchUI(
            stateView = loadingViewState,
            error = {
                it.printStackTrace()
            },
            block = {
                val responseList = withContext(Dispatchers.IO) {
                    onLoadImageListImpl()
                }

                if (isRefresh) {
                    onGalleryLiveData.value = responseList
                } else {
                    onGalleryLiveData.value = onGalleryLiveData.value.copyAddAll(responseList)
                }

                loadingMoreState = if (isRefresh && responseList.isEmpty()) {
                    LoadState.None
                } else {
                    LoadState.NotLoading(responseList.isEmpty())
                }
            }
        )
    }

    fun downloadWallpaperFromUrl(item: String, action: String) {
        launchIO(
            state = loadingDialogState(cancelable = false),
            error = {
                it.printStackTrace()
            },
            block = {
                val wallpaperFile = Glide.with(Utils.getApp())
                    .asFile()
                    .load(item)
                    .submit()
                    .get()

                when (shareMenus.indexOf(action)) {
                    0 -> {
                        val saveImpl = {
                            ImageUtils.save2Album(
                                BitmapFactory.decodeFile(wallpaperFile.absolutePath),
                                Bitmap.CompressFormat.PNG
                            )
                        }
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                            if (requireStoragePermission()) {
                                saveImpl()
                            }
                        } else {
                            saveImpl()
                        }
                    }

                    1 -> {
                        ActivityUtils.startActivity(IntentUtils.getShareImageIntent(wallpaperFile))
                    }

                    2 -> {
                        val wallpaperManager = WallpaperManager.getInstance(Utils.getApp())
                        val wallpaperBitmap = BitmapFactory.decodeFile(wallpaperFile.absolutePath)
                        wallpaperManager.setBitmap(
                            wallpaperBitmap,
                            null,
                            true,
                            WallpaperManager.FLAG_SYSTEM
                        )
                    }
                }
            }
        )
    }

    fun previewImage(url: String) {
        launchUI(state = loadingDialogState(cancelable = false)) {
            previewImageFile.value = withContext(Dispatchers.IO) {
                val startTime = System.currentTimeMillis()
                val imageFile = suspendCancellableCoroutine<File> { emit ->
                    Glide.with(context)
                        .asFile()
                        .load(url)
                        .listener(
                            onLoadFailed = {
                                emit.resumeWith(Result.failure(GlideException(it.toString())))
                            },
                            onResourceReady = {
                                emit.resumeWith(Result.success(it))
                            }
                        )
                        .submit()
                }

                // 最小间隔 500ms，避免突兀
                val totalLimit = 500
                val offset = System.currentTimeMillis() - startTime
                if (offset < totalLimit) {
                    delay(totalLimit - offset)
                }

                return@withContext imageFile
            }
        }
    }

    private suspend fun onLoadImageListImpl(): List<AnimeGalleryAdapter.GalleryEntity> {
        return when (galleryUrlType) {
            // https://api.waifu.pics
            AnimeGalleryUrlType.TYPE_WAIFU_SFW_PICS, AnimeGalleryUrlType.TYPE_WAIFU_NSFW_PICS -> {
                AnimeApiManager.animeApi.queryAnimeGallery(galleryUrlType).files.orEmpty()
                    .map { AnimeGalleryAdapter.GalleryEntity(it, UUID.randomUUID().toString()) }
            }
            // https://pic.re
            AnimeGalleryUrlType.TYPE_PIC_RE -> {
                arrayListOf<AnimeGalleryAdapter.GalleryEntity>().apply {
                    repeat(20) {
                        val string = UUID.randomUUID().toString()
                        val url = "https://pic.re/images?uuid=$string"
                        add(AnimeGalleryAdapter.GalleryEntity(url, string))
                    }
                }
            }

            else -> emptyList()
        }
    }
}