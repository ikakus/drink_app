package ikakus.com.drink.history

/**
 * Created by ikakus on 4/5/17.
 */
class HistoryInteractorImpl : IHistoryInteractor {
    private var mHistory: ArrayList<Long> = ArrayList()

    override fun clear() {
        mHistory = ArrayList()
    }

    override fun addTime(time: Long) {
        mHistory.add(time)
    }

    override fun getHistoryAllTime(): List<Long> {
        return mHistory
    }

    override fun getHistoryToday(): List<Long> {
        return mHistory
    }
}