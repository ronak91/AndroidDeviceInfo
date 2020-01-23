package library.fiftyonedegrees.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import library.fiftyonedegrees.activity.MainActivity;
import library.fiftyonedegrees.util.DeviceInfoConstants;
import library.fiftyonedegrees.util.DeviceInfoUtil;

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
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(DeviceInfoConstants.EXTRA_PERMISSIONS, permissions);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_USER_ACTION);
        MainActivity.startActivity(context, intent, listener);
        DeviceInfoPermissionHelper.setFirstRequest(context, permissions);
    }

}