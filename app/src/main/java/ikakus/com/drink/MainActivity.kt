package ikakus.com.drink

import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ikakus.com.drink.mainpage.IMainView
import ikakus.com.drink.mainpage.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IMainView {
    private var mPresenter: MainPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mPresenter = MainPresenter()
        mPresenter?.setView(this)
        mPresenter?.initialize()
        button.setOnClickListener {
            mPresenter?.onDrinkClicked()
        }
    }

    override fun setPercentWithAnim(percent: Int) {
        water_level.setPercentage(percent)
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

}
