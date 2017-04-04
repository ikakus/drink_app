package ikakus.com.drink.mainpage

import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewTreeObserver
import ikakus.com.drink.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IMainView {
    private var mPresenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenter()
        mPresenter?.setView(this)
        button.setOnClickListener {
            mPresenter?.onDrinkClicked()
        }

        button.afterMeasured {
            mPresenter?.initialize()
        }
    }

    override fun setPercentWithAnim(percent: Int) {
        water_level.setPercentageWithAnimation(percent)
        animatePercent(mPresenter?.getCurrentPercent()!!, percent)
    }


    fun animatePercent(from: Int, to: Int) {
        val animator = ValueAnimator.ofInt(from, to)
        animator.addUpdateListener { animation -> setText(animation.animatedValue) }
        animator.start()
    }

    private fun setText(format: Any) {
        percentage_text.text = format.toString()
    }

    inline fun <T: View> T.afterMeasured(crossinline f: T.() -> Unit) {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (measuredWidth > 0 && measuredHeight > 0) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    f()
                }
            }
        })
    }

}
