package com.FiftyOneDegree.research.SensorsActivity

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.FiftyOneDegree.research.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sensors.*


class SensorsActivity : AppCompatActivity() {

    lateinit var smm: SensorManager

    var sensor: List<Sensor>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sensors)

        getsensors()

    }


    data class Sensors(
        val name: String,
        val vendor: String,
        val version: Int,
        val type: Int,
        val maxRange: Float,
        val resolution: Float,
        val power: Float,
        val minDelay: Int
    )

    data class SensorName(
        val name: String
    )


    fun getsensors() {

        smm = getSystemService(Context.SENSOR_SERVICE) as SensorManager;
        val sensor = smm.getSensorList(Sensor.TYPE_ALL);

        val allSensors = ArrayList<Sensors>()
        val allSensorName = ArrayList<SensorName>()

        for (i in sensor) {
            allSensors.add(
                Sensors(i.name,
                i.vendor,
                i.version,
                i.type,
                i.maximumRange,
                i.resolution,
                i.power,
                i.minDelay)
            )
            allSensorName.add(SensorName(i.name))
            Log.e("Sensors", "Sensor Name : "+i.name+"\n")
        }


        // txt_sensors.text = Gson().toJson(sensor).toString()
/*
{Sensor name="BMI120 Accelerometer", vendor="BOSCH", version=2062600, type=1, maxRange=156.9064, resolution=0.0023956299, power=0.18, minDelay=5000}
* */
     //   Log.e("Sensors : ", Gson().toJson(allSensors))
        lv.setAdapter(
            ArrayAdapter<Sensors>(
                this,
                android.R.layout.simple_list_item_1,
                allSensors as MutableList<Sensors>
            )
        );

    }


}

