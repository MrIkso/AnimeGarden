package com.xiaoyv.anime.garden.kts

import kotlin.math.roundToLong

/**
 * TimeKy
 *
 * @author why
 * @since 11/19/23
 */
fun Double.formatSecondsToTime(): String {
    val seconds = this.roundToLong()
    val hours = seconds / 3600
    val minutes = seconds % 3600 / 60
    val remainingSeconds = seconds % 60
    return String.format("%02d:%02d:%02d", hours, minutes, remainingSeconds)
}