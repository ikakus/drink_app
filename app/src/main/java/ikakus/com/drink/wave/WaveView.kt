package ikakus.com.drink.wave

/**
 * Created by idadiani on 10/11/16.
 */

/*
 *  Copyright (C) 2015, gelitenight(gelitenight@gmail.com).
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import android.content.Context
import android.graphics.*
import android.graphics.Paint.Style
import android.util.AttributeSet
import android.view.ViewGroup

class WaveView : ViewGroup {
    // if true, the shader will display the wave
    var isShowWave: Boolean = false
    // shader containing repeated waves
    private var mWaveShader: BitmapShader? = null
    // shader matrix
    private var mShaderMatrix: Matrix? = null
    // paint to draw wave
    private var mViewPaint: Paint? = null
    // paint to draw border
    private var mBorderPaint: Paint? = null
    private var mDefaultAmplitude: Float = 0.toFloat()
    private var mDefaultWaterLevel: Float = 0.toFloat()
    private var mDefaultWaveLength: Float = 0.toFloat()
    private var mDefaultAngularFrequency: Double = 0.toDouble()
    /**
     * Set vertical size of wave according to `amplitudeRatio`

     * @param amplitudeRatio Default to be 0.05. Result of amplitudeRatio + waterLevelRatio should be less than 1.
     * *                       <br></br>Ratio of amplitude to height of WaveView.
     */
    var amplitudeRatio = DEFAULT_AMPLITUDE_RATIO
        set(amplitudeRatio) {
            if (this.amplitudeRatio != amplitudeRatio) {
                field = amplitudeRatio
                invalidate()
            }
        }
    /**
     * Set horizontal size of wave according to `waveLengthRatio`

     * @param waveLengthRatio Default to be 1.
     * *                        <br></br>Ratio of wave length to width of WaveView.
     */
    var waveLengthRatio = DEFAULT_WAVE_LENGTH_RATIO
    /**
     * Set water level according to `waterLevelRatio`.

     * @param waterLevelRatio Should be 0 ~ 1. Default to be 0.5.
     * *                        <br></br>Ratio of water level to WaveView height.
     */
    var waterLevelRatio = DEFAULT_WATER_LEVEL_RATIO
        set(waterLevelRatio) {
            if (this.waterLevelRatio != waterLevelRatio) {
                field = waterLevelRatio
                invalidate()
            }
        }
    /**
     * Shift the wave horizontally according to `waveShiftRatio`.

     * @param waveShiftRatio Should be 0 ~ 1. Default to be 0.
     * *                       <br></br>Result of waveShiftRatio multiples width of WaveView is the length to shift.
     */
    var waveShiftRatio = DEFAULT_WAVE_SHIFT_RATIO
        set(waveShiftRatio) {
            if (this.waveShiftRatio != waveShiftRatio) {
                field = waveShiftRatio
                invalidate()
            }
        }
    private var mBehindWaveColor = DEFAULT_BEHIND_WAVE_COLOR
    private var mFrontWaveColor = DEFAULT_FRONT_WAVE_COLOR
    private var mShapeType = DEFAULT_WAVE_SHAPE

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        init()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }

    private fun init() {
        mShaderMatrix = Matrix()
        mViewPaint = Paint()
        mViewPaint!!.isAntiAlias = true
    }

    fun setBorder(width: Int, color: Int) {
        if (mBorderPaint == null) {
            mBorderPaint = Paint()
            mBorderPaint!!.isAntiAlias = true
            mBorderPaint!!.style = Style.STROKE
        }
        mBorderPaint!!.color = color
        mBorderPaint!!.strokeWidth = width.toFloat()

        invalidate()
    }

    fun setWaveColor(behindWaveColor: Int, frontWaveColor: Int) {
//        mBehindWaveColor = behindWaveColor
        mFrontWaveColor = frontWaveColor

        // need to recreate shader when color changed
        mWaveShader = null
        createShader()
        invalidate()
    }

    fun setShapeType(shapeType: ShapeType) {
        mShapeType = shapeType
        invalidate()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        createShader()
    }

    /**
     * Create the shader with default waves which repeat horizontally, and clamp vertically
     */
    private fun createShader() {
        mDefaultAngularFrequency = 2.0f * Math.PI / DEFAULT_WAVE_LENGTH_RATIO.toDouble() / width.toDouble()
        mDefaultAmplitude = height * DEFAULT_AMPLITUDE_RATIO
        mDefaultWaterLevel = height * DEFAULT_WATER_LEVEL_RATIO
        mDefaultWaveLength = width.toFloat()

        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        val wavePaint = Paint()
        wavePaint.strokeWidth = 2f
        wavePaint.isAntiAlias = true

        // Draw default waves into the bitmap
        // y=Asin(ωx+φ)+h
        val endX = width + 1
        val endY = height + 1

        val waveY = FloatArray(endX)

        wavePaint.color = mBehindWaveColor
        for (beginX in 0..endX - 1) {
            val wx = beginX * mDefaultAngularFrequency
            val beginY = (mDefaultWaterLevel + mDefaultAmplitude * Math.sin(wx)).toFloat()
            canvas.drawLine(beginX.toFloat(), beginY, beginX.toFloat(), endY.toFloat(), wavePaint)

            waveY[beginX] = beginY
        }

        wavePaint.color = mFrontWaveColor
        val wave2Shift = (mDefaultWaveLength / 4).toInt()
        for (beginX in 0..endX - 1) {
            canvas.drawLine(beginX.toFloat(), waveY[(beginX + wave2Shift) % endX], beginX.toFloat(), endY.toFloat(), wavePaint)
        }

        // use the bitamp to create the shader
        mWaveShader = BitmapShader(bitmap, Shader.TileMode.REPEAT, Shader.TileMode.CLAMP)
        mViewPaint!!.shader = mWaveShader
    }

    override fun onDraw(canvas: Canvas) {
        // modify paint shader according to mShowWave state
        if (isShowWave && mWaveShader != null) {
            // first call after mShowWave, assign it to our paint
            if (mViewPaint!!.shader == null) {
                mViewPaint!!.shader = mWaveShader
            }

            // sacle shader according to mWaveLengthRatio and mAmplitudeRatio
            // this decides the size(mWaveLengthRatio for width, mAmplitudeRatio for height) of waves
            mShaderMatrix!!.setScale(
                    waveLengthRatio / DEFAULT_WAVE_LENGTH_RATIO,
                    amplitudeRatio / DEFAULT_AMPLITUDE_RATIO,
                    0f,
                    mDefaultWaterLevel)
            // translate shader according to mWaveShiftRatio and mWaterLevelRatio
            // this decides the start position(mWaveShiftRatio for x, mWaterLevelRatio for y) of waves
            mShaderMatrix!!.postTranslate(
                    waveShiftRatio * width,
                    (DEFAULT_WATER_LEVEL_RATIO - waterLevelRatio) * height)

            // assign matrix to invalidate the shader
            mWaveShader!!.setLocalMatrix(mShaderMatrix)

            val borderWidth = if (mBorderPaint == null) 0f else mBorderPaint!!.strokeWidth
            when (mShapeType) {
                ShapeType.CIRCLE -> {
                    if (borderWidth > 0) {
                        canvas.drawCircle(width / 2f, height / 2f,
                                (width - borderWidth) / 2f - 1f, mBorderPaint!!)
                    }
                    val radius = width / 2f - borderWidth
                    canvas.drawCircle(width / 2f, height / 2f, radius, mViewPaint!!)
                }
                ShapeType.SQUARE -> {
                    if (borderWidth > 0) {
                        canvas.drawRect(
                                borderWidth / 2f,
                                borderWidth / 2f,
                                width.toFloat() - borderWidth / 2f - 0.5f,
                                height.toFloat() - borderWidth / 2f - 0.5f,
                                mBorderPaint!!)
                    }
                    canvas.drawRect(borderWidth, borderWidth, width - borderWidth,
                            height - borderWidth, mViewPaint!!)
                }
            }
        } else {
            mViewPaint!!.shader = null
        }
    }

    enum class ShapeType {
        CIRCLE,
        SQUARE
    }

    companion object {
        val DEFAULT_BEHIND_WAVE_COLOR = Color.parseColor("#267bbc")
        val DEFAULT_FRONT_WAVE_COLOR = Color.parseColor("#32bafa")
        val DEFAULT_WAVE_SHAPE = ShapeType.CIRCLE
        /**
         * +------------------------+
         * |<--wave length->        |______
         * |   /\          |   /\   |  |
         * |  /  \         |  /  \  | amplitude
         * | /    \        | /    \ |  |
         * |/      \       |/      \|__|____
         * |        \      /        |  |
         * |         \    /         |  |
         * |          \  /          |  |
         * |           \/           | water level
         * |                        |  |
         * |                        |  |
         * +------------------------+__|____
         */
        private val DEFAULT_AMPLITUDE_RATIO = 0.05f
        private val DEFAULT_WATER_LEVEL_RATIO = 0.5f
        private val DEFAULT_WAVE_LENGTH_RATIO = 1.0f
        private val DEFAULT_WAVE_SHIFT_RATIO = 0.0f
    }
}