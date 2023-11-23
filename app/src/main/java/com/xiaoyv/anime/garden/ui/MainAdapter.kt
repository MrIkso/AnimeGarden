package com.xiaoyv.anime.garden.ui

import com.xiaoyv.anime.garden.databinding.ActivityMainItemBinding
import com.xiaoyv.anime.garden.type.MainFuncType
import com.xiaoyv.widget.binder.BaseQuickBindingAdapter
import com.xiaoyv.widget.binder.BaseQuickBindingHolder

/**
 * MainAdapter
 *
 * @author why
 * @since 11/18/23
 */
class MainAdapter : BaseQuickBindingAdapter<MainAdapter.FuncItem, ActivityMainItemBinding>() {
    private val payloadPause = "PAUSE"
    private val payloadResume = "RESUME"

    override fun BaseQuickBindingHolder<ActivityMainItemBinding>.converted(item: FuncItem) {
        binding.tvFunc.text = item.name
        binding.ivFuncLogo.setAnimation(item.lottieName)
    }

    override fun onBindViewHolder(
        holder: BaseQuickBindingHolder<ActivityMainItemBinding>,
        position: Int,
        item: FuncItem?,
        payloads: List<Any>
    ) {
        payloads.forEach {
            when (it.toString()) {
                payloadPause -> {
                    holder.binding.ivFuncLogo.pauseAnimation()
                }

                payloadResume -> {
                    holder.binding.ivFuncLogo.resumeAnimation()
                }
            }
        }
    }

    fun pauseAnimation() {
        items.forEach { it.animating = false }
        notifyItemRangeChanged(0, itemCount, payloadPause)
    }

    fun resumeAnimation() {
        items.forEach { it.animating = true }
        notifyItemRangeChanged(0, itemCount, payloadResume)
    }

    data class FuncItem(
        var name: String,
        @MainFuncType var type: Int,
        var lottieName: String,
        var animating: Boolean = true
    )
}