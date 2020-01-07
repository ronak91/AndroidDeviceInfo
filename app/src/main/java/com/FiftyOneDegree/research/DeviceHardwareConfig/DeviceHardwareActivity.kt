package com.FiftyOneDegree.research.DeviceHardwareConfig

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.FiftyOneDegree.research.R
import kotlinx.android.synthetic.main.activity_hardware_config.*

class DeviceHardwareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hardware_config)

        txt_cpu.text = getBoard()

    }

    private fun getBoard(): String? {
        return Build.BOARD
    }
}