package library.fiftyonedegrees.helper;

import java.util.ArrayList;

public interface DeviceInfoPermissionListener {

  void onPermissionGranted();

  void onPermissionDenied(ArrayList<String> notGrantedPermissions);
}
