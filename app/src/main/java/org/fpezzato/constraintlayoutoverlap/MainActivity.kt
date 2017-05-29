package org.fpezzato.constraintlayoutoverlap

import android.animation.ValueAnimator
import android.animation.ValueAnimator.INFINITE
import android.animation.ValueAnimator.REVERSE
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var  anim : ValueAnimator = EmptyAnimator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_container.hide(bottom_view).whenOverlapping(top_view)

        initClickListeners()

        //Quick solution: OnPreDrawListener to change visibility.
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

    private fun initClickListeners() {
        run.setOnClickListener {
            if(!anim.isRunning) {
                anim = ValueAnimator.ofInt(main_container.measuredHeight, dpToPixel(300).toInt())
                anim.addUpdateListener { valueAnimator ->
                    val layoutParams = main_container.layoutParams
                    layoutParams.height = valueAnimator.animatedValue as Int
                    main_container.layoutParams = layoutParams
                }
                anim.repeatMode = REVERSE
                anim.repeatCount = INFINITE
                anim.duration = 2000
                anim.start()
            }
        }
    }

    fun dpToPixel(dp: Int): Float {
        return dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}
