package com.xiaoyv.anime.garden

import android.app.Application
import com.xiaoyv.blueprint.BluePrint

/**
 * AnimeApp
 *
 * @author why
 * @since 11/18/23
 */
class AnimeApp : Application() {
    override fun onCreate() {
        super.onCreate()
        BluePrint.init(this, false)
    }
}