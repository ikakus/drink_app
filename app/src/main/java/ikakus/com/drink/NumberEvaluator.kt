package ikakus.com.drink

import android.animation.TypeEvaluator


/**
 * Created by ikakus on 4/4/17.
 */
class NumberEvaluator : TypeEvaluator<Int> {
    override fun evaluate(fraction: Float, startValue: Int, endValue: Int): Int {
        var retVal =  (startValue + fraction * endValue - startValue).toInt()
        return retVal
    }
}
