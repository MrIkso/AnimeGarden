package com.xiaoyv.anime.garden.kts

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.xiaoyv.widget.kts.listener

/**
 * ImageViewKt
 *
 * @author why
 * @since 11/19/23
 */

inline fun ImageView.loadImageAnimate(
    model: Any?, centerCrop: Boolean = true,
    crossinline onReady: (Drawable) -> Unit = {},
    crossinline onFail: (Any?) -> Unit = {},
) {
    Glide.with(this)
        .load(model ?: return)
        .let { if (centerCrop) it.centerCrop() else it }
        .listener (onLoadFailed = onFail, onResourceReady = onReady)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}