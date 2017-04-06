package ikakus.com.drink.history

import com.google.gson.Gson
import ikakus.com.drink.App

/**
 * Created by ikakus on 4/5/17.
 */
class HistoryInteractorImpl : IHistoryInteractor {
    private val PREFS_FILENAME = "ikakus.com.drink"
    private val HISTORY = "history"

    override fun clear() {
        var history = ArrayList<Long>()
        saveHistory(history)
    }

    override fun addTime(time: Long) {
        var history = getHistory()
        history.add(time)
        saveHistory(history)
    }

    override fun getHistoryAllTime(): List<Long> {
        return getHistory()
    }

    override fun getHistoryToday(): List<Long> {
        return getHistory()
    }

    private fun saveHistory(list: ArrayList<Long>) {
        var gson = Gson()
        var string = gson.toJson(list)
        val context = App.instance.mContext
        val prefs = context!!.getSharedPreferences(PREFS_FILENAME, 0)
        val editor = prefs!!.edit()
        editor!!.putString(HISTORY, string)
        editor.apply()
    }

    private fun getHistory() : ArrayList<Long> {
        val context = App.instance.mContext
        val prefs = context!!.getSharedPreferences(PREFS_FILENAME, 0)
        val history = prefs!!.getString(HISTORY, "")
        var gson = Gson()
        val list = gson.fromJson(history, ArrayList::class.java)
        var ret =  ArrayList<Long>()
        if(list != null){
            ret = list as ArrayList<Long>
        }
        return ret
    }
}