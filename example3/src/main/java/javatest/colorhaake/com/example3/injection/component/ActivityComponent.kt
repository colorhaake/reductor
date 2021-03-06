package javatest.colorhaake.com.example3.injection.component

import javatest.colorhaake.com.example3.injection.PerActivity
import javatest.colorhaake.com.example3.reducer.main.MainActions
import javatest.colorhaake.com.example3.injection.module.ActivityModule

import dagger.Component
import javatest.colorhaake.com.example3.view.main.MainActivity
import javatest.colorhaake.com.example3.view.main.MainPresenter

@PerActivity
@Component(dependencies = [(ApplicationComponent::class)], modules = [(ActivityModule::class)])
interface ActivityComponent {
    val mainActions: MainActions
    val presenter: MainPresenter
    fun inject(mainActivity: MainActivity)
}
