package com.xiaoyv.anime.garden.kts

import android.app.Activity
import android.content.Intent
import android.graphics.Rect
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xiaoyv.blueprint.kts.toJson
import com.xiaoyv.widget.kts.orEmpty
import java.lang.reflect.Field


typealias GoogleAttr = com.google.android.material.R.attr

val gson by lazy { Gson() }

fun openInBrowser(url: String) {
    runCatching {
        ActivityUtils.startActivity(Intent.parseUri(url, Intent.URI_ALLOW_UNSAFE))
    }
}

inline fun <reified T> String.fromJson(): T? {
    runCatching {
        val type = object : TypeToken<T>() {}.type
        return gson.fromJson(this, type)
    }.onFailure {
        LogUtils.e("JsonError: $this", it)
    }
    return null
}

fun Any.toJsonMap(): Map<String, Any> {
    return toJson().fromJson<Map<String, Any>>().orEmpty()
}

fun getStatusBarHeight(activity: Activity): Int {
    val rect = Rect()
    activity.window.decorView.getWindowVisibleDisplayFrame(rect)
    var statusBarHeight = rect.top
    if (statusBarHeight == 0) {
        try {
            val c = Class.forName("com.android.internal.R\$dimen")
            val obj = c.newInstance()
            val field: Field = c.getField("status_bar_height")
            val x: Int = field.get(obj).toString().toInt()
            statusBarHeight = activity.resources.getDimensionPixelSize(x)
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }
    if (statusBarHeight == 0) {
        statusBarHeight = (activity.resources.displayMetrics.density * 25).toInt()
    }
    return statusBarHeight
}