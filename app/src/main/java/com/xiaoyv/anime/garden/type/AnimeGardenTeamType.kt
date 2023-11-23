package com.xiaoyv.anime.garden.type

import androidx.annotation.Keep
import com.blankj.utilcode.util.GsonUtils
import com.blankj.utilcode.util.ResourceUtils
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken


/**
 * AnimeGardenTeamType
 *
 * @author why
 * @since 11/18/23
 */
object AnimeGardenTeamType {

    @Keep
    data class AnimeGardenTeamTeam(
        @SerializedName("label")
        var label: String? = null,
        @SerializedName("value")
        var value: String? = null
    )

    /**
     * 全部的字幕组
     */
    val types: List<AnimeGardenTeamTeam> by lazy {
        val type = object : TypeToken<List<AnimeGardenTeamTeam>>() {}.type
        GsonUtils.fromJson(ResourceUtils.readAssets2String("json/team.json"), type)
    }

    /**
     * 获取 AnimeType 名称
     */
    fun getTypeName(type: String?, default: String? = ""): String {
        return types.find { it.value == type }?.label.orEmpty().ifBlank { default.orEmpty() }
    }
}
