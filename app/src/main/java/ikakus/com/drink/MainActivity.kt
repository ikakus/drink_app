package ikakus.com.drink

import android.animation.ValueAnimator
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val mStep: Int = 5
    private val mMaxPercentage: Int = 100
    private var mPercentage: Int = 10
    private val mDefaultPercentage: Int = 10
    private var mNextPercentage: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        percentage_text.text = mPercentage.toString()
        button.setOnClickListener {
            handleOnClick()
        }
    }

    private fun handleOnClick() {
        if (mPercentage != mMaxPercentage) {
            mNextPercentage = mPercentage + mStep
            water_level.setPercentage(mNextPercentage)
            animatePercent(mPercentage, mNextPercentage)
        } else {
            mNextPercentage = mDefaultPercentage
            water_level.setPercentage(mNextPercentage)
            animatePercent(mPercentage, mNextPercentage)
        }
    }

    fun animatePercent(from : Int , to : Int){
        val animator = ValueAnimator.ofInt(from, to)
        animator.addUpdateListener { animation -> setText(animation.animatedValue)}
        animator.start()
        mPercentage = to
    }

    private fun setText(format: Any) {
        percentage_text.text = format.toString()
    }

}
