package com.xiaoyv.anime.garden.kts

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView


/**
 * 添加一个方法用于高亮指定文本
 */
fun TextView.highlightText(text: String, highlightColor: Int) {
    // 获取文本内容
    val fullText: String = getText().toString()

    // 创建一个 SpannableStringBuilder 对象，用于构建带有样式的文本
    val spannable = SpannableStringBuilder(fullText)

    // 寻找并高亮指定文本
    val startPos = fullText.indexOf(text, ignoreCase = true)
    val endPos = startPos + text.length
    if (startPos >= 0) {
        spannable.setSpan(
            ForegroundColorSpan(highlightColor),
            startPos,
            endPos,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        setText(spannable)
    }
}