package com.mtm.cloudconsult.mvp.ui.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.DecelerateInterpolator
import com.blankj.utilcode.util.ImageUtils
import com.mtm.cloudconsult.R
import kotlinx.android.synthetic.main.activity_about.*
import java.util.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        //透明状态栏
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        val targets: MutableList<View> = ArrayList()
        targets.add(civ_icon)
        targets.add(tv_name)
        targets.add(tv_sign)
        targets.add(ll_github)
        targets.add(ll_jianshu)
        targets.add(ll_qq)
        iv_blur.setImageBitmap(ImageUtils.renderScriptBlur(ImageUtils.getBitmap(R.drawable.giao_icon), 25f))
        iv_blur.post {
            changeViewAlpha(iv_blur, 0f, 1f, 600)
            changeViewSize(iv_blur, 2f, 1f, 1000)
        }
        civ_icon.post {
            //渐变动画
            changeViewSize(civ_icon, 0f, 1f, 300)
            doDelayShowAnim(800, 60, *targets.toTypedArray())
        }

    }

    private fun changeViewAlpha(target: View, from: Float, to: Float, dur: Long) {
        val animator = ObjectAnimator.ofFloat(target, "alpha", from, to)
        animator.duration = dur
        animator.interpolator = DecelerateInterpolator()
        animator.start()
    }

    private fun changeViewSize(target: View?, from: Float, to: Float, dur: Long) {
        val animator = ValueAnimator.ofFloat(from, to)
        animator.duration = dur
        animator.interpolator = DecelerateInterpolator()
        animator.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animator ->
            if (target == null) {
                animator.cancel()
                return@AnimatorUpdateListener
            }
            //as 强制类型转换
            val f = animator.animatedValue as Float
            target.scaleX = f
            target.scaleY = f
        })
        animator.start()
    }

    private fun doDelayShowAnim(dur: Long, delay: Long, vararg targets: View) {
        //until：不包含  = for(int i=0;i<target.size();i++)
        for (i in 0 until targets.size) {
            //val:参数不可变
            val target = targets[i]
            target.alpha = 0f
            val animatorY = ObjectAnimator.ofFloat(target, "translationY", 100f, 0f)
            val animatorA = ObjectAnimator.ofFloat(target, "alpha", 0f, 1f)
            animatorY.duration = dur
            animatorA.duration = (dur * 0.618f).toLong()
            val animator = AnimatorSet()
            animator.playTogether(animatorA, animatorY)
            animator.interpolator = DecelerateInterpolator()
            animator.startDelay = delay * i
            animator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    changeVisible(View.VISIBLE, target)
                }

                override fun onAnimationEnd(animation: Animator) {}
                override fun onAnimationCancel(animation: Animator) {}
                override fun onAnimationRepeat(animation: Animator) {}
            })
            animator.start()
        }
    }

    //vararg:表示参数可变
    private fun changeVisible(visible: Int, vararg views: View) {
        //for循环使用in
        for (view in views) {
            view.visibility = visible
        }
    }
}