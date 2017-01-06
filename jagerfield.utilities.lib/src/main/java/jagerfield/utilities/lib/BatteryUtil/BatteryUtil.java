package jagerfield.utilities.lib.BatteryUtil;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import jagerfield.utilities.lib.C;

public class BatteryUtil
{
    public static BatteryUtil newInstance()
    {
        return new BatteryUtil();
    }

    public int getBatteryPercent(Activity activity)
    {
        Intent intent = getBatteryStatusIntent(activity);
        int rawlevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        int level = -1;
        if (rawlevel >= 0 && scale > 0) {
            level = (rawlevel * 100) / scale;
        }
        return level;
    }

    public boolean isPhoneCharging(Activity activity) {
        Intent intent = getBatteryStatusIntent(activity);
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
        return plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB;
    }

    public String getBatteryHealth(Activity activity)
    {
        String health = C.BATTERY_HEALTH_UNKNOWN;
        Intent intent = getBatteryStatusIntent(activity);
        int status = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
        switch (status) {
            case BatteryManager.BATTERY_HEALTH_COLD:
                health = C.BATTERY_HEALTH_COLD;
                break;

            case BatteryManager.BATTERY_HEALTH_DEAD:
                health = C.BATTERY_HEALTH_DEAD;
                break;

            case BatteryManager.BATTERY_HEALTH_GOOD:
                health = C.BATTERY_HEALTH_GOOD;
                break;

            case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                health = C.BATTERY_HEALTH_OVERHEAT;
                break;

            case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                health = C.BATTERY_HEALTH_OVER_VOLTAGE;
                break;

            case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                health = C.BATTERY_HEALTH_UNKNOWN;
                break;

            case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                health = C.BATTERY_HEALTH_UNSPECIFIED_FAILURE;
                break;
        }
        return health;
    }

    public final String getBatteryTechnology(Activity activity) {
        Intent intent = getBatteryStatusIntent(activity);
        return intent.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY);
    }

    public final float getBatteryTemperature(Activity activity) {
        Intent intent = getBatteryStatusIntent(activity);
        int temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0);
        return (float) (temperature / 10.0);
    }

    public final int getBatteryVoltage(Activity activity) {
        Intent intent = getBatteryStatusIntent(activity);
        return intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);
    }

    public final String getChargingSource(Activity activity) {
        Intent intent = getBatteryStatusIntent(activity);
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
        switch (plugged) {
            case BatteryManager.BATTERY_PLUGGED_AC:
                return C.BATTERY_PLUGGED_AC;
            case BatteryManager.BATTERY_PLUGGED_USB:
                return C.BATTERY_PLUGGED_USB;
            case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                return C.BATTERY_PLUGGED_WIRELESS;
            default:
                return C.BATTERY_PLUGGED_UNKNOWN;
        }
    }

    public final boolean isBatteryPresent(Activity activity) {
        Intent intent = getBatteryStatusIntent(activity);
        return intent.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false);
    }

    private Intent getBatteryStatusIntent(Activity activity) {
        IntentFilter batFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        return activity.registerReceiver(null, batFilter);
    }

}
