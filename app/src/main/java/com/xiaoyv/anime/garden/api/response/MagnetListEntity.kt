package com.xiaoyv.anime.garden.api.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


/**
 * Class: [MagnetListEntity]
 *
 * @author why
 * @since 11/18/23
 */
@Keep
data class MagnetListEntity(
    @SerializedName("detailId")
    var detailId: String? = null,
    @SerializedName("download")
    var download: Int = 0,
    @SerializedName("finish")
    var finish: Int = 0,
    @SerializedName("kind")
    var kind: String? = null,
    @SerializedName("magnet")
    var magnet: String? = null,
    @SerializedName("publisher")
    var publisher: String? = null,
    @SerializedName("publisherId")
    var publisherId: String? = null,
    @SerializedName("size")
    var size: String? = null,
    @SerializedName("tag")
    var tag: String? = null,
    @SerializedName("tagId")
    var tagId: String? = null,
    @SerializedName("time")
    var time: String? = null,
    @SerializedName("title")
    var title: String? = null,
    @SerializedName("torrentCount")
    var torrentCount: Int = 0
)