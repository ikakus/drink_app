package ikakus.com.drink

import android.app.Application
import android.content.Context

/**
 * Created by ikakus on 4/4/17.
 */
class App  : Application() {
    override fun onCreate() {
        super.onCreate()
        instance.mContext = this
    }

    object instance{
        var mContext : Context? = null
    }
}