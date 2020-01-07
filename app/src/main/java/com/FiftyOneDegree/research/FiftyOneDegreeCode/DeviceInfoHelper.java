package com.FiftyOneDegree.research.FiftyOneDegreeCode;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.regex.Pattern;


public class DeviceInfoHelper {

    /**
     * Preparing Device Information
     *
     * @param context
     * @return DeviceInfo
     */
    public static DeviceInfo prepareDeviceInfo(Context context) {
        DeviceInfo deviceInfo = new DeviceInfo();
        deviceInfo.setPackageName(getPackageName(context));
        deviceInfo.setImeiNumber(getIMEINumber(context));
        deviceInfo.setTacCode(getTacCode(context));
        deviceInfo.setManufacturer(getManufacturer());
        deviceInfo.setModel(getModel());
        deviceInfo.setVersion(getSdkInt());
        deviceInfo.setScreenResolution(getScreenResolution(context).toString());
        deviceInfo.setDpi(getDPI(context));
        deviceInfo.setSerialNumber(getSerial());
        deviceInfo.setBoard(getBoard());
        deviceInfo.setBrand(getBrand());
        deviceInfo.setDeviceName(getDevice());
        deviceInfo.setDisplayBuildId(getDisplay());
        deviceInfo.setFingerprint(getFingerprint());
        deviceInfo.setHardware(getHardware());
        deviceInfo.setHost(getHost());
        deviceInfo.setLableId(getId());
        deviceInfo.setProduct(getProduct());
        deviceInfo.setRadioFirmwareNumber(getRadioVersion());
        deviceInfo.setBuildTags(getTags());
        deviceInfo.setTimes(getTime());
        deviceInfo.setBuildType(getType());
        deviceInfo.setUser(getUser());
        deviceInfo.setUserAgentSystem(getProperty());
        deviceInfo.setUserAgentWebView(SessionManager.getInstance().getUserAgent());

        deviceInfo.setCurrentBatteryCharge(String.valueOf(getBatteryPercentage(context)));
        deviceInfo.setBackCameraMegaPixels(getCameraResolutionInMp(context, Camera.CameraInfo.CAMERA_FACING_BACK));
        deviceInfo.setCameraTypes(getCameraTypes(context));
        deviceInfo.setFrontCameraMegaPixels(getCameraResolutionInMp(context, Camera.CameraInfo.CAMERA_FACING_FRONT));
        deviceInfo.setHasCamera(hasCamera());
        deviceInfo.setHasNFC(hasNFC(context));
        deviceInfo.setSupportsPhoneCalls(hasSupportPhoneCalls(context));
        deviceInfo.setDeviceType(getDeviceType(context));
        deviceInfo.setIsMobile(isSmartPhone(context));
        deviceInfo.setIsSmallScreen(isSmallScreen(context));
        deviceInfo.setIsSmartPhone(isSmartPhone(context));
        deviceInfo.setIsTablet(isTablet(context));
        deviceInfo.setDeviceRAM(getRAM(context));
        deviceInfo.setMaxInternalStorage(getMaxInternalMemorySize());
        deviceInfo.setHardwareFamily(getHardwareFamily());
        deviceInfo.setHardwareModel(getHardwareModel());
        deviceInfo.setHardwareModelVariants(getHardwareModelVariants());
        deviceInfo.setHardwareName(getHardwareName());
        deviceInfo.setHardwareVendor(getHardwareVendor());
        deviceInfo.setOem(getOEM());
        deviceInfo.setNativeBrand(getNativeBrand());
        deviceInfo.setNativeDevice(getNativeDevice());
        deviceInfo.setNativeModel(getNativeModel());
        deviceInfo.setCpu(getCPU());
        deviceInfo.setCpuCores(getCPUCores());
        deviceInfo.setCpuDesigner(getCPUDesigner());
        deviceInfo.setSoC(getCPUDesigner());
        deviceInfo.setCpuMaximumFrequency(getCPUMaximumFrequency());

        deviceInfo.setScreenInchesDiagonal(getScreenInchesDiagonal(context));
        deviceInfo.setScreenInchesDiagonalRounded(getScreenInchesDiagonalRounded(context));
        deviceInfo.setScreenInchesHeight(getScreenInchesHeight(context));
        deviceInfo.setScreenInchesSquare(getScreenInchesSquare(context));
        deviceInfo.setScreenInchesWidth(getScreenInchesWidth(context));
        deviceInfo.setScreenMMDiagonal(getScreenMMDiagonal(context));
        deviceInfo.setScreenMMDiagonalRounded(getScreenMMDiagonalRounded(context));
        deviceInfo.setScreenMMHeight(getScreenMMHeight(context));
        deviceInfo.setScreenMMSquare(getScreenMMSquare(context));
        deviceInfo.setScreenMMWidth(getScreenMMWidth(context));
        deviceInfo.setScreenPixelsHeight(getScreenPixelsHeight(context));
        deviceInfo.setScreenPixelsWidth(getScreenPixelsWidth(context));
        deviceInfo.setSupportedSensorTypes(getSupportedSensorTypes(context));
        deviceInfo.setMaxNumberOfSIMCards(getMaxNumberOfSIMCards(context));
        deviceInfo.setOnPowerConsumption(getPowerConsumption(context));
        deviceInfo.setRefreshRate(getRefreshRate(context));
        deviceInfo.setPublicIP(getPublicIPAddress(context));
        deviceInfo.setMccNetwork(getMCCNetwork(context));
        deviceInfo.setMncNetwork(getMNCNetwork(context));
        deviceInfo.setMccSIM(getMCCSim(context));
        deviceInfo.setMncSIM(getMNCSim(context));
        getGPSLocation(context, deviceInfo);
        deviceInfo.setConnectivityType(getConnectivityType(context));
        if (getConnectivityType(context).equals("Network")) {
            deviceInfo.setNetworkType(getNetworkType(context));
        }
        return deviceInfo;
    }

