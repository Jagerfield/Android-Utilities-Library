package jagerfield.utilities.lib.PermissionsUtil.Results;

import java.util.ArrayList;
import java.util.HashMap;

public interface IGetPermissionResult
{
    boolean isGranted();
    ArrayList<String> getGrantedPermissionsList();
    ArrayList<String> getUserDeniedPermissionsList();
    ArrayList<String> getNeverAskAgainPermissionsList();
    ArrayList<String> getMissingInManifest_ForSdkBelowM();
    String getRequestedPermissionStatus(String permissionName);
    HashMap<String, String> getRequestedPermissionsAndStatusMap();

}
