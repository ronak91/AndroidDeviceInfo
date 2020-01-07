package com.FiftyOneDegree.research.IMEI;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.FiftyOneDegree.research.R;

public class DeviceInfoActivity extends AppCompatActivity {
    public static final int REQUEST_CODE_PHONE_STATE_READ = 100;
    private int checkedPermission = PackageManager.PERMISSION_DENIED;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deviceinfo);

        checkedPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (Build.VERSION.SDK_INT >= 23 && checkedPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else
            checkedPermission = PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {
        Toast.makeText(DeviceInfoActivity.this, "Requesting permission", Toast.LENGTH_SHORT).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            this.requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_CODE_PHONE_STATE_READ);
        }
    }

    /**
     * Method will be use to show device info
     *
     * @param v
     */
    public void showDeviceInfo(View v) {
        TelephonyManager manager = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
        AlertDialog.Builder dBuilder = new AlertDialog.Builder(this);
        StringBuilder stringBuilder = new StringBuilder();

        if (checkedPermission != PackageManager.PERMISSION_DENIED) {
            dBuilder.setTitle("Device Info");
            // Name of underlying board like "GoldFish"
            stringBuilder.append("Board : " + Build.BOARD + "\n");
            // The consumer-visible brand with which the product/hardware will be associated, if any.
            stringBuilder.append("Brand : " + Build.BRAND + "\n");
            // The name of the industrial design.
            stringBuilder.append("DEVICE : " + Build.DEVICE + "\n");
            // A build ID string meant for displaying to the user
            stringBuilder.append("Display : " + Build.DISPLAY + "\n");
            // A string that uniquely identifies this build.
            stringBuilder.append("FINGERPRINT : " + Build.FINGERPRINT + "\n");
            // The name of the hardware
            stringBuilder.append("HARDWARE : " + Build.HARDWARE + "\n");
            // either a changelist number, or a label like "M4-rc20".
            stringBuilder.append("ID : " + Build.ID + "\n");
            // The manufacturer of the product/hardware.
            stringBuilder.append("Manufacturer : " + Build.MANUFACTURER + "\n");
            // The end-user-visible name for the end product.
            stringBuilder.append("MODEL : " + Build.MODEL + "\n");
            // A hardware serial number, if available.
            stringBuilder.append("SERIAL : " + Build.SERIAL + "\n");
            // The user-visible SDK version of the framework; its possible values are defined in Build.VERSION_CODES.
            stringBuilder.append("VERSION : " + Build.VERSION.SDK_INT + "\n");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    Activity#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for Activity#requestPermissions for more details.
                    return;
                }
            }

            // Returns the phone number string for line 1, for example, the MSISDN for a GSM phone.
            // Return null if it is unavailable
            stringBuilder.append("Line 1 : " + manager.getLine1Number() + "\n");
            // Returns the unique device ID, for example, the IMEI for GSM and the MEID or ESN for CDMA phones.
            // Return null if device ID is not available.
            stringBuilder.append("Device ID/IMEI : " + manager.getDeviceId() + "\n");
            // Returns the unique subscriber ID, for example,
            // the IMSI for a GSM phone. Return null if it is unavailable.
            stringBuilder.append("IMSI : " + manager.getSubscriberId());
        } else {
            dBuilder.setTitle("Permission denied");
            stringBuilder.append("Can't access device info !");
        }
        dBuilder.setMessage(stringBuilder);
        dBuilder.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case REQUEST_CODE_PHONE_STATE_READ:
                if (grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED ) {
                    checkedPermission = PackageManager.PERMISSION_GRANTED;
                }
                break;

        }
    }
}
