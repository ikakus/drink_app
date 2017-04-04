package ikakus.com.drink.waterview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
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
    val mDefaultPercentage = 50
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

    private fun initView(context: Context) {
        view = LayoutInflater.from(context).inflate(R.layout.view_water_level, this, true)
        var waveView: WaveView = (view as View?)!!.findViewById(R.id.wave_view) as WaveView
        waveView.setBorder(mBorderWidth, mBorderColor)
        waveView.setShapeType(WaveView.ShapeType.SQUARE)
        var waveHelper = WaveHelper(waveView)
        waveHelper.start()

        afterMeasured {
            setPercentage(mDefaultPercentage)
        }
    }

    fun setPercentage(percent: Int) {
        val percentHeight = height / 100
        val topBack = view?.findViewById(R.id.top_back_color)
        val waveView = view?.findViewById(R.id.wave_view)
        val topParams = topBack?.layoutParams
        val newHeight = (height - percentHeight * percent)
        topParams?.height = newHeight
        topBack?.layoutParams = topParams

        val waveParams = waveView?.layoutParams
        (waveParams as FrameLayout.LayoutParams).setMargins(0, newHeight , 0 ,0)
        waveView.layoutParams = waveParams
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