package ikakus.com.drink

/**
 * Created by ikakus on 4/4/17.
 */

abstract class IPresenter<T> {
    protected var mView: T? = null

    abstract fun setView(view: T)
}
