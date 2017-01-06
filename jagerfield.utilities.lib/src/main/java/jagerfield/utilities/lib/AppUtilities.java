package jagerfield.utilities.lib;

import android.app.Activity;
import jagerfield.utilities.lib.BatteryUtil.BatteryUtil;
import jagerfield.utilities.lib.DeviceUtil.DeviceUtil;
import jagerfield.utilities.lib.MemoryUtil.MemoryUtil;
import jagerfield.utilities.lib.NetworkUtil.NetworkUtil;
import jagerfield.utilities.lib.PermissionsUtil.PermissionsUtil;
import jagerfield.utilities.lib.SoftKeyboradUtil.SoftKeyboardUtil;

public class AppUtilities
{
    public static void getSoftKeyboard()
    {
        SoftKeyboardUtil.newInstance();
    }

    public static PermissionsUtil getPermissionUtil(Activity activity)
    {
        return PermissionsUtil.newInstance(activity);
    }

    public static NetworkUtil getNetworkUtil()
    {
        return NetworkUtil.newInstance();
    }

    public static MemoryUtil getMemoryUtil()
    {
        return MemoryUtil.newInstance();
    }

    public static DeviceUtil getDeviceUtil()
    {
        return DeviceUtil.newInstance();
    }

    public static BatteryUtil getBatteryUtil()
    {
        return BatteryUtil.newInstance();
    }
}
