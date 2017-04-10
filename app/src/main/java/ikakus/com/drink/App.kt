package ikakus.com.drink

import android.app.Application
import android.content.Context
import com.github.nisrulz.sensey.Sensey

/**
 * Created by ikakus on 4/4/17.
 */
class App  : Application() {
    override fun onCreate() {
        super.onCreate()
        instance.mContext = this
        Sensey.getInstance().init(this, Sensey.SAMPLING_PERIOD_UI)
    }

    object instance{
        var mContext : Context? = null
    }
}