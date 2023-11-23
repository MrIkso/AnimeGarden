package com.xiaoyv.anime.garden.ui

import com.xiaoyv.anime.garden.type.MainFuncType
import com.xiaoyv.blueprint.base.mvvm.normal.BaseViewModel

/**
 * MainViewModel
 *
 * @author why
 * @since 11/18/23
 */
class MainViewModel : BaseViewModel() {

    val mainFuncList: List<MainAdapter.FuncItem> by lazy {
        arrayListOf(
            MainAdapter.FuncItem(
                name = "以图搜番",
                type = MainFuncType.TYPE_DETECT_ANIME,
                lottieName = "lottie/lottie_search.json"
            ),
            MainAdapter.FuncItem(
                name = "以图识人",
                type = MainFuncType.TYPE_DETECT_CHARACTER,
                lottieName = "lottie/lottie_character.json"
            ),
            MainAdapter.FuncItem(
                name = "番剧资料",
                type = MainFuncType.TYPE_ANIME_INFO,
                lottieName = "lottie/lottie_fire.json"
            ),
            MainAdapter.FuncItem(
                name = "动漫美图",
                type = MainFuncType.TYPE_ANIME_IMAGE,
                lottieName = "lottie/lottie_anime.json"
            )
        )
    }
}