package com.xiaoyv.anime.garden.view.fuckmi

import android.content.Context
import android.util.AttributeSet
import androidx.core.widget.NestedScrollView
import com.xiaoyv.anime.garden.AnimeApp

/**
 * Class: [AnimeNestedScrollView]
 *
 * @author why
 * @since 11/23/23
 */
class AnimeNestedScrollView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : NestedScrollView(context, attrs) {

    init {
        AnimeApp.fuckMiuiOverScroller(this)
    }

}