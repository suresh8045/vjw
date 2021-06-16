package com.viswakarma.jewelleryworks

import android.app.Application
import android.util.Log
import com.viswakarma.jewelleryworks.di.appModule
import com.viswakarma.jewelleryworks.di.dataModule
import com.viswakarma.jewelleryworks.di.databaseModule
import com.viswakarma.jewelleryworks.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class ViswakarmaApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ViswakarmaApplication)
            modules(appModule, viewModelModule, databaseModule, dataModule)
        }

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

//            if (t != null) {
//                if (priority == Log.ERROR) {
            //  FakeCrashLibrary.logError(t)
//                } else if (priority == Log.WARN) {
            //  FakeCrashLibrary.logWarning(t)
//                }
//            }
        }
    }

}