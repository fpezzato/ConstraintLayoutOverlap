package org.fpezzato.constraintlayoutoverlap

import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        push_up.setOnClickListener {
            val layoutParams = top_view.layoutParams as ViewGroup.MarginLayoutParams
            val test = layoutParams.topMargin;
            Log.w("-->", test.toString() + "")

            val layoutParams2 = bottom_view.layoutParams as ViewGroup.MarginLayoutParams
            val test3 = layoutParams.topMargin;
            Log.w("-->", test3.toString() + "")

            val anim = ValueAnimator.ofInt(main_container.measuredHeight, dpToPixel(300).toInt())
            anim.addUpdateListener { valueAnimator ->
                val layoutParams = main_container.layoutParams
                layoutParams.height = valueAnimator.animatedValue as Int
                main_container.layoutParams = layoutParams
            }
            anim.duration = 3000
            anim.start()
        }

        main_container.hide(bottom_view).whenOverlappedBy(top_view)

        /*main_container.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                main_container.viewTreeObserver.removeOnPreDrawListener(this)

                if (top_view.bottom > bottom_view.top) {
                    bottom_view.visibility = GONE
                }

                return false
            }
        })*/
    }

    fun dpToPixel(dp: Int): Float {
        return dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}
