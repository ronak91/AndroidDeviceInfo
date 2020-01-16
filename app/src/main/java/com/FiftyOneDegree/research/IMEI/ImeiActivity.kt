package com.FiftyOneDegree.research.imei


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.telephony.TelephonyManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.FiftyOneDegree.research.R
import com.google.android.gms.ads.identifier.AdvertisingIdClient
import kotlinx.android.synthetic.main.activity_imei.*


class ImeiActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imei)

        getImei()
        getAdvertisingId()
    }

    private fun getImei() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            txtImei.text = "restricted"
            return
        }
        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_PHONE_STATE),
                0
            )
            txtImei.text = "request denied"
            txtMeid.text = "request denied"
            return

        } else {
            getIMEIValue()
        }
    }



    @SuppressLint("MissingPermission")
    fun getIMEIValue() {

        val telephonyManager = applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        TelephonyManager.PHONE_TYPE_CDMA
        val phoneType: Int = telephonyManager.getPhoneType()
        when (phoneType) {
            TelephonyManager.PHONE_TYPE_CDMA -> {
                txtPhoneType.text = "CDMA"
            }
            TelephonyManager.PHONE_TYPE_GSM -> {
                txtPhoneType.text = "GSM"
            }
            TelephonyManager.PHONE_TYPE_NONE -> {
                txtPhoneType.text = "NONE"
            }
            TelephonyManager.PHONE_TYPE_SIP -> {
                txtPhoneType.text = "SIP"
            }
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            txtImei.text = "DeviceId:" + telephonyManager.deviceId
            txtMeid.text = "Meid: same as above"
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            txtImei.text = "DeviceId:" + telephonyManager.getDeviceId(0) +" "+telephonyManager.getDeviceId(1)+" "+telephonyManager.getDeviceId(2)+" "+ if(telephonyManager.getDeviceId(3)==null)"" else telephonyManager.getDeviceId(3)
            txtMeid.text = "Meid: same as above"

        } else {
            txtImei.text = "DeviceId:" + telephonyManager.deviceId + "\n Imei: " +
                    telephonyManager.getImei(0) + " " + telephonyManager.getImei(1) + " " + telephonyManager.getImei(2)
            txtMeid.text = "Meid " + telephonyManager.getMeid(0)+" "+telephonyManager.getMeid(1)
        }
    }

    fun getAdvertisingId() {
        val task: AsyncTask<Void, Void, String> = @SuppressLint("StaticFieldLeak")
        object : AsyncTask<Void, Void, String>() {

            override fun onPostExecute(token: String?) {
                Log.e("ImeiActivity", "Access token retrieved:$token")
                runOnUiThread {
                    txtAdvertisingId.text = token
                }
            }

            override fun doInBackground(vararg params: Void?): String? {
                try {
                    return AdvertisingIdClient.getAdvertisingIdInfo(applicationContext)?.id
                } catch (authEx: Exception) {
                    // The call is not ever expected to succeed
                    // assuming you have already verified that
                    // Google Play services is installed.
                    Log.e("ImeiActivity", authEx.toString())
                }
                return null
            }
        }
        task.execute()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            getImei()
    }
}
