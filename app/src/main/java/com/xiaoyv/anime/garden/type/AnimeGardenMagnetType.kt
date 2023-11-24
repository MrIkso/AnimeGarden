package com.xiaoyv.anime.garden.type

import androidx.annotation.StringDef
import com.blankj.utilcode.util.StringUtils
import com.xiaoyv.anime.garden.R

/**
 * Class: [AnimeGardenMagnetType]
 *
 * @author why
 * @since 11/18/23
 */
@StringDef(
    AnimeGardenMagnetType.TYPE_ALL,
    AnimeGardenMagnetType.TYPE_ANIMATION,
    AnimeGardenMagnetType.TYPE_SEASON_FULL_SET,
    AnimeGardenMagnetType.TYPE_COMIC,
    AnimeGardenMagnetType.TYPE_HK_TAIWAN_ORIGINAL,
    AnimeGardenMagnetType.TYPE_JAPANESE_ORIGINAL,
    AnimeGardenMagnetType.TYPE_MUSIC,
    AnimeGardenMagnetType.TYPE_ANIMATION_MUSIC,
    AnimeGardenMagnetType.TYPE_FANDOM_MUSIC,
    AnimeGardenMagnetType.TYPE_POPULAR_MUSIC,
    AnimeGardenMagnetType.TYPE_JAPANESE_DRAMA,
    AnimeGardenMagnetType.TYPE_RAW,
    AnimeGardenMagnetType.TYPE_GAME,
    AnimeGardenMagnetType.TYPE_COMPUTER_GAME,
    AnimeGardenMagnetType.TYPE_TV_GAME,
    AnimeGardenMagnetType.TYPE_HANDHELD_GAME,
    AnimeGardenMagnetType.TYPE_ONLINE_GAME,
    AnimeGardenMagnetType.TYPE_GAME_PERIPHERAL,
    AnimeGardenMagnetType.TYPE_SPECIAL,
    AnimeGardenMagnetType.TYPE_OTHER
)
@Retention(AnnotationRetention.SOURCE)
annotation class AnimeGardenMagnetType {
    companion object {
        const val TYPE_ALL = "0"
        const val TYPE_ANIMATION = "2"
        const val TYPE_SEASON_FULL_SET = "31"
        const val TYPE_COMIC = "3"
        const val TYPE_HK_TAIWAN_ORIGINAL = "41"
        const val TYPE_JAPANESE_ORIGINAL = "42"
        const val TYPE_MUSIC = "4"
        const val TYPE_ANIMATION_MUSIC = "43"
        const val TYPE_FANDOM_MUSIC = "44"
        const val TYPE_POPULAR_MUSIC = "15"
        const val TYPE_JAPANESE_DRAMA = "6"
        const val TYPE_RAW = "7"
        const val TYPE_GAME = "9"
        const val TYPE_COMPUTER_GAME = "17"
        const val TYPE_TV_GAME = "18"
        const val TYPE_HANDHELD_GAME = "19"
        const val TYPE_ONLINE_GAME = "20"
        const val TYPE_GAME_PERIPHERAL = "21"
        const val TYPE_SPECIAL = "12"
        const val TYPE_OTHER = "1"

        /**
         * 映射 AnimeType 的值和标题的对应关系
         */
        val typeTitleMap by lazy {
            mapOf(
                TYPE_ALL to R.string.anime_type_all,
                TYPE_ANIMATION to R.string.anime_type_animation,
                TYPE_SEASON_FULL_SET to R.string.anime_type_season_full_set,
                TYPE_COMIC to R.string.anime_type_comic,
                TYPE_HK_TAIWAN_ORIGINAL to R.string.anime_type_hk_taiwan_original,
                TYPE_JAPANESE_ORIGINAL to R.string.anime_type_japanese_original,
                TYPE_MUSIC to R.string.anime_type_music,
                TYPE_ANIMATION_MUSIC to R.string.anime_type_animation_music,
                TYPE_FANDOM_MUSIC to R.string.anime_type_fandom_music,
                TYPE_POPULAR_MUSIC to R.string.anime_type_popular_music,
                TYPE_JAPANESE_DRAMA to R.string.anime_type_japanese_drama,
                TYPE_RAW to R.string.anime_type_raw,
                TYPE_GAME to R.string.anime_type_game,
                TYPE_COMPUTER_GAME to R.string.anime_type_computer_game,
                TYPE_TV_GAME to R.string.anime_type_tv_game,
                TYPE_HANDHELD_GAME to R.string.anime_type_handheld_game,
                TYPE_ONLINE_GAME to R.string.anime_type_online_game,
                TYPE_GAME_PERIPHERAL to R.string.anime_type_game_peripheral,
                TYPE_SPECIAL to R.string.anime_type_special,
                TYPE_OTHER to R.string.anime_type_other,
            )
        }

        /**
         * 获取 AnimeType 名称
         */
        fun getTypeName(@AnimeGardenMagnetType type: String): String {
            return StringUtils.getString(typeTitleMap[type] ?: return "")
        }
    }
}

