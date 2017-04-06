package ikakus.com.drink.mainpage

import ikakus.com.drink.IPresenter
import ikakus.com.drink.history.HistoryInteractorImpl
import ikakus.com.drink.history.IHistoryInteractor

/**
 * Created by ikakus on 4/4/17.
 */
class MainPresenter : IPresenter<IMainView>() {

    private val mStep: Int = 10
    private val mMaxPercentage: Int = 100
    private var mCurrentPercentage: Int = 0
    private var mNextPercentage: Int = 0
    private val mDefaultPercentage: Int = 0

    private var mPercentInteractor: IPercentInteractor? = PercentInteractorImpl()
    private var mHistoryInteractor: IHistoryInteractor? = HistoryInteractorImpl()

    override fun setView(view: IMainView) {
        mView = view
    }

    fun initialize() {
        mCurrentPercentage = mPercentInteractor!!.getPercent()
        mView?.setPercentWithAnim(mCurrentPercentage)
        mView?.setHistory(mHistoryInteractor?.getHistoryToday())
    }

    fun onDrinkClicked() {
        handleOnClick()
    }

    private fun handleOnClick() {
        if (mCurrentPercentage != mMaxPercentage) {
            mNextPercentage = mCurrentPercentage + mStep
            mHistoryInteractor?.addTime(System.currentTimeMillis())
        } else {
            mNextPercentage = mDefaultPercentage
            mHistoryInteractor?.clear()
            mView?.reset()
        }
        mView?.setPercentWithAnim(mNextPercentage)
        mView?.setHistory(mHistoryInteractor?.getHistoryToday())
        mPercentInteractor?.setPercent(mNextPercentage)
        mCurrentPercentage = mNextPercentage
    }

    fun getCurrentPercent(): Int {
        mCurrentPercentage = mPercentInteractor!!.getPercent()
        return mCurrentPercentage
    }
}
