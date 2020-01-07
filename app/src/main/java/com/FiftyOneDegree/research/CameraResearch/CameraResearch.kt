package com.FiftyOneDegree.research.CameraResearch

import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Camera
import android.hardware.Camera.CameraInfo
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.FiftyOneDegree.research.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.camera_research.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.absoluteValue
import android.hardware.camera2.CameraCharacteristics as CameraCharacteristics1


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class CameraResearch : AppCompatActivity() {

    val PERMISSION_CAMERA = 1
    @JvmField
    val REQUEST_CAMERA_PERMISSION = 2
    var selectedQR = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.camera_research)

        if (checkCameraPermission()) {

            hasPermissionContinew()

            Log.e("", "Highest resolution For Back Camera : " +  String.format("%f MP",Math.ceil(getBackCameraResolutionInMp())))
            Log.e("", "Highest resolution For Front Camera : " +  String.format("%f MP",Math.ceil(getFrontCameraResolutionInMp())))

        }

        if (hasCamera()) {

            try {
                scanresult.text = getCameraTypes()

            } catch (e: Exception) {
                Log.e("Camera test", "Error : " + e.toString())
            }
        }

        getCameraDetails()

    }

    // check camera
    private fun hasCamera(): Boolean {
        return Camera.getNumberOfCameras() > 0
    }

    // Return Cameras Front and Back
    private fun getCameraTypes(): String? {
        var cameraType = ""
        if (this.packageManager.hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FRONT
            )
        ) {
            cameraType += "FrontFacing"
        }
        if (this.packageManager.hasSystemFeature(
                PackageManager.FEATURE_CAMERA
            )
        ) {
            if (!cameraType.isEmpty()) cameraType += "|"
            cameraType += "BackFacing"
        }
        return cameraType
    }


    fun getCameraDetails() {
        var camera = Camera.open(0) // For Back Camera

        val params = camera.parameters
        val sizes: List<*> = params.supportedPictureSizes
        var result: Camera.Size? = null

        val arrayListForWidth = ArrayList<Int>()
        val arrayListForHeight = ArrayList<Int>()

        for (i in sizes.indices) {
            result = sizes[i] as Camera.Size?
            result?.width?.let { arrayListForWidth.add(it) }
            result?.height?.let { arrayListForHeight.add(it) }
            Log.e("PictureSize","Supported Size: " + result?.width + "height : " + result?.height)
            println("BACK PictureSize Supported Size: " + result?.width + "height : " + result?.height)
        }
        if (arrayListForWidth.size !== 0 && arrayListForHeight.size !== 0) {
            Log.e("Camera","Back max W :" + Collections.max(arrayListForWidth)) // Gives Maximum Width
            Log.e("Camera","Back max H :" + Collections.max(arrayListForHeight)) // Gives Maximum Height


            var BackMP = (Collections.max(arrayListForWidth) * Collections.max(arrayListForHeight)) / 1024000.0
            Log.e("Camera", "Back Megapixel :"+BackMP);
            Log.e("Camera","Back Megapixel :" + String.format("%.0f MP",Math.ceil(BackMP)))

        }
        camera.release()

        arrayListForWidth.clear()
        arrayListForHeight.clear()

        camera = Camera.open(1) //  For Front Camera

        val params1 = camera.parameters
        val sizes1: List<*> = params1.supportedPictureSizes
        var result1: Camera.Size? = null
        for (i in sizes1.indices) {
            result1 = sizes1[i] as Camera.Size?
            result1?.width?.let { arrayListForWidth.add(it) }
            result1?.height?.let { arrayListForHeight.add(it) }
            Log.e("PictureSize","Supported Size: " + result1?.width + "height : " + result1?.height)
            Log.e(   "Camera","FRONT PictureSize Supported Size: " + result1?.width + "height : " + result1?.height)
        }
        if (arrayListForWidth.size !== 0 && arrayListForHeight.size !== 0) {
            Log.e("Camera", "FRONT max W :" + Collections.max(arrayListForWidth))
            Log.e("Camera", "FRONT max H :" + Collections.max(arrayListForHeight))

            var FrontMP = (Collections.max(arrayListForWidth) * Collections.max(arrayListForHeight)) / 1024000.0
            Log.e("Camera", "FRONT Megapixels :"+FrontMP);
            Log.e( "Camera","FRONT Megapixel :" + String.format("%.0f MP",Math.ceil(FrontMP)))
        }

        camera.release()
    }



    private fun hasPermissionContinew() {
        if (checkCameraHardware(this)) {
            if (spinner != null) {
                val adapter = ArrayAdapter(
                    this,
                    android.R.layout.simple_spinner_item, getCameraIdList()
                )
                spinner.adapter = adapter

            }

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    selectedQR = getCameraIdList()[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action

                }
            }
        } else {
            AlertDialog.Builder(this)
                .setMessage("Device has no camera.")
                .setPositiveButton(android.R.string.ok) { _, _ -> finish() }
                .create()
        }

    }

    fun getBackCameraResolutionInMp(): Double {
        val noOfCameras: Int = Camera.getNumberOfCameras()
        var maxResolution = 0.0
        var pixelCount: Int = -1
        for (i in 0 until noOfCameras) {
            val cameraInfo = CameraInfo()
            Camera.getCameraInfo(i, cameraInfo)
            if (cameraInfo.facing === CameraInfo.CAMERA_FACING_BACK) {
                val camera: Camera = Camera.open(i)
                val cameraParams: Camera.Parameters = camera.getParameters()
                for (j in 0 until cameraParams.getSupportedPictureSizes().size) {
                    val pixelCountTemp: Int =
                        cameraParams.getSupportedPictureSizes().get(j).width * cameraParams.getSupportedPictureSizes().get(j).height // Just changed i to j in this loop
                    if (pixelCountTemp > pixelCount) {
                        pixelCount = pixelCountTemp
                        maxResolution = pixelCountTemp.toFloat() / 1024000.0
                    }
                }
                camera.release()
            }
        }
        return maxResolution
    }

    fun getFrontCameraResolutionInMp(): Double {
        val noOfCameras: Int = Camera.getNumberOfCameras()
        var maxResolution = 0.0
        var pixelCount: Int = -1
        for (i in 0 until noOfCameras) {
            val cameraInfo = CameraInfo()
            Camera.getCameraInfo(i, cameraInfo)
            if (cameraInfo.facing === CameraInfo.CAMERA_FACING_FRONT) {
                val camera: Camera = Camera.open(i)
                val cameraParams: Camera.Parameters = camera.getParameters()
                for (j in 0 until cameraParams.getSupportedPictureSizes().size) {
                    val pixelCountTemp: Int =
                        cameraParams.getSupportedPictureSizes().get(j).width * cameraParams.getSupportedPictureSizes().get(
                            j
                        ).height // Just changed i to j in this loop
                    if (pixelCountTemp > pixelCount) {
                        pixelCount = pixelCountTemp
                        maxResolution = pixelCountTemp.toFloat() / 1024000.0
                    }
                }
                camera.release()
            }
        }
        return maxResolution
    }



    /** Check if this device has a camera */
    private fun checkCameraHardware(context: Context): Boolean {
        return context.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)
    }

    private fun getCameraIdList(): List<String> {
        val manager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        val list: MutableList<String> = ArrayList()
        list.addAll(manager.cameraIdList)
        return list
    }

    fun checkCameraPermission(): Boolean {
        if (ActivityCompat.checkSelfPermission(applicationContext, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                PERMISSION_CAMERA
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.size != 1 || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                AlertDialog.Builder(this)
                    .setMessage("Camera Not permission granted.")
                    .setPositiveButton(android.R.string.ok) { _, _ -> finish() }
                    .create()
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
            hasPermissionContinew()
        }
    }


    /**
     * Helper class used to encapsulate a logical camera and two underlying
     * physical cameras
     */
//    data class DualCamera(val logicalId: String, val physicalId1: String, val physicalId2: String)
//
//    @SuppressLint("NewApi")
//    fun findDualCameras(manager: CameraManager, facing: Int? = null): Array<DualCamera> {
//        val dualCameras = ArrayList<DualCamera>()
//
//        val manager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
//        for (id in manager.cameraIdList)
//            Log.e("test", id)
//
//
//        try {
//            for (cameraId in manager.cameraIdList) {
//
//                var characteristics = manager.getCameraCharacteristics(cameraId);
//
//                var logCamera =
//                    CameraCharacteristics1.REQUEST_AVAILABLE_CAPABILITIES_LOGICAL_MULTI_CAMERA
//
////                if (characteristics.get(logCamera))
////                {
//                Log.e(
//                    "Camera test",
//                    String.format(
//                        "Camera %s is a logical camera backed by two or more physical cameras",
//                        cameraId
//                    )
//                )
//                try {
//                    var physicalIds = characteristics.getPhysicalCameraIds()
//
//                    for (physicalId in physicalIds) {
//                        Log.e(
//                            "Camera test",
//                            String.format("Physical camera found : %s", physicalId)
//                        )
//                    }
//                } catch (e: Exception) {
//                    Log.e("Camera test", "Error : " + e.toString())
//                }
//
////                } else {
////                    Log.e("Camera test", String.format("Camera %s is a simple camera", cameraId))
////                }
//
//
//                val chars = manager.getCameraCharacteristics(cameraId)
//                // Do something with the characteristics
//
//                // Does the camera have a forwards facing lens?
//                val facing = chars[CameraCharacteristics1.LENS_FACING]
//                Log.e("Camera test", "Camera Facing : " + facing.toString())
//
//                val multi_sensor =
//                    chars[CameraCharacteristics1.LOGICAL_MULTI_CAMERA_SENSOR_SYNC_TYPE]
//                Log.e("Camera test", "Camera Facing : " + multi_sensor.toString())
//
//                // manager.openCamera()
//
//                // var physical_cameras = CameraCharacteristics.getPhysicalCameraIds()
//
//
//            }
//        } catch (e: Exception) {
//            e.printStackTrace();
//        }
//
//        // Iterate over all the available camera characteristics
//        manager.cameraIdList.map {
//            Pair(manager.getCameraCharacteristics(it), it)
//        }.filter {
//            // Filter by cameras facing the requested direction
//            facing == null || it.first.get(CameraCharacteristics1.LENS_FACING) == facing
//        }.filter {
//            // Filter by logical cameras
//            it.first.get(CameraCharacteristics1.REQUEST_AVAILABLE_CAPABILITIES)!!.contains(
//                CameraCharacteristics1.REQUEST_AVAILABLE_CAPABILITIES_LOGICAL_MULTI_CAMERA
//            )
//        }.forEach {
//            // All possible pairs from the list of physical cameras are valid results
//            // NOTE: There could be N physical cameras as part of a logical camera grouping
//            val physicalCameras = it.first.physicalCameraIds.toTypedArray()
//            Log.e("Physical Cameras : ", Gson().toJson(physicalCameras).toString())
//            for (idx1 in 0 until physicalCameras.size) {
//                for (idx2 in (idx1 + 1) until physicalCameras.size) {
//                    dualCameras.add(
//                        DualCamera(
//                            it.second,
//                            physicalCameras[idx1],
//                            physicalCameras[idx2]
//                        )
//                    )
//                }
//            }
//        }
//
//        return dualCameras.toTypedArray()
//    }



}
