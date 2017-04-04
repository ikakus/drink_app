package ikakus.com.drink

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mPercentage: Int = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        percentage_text.text = mPercentage.toString()
        button.setOnClickListener {
            if(mPercentage != 100) {
                mPercentage += 10
                water_level.setPercentage(mPercentage)
                percentage_text.text = mPercentage.toString()
            }else{
                mPercentage = 10
                water_level.setPercentage(mPercentage)
                percentage_text.text = mPercentage.toString()
            }
        }
    }

    override fun onStart() {
        super.onStart()
    }
}
