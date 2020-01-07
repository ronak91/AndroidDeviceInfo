package com.FiftyOneDegree.research.FinalDetails;

import android.Manifest;
import android.app.Activity;
import android.app.usage.StorageStatsManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;
import android.text.format.Formatter;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.FiftyOneDegree.research.R;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FinalDetailsActivity extends AppCompatActivity {


    TextView txt_fingerprint, txt_deviceRAM, txt_maxInternalStorage, txt_CPUMaximumFrequency, txt_bluetooth, txt_screenPixelsHeight, txt_screenPixelsWidth, txt_deviceResolution, txt_screenInchesDiagonal,
            txt_frontCameraMegaPixels, txt_backCameraMegaPixels, txt_frontCameraMegaPixelsOriginal, txt_backCameraMegaPixelsOriginal;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_functions);

        txt_fingerprint = findViewById(R.id.txt_fingerprint);
        txt_deviceRAM = findViewById(R.id.txt_deviceRAM);
        txt_maxInternalStorage = findViewById(R.id.txt_maxInternalStorage);
        txt_CPUMaximumFrequency = findViewById(R.id.txt_CPUMaximumFrequency);
        txt_bluetooth = findViewById(R.id.txt_bluetooth);
        txt_screenPixelsHeight = findViewById(R.id.txt_screenPixelsHeight);
        txt_screenPixelsWidth = findViewById(R.id.txt_screenPixelsWidth);
        txt_deviceResolution = findViewById(R.id.txt_deviceResolution);
        txt_screenInchesDiagonal = findViewById(R.id.txt_screenInchesDiagonal);
        txt_frontCameraMegaPixels = findViewById(R.id.txt_frontCameraMegaPixels);
        txt_backCameraMegaPixels = findViewById(R.id.txt_backCameraMegaPixels);
        txt_frontCameraMegaPixelsOriginal = findViewById(R.id.txt_frontCameraMegaPixelsOriginal);
        txt_backCameraMegaPixelsOriginal = findViewById(R.id.txt_backCameraMegaPixelsOriginal);

        // fingerprint
        txt_fingerprint.setText(isFingerPrintAvailable().toString());
        // deviceRAM
        txt_deviceRAM.setText(getTotalRAM());
        // maxInternalStorage
        txt_maxInternalStorage.setText(maxInternalStorage());
        // CPU Processor Frequency
        txt_CPUMaximumFrequency.setText(getCPUMaximumFrequency());
        // bluetooth
        txt_bluetooth.setText(isBluetoothAvailable().toString());
        // screenPixelsHeight
        txt_screenPixelsWidth.setText(getMyScreenWidthInPixel().toString());
        // screenPixelsHeight
        txt_screenPixelsHeight.setText(getMyScreenHeightInPixel().toString());
        // Device Resolution
        txt_deviceResolution.setText(screenResolution());
        // screenInchesDiagonal
        txt_screenInchesDiagonal.setText(getScreenInchesDiagonal());
        // frontCameraMegaPixels
        txt_frontCameraMegaPixels.setText(String.valueOf(getCameraResolutionInMp(this, Camera.CameraInfo.CAMERA_FACING_FRONT)));
        // frontCameraMegaPixelsOriginal
        txt_frontCameraMegaPixelsOriginal.setText(getFrontCameraResolutionInMp(this));
        // backCameraMegaPixels
        txt_backCameraMegaPixels.setText(String.valueOf(getCameraResolutionInMp(this, Camera.CameraInfo.CAMERA_FACING_BACK)));
        // backCameraMegaPixelsOriginal
        txt_backCameraMegaPixelsOriginal.setText(getBackCameraResolutionInMp(this));
    }


    // Finger Print
    public Boolean isFingerPrintAvailable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);
                // Check whether the device has a Fingerprint sensor.
                if (fingerprintManager == null || !fingerprintManager.isHardwareDetected()) {
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


    // @return RAM in MB
    public String getTotalRAM() {

        RandomAccessFile reader = null;
        String load = null;
        DecimalFormat twoDecimalForm = new DecimalFormat("#.##");
        double totRam = 0;
        String lastValue = "";
        try {
            reader = new RandomAccessFile("/proc/meminfo", "r");
            load = reader.readLine();

            // Get the Number value from the string
            Pattern p = Pattern.compile("(\\d+)");
            Matcher m = p.matcher(load);
            String value = "";
            while (m.find()) {
                value = m.group(1);
                // System.out.println("Ram : " + value);
            }
            reader.close();

            totRam = Double.parseDouble(value);
            // totRam = totRam / 1024;

            double ram = totRam / 1048576.0; // in gb
            if (ram > 0) {
                if (ram <= 0.5) {
                    return "512";
                } else {
                    Integer ramGB = Integer.parseInt(String.format("%.0f", Math.ceil(ram)));
                    return String.valueOf(ramGB*1024);
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            // Streams.close(reader);
        }
        return "";
    }

    //Check External Memory Available
    public static boolean externalMemoryAvailable() {
        return android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED);
    }

    //maxInternalStorage
    private String maxInternalStorage() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            StorageStatsManager storageStatsManager = null;
            storageStatsManager = (StorageStatsManager) getSystemService(Context.STORAGE_STATS_SERVICE);

            StorageManager storageManager = (StorageManager) getSystemService(Context.STORAGE_SERVICE);
            if (storageManager == null || storageStatsManager == null) {
                return "";
            }
            List<StorageVolume> storageVolumes = storageManager.getStorageVolumes();
            for (StorageVolume storageVolume : storageVolumes) {
                final String uuidStr = storageVolume.getUuid();
                try {

                    final UUID uuid = uuidStr == null ? StorageManager.UUID_DEFAULT : UUID.fromString(uuidStr);
                    return Formatter.formatShortFileSize(this, storageStatsManager.getTotalBytes(uuid));
                } catch (Exception e) {
                    // IGNORED
                    Log.e("Storage", "showStorageVolumes not working");
                    return "";
                }
            }
        } else {
            if (externalMemoryAvailable()) {
                File path = Environment.getExternalStorageDirectory();
                StatFs stat = new StatFs(path.getPath());
                long blockSize = stat.getBlockSizeLong();
                long totalBlocks = stat.getBlockCountLong();
                return formatSize(totalBlocks * blockSize);
            } else {
                return "";
            }
        }
        return "";
    }

    // CPU Processor Frequency
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
            cpuMaxFreqMHz = Double.valueOf(cpuMaxFreq) / 1000;
            cpuMaxFreqGhz = Double.valueOf(cpuMaxFreq) / 1000000;

            roundedFreq =  Math.round(cpuMaxFreqGhz *10)/ 10.0;
            Log.e("CPU", "Max Frequency: "+ roundedFreq);

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


    // bluetooth
    public Boolean isBluetoothAvailable() {
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

    @NonNull
    private static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    private Integer getMyScreenWidthInPixel() {
        DisplayMetrics displayMetrics = getDisplayMetrics(this);
        return displayMetrics.widthPixels;
    }

    // get Bottombar Height
    private Integer getNavigationBarHeight() {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
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

    private Integer getMyScreenHeightInPixel() {
        DisplayMetrics displayMetrics = getDisplayMetrics(this);
        return displayMetrics.heightPixels + getNavigationBarHeight();
    }

    private String screenResolution() {
        return getMyScreenWidthInPixel().toString() +" x "+ getMyScreenHeightInPixel().toString();
    }

    private String getScreenInchesDiagonal(){
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getRealSize(point);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        Integer width = point.x;
        Integer height = point.y;
        Double wi = width / Double.valueOf(displayMetrics.xdpi);
        Double hi = height / Double.valueOf(displayMetrics.ydpi);
        Double x = Math.pow(wi, 2.0);
        Double y = Math.pow(hi, 2.0);
        return String.valueOf((Math.round(Math.sqrt(x + y) * 10.0) / 10.0));
    }

    private String getFrontCameraResolutionInMp(Context context){
        return String.format("%.0f MP",Math.ceil(getCameraResolutionInMp(context, Camera.CameraInfo.CAMERA_FACING_FRONT)));
    }

    private String getBackCameraResolutionInMp(Context context){
        return String.format("%.0f MP",Math.ceil(getCameraResolutionInMp(context, Camera.CameraInfo.CAMERA_FACING_BACK)));
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


    //Format Size
    public static String formatSize(long size) {
        String suffix = null;
        if (size >= 1024) {
            suffix = "KB";
            size /= 1024;
            if (size >= 1024) {
                suffix = "MB";
                size /= 1024;
                if (size >= 1024) {
                    suffix = "GB";
                    size /= 1024;
                }
            }
        }
        StringBuilder resultBuffer = new StringBuilder(Long.toString(size));

        int commaOffset = resultBuffer.length() - 3;
        while (commaOffset > 0) {
            resultBuffer.insert(commaOffset, ',');
            commaOffset -= 3;
        }
        if (suffix != null) resultBuffer.append(suffix);
        return resultBuffer.toString();
    }

    private static boolean checkPermission(Context context, String permission) {
        int res = context.checkCallingOrSelfPermission(permission);
        return (res == PackageManager.PERMISSION_GRANTED);
    }

}
