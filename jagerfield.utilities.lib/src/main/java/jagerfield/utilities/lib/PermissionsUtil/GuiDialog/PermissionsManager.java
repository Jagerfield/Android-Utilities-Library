package jagerfield.utilities.lib.PermissionsUtil.GuiDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import jagerfield.utilities.lib.AppUtilities;
import jagerfield.utilities.lib.PermissionsUtil.PermissionsUtil;
import jagerfield.utilities.lib.PermissionsUtil.Results.IGetPermissionResult;
import jagerfield.utilities.lib.R;

public class PermissionsManager
{
    final Dialog dialog;

    private PermissionsManager(final Activity activity, IGetPermissionResult result, final String[]permissions, final PermissionsManagerCallback callback)
    {
        dialog = new Dialog(activity);
        dialog.setContentView(R.layout.dialog_missing_perm);
        dialog.setCancelable(true);
        dialog.setTitle("Missing Permissions");

        PermissionsNotificationDialog.getNewInstance(activity,callback).initiateDialogViews(dialog, result, permissions);
        dialog.show();

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog)
            {
                PermissionsUtil permissionsUtil = AppUtilities.getPermissionUtil(activity);
                IGetPermissionResult result = permissionsUtil.getPermissionResults(permissions);

                if (result==null) {return;}
                else if (result.isGranted())
                {
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
                    callback.onPermissionsMissing(result);
                }
            }
        });
    }

    public static PermissionsManager getNewInstance(Activity activity, IGetPermissionResult result, String[]permissions, PermissionsManagerCallback callback)
    {
        return new PermissionsManager(activity, result, permissions,callback);
    }

    public interface PermissionsManagerCallback
    {
        void onPermissionsGranted(IGetPermissionResult result);
        void onPermissionsMissing(IGetPermissionResult result);

    }
}