    private static void getGPSLocation(Context context, DeviceInfo deviceInfo) {
        GPSTracker gpsTracker = new GPSTracker(context);
        if (gpsTracker.canGetLocation()) {
            deviceInfo.setLatitude(gpsTracker.getLatitude());
            deviceInfo.setLongitude(gpsTracker.getLongitude());
        }
    }

    private static String getProperty() {
        return System.getProperty(DeviceInfoConstants.HTTP_AGENT);
    }

    private static String getPackageName(Context context) {
        return context.getApplicationContext().getPackageName();
    }

    @SuppressLint("MissingPermission")
    private static String getIMEINumber(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
            return telephonyManager.getDeviceId();
        }
        return "Unknown";
    }

    @SuppressLint("MissingPermission")
    private static String getTacCode(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
            String deviceId = telephonyManager.getDeviceId();
            return deviceId.substring(0, Math.min(deviceId.length(), 8));
        }
        return "Unknown";
    }

    private static String getUser() {
        return Build.USER;
    }

    private static String getType() {
        return Build.TYPE;
    }

    private static long getTime() {
        return Build.TIME;
    }

    private static String getTags() {
        return Build.TAGS;
    }

    private static String getRadioVersion() {
        return Build.getRadioVersion();
    }

    private static String getProduct() {
        return Build.PRODUCT;
    }

    private static String getId() {
        return Build.ID;
    }

    private static String getHost() {
        return Build.HOST;
    }

    private static String getHardware() {
        return Build.HARDWARE;
    }

    private static String getFingerprint() {
        return Build.FINGERPRINT;
    }

    private static String getDisplay() {
        return Build.DISPLAY;
    }

    private static String getDevice() {
        return Build.DEVICE;
    }

    private static String getBrand() {
        return Build.BRAND;
    }

    private static String getBoard() {
        return Build.BOARD;
    }

    private static String getSerial() {
        return Build.SERIAL;
    }

    private static int getSdkInt() {
        return Build.VERSION.SDK_INT;
    }

    private static String getModel() {
        return Build.MODEL;
    }

    private static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    private static double getScreenSizeInInches(Context context, int width, int height) {
        DisplayMetrics dm = getDisplayMetrics(context);
        double x = Math.pow(width / dm.xdpi, 2);
        double y = Math.pow(height / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        return round(screenInches, 1);
    }

    private static double round(double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    private static Point getScreenResolution(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point screenResolution = new Point();

        if (Build.VERSION.SDK_INT < 14)
            throw new RuntimeException("Unsupported Android version.");
        display.getRealSize(screenResolution);

        return screenResolution;
    }

    private static int getBatteryPercentage(Context context) {
        IntentFilter iFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, iFilter);

        int level = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) : -1;
        int scale = batteryStatus != null ? batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1) : -1;

        float batteryPct = level / (float) scale;
        return (int) (batteryPct * 100);
    }

    private static long getRAM(Context context) {
        ActivityManager actManager = (ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memInfo = new ActivityManager.MemoryInfo();
        actManager.getMemoryInfo(memInfo);
        return memInfo.totalMem;
    }

    private static String getTelephonyOperator(Context context, String operatorType) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String operator = "";
        if (operatorType.equalsIgnoreCase(DeviceInfoConstants.NETWORK)) {
            operator = telephonyManager.getNetworkOperator();
        } else if (operatorType.equalsIgnoreCase(DeviceInfoConstants.SIM)) {
            operator = telephonyManager.getSimOperator();
        }
        return operator;
    }

    private static int getMCCNetwork(Context context) {
        String networkOperator = getTelephonyOperator(context, DeviceInfoConstants.NETWORK);
        if (!TextUtils.isEmpty(networkOperator)) {
            return Integer.parseInt(networkOperator.substring(0, 3));
        }
        return 0;
    }

    private static int getMNCNetwork(Context context) {
        String networkOperator = getTelephonyOperator(context, DeviceInfoConstants.NETWORK);
        if (!TextUtils.isEmpty(networkOperator)) {
            return Integer.parseInt(networkOperator.substring(3));
        }
        return 0;
    }

    private static int getMCCSim(Context context) {
        String simOperator = getTelephonyOperator(context, DeviceInfoConstants.SIM);
        if (!TextUtils.isEmpty(simOperator)) {
            return Integer.parseInt(simOperator.substring(0, 3));
        }
        return 0;
    }

    private static int getMNCSim(Context context) {
        String simOperator = getTelephonyOperator(context, DeviceInfoConstants.SIM);
        if (!TextUtils.isEmpty(simOperator)) {
            return Integer.parseInt(simOperator.substring(3));
        }
        return 0;
    }

    private static float getCameraResolutionInMp(Context context, int facing) {
        int noOfCameras = Camera.getNumberOfCameras();
        float maxResolution = -1;
        long pixelCount = -1;
        for (int index = 0; index < noOfCameras; index++) {
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(index, cameraInfo);

            if (cameraInfo.facing == facing && checkPermission(context, Manifest.permission.CAMERA)) {
                Camera camera = Camera.open(index);
                Camera.Parameters cameraParams = camera.getParameters();
                for (int cpIndex = 0; cpIndex < cameraParams.getSupportedPictureSizes().size(); cpIndex++) {
                    long pixelCountTemp = cameraParams.getSupportedPictureSizes().get(cpIndex).width *
                            cameraParams.getSupportedPictureSizes().get(cpIndex).height;
                    // Just changed index to cpIndex in this loop
                    if (pixelCountTemp > pixelCount) {
                        pixelCount = pixelCountTemp;
                        maxResolution = ((float) pixelCountTemp) / (1024000.0f);
                    }
                }

                camera.release();
            }
        }

        return maxResolution;
    }

    private static String getCameraTypes(Context context) {
        String cameraType = "";
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA_FRONT)) {
            cameraType += "FrontFacing";
        }
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            if(!cameraType.isEmpty()) cameraType+="|";
            cameraType += "BackFacing";
        }
        return cameraType;
    }

    private static boolean hasCamera() {
        return Camera.getNumberOfCameras() > 0;
    }

    private static boolean hasNFC(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_NFC);
    }

    private static boolean hasSupportPhoneCalls(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
    }

    private static boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }

    private static String getDeviceType(Context context) {
        int screenSize = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_NORMAL) {
            return "Mobile";
        } else if (screenSize == Configuration.SCREENLAYOUT_SIZE_LARGE) {
            return "Tablet";
        }
        return "";
    }

    private static boolean isSmallScreen(Context context) {
        int width = getScreenWidthInPixel(context);
        int height = getScreenHeightInPixel(context);
        double screenSize = getScreenSizeInInches(context, width, height);
        return screenSize < DeviceInfoConstants.SMALL_SCREEN_SIZE;
    }

    private static boolean isSmartPhone(Context context) {
        boolean isSmartPhone = false;
        int screenSize = context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
        switch (screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                isSmartPhone = true;
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                isSmartPhone = true;
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                isSmartPhone = true;
                break;
        }

        return isSmartPhone && hasSupportPhoneCalls(context);
    }

    private static String getMaxInternalMemorySize() {
        File path = Environment.getDataDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return String.valueOf(totalBlocks * blockSize);
    }

    private static String getHardwareFamily() {
        return Build.MODEL;
    }

    private static String getHardwareModel() {
        return Build.MODEL;
    }

    private static String getHardwareModelVariants() {
        return Build.MODEL;
    }

    private static String getHardwareName() {
        return Build.MODEL;
    }

    private static String getHardwareVendor() {
        return Build.MANUFACTURER;
    }

    private static String getOEM() {
        return Build.MANUFACTURER;
    }

    private static String getNativeBrand() {
        return Build.BRAND;
    }

    private static String getNativeDevice() {
        return Build.DEVICE;
    }

    private static String getNativeModel() {
        return Build.MODEL;
    }

    private static String getCPU() {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        FileReader fileReader = null;
        if (new File("/proc/cpuinfo").exists()) {
            try {
                fileReader = new FileReader(new File("/proc/cpuinfo"));
                br = new BufferedReader(fileReader);
                String aLine;
                while ((aLine = br.readLine()) != null) {
                    if (aLine.contains("model name")) {
                        sb.append(aLine.substring(aLine.indexOf(":") + 2, aLine.length()));
                        break;
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sb.toString();
    }

    private static int getCPUCores() {
        if (Build.VERSION.SDK_INT >= 17) {
            return Runtime.getRuntime().availableProcessors();
        } else {
            return getNumCoresOldPhones();
        }
    }

    private static int getNumCoresOldPhones() {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]+", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            //Default to return 1 core
            return 1;
        }
    }

    private static String getCPUDesigner() {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = null;
        FileReader fileReader = null;
        if (new File("/proc/cpuinfo").exists()) {
            try {
                fileReader = new FileReader(new File("/proc/cpuinfo"));
                br = new BufferedReader(fileReader);
                String aLine;
                while ((aLine = br.readLine()) != null) {
                    if (aLine.contains("Hardware")) {
                        sb.append(aLine.substring(aLine.indexOf(":"), aLine.length()));
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (fileReader != null) {
                    try {
                        fileReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return sb.toString().replace(" ", "").replace(":", "");
    }

    private static String getCPUMaximumFrequency() {
        String cpuMaxFreq = "";
        RandomAccessFile reader = null;
        try {
            reader = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq", "r");
            cpuMaxFreq = reader.readLine();
            reader.close();
        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return cpuMaxFreq;
    }

    private static double getScreenInchesDiagonal(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(getScreenWidthInPixel(context) / dm.xdpi, 2);
        double y = Math.pow(getScreenHeightInPixel(context) / dm.ydpi, 2);
        return Math.sqrt(x + y);
    }

    private static int getScreenInchesDiagonalRounded(Context context) {
        if (getScreenInchesDiagonal(context) > 0) {
            return (int) Math.round(getScreenInchesDiagonal(context));
        }
        return 0;
    }

    private static double getScreenInchesHeight(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);
        int height = dm.heightPixels;
        return ((double) height / (double) dm.ydpi);
    }

    private static double getScreenInchesWidth(Context context) {
        DisplayMetrics dm = getDisplayMetrics(context);
        int width = dm.widthPixels;
        return ((double) width / (double) dm.ydpi);
    }

    private static int getScreenInchesSquare(Context context) {
        return (int) Math.round(getScreenInchesWidth(context) * getScreenInchesHeight(context));
    }

    private static double getScreenMMDiagonal(Context context) {
        return getScreenInchesDiagonal(context) * DeviceInfoConstants.INCH_TO_MM;
    }

    private static int getScreenMMDiagonalRounded(Context context) {
        double screenMM = 0;
        if (getScreenInchesDiagonalRounded(context) > 0) {
            screenMM = getScreenInchesDiagonalRounded(context) * DeviceInfoConstants.INCH_TO_MM;
        }
        return (int) Math.round(screenMM);
    }

    private static double getScreenMMHeight(Context context) {
        return getScreenInchesHeight(context) * DeviceInfoConstants.INCH_TO_MM;
    }

    private static int getScreenMMSquare(Context context) {
        double mmSquare = getScreenInchesSquare(context) * DeviceInfoConstants.INCH_TO_MM;
        return (int) Math.round(mmSquare);
    }

    private static double getScreenMMWidth(Context context) {
        return getScreenInchesWidth(context) * DeviceInfoConstants.INCH_TO_MM;
    }

    private static int getScreenPixelsHeight(Context context) {
        return getScreenHeightInPixel(context);
    }

    private static int getScreenPixelsWidth(Context context) {
        return getScreenWidthInPixel(context);
    }

    private static String getSupportedSensorTypes(Context context) {
        String sensorTypes = "";
        SensorManager mgr = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        List<Sensor> sensors = mgr.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : sensors) {
            sensorTypes = sensorTypes + sensor.getName() + ",";
        }
        return sensorTypes.substring(0, sensorTypes.length() - 1);
    }

    private static int getMaxNumberOfSIMCards(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return tm.getPhoneCount();
            }
        }
        return -1;
    }

    private static int getPowerConsumption(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            BatteryManager mBatteryManager = (BatteryManager) context.getSystemService(Context.BATTERY_SERVICE);
            int level = mBatteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
            double powerConsumption = (getBatteryCapacity(context) * level * 0.01);
            return (int) Math.sqrt(powerConsumption);
        } else {
            return -1;
        }
    }

    private static double getBatteryCapacity(Context context) {
        Object mPowerProfile_ = null;
        final String POWER_PROFILE_CLASS = "com.android.internal.os.PowerProfile";
        try {
            mPowerProfile_ = Class.forName(POWER_PROFILE_CLASS)
                    .getConstructor(Context.class).newInstance(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            double batteryCapacity = (Double) Class
                    .forName(POWER_PROFILE_CLASS)
                    .getMethod("getAveragePower", java.lang.String.class)
                    .invoke(mPowerProfile_, "battery.capacity");
            return batteryCapacity;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static int getRefreshRate(Context context) {
        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        float refreshRating = display.getRefreshRate();
        return (int) refreshRating;
    }

    @SuppressLint("MissingPermission")
    private static String getPublicIPAddress(Context context) {
        WifiManager wifiManager = (WifiManager) context.getApplicationContext().getSystemService(context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return String.format("%d.%d.%d.%d", (ipAddress & 0xff), (ipAddress >> 8 & 0xff), (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
    }

    private static int getDPI(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        return (int) (displayMetrics.density * DeviceInfoConstants.DENSITY_MULTIPLIER);
    }

    private static int getScreenWidthInPixel(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        return displayMetrics.widthPixels;
    }

    private static int getScreenHeightInPixel(Context context) {
        DisplayMetrics displayMetrics = getDisplayMetrics(context);
        return displayMetrics.heightPixels;
    }

    @NonNull
    private static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    private static boolean checkPermission(Context context, String permission) {
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

    private static String getConnectivityType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        boolean isNetwork = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

        if (isNetwork) {
            return "Network";
        } else if (isWifi) {
            return "Wifi";
        }
        return "";
    }

    private static String getNetworkType(Context context) {
        TelephonyManager mTelephonyManager = (TelephonyManager)
                context.getSystemService(Context.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return "GPRS";
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return "EDGE";
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return "1xRTT";
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return "IDEN";
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return "UMTS";
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return "EVDO_0";
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return "EVDO_A";
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return "HSDPA";
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return "HSUPA";
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return "HSPA";
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return "EVDO_B";
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return "EHRPD";
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return "HSPAP";
            case TelephonyManager.NETWORK_TYPE_LTE:
                return "LTE";
            default:
                return "Unknown";
        }
    }
}
