package ikakus.com.drink.waterview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.FrameLayout
import com.github.nisrulz.sensey.RotationAngleDetector
import com.github.nisrulz.sensey.Sensey
import ikakus.com.drink.R
import ikakus.com.drink.wave.WaveHelper
import ikakus.com.drink.wave.WaveView
import kotlinx.android.synthetic.main.view_water_level.view.*


/**
 * Created by ikakus on 4/4/17.
 */
class WaterLevel : FrameLayout {

    val mBorderColor = Color.parseColor("#3F51B5")
    val mBorderWidth = 0
    val mDefaultPercentage = 10
    private var view: View? = null
    private var mContext: Context? = null
    private var waveView: WaveView? = null

    var currentAngle: Float = 0f

    constructor(context: Context) : super(context) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initView(context)
    }

    private fun initView(context: Context) {
        mContext = context
        view = LayoutInflater.from(context).inflate(R.layout.view_water_level, this, true)
        waveView = (view as View?)!!.findViewById(R.id.wave_view) as WaveView
        waveView?.setBorder(mBorderWidth, mBorderColor)
        waveView?.setShapeType(WaveView.ShapeType.SQUARE)
        val waveHelper = WaveHelper(waveView!!)
        waveHelper.start()

        Sensey.getInstance().startRotationAngleDetection(rotationAngleListener)

        afterMeasured {
            setDefaultPositions(mDefaultPercentage)
        }
    }

    private var biggerWidth: Int = 0

    private fun setDefaultPositions(percent: Int) {
        val percentHeight = height / 100
        var newHeight = (height - percentHeight * percent)

        val bottomBack = view?.findViewById(R.id.bottom_back_color)
        val waveView = view?.findViewById(R.id.wave_view)

        biggerWidth = height * 2

        val botParams = bottomBack!!.layoutParams
        botParams.height = biggerWidth
        botParams.width = biggerWidth
        var leftMargin = -500


        (botParams as FrameLayout.LayoutParams).setMargins(leftMargin, newHeight, 0, 0)
        bottomBack.layoutParams = botParams

        val waveParams = waveView!!.layoutParams
        waveParams.width = height
        (waveParams as FrameLayout.LayoutParams).setMargins(leftMargin, newHeight, 0, 0)
        waveView.layoutParams = waveParams

    }

    private var newHeight: Int = 0

    fun setPercentageWithAnimation(percent: Int) {
        var mAnimationDuration = mContext?.getResources()?.getInteger(android.R.integer.config_mediumAnimTime)

        val percentHeight = height / 100
        newHeight = (height - percentHeight * percent)
        // show waves a little bit when percent is 0
        if (percent == 0) {
            newHeight = (height - percentHeight * 5)
        }

        var mWavesAnimation = wave_view!!.animate()?.setDuration(mAnimationDuration!!.toLong())
        mWavesAnimation!!.interpolator = AccelerateDecelerateInterpolator()
        mWavesAnimation!!.y(newHeight.toFloat())

        var mTopBackAnimation = bottom_back_color!!.animate()?.setDuration(mAnimationDuration!!.toLong())
        mTopBackAnimation!!.interpolator = AccelerateDecelerateInterpolator()
        mTopBackAnimation!!.y(newHeight.toFloat() + 10)

    }

    inline fun <T : View> T.afterMeasured(crossinline f: T.() -> Unit) {
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (measuredWidth > 0 && measuredHeight > 0) {
                    viewTreeObserver.removeOnGlobalLayoutListener(this)
                    f()
                }
            }
        })
    }

    private val threshold: Float = 2f
    private val maxAngle: Float = 20f
    var rotationAngleListener: RotationAngleDetector.RotationAngleListener
            = RotationAngleDetector.RotationAngleListener { angleInAxisX, angleInAxisY, angleInAxisZ ->
        // Do something with the angles, values are in degrees
        var angle = Math.round(angleInAxisZ * -1).toFloat()
        if (Math.abs(currentAngle - Math.abs(angleInAxisZ)) > threshold) {

            if (angle > maxAngle) {
                angle = maxAngle
            }

            if (angle < -maxAngle) {
                angle = -maxAngle
            }
            currentAngle = angle
            waveView?.rotation = angle
            bottom_back_color?.rotation = angle
        }
        val xPivot: Float = (biggerWidth / 4).toFloat()
        bottom_back_color?.pivotX = xPivot
        waveView?.pivotX = xPivot
    }


}