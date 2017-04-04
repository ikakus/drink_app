package ikakus.com.drink.waterview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import ikakus.com.drink.R
import ikakus.com.drink.wave.WaveHelper
import ikakus.com.drink.wave.WaveView

/**
 * Created by ikakus on 4/4/17.
 */
class WaterLevel : FrameLayout {

    val mBorderColor = Color.parseColor("#3F51B5")
    val mBorderWidth = 0
    val mDefaultPercentage = 10
    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }


    private var view: View? = null

    private var mContext: Context? = null

    private fun initView(context: Context) {
        mContext = context
        view = LayoutInflater.from(context).inflate(R.layout.view_water_level, this, true)
        var waveView: WaveView = (view as View?)!!.findViewById(R.id.wave_view) as WaveView
        waveView.setBorder(mBorderWidth, mBorderColor)
        waveView.setShapeType(WaveView.ShapeType.SQUARE)
        var waveHelper = WaveHelper(waveView)
        waveHelper.start()

        afterMeasured {
//            setDefaultPositions(mDefaultPercentage)
        }
    }

    private fun setDefaultPositions(percent: Int) {
        val percentHeight = height / 100
        val newHeight = (height - percentHeight * percent)

        val bottomBack = view?.findViewById(R.id.bottom_back_color)
        val waveView = view?.findViewById(R.id.wave_view)

        val botParams  = bottomBack!!.layoutParams
        (botParams as FrameLayout.LayoutParams).setMargins(0, newHeight , 0 ,0)
        bottomBack.layoutParams = botParams

        val waveParams = waveView?.layoutParams
        (waveParams as FrameLayout.LayoutParams).setMargins(0, newHeight , 0 ,0)
        waveView.layoutParams = waveParams

    }

    fun setPercentageWithAnimation(percent: Int) {
        var mAnimationDuration = mContext?.getResources()?.getInteger(android.R.integer.config_mediumAnimTime)

        val percentHeight = height / 100
        val newHeight = (height - percentHeight * percent)

        val bottomBack = view?.findViewById(R.id.bottom_back_color)
        val waveView = view?.findViewById(R.id.wave_view)

        var mWavesAnimation = waveView!!.animate()?.setDuration(mAnimationDuration!!.toLong())
        mWavesAnimation!!.interpolator = AccelerateDecelerateInterpolator()
        mWavesAnimation!!.y(newHeight.toFloat())

        var mTopBackAnimation = bottomBack!!.animate()?.setDuration(mAnimationDuration!!.toLong())
        mTopBackAnimation!!.interpolator = AccelerateDecelerateInterpolator()
        mTopBackAnimation!!.y(newHeight.toFloat())

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