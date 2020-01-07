package com.FiftyOneDegree.research.marketingName;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.FiftyOneDegree.research.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

public class marketingNameActivity extends AppCompatActivity {

    TextView txt_fulldevicename, txt_modelname1, txt_modelname2, txt_marketingname, txt_manufacturer, txt_deviceName, txt_codename, txt_processorName, txt_cpuInfo, txt_cpuInfo1, txt_hasBluetooth;

    Integer REQUEST_PHONE_STATE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketingname);

        txt_fulldevicename = findViewById(R.id.txt_fulldevicename);
        txt_modelname1 = findViewById(R.id.txt_modelname1);
        txt_modelname2 = findViewById(R.id.txt_modelname2);
        txt_marketingname = findViewById(R.id.txt_marketingname);
        txt_deviceName = findViewById(R.id.txt_deviceName);
        txt_codename = findViewById(R.id.txt_codename);
        txt_manufacturer = findViewById(R.id.txt_manufacturer);
        txt_processorName = findViewById(R.id.txt_processorName);
        txt_cpuInfo = findViewById(R.id.txt_cpuInfo);
        txt_cpuInfo1 = findViewById(R.id.txt_cpuInfo1);
        txt_hasBluetooth = findViewById(R.id.txt_hasBluetooth);

        setAllData();


    }



    public void setAllData(){

        DeviceName.with(this).request(new DeviceName.Callback() {

            @Override
            public void onFinished(DeviceName.DeviceInfo info, Exception error) {
                String manufacturer = info.manufacturer;  // "Samsung"
                String name = info.marketName;            // "Galaxy S8+"
                String model = info.model;                // "SM-G955W"
                String codename = info.codename;          // "dream2qltecan"
                String deviceName = info.getName();       // "Galaxy S8+"
                // FYI: We are on the UI thread.
                txt_manufacturer.setText(manufacturer);
                txt_marketingname.setText(name);
                txt_modelname1.setText(getModelName());
                txt_modelname2.setText(model);
                txt_deviceName.setText(codename);
                txt_codename.setText(deviceName);

            }
        });

        txt_fulldevicename.setText(getFullDeviceName());

        txt_processorName.setText(getDeviceInfo());

        txt_cpuInfo.setText(getCPU());

        txt_hasBluetooth.setText(hasBluetooth().toString());

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


    private String getModelName() {
        return DeviceName.getDeviceName();
    }


    public String getFullDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        String product = Build.PRODUCT;
        String brand = Build.BRAND;
        String device = Build.DEVICE;
        String hardware = Build.HARDWARE;
        String display = Build.DISPLAY;
        String bootloader = Build.BOOTLOADER;
        String Id = Build.ID;
        String Tags = Build.TAGS;
        String User = Build.USER;

      //  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this,Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {

                RequestPhoneStatePermission();

                return "";
            }
            else {

                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,        int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                String serialNo = "";
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    serialNo = Build.getSerial();
                }
                String RadioVersion = Build.getRadioVersion();

                if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
                    return capitalize("Model : " + model + " \nProduct : " + product + " \nBrand : " + brand + " \nDevice : " + device + " \nHardware : " + hardware + " \nDisplay : " +
                            display + " \nBootLoader : " + bootloader + " \nId : " + Id + " \nTags : " + Tags + " \nUser : " + User + " \nSerialNo : " + serialNo + " \nRadioVersion : " + RadioVersion);
                } else {
                    return "Manufacture : " + capitalize(manufacturer) + " \nModel : " + model + " \nProduct : " + product + " \nBrand : " + brand + " \nDevice : " + device + " \nHardware : " + hardware + " \nDisplay : " +
                            display + " \nBootLoader : " + bootloader + " \nId : " + Id + " \nTags : " + Tags + " \nUser : " + User + " \nSerialNo : " + serialNo + " \nRadioVersion : " + RadioVersion;
                }
            }

//        } else {
//            return "";
//        }

    }

    private void RequestPhoneStatePermission() {
        Log.e("", "PhoneState permission has NOT been granted. Requesting permission.");

        String[] permissions = new String[1];

        permissions[0] = Manifest.permission.READ_PHONE_STATE;


            // PhoneState permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_PHONE_STATE }, REQUEST_PHONE_STATE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PHONE_STATE)
        {
            // Received permission result for camera permission.
            Log.e("", "Received response for phone state permission request.");
            setAllData();

        }
        else
        {

        }
    }

    private String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    public String getDeviceInfo(){
        String ProcessorName = "";
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

            String[] allFields = output.split("\n");

            String processorlist = allFields[0];

            String[] processor = processorlist.split("\t");

            ProcessorName = processor[0];

            for(int i=0;i<allFields.length;i++) {
                Log.e("CPU_INFO : ", "CPU Processor : "+ProcessorName);
            }

            txt_cpuInfo1.setText(output);

        } catch (Exception ex) {
            ex.printStackTrace();
            ProcessorName = "";
        }

        return ProcessorName;
    }

    public Boolean hasBluetooth(){
        try {
            final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (mBluetoothAdapter == null) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e){
            return false;
        }
    }
}
