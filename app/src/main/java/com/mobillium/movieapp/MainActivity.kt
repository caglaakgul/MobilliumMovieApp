package com.mobillium.movieapp

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity
import android.view.WindowManager

import android.os.Build
import android.view.Window
import android.app.Activity
import android.graphics.Color
import android.view.View

class MainActivity : DaggerAppCompatActivity() {
    var activity: Activity? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity = this@MainActivity

        val fullyTransparentStatusBar = FullyTransparentStatusBar(activity, window)
        fullyTransparentStatusBar.setFullTransparentStatus()
    }
}

class FullyTransparentStatusBar(activity: Activity?, window: Window) {
    private var activity: Activity? = null
    private val window: Window
    fun setFullTransparentStatus() {

        // make translucent statusBar on kitkat devices
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(activity, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.TRANSPARENT
        }
    }

    companion object {
        fun setWindowFlag(activity: Activity?, bits: Int, on: Boolean) {
            val win = activity!!.window
            val winParams = win.attributes
            if (on) {
                winParams.flags = winParams.flags or bits
            } else {
                winParams.flags = winParams.flags and bits.inv()
            }
            win.attributes = winParams
        }
    }

    init {
        this.activity = activity
        this.window = window
    }
}