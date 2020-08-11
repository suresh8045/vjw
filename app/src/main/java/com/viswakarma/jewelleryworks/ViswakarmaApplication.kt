package com.viswakarma.jewelleryworks


import timber.log.Timber
import timber.log.Timber.DebugTree
import android.util.Log
import com.viswakarma.jewelleryworks.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

val sharedPreferences: Prefs by lazy {
    ViswakarmaApplication.prefs!!
}

class ViswakarmaApplication: DaggerApplication() {

    private val applicationInjector = DaggerAppComponent.factory().create(this)

    companion object {
        var prefs: Prefs? = null
    }


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return applicationInjector
    }



    override fun onCreate() {
        super.onCreate()
        prefs = Prefs(applicationContext)
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        } else {
            Timber.plant(CrashReportingTree())
        }
    }

    /** A tree which logs important information for crash reporting.  */
    private class CrashReportingTree : Timber.Tree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (priority == Log.VERBOSE || priority == Log.DEBUG) {
                return
            }

           // FakeCrashLibrary.log(priority, tag, message)

            if (t != null) {
                if (priority == Log.ERROR) {
                  //  FakeCrashLibrary.logError(t)
                } else if (priority == Log.WARN) {
                  //  FakeCrashLibrary.logWarning(t)
                }
            }
        }
    }

}