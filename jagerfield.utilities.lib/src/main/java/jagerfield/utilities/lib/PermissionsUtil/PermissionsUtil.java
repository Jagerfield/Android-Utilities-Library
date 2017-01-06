package jagerfield.utilities.lib.PermissionsUtil;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import java.util.ArrayList;
import jagerfield.utilities.lib.C;
import jagerfield.utilities.lib.PermissionsUtil.Results.IGetPermissionResult;
import jagerfield.utilities.lib.PermissionsUtil.Results.PermissionsResults;

public class PermissionsUtil
{
    private Activity activity;
    private final int PERMISSIONS_REQ = 1009989;

    public PermissionsUtil(Activity activity)
    {
        this.activity = activity;
    }

    public static PermissionsUtil newInstance(Activity activity)
    {
        PermissionsUtil obj = new PermissionsUtil(activity);
        return obj;
    }

    public int getPermissionsReqCodeId() {
        return PERMISSIONS_REQ;
    }

    public synchronized void requestPermissions(String permissionsItem)
    {
        if (permissionsItem==null || permissionsItem.isEmpty() )
        {
            Log.i(C.TAG_LIB, "The given permission item is incorrect");
            return;
        }

        requestPermissions(new String[]{permissionsItem});
    }

    public synchronized void requestPermissions(String[] permissionsArray)
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            return;
        }

        if (permissionsArray==null || permissionsArray.length==0)
        {
            Log.i(C.TAG_LIB, "The given permission request is incorrect");
            return;
        }

        ArrayList<String> firstTimeAndNeverShowAgainPermissionsList = new ArrayList<>();
        ArrayList<String> previouslyDeniedPermissionsList = new ArrayList<>();
        ArrayList<String> missingPermissionsList = new ArrayList<>();

        for (int i = 0; i < permissionsArray.length; i++)
        {
            if (activity.checkSelfPermission(permissionsArray[i]) == PackageManager.PERMISSION_DENIED)
            {
                // if permission has been denied before
                if (activity.shouldShowRequestPermissionRationale(permissionsArray[i]))
                {
                    previouslyDeniedPermissionsList.add(permissionsArray[i]);
                }
                else //First time permissionsArray + permission has been denied before and box checked for not showing them again.
                {
                    firstTimeAndNeverShowAgainPermissionsList.add(permissionsArray[i]);
                }
            }
        }

        missingPermissionsList.addAll(firstTimeAndNeverShowAgainPermissionsList);
        missingPermissionsList.addAll(previouslyDeniedPermissionsList);

        if (!missingPermissionsList.isEmpty())
        {
            String[] missingPermissionsArray = missingPermissionsList.toArray(new String[missingPermissionsList.size()]);
            if (missingPermissionsArray.length > 0)
            {
                activity.requestPermissions(missingPermissionsArray, PERMISSIONS_REQ);
            }
        }
    }

    public synchronized final IGetPermissionResult isPermissionGranted(String permission)
    {
        IGetPermissionResult result = getPermissionResults(permission);

        return result;
    }

    public synchronized IGetPermissionResult getPermissionResults(String[] permissionsArray)
    {
        return getResults(permissionsArray);
    }

    public synchronized IGetPermissionResult getPermissionResults(String permissionItem)
    {
        return getResults(new String[]{permissionItem});
    }

    private synchronized IGetPermissionResult getResults(String[] permissionsArray)
    {
        PermissionsResults permissionsResults = new PermissionsResults();
        permissionsResults.setPermissionStatus(true);

        for (int i = 0; i < permissionsArray.length; i++)
        {
            /**
             *  Running the checkSelfPermission again will filter the persmissions denyed by the user from the Never Show Again permissionsArray
             *
             */

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                int value = activity.checkSelfPermission(permissionsArray[i]);
                if (value == PackageManager.PERMISSION_DENIED)
                {
                    if (activity.shouldShowRequestPermissionRationale(permissionsArray[i]))
                    {
                       /*
                        * If permission has been denied by the user
                        */
                        permissionsResults.addItemUserDeniedPermissionsList(permissionsArray[i]);
                        permissionsResults.addItemAllPermissionsMap(permissionsArray[i], C.USER_DENIED);
                    }
                    else
                    {
                       /*
                        * Found a "Never ask again" case"
                        */
                        permissionsResults.addItemNeverAskAgainList(permissionsArray[i]);
                        permissionsResults.addItemAllPermissionsMap(permissionsArray[i], C.NEVER_SHOW_AGAIN);
                    }

                    permissionsResults.setPermissionStatus(false);
                }
                else
                {
                    permissionsResults.addItemGrantedPermissionsList(permissionsArray[i]);
                    permissionsResults.addItemAllPermissionsMap(permissionsArray[i], C.GRANTED);
                }
            }
            else
            {
                boolean result = activity.checkCallingOrSelfPermission(permissionsArray[i]) == PackageManager.PERMISSION_GRANTED;

                if (result)
                {
                    permissionsResults.addItemGrantedPermissionsList(permissionsArray[i]);
                    permissionsResults.addItemAllPermissionsMap(permissionsArray[i], C.GRANTED);
                }
                else
                {
                    permissionsResults.setPermissionStatus(false);
                    permissionsResults.addItemMissingInManifest_ForSdkBelowM(permissionsArray[i]);
                    permissionsResults.addItemAllPermissionsMap(permissionsArray[i], C.MISSING_IN_MAIFEST);
                }

            }

        }

        return permissionsResults;
    }
}
