package com.xiaoyv.anime.garden.ui.detect.anime

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.UriUtils
import com.xiaoyv.anime.garden.api.AnimeApiManager
import com.xiaoyv.anime.garden.api.response.AnimeSourceEntity
import com.xiaoyv.anime.garden.ui.detect.ImageDetectViewModel
import com.xiaoyv.anime.garden.view.SearchOptionView
import com.xiaoyv.blueprint.kts.launchUI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

/**
 * ImageDetectAnimeViewModel
 *
 * @author why
 * @since 11/18/23
 */
class ImageDetectAnimeViewModel : ImageDetectViewModel() {
    internal val onAnimeSourceLiveData = MutableLiveData<AnimeSourceEntity?>()


    override fun onImageSelected(imageUri: Uri, selectedOptions: List<SearchOptionView.Option>) {
        launchUI(
            state = loadingDialogState(cancelable = false),
            error = {
                it.printStackTrace()
                onAnimeSourceLiveData.value = null
            },
            block = {
                onAnimeSourceLiveData.value = withContext(Dispatchers.IO) {
                    val targetFile = UriUtils.uri2File(imageUri)
                    val requestBody = targetFile.asRequestBody("multipart/form-data".toMediaType())
                    val body = MultipartBody.Part.createFormData("file", "image.jpg", requestBody)
                    AnimeApiManager.animeApi.queryAnimeByImage(body)
                }
            }
        )
    }
}