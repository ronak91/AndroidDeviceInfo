package com.FiftyOneDegree.research.FiftyOneDegreeCode;

import java.util.ArrayList;

public interface DeviceInfoPermissionListener {

    void onPermissionGranted();

    void onPermissionDenied(ArrayList<String> notGrantedPermissions);
}
