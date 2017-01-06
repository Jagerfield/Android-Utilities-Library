package jagerfield.utilities.lib.PermissionsUtil.Results;

import java.util.ArrayList;
import java.util.HashMap;

public class PermissionsResults implements IGetPermissionResult
{
    private boolean permissionStatus = false;
    private ArrayList<String> neverAskAgainPermissionsList = new ArrayList<>();
    private ArrayList<String> userDeniedPermissionsList = new ArrayList<>();
    private ArrayList<String> grantedPermissionsList = new ArrayList<>();
    private ArrayList<String> missingInManifest_SdkLessThanM = new ArrayList<>();
    private HashMap<String, String> allPermissionsMap = new HashMap<>();

    public void addItemNeverAskAgainList(String item)
    {
        if (item != null && !item.isEmpty()) {
            neverAskAgainPermissionsList.add(item);
        }
    }

    @Override
    public ArrayList<String> getUserDeniedPermissionsList() {
        return userDeniedPermissionsList;
    }

    public void addItemUserDeniedPermissionsList(String item) {
        if (item != null && !item.isEmpty()) {
            userDeniedPermissionsList.add(item);
        }
    }

    @Override
    public boolean isGranted() {
        return permissionStatus;
    }

    public void setPermissionStatus(boolean status) {
        permissionStatus = status;
    }

    @Override
    public ArrayList<String> getGrantedPermissionsList() {
        return grantedPermissionsList;
    }

    @Override
    public ArrayList<String> getNeverAskAgainPermissionsList() {
        return neverAskAgainPermissionsList;
    }

    @Override
    public ArrayList<String> getMissingInManifest_ForSdkBelowM() {
        return missingInManifest_SdkLessThanM;
    }

    @Override
    public String getRequestedPermissionStatus(String permissionName)
    {
        String result = allPermissionsMap.get(permissionName);
        return result;
    }

    public void addItemGrantedPermissionsList(String item)
    {
        if (item != null && !item.isEmpty()) {
            grantedPermissionsList.add(item);
        }
    }

    @Override
    public HashMap<String, String> getRequestedPermissionsAndStatusMap()
    {
        return allPermissionsMap;
    }

    public void addItemAllPermissionsMap(String name, String type)
    {
        allPermissionsMap.put(name, type);
    }

    public void addItemMissingInManifest_ForSdkBelowM(String item)
    {
        if (item != null && !item.isEmpty()) {
            missingInManifest_SdkLessThanM.add(item);
        }
    }
}