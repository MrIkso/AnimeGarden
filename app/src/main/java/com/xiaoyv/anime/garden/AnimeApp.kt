package com.xiaoyv.anime.garden

import android.app.Application
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
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


    companion object {

        /**
         * 傻逼小米魔改 Android SDK 导致 fling 有闪退 BUG
         */
        fun fuckMiuiOverScroller(instance: RecyclerView) {
            runCatching {
                val v = RecyclerView::class.java.getDeclaredField("mViewFlinger").apply {
                    isAccessible = true
                }.get(instance)!!

                val mOverScroller = v.javaClass.getDeclaredField("mOverScroller").apply {
                    isAccessible = true
                }.get(v)!!

                val mFlingAnimationStub =
                    mOverScroller.javaClass.getDeclaredField("mFlingAnimationStub").apply {
                        isAccessible = true
                    }.get(mOverScroller)!!

                val mIsOptimizeEnable =
                    mFlingAnimationStub.javaClass.getDeclaredField("mIsOptimizeEnable").apply {
                        isAccessible = true
                    }

                mIsOptimizeEnable.set(mFlingAnimationStub, false)
            }
        }

        /**
         * 傻逼小米魔改 Android SDK 导致 fling 有闪退 BUG
         */
        fun fuckMiuiOverScroller(instance: NestedScrollView) {
            runCatching {
                val mOverScroller =
                    NestedScrollView::class.java.getDeclaredField("mScroller").apply {
                        isAccessible = true
                    }.get(instance)!!

                val mFlingAnimationStub =
                    mOverScroller.javaClass.getDeclaredField("mFlingAnimationStub").apply {
                        isAccessible = true
                    }.get(mOverScroller)!!

                val mIsOptimizeEnable =
                    mFlingAnimationStub.javaClass.getDeclaredField("mIsOptimizeEnable").apply {
                        isAccessible = true
                    }

                mIsOptimizeEnable.set(mFlingAnimationStub, false)
            }
        }
    }
}