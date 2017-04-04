package ikakus.com.drink.mainpage

import ikakus.com.drink.IPresenter

/**
 * Created by ikakus on 4/4/17.
 */
class MainPresenter : IPresenter<IMainView>() {

    private val mStep: Int = 5
    private val mMaxPercentage: Int = 100
    private var mPercentage: Int = 10
    private val mDefaultPercentage: Int = 10
    private var mNextPercentage: Int = 10

    private var mIteractor: MainInteractorImpl? = MainInteractorImpl()

    override fun setView(view: IMainView) {
        mView = view
    }

    fun initialize() {
        mView?.setPercentWithAnim(mIteractor!!.getPercent())
    }

    fun onDrinkClicked() {
        handleOnClick()
        mPercentage += mStep
    }

    private fun handleOnClick() {
        if (mPercentage != mMaxPercentage) {
            mNextPercentage = mPercentage + mStep
            mView?.setPercentWithAnim(mNextPercentage)
        } else {
            mNextPercentage = mDefaultPercentage
            mView?.setPercentWithAnim(mNextPercentage)
        }
    }

    fun getCurrentPercent(): Int {
        return mPercentage
    }
}