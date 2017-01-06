package jagerfield.utilities.lib.PermissionsUtil.GuiDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import jagerfield.utilities.lib.AppUtilities;
import jagerfield.utilities.lib.PermissionsUtil.PermissionsUtil;
import jagerfield.utilities.lib.PermissionsUtil.Results.IGetPermissionResult;
import jagerfield.utilities.lib.R;

public class PermissionsNotificationDialog
{
    private PermissionsManager.PermissionsManagerCallback callback;
    private TextView tv_getting_permission;
    private TextView tv_requestPermissions;
    private TextView tv_requestNeverAskAgainPermissions;
    private TextView tv_neverAskAgainPermissionsList;
    private TextView tv_deniedPermissionsList;
    private Button bt_getDeniedPermissions;
    private Button bt_openSettings;
    private Button bt_checkPermissions;
    public String PERMISSIONS_MISSING = "Permissions are missing";
    public String PERMISSIONS_GRANTED = "All Permission Available";

    private Activity activity;

    public PermissionsNotificationDialog(Activity activity, PermissionsManager.PermissionsManagerCallback callback)
    {
        this.activity = activity;
        this.callback = callback;
    }

    public static PermissionsNotificationDialog getNewInstance(Activity activity, PermissionsManager.PermissionsManagerCallback callback)
    {
        return new PermissionsNotificationDialog(activity, callback);
    }

