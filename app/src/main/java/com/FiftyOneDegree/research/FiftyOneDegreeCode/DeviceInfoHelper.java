package library.fiftyonedegrees.helper;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.usage.StorageStatsManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.hardware.fingerprint.FingerprintManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.support.annotation.NonNull;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import library.fiftyonedegrees.manager.SessionManager;
import library.fiftyonedegrees.model.DeviceInfo;
import library.fiftyonedegrees.util.DeviceInfoConstants;
import library.fiftyonedegrees.util.GPSTracker;

import static android.content.Context.FINGERPRINT_SERVICE;


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
        deviceInfo.setSimType(getSimType(context));
        deviceInfo.setImeiNumber(getIMEINumber(context));
        deviceInfo.setMeidNumber(getMEIDNumber(context));
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
        deviceInfo.setBackCameraMegaPixels(getBackCameraResolutionInMpOriginal(context));
        deviceInfo.setBackCameraMegaPixelsOriginal(getCameraResolutionInMp(context, Camera.CameraInfo.CAMERA_FACING_BACK));
        deviceInfo.setCameraTypes(getCameraTypes(context));
        deviceInfo.setFrontCameraMegaPixels(getFrontCameraResolutionInMpOriginal(context));
        deviceInfo.setFrontCameraMegaPixelsOriginal(getCameraResolutionInMp(context, Camera.CameraInfo.CAMERA_FACING_FRONT));
        deviceInfo.setHasCamera(hasCamera());
        deviceInfo.setHasNFC(hasNFC(context));
        deviceInfo.setHasFingerPrint(hasFingerPrint(context));
        deviceInfo.setHasBluetooth(hasBluetooth());
        deviceInfo.setSupportsPhoneCalls(hasSupportPhoneCalls(context));
        deviceInfo.setDeviceType(getDeviceType(context));
        deviceInfo.setIsMobile(isSmartPhone(context));
        deviceInfo.setIsSmallScreen(isSmallScreen(context));
        deviceInfo.setIsSmartPhone(isSmartPhone(context));
        deviceInfo.setIsTablet(isTablet(context));
        deviceInfo.setDeviceRAM(getRAM(context));
        deviceInfo.setMaxInternalStorage(getMaxInternalMemorySize(context));
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
        deviceInfo.setCpuDetails(getCPUDetails());
        deviceInfo.setCpuCores(getCPUCores());
        deviceInfo.setCpuDesigner(getCPUDesigner());
        deviceInfo.setSoC(getCPUDesigner());
        deviceInfo.setCpuMaximumFrequencyOriginal(getCPUMaximumFrequency());
        deviceInfo.setCpuMaximumFrequency(getCPUMaximumFrequencyOriginal());

        deviceInfo.setScreenInchesDiagonal(getScreenInchesDiagonalOriginal(context));
        deviceInfo.setScreenInchesDiagonalOriginal(getScreenInchesDiagonal(context));
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
        deviceInfo.setNetworkType(getNetworkType(context));

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

    private static String getSimType(Context context){
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        int phoneType = telephonyManager.getPhoneType();
        switch (phoneType) {
            case TelephonyManager.PHONE_TYPE_CDMA:
                return "CDMA";
            case TelephonyManager.PHONE_TYPE_GSM:
                return "GSM";
            case TelephonyManager.PHONE_TYPE_NONE:
                return "NONE";
            case TelephonyManager.PHONE_TYPE_SIP:
                return "SIP";
        }
        return "";
    }

    @SuppressLint("MissingPermission")
    private static String getIMEINumber(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return "";
        }

        String Simtype = getSimType(context);

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if(!Simtype.equals("NONE")) {
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    if(Simtype.equals("GSM")) {
                        return (telephonyManager.getDeviceId() == null ? "" : telephonyManager.getDeviceId());
                    } else { return  ""; }
                } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    if(Simtype.equals("GSM")) {
                        return (telephonyManager.getDeviceId(0) == null ? "" : telephonyManager.getDeviceId(0))+ "" + (telephonyManager.getDeviceId(1) == null ? "" : "," + telephonyManager.getDeviceId(1)) + "" + (telephonyManager.getDeviceId(2) == null ? "" : "," + telephonyManager.getDeviceId(2)) + "" + (telephonyManager.getDeviceId(3) == null ? "" : "," + telephonyManager.getDeviceId(3));
                    } else { return  ""; }

                } else {
                    if(Simtype.equals("GSM")) {
                        return (telephonyManager.getImei(0) == null ? "" : telephonyManager.getImei(0))+ "" + (telephonyManager.getImei(1) == null ? "" : "," + telephonyManager.getImei(1)) + "" + (telephonyManager.getImei(2) == null ? "" : "," + telephonyManager.getImei(2)) + "" + (telephonyManager.getImei(3) == null ? "" : "," + telephonyManager.getImei(3));
                    } else { return  ""; }
                }
            }
        } else {
            return "";
        }
        return "Unknown";
    }

    @SuppressLint("MissingPermission")
    private static String getMEIDNumber(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return "";
        }

        String Simtype = getSimType(context);

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if(!Simtype.equals("NONE")) {
            if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                    if(Simtype.equals("CDMA")) {
                        return (telephonyManager.getDeviceId() == null ? "" : telephonyManager.getDeviceId());
                    } else { return  ""; }
                } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                    if(Simtype.equals("CDMA")) {
                        return (telephonyManager.getDeviceId(0) == null ? "" : telephonyManager.getDeviceId(0)) + "" + (telephonyManager.getDeviceId(1) == null ? "" : "," + telephonyManager.getDeviceId(1)) + "" + (telephonyManager.getDeviceId(2) == null ? "" : "," + telephonyManager.getDeviceId(2)) + "" + (telephonyManager.getDeviceId(3) == null ? "" : "," + telephonyManager.getDeviceId(3));
                    } else { return  ""; }

                } else {
                    if(Simtype.equals("CDMA")) {
                        return (telephonyManager.getMeid(0) == null ? "" : telephonyManager.getMeid(0))+ "" + (telephonyManager.getMeid(1) == null ? "" : "," + telephonyManager.getMeid(1)) + "" + (telephonyManager.getMeid(2) == null ? "" : "," + telephonyManager.getMeid(2)) + "" + (telephonyManager.getMeid(3) == null ? "" : "," + telephonyManager.getMeid(3));
                    } else { return  ""; }

                }
            }
        } else {
            return "";
        }
        return "Unknown";
    }

    @SuppressLint("MissingPermission")
    private static String getTacCode(Context context) {

        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return "";
        }

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
        RandomAccessFile reader = null;
        String load = null;
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        double totRam = 0;
        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            load = reader.readLine();

            // Get the Number value from the string
            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(load);
            String value = "";
            while (m.find()) {
                value = m.group(1);
            }
            reader.close();

            totRam = Double.parseDouble(value);

            double ram = totRam / 1048576.0; // in gb
            if (ram > 0) {
                if (ram <= 0.5) {
                    return 512;
                } else {
                    Integer ramGB = Integer.parseInt(String.format("%.0f", Math.ceil(ram)));
                    return ramGB * 1024;
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
            return 0;
        } finally {

        }
        return 0;
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

    private static Float getFrontCameraResolutionInMpOriginal(Context context) {
        return (float) Math.ceil(getCameraResolutionInMp(context, Camera.CameraInfo.CAMERA_FACING_FRONT));
    }

    private static Float getBackCameraResolutionInMpOriginal(Context context) {
        return (float) Math.ceil(getCameraResolutionInMp(context, Camera.CameraInfo.CAMERA_FACING_BACK));
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

    private static boolean hasFingerPrint(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                FingerprintManager fingerprintManager = (FingerprintManager) context.getSystemService(FINGERPRINT_SERVICE);
                // Check whether the device has a Fingerprint sensor.
                if (fingerprintManager == null || fingerprintManager.isHardwareDetected()) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        } else {
            return false;
        }
    }

    private static boolean hasBluetooth() {
        try {
            final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
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
            return "SmartPhone";
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

    private static String getMaxInternalMemorySize(Context context) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            StorageStatsManager storageStatsManager = null;
            storageStatsManager = (StorageStatsManager) context.getSystemService(Context.STORAGE_STATS_SERVICE);

            StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
            if (storageManager == null || storageStatsManager == null) {
                return "";
            }
            List<StorageVolume> storageVolumes = storageManager.getStorageVolumes();
            for (StorageVolume storageVolume : storageVolumes) {
                final String uuidStr = storageVolume.getUuid();
                try {

                    final UUID uuid = uuidStr == null ? StorageManager.UUID_DEFAULT : UUID.fromString(uuidStr);
                    Double size = Double.valueOf(storageStatsManager.getTotalBytes(uuid));

                    if (size >= 1000) {
                        size /= 1000;
                        if (size >= 1000) {
                            size /= 1000;
//                          if (size >= 1000) {
                            size /= 1000;
//                          }
                        }
                    }
                    String deviceStorage = size.toString();

                    return deviceStorage;
                } catch (Exception e) {
                    // IGNORED
                    Log.e("Storage", "showStorageVolumes not working");
                    return "";
                }
            }
        } else {
            File path = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(path.getPath());
            long blockSize = stat.getBlockSizeLong();
            long totalBlocks = stat.getBlockCountLong();
            return formatSize((double) (totalBlocks * blockSize));
        }
        return "";
    }


    private static String formatSize(Double size) {
        String suffix = null;
        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
//                if (size >= 1024) {
                suffix = "GB";
                size /= 1024;
//                }
            }
        }
        StringBuilder resultBuffer = new StringBuilder(Double.toString(size));

