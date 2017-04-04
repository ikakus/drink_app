package ikakus.com.drink

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ikakus.com.drink.wave.WaveHelper
import ikakus.com.drink.wave.WaveView
import kotlinx.android.synthetic.main.activity_main.*

//import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val mBorderColor = Color.parseColor("#3F51B5")
    val mBorderWidth = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wave_view.setBorder(mBorderWidth, mBorderColor)
        wave_view.setShapeType(WaveView.ShapeType.SQUARE)
        var waveHelper = WaveHelper(wave_view)
        waveHelper.start()

        button.setOnClickListener {
            var frontWaveColor = resources.getColor(R.color.colorFrontWave)
            var backWaveColor = resources.getColor(R.color.colorFrontWave)
            wave_view.setWaveColor(backWaveColor, frontWaveColor)
        }
    }

    override fun onStart() {
        super.onStart()

    }
}
