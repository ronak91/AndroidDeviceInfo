package com.FiftyOneDegree.research.DisplayActivity

import android.graphics.Point
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import com.FiftyOneDegree.research.R
import kotlinx.android.synthetic.main.activity_display.*


class ScreenResolution : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        txt_currentDisplayHeight.text = getScreenHeightInPixel().toString()
        txt_currentDisplayWidth.text = getScreenWidthInPixel().toString()
        txt_myDisplayHeight.text =   getMyScreenHeightInPixel().toString()
        txt_myDisplayWidth.text = getMyScreenWidthInPixel().toString()
        txt_oldDiagoanlValue.text = getScreenInchesDiagonal().toString()
        txt_newDiagoanlValue.text = getScreenInchesDiagonalNew().toString()
        txt_pergfectDiagoanlValue.text = getScreenSize()
        txt_screenResolution.text = screenResolution()

        getDiagonal()
    }

    private fun screenResolution() : String{
        return getMyScreenWidthInPixel().toString() +" * "+ getMyScreenHeightInPixel().toString()
    }

    private fun getScreenWidthInPixel(): Int {
        val displayMetrics: DisplayMetrics =  getDisplayMetrics()
        return displayMetrics.widthPixels
    }

    private fun getScreenHeightInPixel(): Int {
        val displayMetrics: DisplayMetrics = getDisplayMetrics()
        return displayMetrics.heightPixels
    }


    private fun getMyScreenWidthInPixel(): Int {
        val displayMetrics: DisplayMetrics =  getDisplayMetrics()
        return displayMetrics.widthPixels
    }

    private fun getMyScreenHeightInPixel(): Int {
        val displayMetrics: DisplayMetrics = getDisplayMetrics()
        return displayMetrics.heightPixels + getNavigationBarHeight()
    }


    @NonNull
    private fun getDisplayMetrics(): DisplayMetrics {
        val displayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics
    }

    private fun getNavigationBarHeight(): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            val metrics = DisplayMetrics()
            windowManager.defaultDisplay.getMetrics(metrics)
            val usableHeight = metrics.heightPixels
            windowManager.defaultDisplay.getRealMetrics(metrics)
            val realHeight = metrics.heightPixels
            return if (realHeight > usableHeight) realHeight - usableHeight else 0
        }
        return 0
    }


    private fun getScreenInchesDiagonal(): Double {
        val dm = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(dm)
        val x = Math.pow(getScreenWidthInPixel() / dm.xdpi.toDouble(), 2.0)
        val y = Math.pow(getScreenHeightInPixel() / dm.ydpi.toDouble(),2.0)
        return Math.sqrt(x + y)
    }

    private fun getScreenInchesDiagonalNew(): Double {
        val dm = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(dm)
        val x = Math.pow(getMyScreenWidthInPixel() / dm.xdpi.toDouble(), 2.0)
        val y = Math.pow(getMyScreenHeightInPixel() / dm.ydpi.toDouble(),2.0)
        return Math.sqrt(x + y)
    }

    fun getDiagonal() {
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val x = Math.pow(dm.widthPixels / dm.xdpi.toDouble(), 2.0)
        val y = Math.pow(dm.heightPixels / dm.ydpi.toDouble(), 2.0)
        val screenInches = Math.sqrt(x + y) * dm.scaledDensity
        Log.e("debug", "Screen inches : $screenInches")
    }

    private fun getScreenSize(): String? {
        val point = Point()
        (getSystemService(WINDOW_SERVICE) as WindowManager).defaultDisplay.getRealSize(point)
        val displayMetrics = resources.displayMetrics
        val width: Int = point.x
        val height: Int = point.y
        val wi = width.toDouble() / displayMetrics.xdpi.toDouble()
        val hi = height.toDouble() / displayMetrics.ydpi.toDouble()
        val x = Math.pow(wi, 2.0)
        val y = Math.pow(hi, 2.0)
        return (Math.round(Math.sqrt(x + y) * 10.0) / 10.0).toString()
    }

    private fun getScreenDimension(): Array<String?>? {
        val dm = DisplayMetrics()
        getWindowManager().getDefaultDisplay().getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels
        val dens = dm.densityDpi
        val wi = width.toDouble() / dens.toDouble()
        val hi = height.toDouble() / dens.toDouble()
        val x = Math.pow(wi, 2.0)
        val y = Math.pow(hi, 2.0)
        val screenInches = Math.sqrt(x + y)
        val screenInformation = arrayOfNulls<String>(3)
        screenInformation[0] = "$width px"
        screenInformation[1] = "$height px"
        screenInformation[2] = String.format("%.2f", screenInches) + " inches"
        return screenInformation
    }
}