    public void initiateDialogViews(final Dialog dialog, IGetPermissionResult result, final String[] permissions)
    {
        tv_getting_permission = (TextView) dialog.findViewById(R.id.tv_getting_permission);
        tv_requestPermissions = (TextView) dialog.findViewById(R.id.tv_requestPermissions);
        tv_requestNeverAskAgainPermissions = (TextView) dialog.findViewById(R.id.tv_requestNeverAskAgainPermissions);
        tv_neverAskAgainPermissionsList = (TextView) dialog.findViewById(R.id.tv_neverAskAgainPermissionsList);
        tv_deniedPermissionsList = (TextView) dialog.findViewById(R.id.tv_deniedPermissionsList);
        bt_getDeniedPermissions = (Button) dialog.findViewById(R.id.bt_getDeniedPermissions);
        bt_openSettings = (Button) dialog.findViewById(R.id.bt_openSettings);
        bt_checkPermissions = (Button) dialog.findViewById(R.id.bt_checkPermissions);

        bt_getDeniedPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
                managePermissions(permissions);

            }
        });

        bt_openSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                delayedActions();
                openSettings(activity);

            }
        });

        bt_checkPermissions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();

                /**
                 * This button is enabled after clicking the bt_openSettings button.
                 * When clicked do an extra check for permissions.
                 */
                managePermissions(permissions);
            }
        });


        final Boolean status = hasPermissions(permissions);

        if (status == null)
        {

        }
        else if(status)
        {
            /**
             * Check for existing of permissions.
             */
            callback.onPermissionsGranted(result);
        }
        else if(!status)
        {
            /**
             * Notify user about missing permissions and rquest actions.
             */
            setUserInterface(result);
        }

    }

    private void delayedActions()
    {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run()
            {
                bt_checkPermissions.setVisibility(View.VISIBLE);
                bt_openSettings.setEnabled(false);
            }
        }, 1000);
    }

    private Boolean hasPermissions(String[] permissions)
    {
        PermissionsUtil permissionsUtil = AppUtilities.getPermissionUtil(activity);
        IGetPermissionResult result = permissionsUtil.getPermissionResults(permissions);

        if (result==null) {return null;}

        else if (result.isGranted())
        {
            return true;
        }
        else if (!result.isGranted() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            return false;
        }

        return null;
    }

    private void managePermissions(String[] permissions)
    {
        PermissionsUtil permissionsUtil = AppUtilities.getPermissionUtil(activity);

        IGetPermissionResult result = permissionsUtil.getPermissionResults(permissions);

        if (result==null) {return;}
        else if (result.isGranted())
        {
            /**
             * Allpermissions granted
             */
            Toast.makeText(activity, "All permissions are granted", Toast.LENGTH_SHORT).show();

            /**
             * Make a callback inside the onRequestPermissionsResult
             */
            callback.onPermissionsGranted(result);
        }
        else if (!result.isGranted() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            /**
             * There are missing permissions ask for them
             */
            permissionsUtil.requestPermissions(permissions);
        }
    }

    public void setUserInterface(IGetPermissionResult permissionsResults)
    {
        if (permissionsResults ==null)
        {
            return;
        }

        /**
         *  All permissions granted
         */
        managePermissionStatusTitle(permissionsResults);

        /**
         * If not all permissions granted, then check the permissions user had denied
         */
        manageDeniedPermissionsViews(permissionsResults);

        /**
         * If not all permissions granted,check never show again permissions
         */
        manageNeverAskPermissionsViews(permissionsResults);
    }

    private void managePermissionStatusTitle(IGetPermissionResult permissionsResults)
    {
        String msg = "";
        boolean gotPermissions = permissionsResults.isGranted();

        if (gotPermissions && permissionsResults.getNeverAskAgainPermissionsList().isEmpty() && permissionsResults.getUserDeniedPermissionsList().isEmpty())
        {
            msg = PERMISSIONS_GRANTED;
            tv_getting_permission.setText(msg);
            tv_getting_permission.setTextColor(getThisColor(R.color.green));
            bt_checkPermissions.setVisibility(View.VISIBLE);
        }
        else if (!gotPermissions && !permissionsResults.getNeverAskAgainPermissionsList().isEmpty() && permissionsResults.getUserDeniedPermissionsList().isEmpty())
        {
            msg = PERMISSIONS_MISSING;
            tv_getting_permission.setText(msg);
            tv_getting_permission.setTextColor(getThisColor(R.color.red));
            bt_checkPermissions.setVisibility(View.GONE);
        }
        else if (!gotPermissions && !permissionsResults.getUserDeniedPermissionsList().isEmpty())
        {
            msg = PERMISSIONS_MISSING;
            tv_getting_permission.setText(msg);
            tv_getting_permission.setTextColor(getThisColor(R.color.red));
            bt_checkPermissions.setVisibility(View.GONE);
        }
    }

    private int getThisColor(int color)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            return activity.getColor(color);
        }
        else
        {
            return activity.getResources().getColor(color);
        }
    }

    private void manageDeniedPermissionsViews(IGetPermissionResult results)
    {
        int status = View.GONE;
        boolean gotPermissions = results.isGranted();
        ArrayList<String> list = results.getUserDeniedPermissionsList();

        if (!results.isGranted() && results.getUserDeniedPermissionsList().isEmpty())
        {
            status = View.GONE;
        }
        else if (!gotPermissions && !results.getUserDeniedPermissionsList().isEmpty())
        {
            status = View.VISIBLE;
        }

        tv_requestPermissions.setVisibility(status);
        String text = getList(list, status);
        tv_deniedPermissionsList.setText(text);
        tv_deniedPermissionsList.setVisibility(status);
        bt_getDeniedPermissions.setVisibility(status);
    }

    private void manageNeverAskPermissionsViews(IGetPermissionResult results)
    {
        int status = View.GONE;
        boolean gotPermissions = results.isGranted();
        ArrayList<String> list = results.getNeverAskAgainPermissionsList();

        if (!gotPermissions && results.getNeverAskAgainPermissionsList().isEmpty())
        {
            status = View.GONE;
        }
        else if (!gotPermissions && !results.getNeverAskAgainPermissionsList().isEmpty())
        {
            status = View.VISIBLE;
        }

        tv_requestNeverAskAgainPermissions.setVisibility(status);
        String text = getList(list, status);
        tv_neverAskAgainPermissionsList.setText(text);
        tv_neverAskAgainPermissionsList.setVisibility(status);
        bt_openSettings.setVisibility(status);
    }

    @NonNull
    private String getList(ArrayList<String> list, int status)
    {
        StringBuilder items = new StringBuilder();

        if (status == View.VISIBLE)
        {
            for (int i = 0; i < list.size(); i++)
            {
                String permission = list.get(i);
                int v = permission.lastIndexOf(".");
                String name = permission.substring(v+1, permission.length()).toLowerCase();
                char c = Character.toUpperCase(name.charAt(0));
                name = name.replaceFirst(Character.toString(name.charAt(0)), Character.toString(c));
                items.append(i +1 + " - " + name + "\n");
            }
        }

        return items.toString().replace("_", " ").trim();
    }

    public static void openSettings(final Activity activity) {
        if (activity == null) {
            return;
        }

        Uri uri = Uri.parse("package:" + activity.getPackageName());
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
        activity.startActivity(intent);
    }

}
