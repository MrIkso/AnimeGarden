package com.xiaoyv.anime.garden.view.fuckmi

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.xiaoyv.anime.garden.AnimeApp

/**
 * Class: [AnimeRecyclerView]
 *
 * @author why
 * @since 11/23/23
 */
class AnimeRecyclerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : RecyclerView(context, attrs) {

    init {
        AnimeApp.fuckMiuiOverScroller(this)
    }
}