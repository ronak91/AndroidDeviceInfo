package library.fiftyonedegrees.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.techgrains.application.TGApplication;
import com.techgrains.service.TGIResponseListener;
import com.techgrains.service.TGParams;
import com.techgrains.service.TGResponse;

import java.util.ArrayList;

import library.fiftyonedegrees.model.DeviceInfo;
import library.fiftyonedegrees.request.DeviceInfoRequest;
import library.fiftyonedegrees.request.DeviceInfoRequestFactory;
import library.fiftyonedegrees.util.DeviceInfoConstants;
import library.fiftyonedegrees.util.DeviceInfoUtil;

public class FiftyOneDegrees {

    /**
     * Created intent for device Info
     *
     * @param context
     */
    public static void init(final Context context) {
        TGApplication.setContext(context.getApplicationContext());
        DeviceInfoPermissionListener permissionListener = new DeviceInfoPermissionListener() {
            @Override
            public void onPermissionGranted() {
                try {
                    Boolean isDataUploaded = DeviceInfoUtil.getPreferenceValue(context, DeviceInfoConstants.IS_DATA_UPLOADED);
                    Boolean isPermissionAcceptedBefore = DeviceInfoUtil.getPreferenceValue(context, DeviceInfoConstants.IS_PERMISSION_ACCEPT);
                    Log.e("51Degree","JSON Uploaded : "+isDataUploaded.toString());
                    if (!isDataUploaded)
                        updateDeviceInfo(context);
                    else if (!isPermissionAcceptedBefore)
                        updateDeviceInfo(context);

                    DeviceInfoUtil.putPreferenceValue(context, DeviceInfoConstants.IS_PERMISSION_ACCEPT, true);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPermissionDenied(ArrayList<String> notGrantedPermissions) {
                try {
                    Boolean isDataUploaded = DeviceInfoUtil.getPreferenceValue(context, DeviceInfoConstants.IS_DATA_UPLOADED);
                    Log.e("51Degree","JSON Uploaded : "+isDataUploaded.toString());
                    if (!isDataUploaded)
                        updateDeviceInfo(context);

                    DeviceInfoUtil.putPreferenceValue(context, DeviceInfoConstants.IS_PERMISSION_ACCEPT, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        new DeviceInfoPermission(context, permissionListener, (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)?DeviceInfoConstants.PERMISSIONS_ABOUVE_Q:DeviceInfoConstants.PERMISSIONS).init();
    }

    private static void updateDeviceInfo(Context context) {
        DeviceInfo deviceInfo = DeviceInfoHelper.prepareDeviceInfo(context);
        Log.e("51Degree","JSON : "+new Gson().toJson(deviceInfo));
        String requestBody = DeviceInfoUtil.convertToJSON(deviceInfo);
        try {
            String encryptedRequestBody = DeviceInfoUtil.encrypt(requestBody);
            if (isNetworkConnected(context)) {
                Log.e("51Degree","Api Call Started");
                callDeviceInfoService(context, encryptedRequestBody);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void callDeviceInfoService(final Context context, String requestBody) {
        final TGParams tgParams = new TGParams();
        tgParams.putParam(DeviceInfoConstants.DATA, requestBody);
        TGIResponseListener<DeviceInfo> listener = new TGIResponseListener<DeviceInfo>() {
            @Override
            public void onSuccessMainThread(DeviceInfo deviceInfo) {
            }

            @Override
            public void onSuccessBackgroundThread(DeviceInfo deviceInfo) {
                try {
                    Log.e("51Degree","Api Call Success");
                    DeviceInfoUtil.putPreferenceValue(context, DeviceInfoConstants.TIME_STAMP, DeviceInfoUtil.getTimeStamp());
                    DeviceInfoUtil.putPreferenceValue(context, DeviceInfoConstants.IS_DATA_UPLOADED, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(TGResponse tgResponse) {
                try {
                    Log.e("51Degree","Api Call Failed.  Response Status code"+ tgResponse.getStatusCode());
                    DeviceInfoUtil.putPreferenceValue(context, DeviceInfoConstants.TIME_STAMP, DeviceInfoUtil.getTimeStamp());
                    DeviceInfoUtil.putPreferenceValue(context, DeviceInfoConstants.IS_DATA_UPLOADED, false);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        DeviceInfoRequest deviceInfoRequest = DeviceInfoRequestFactory.infoRequest(listener, tgParams);
        DeviceInfoRequestFactory.performRequest(deviceInfoRequest);
    }

    private static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
