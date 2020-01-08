package com.FiftyOneDegree.research.FiftyOneDegreeCode;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class DeviceInfoPermission {

    private DeviceInfoPermissionListener listener;
    private String[] permissions;
    private Context context;

    public DeviceInfoPermission(Context context, DeviceInfoPermissionListener listener, String[] permissions) {
        this.context = context;
        this.listener = listener;
        this.permissions = permissions;
    }

    protected void init() {
        if (listener == null) {
            throw new IllegalArgumentException("You must setPermissionListener()");
        } else if (DeviceInfoUtil.isEmpty(permissions)) {
            throw new IllegalArgumentException("You must setPermissions()");
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            listener.onPermissionGranted();
            return;
        }
    }

}
