package com.FiftyOneDegree.research.FiftyOneDegreeCode;

import android.Manifest;

import java.util.concurrent.TimeUnit;

public class DeviceInfoConstants {
    public static final String PREFS_NAME = "FiftyOneDegrees";
    public static final double SMALL_SCREEN_SIZE = 2.5;
    public static final double INCH_TO_MM = 25.4;
    public static final String IS_DATA_UPLOADED = "is_data_uploaded";
    public static float DENSITY_MULTIPLIER = 160f;
    public static final int REQUEST_ATTEMPTS = 0;
    public static final int REQUEST_TIMEOUT = (int) TimeUnit.SECONDS.toMillis(30L);
    public static final String HTTP_AGENT = "http.agent";
    public static final String NETWORK = "network";
    public static final String DATA = "data";
    public static final String SIM = "sim";
    public static final String[] PERMISSIONS = {
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };
    public static final String EXTRA_PACKAGE_NAME = "package_name";
    public static final String EXTRA_PERMISSIONS = "permissions";
    public static final int REQ_CODE_PERMISSION_REQUEST = 10;

    //Don't change these values
    public static final String ENCRYPTION_KEY = "A6ECC971-45E5-4FC5-9581-759E7EE02ECE";
    public static final String TIME_STAMP = "time_stamp";
    public static final String IS_PERMISSION_ACCEPT = "is_permission_accept";
}