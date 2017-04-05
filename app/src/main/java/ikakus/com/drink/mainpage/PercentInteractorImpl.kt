package ikakus.com.drink.mainpage

import ikakus.com.drink.App

/**
 * Created by ikakus on 4/4/17.
 */
class PercentInteractorImpl : IPercentInteractor {
    private val PREFS_FILENAME = "ikakus.com.drink"
    private val CURRENT_PERCENT = "percent"

    override fun setPercent(percent: Int) {
        val context = App.instance.mContext
        val prefs = context!!.getSharedPreferences(PREFS_FILENAME, 0)
        val editor = prefs!!.edit()
        editor!!.putInt(CURRENT_PERCENT, percent)
        editor.apply()
    }

    override fun getPercent(): Int {
        val context = App.instance.mContext
        val prefs = context!!.getSharedPreferences(PREFS_FILENAME, 0)
        val percent = prefs!!.getInt(CURRENT_PERCENT, 0)
        return percent
    }
}