//        int commaOffset = resultBuffer.length() - 3;
//        while (commaOffset > 0) {
//            resultBuffer.insert(commaOffset, ',');
//            commaOffset -= 3;
//        }
//        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
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

    private static String getCPUDetails() {
        if (new File("/proc/cpuinfo").exists()) {
            try {

                String[] DATA = {"/system/bin/cat", "/proc/cpuinfo"};
                ProcessBuilder processBuilder = new ProcessBuilder(DATA);
                Process process = processBuilder.start();
                InputStream inputStream = process.getInputStream();
                byte[] byteArry = new byte[1024];
                String output = "";
                while (inputStream.read(byteArry) != -1) {
                    output = output + new String(byteArry);
                }
                inputStream.close();
                return output;
            } catch (Exception ex) {
                ex.printStackTrace();
                return "";
            }
        }
        return "";
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

    private static String getCPUMaximumFrequencyOriginal() {
        String cpuMaxFreq = "";
        Double roundedFreq = 0.0;
        Double cpuMaxFreqMHz = 0.0;
        Double cpuMaxFreqGhz = 0.0;
        RandomAccessFile reader = null;
        try {
            reader = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq", "r");
            cpuMaxFreq = reader.readLine();
            reader.close();
            cpuMaxFreqMHz = Double.valueOf(cpuMaxFreq) / 1000;
            cpuMaxFreqGhz = Double.valueOf(cpuMaxFreq) / 1000000;

            roundedFreq = Math.round(cpuMaxFreqGhz * 10) / 10.0;

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
        return roundedFreq.toString();
    }

    private static String getCPUMaximumFrequency() {
        String cpuMaxFreq = "";
        Double roundedFreq = 0.0;
        Double cpuMaxFreqMHz = 0.0;
        Double cpuMaxFreqGhz = 0.0;
        RandomAccessFile reader = null;
        try {
            reader = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq", "r");
            cpuMaxFreq = reader.readLine();
            reader.close();
            cpuMaxFreqGhz = Double.valueOf(cpuMaxFreq) / 1000000;

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
        return cpuMaxFreqGhz.toString();
    }

    private static double getScreenInchesDiagonal(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(getScreenWidthInPixel(context) / dm.xdpi, 2);
        double y = Math.pow(getScreenHeightInPixel(context) / dm.ydpi, 2);
        return Math.sqrt(x + y);
    }

    private static double getScreenInchesDiagonalOriginal(Context context){
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        Integer width = point.x;
        Integer height = point.y;
        Double wi = width / Double.valueOf(displayMetrics.xdpi);
        Double hi = height / Double.valueOf(displayMetrics.ydpi);
        Double x = Math.pow(wi, 2.0);
        Double y = Math.pow(hi, 2.0);
        return Double.valueOf((Math.round(Math.sqrt(x + y) * 10.0) / 10.0));
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
        return displayMetrics.heightPixels + getNavigationBarHeight(context);
    }

    private static Integer getNavigationBarHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            DisplayMetrics metrics = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(metrics);
            Integer usableHeight = metrics.heightPixels;
            windowManager.getDefaultDisplay().getRealMetrics(metrics);
            Integer realHeight = metrics.heightPixels;
            return (realHeight > usableHeight) ? realHeight - usableHeight : 0;
        }
        return 0;
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
        try{
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
            boolean isNetwork = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
            boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

            if (isNetwork) {
                return "Network";
            } else if (isWifi) {
                return "Wifi";
            }
        } catch(Exception e){
            return "";
        }
        return "";
    }

    private static String getNetworkType(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isConnected())
            return ""; // not connected
        if (info.getType() == ConnectivityManager.TYPE_WIFI) {
            return "";
        } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int networkType = info.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return "GPRS";
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return "EDGE";
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return "CDMA";
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return "1xRTT";
                case TelephonyManager.NETWORK_TYPE_IDEN:     // api< 8: replace by 11
                    return "IDEN";
                case TelephonyManager.NETWORK_TYPE_GSM:      // api<25: replace by 16
                    return "GSM";
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
                case TelephonyManager.NETWORK_TYPE_EVDO_B:   // api< 9: replace by 12
                    return "EVDO_B";
                case TelephonyManager.NETWORK_TYPE_EHRPD:    // api<11: replace by 14
                    return "EHRPD";
                case TelephonyManager.NETWORK_TYPE_HSPAP:    // api<13: replace by 15
                    return "HSPAP";
                case TelephonyManager.NETWORK_TYPE_TD_SCDMA: // api<25: replace by 17
                    return "SCDMA";
                case TelephonyManager.NETWORK_TYPE_LTE:      // api<11: replace by 13
                    return "LTE";
                case TelephonyManager.NETWORK_TYPE_IWLAN:    // api<25: replace by 18
                    return "IWLAN";
                case 19: // LTE_CA
                    return "LTE_CA";
                case TelephonyManager.NETWORK_TYPE_NR:       // api<29: replace by 20
                    return "NR";
                default:
                    return "";
            }
        }
        return "Unknown";
    }
}

