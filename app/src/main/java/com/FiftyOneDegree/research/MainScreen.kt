package com.FiftyOneDegree.research

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.CellInfo
import android.telephony.TelephonyManager
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.FiftyOneDegree.research.CameraResearch.CameraResearch
import com.FiftyOneDegree.research.DeviceConfig.DeviceConfigActivity
import com.FiftyOneDegree.research.DeviceHardwareConfig.DeviceHardwareActivity
import com.FiftyOneDegree.research.DisplayActivity.ScreenResolution
import com.FiftyOneDegree.research.FinalDetails.FinalDetailsActivity
import com.FiftyOneDegree.research.IMEI.DeviceInfoActivity
import com.FiftyOneDegree.research.RecycleDragDrop.RvMainActivity
import com.FiftyOneDegree.research.SensorsActivity.AllSensorActivity
import com.FiftyOneDegree.research.SensorsActivity.SensorsActivity
import com.FiftyOneDegree.research.Storage.StorageActivity
import com.FiftyOneDegree.research.WebView.WebViewActivity
import com.FiftyOneDegree.research.fingerprint.FingerprintActivity
import com.FiftyOneDegree.research.imei.ImeiActivity
import com.FiftyOneDegree.research.marketingName.marketingNameActivity
import com.FiftyOneDegree.research.network.networkActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainScreen : AppCompatActivity() {

    var ACCESS_FINE_LOCATION = 5

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_NETWORK_STATE
            ) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.PACKAGE_USAGE_STATS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            RequestPermission()
        } else {
            RequestPermission()
        }

        cameraResearch.setOnClickListener {
            startActivityForResult(Intent(this, CameraResearch::class.java), 1)
        }

        swipeItemRecycleview.setOnClickListener {
            startActivityForResult(Intent(this, RvMainActivity::class.java), 2)
        }

        webView.setOnClickListener {
            startActivityForResult(Intent(this, WebViewActivity::class.java), 3)
        }

        fingerPrint.setOnClickListener {
            startActivityForResult(Intent(this, FingerprintActivity::class.java), 4)
        }

        storageDetails.setOnClickListener {
            startActivityForResult(Intent(this, StorageActivity::class.java), 5)
        }

        marketingName.setOnClickListener {
            startActivityForResult(Intent(this, marketingNameActivity::class.java), 6)
        }

        networkDetails.setOnClickListener {
            startActivityForResult(Intent(this, networkActivity::class.java), 6)
        }

        getDeviceConfig.setOnClickListener {
            startActivityForResult(Intent(this, DeviceConfigActivity::class.java), 6)
        }

        getDeviceInfo.setOnClickListener {
            startActivityForResult(Intent(this, DeviceInfoActivity::class.java), 12)
        }

        getnetworkDetails.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                RequestPermission()
            } else {
                getData()
            }
        }

        getScreenResolution.setOnClickListener {
            startActivityForResult(Intent(this, ScreenResolution::class.java), 7)
        }

        getDeviceSensors.setOnClickListener {
            startActivityForResult(Intent(this, SensorsActivity::class.java), 8)
        }

        getAllSensors.setOnClickListener {
            startActivityForResult(Intent(this, AllSensorActivity::class.java), 9)
        }

        getCPUHardware.setOnClickListener {
            startActivityForResult(Intent(this, DeviceHardwareActivity::class.java), 10)
        }

        getImei.setOnClickListener {
            startActivityForResult(Intent(this, ImeiActivity::class.java), 11)
        }


        getfinaldetails.setOnClickListener {
            startActivityForResult(Intent(this, FinalDetailsActivity::class.java), 13)
        }


    }

    fun RequestPermission() {

        // ACCESS_FINE_LOCATION permission has not been granted yet. Request it directly.   ACCESS_FINE_LOCATION
        /*
        * ActivityCompat.checkSelfPermission( this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission( this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission( this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission( this,Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission( this,Manifest.permission.ACCESS_NETWORK_STATE) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission( this,Manifest.permission.PACKAGE_USAGE_STATS) != PackageManager.PERMISSION_GRANTED)
        * */
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.ACCESS_NETWORK_STATE
            ), ACCESS_FINE_LOCATION
        )
    }

    fun getData() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val mTelephonyManager =
                this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            var networkType = mTelephonyManager.networkType
            var networkTypes: List<CellInfo>
            try {
                networkTypes = mTelephonyManager.allCellInfo
            } catch (e: Exception) {
                networkTypes = emptyList()
            }
            Log.e("Network ", "Network Type" + networkType)
            Log.e("Network ", "Network Info" + Gson().toJson(networkTypes).toString())
            Toast.makeText(this, "Network Type" + networkType, Toast.LENGTH_LONG).show()
            Toast.makeText(
                this,
                "Network Info" + Gson().toJson(networkTypes).toString(),
                Toast.LENGTH_LONG
            ).show()

        }

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_FINE_LOCATION) {
            // Received permission result for camera permission.
            Log.e("", "Received response for phone state permission request.")
            getData()
        } else {
            RequestPermission()
        }
    }
}