package ikakus.com.drink.history

/**
 * Created by ikakus on 4/5/17.
 */
interface IHistoryInteractor {
    fun clear()
    fun addTime(time:Long)
    fun getHistoryAllTime() : List<Long>
    fun getHistoryToday() : List<Long>
}