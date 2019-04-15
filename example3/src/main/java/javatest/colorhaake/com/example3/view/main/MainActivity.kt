package javatest.colorhaake.com.example3.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager

import javatest.colorhaake.com.example3.R
import javatest.colorhaake.com.example3.Example3App
import javatest.colorhaake.com.example3.injection.component.ActivityComponent
import javatest.colorhaake.com.example3.injection.component.DaggerActivityComponent
import javatest.colorhaake.com.example3.injection.module.ActivityModule
import javatest.colorhaake.com.example3.model.ImageData
import javatest.colorhaake.com.example3.model.Response
import kotlinx.android.synthetic.main.activity_main.*

import javax.inject.Inject

class MainActivity : AppCompatActivity(), MainMvpView {

    @Inject
    lateinit var mMainPresenter: MainPresenter

    private val adapter: MainAdapter by lazy {
        MainAdapter()
    }

    private val component: ActivityComponent by lazy {
        DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .applicationComponent(Example3App[this].component())
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        component.inject(this)

        recycler_view.adapter = adapter
        recycler_view.layoutManager = LinearLayoutManager(this)

        mMainPresenter.attachView(this)
        mMainPresenter.viewReady()
    }

    private fun onSeachTextChange(term: String) {
        mMainPresenter.searchImages(term, 1)
    }

    override fun onStop() {
        super.onStop()
        mMainPresenter.detachView()
    }


    /***** MVP View methods implementation  */

    override fun showMainPage(searchImageData: Response<ImageData>) {
        adapter.updateSearchImageData(searchImageData, ::onSeachTextChange)
    }
}
