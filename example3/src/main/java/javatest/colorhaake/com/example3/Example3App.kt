package javatest.colorhaake.com.example3

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

import javatest.colorhaake.com.example3.injection.component.ApplicationComponent
import javatest.colorhaake.com.example3.injection.component.DaggerApplicationComponent
import javatest.colorhaake.com.example3.injection.module.ApplicationModule
import com.facebook.drawee.backends.pipeline.Fresco

class Example3App : Application() {
    private lateinit var applicationComponent: ApplicationComponent

    fun component(): ApplicationComponent {
        return applicationComponent
    }

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
        applicationComponent.inject(this)

        // for using Fresco Image
        Fresco.initialize(this)
    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    companion object {
        operator fun get(activity: Activity): Example3App {
            return activity.application as Example3App
        }
    }
}
