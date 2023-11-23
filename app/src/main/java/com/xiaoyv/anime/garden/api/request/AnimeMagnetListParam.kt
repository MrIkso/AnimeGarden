package com.xiaoyv.anime.garden.api.request

import androidx.annotation.Keep

/**
 * AnimeMagnetListParam
 *
 * @author why
 * @since 11/18/23
 */
@Keep
data class AnimeMagnetListParam(
    var site: String? = null,
    var keyword: String? = null,
    var team: String? = null,
    var user: String? = null,
    var kind: String? = null,
    var order: String? = null,
    var page: Int = 1
) {

    fun reset() {
        site = null
        keyword = null
        team = null
        user = null
        kind = null
        order = null
        page = 1
    }

    fun toQueryMap(): Map<String, String> {
        val map = hashMapOf<String, String>()

        AnimeMagnetListParam::class.java.declaredFields.forEach {
            it.isAccessible = true
            val value = it.get(this)
            if (value != null) map[it.name] = value.toString()
        }

        return map
    }
}