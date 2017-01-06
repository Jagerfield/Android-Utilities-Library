package jagerfield.utilities.lib;

import android.Manifest;

public class C
{
    public static final String[] PERMISSIONS_ARRAY = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.BODY_SENSORS,
            Manifest.permission.USE_SIP,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public static final String GRANTED = "GRANTED";
    public static final String USER_DENIED = "USER_DENIED";
    public static final String NEVER_SHOW_AGAIN = "NEVER_SHOW_AGAIN";
    public static final String MISSING_IN_MAIFEST = "MISSING_IN_MAIFEST";
    public static final String TAG_LIB = "TAG_LIB";

    public static final int TYPE_WIFI = 1;
    public static final int TYPE_WIMAX = 6;
    public static final int TYPE_MOBILE = 2;
    public static final int TYPE_NOT_CONNECTED = 0;
    public static final int TYPE_NOT_FOUND = -1;

    public static final String BATTERY_HEALTH_COLD = "cold";
    public static final String BATTERY_HEALTH_DEAD = "dead";
    public static final String BATTERY_HEALTH_GOOD = "good";
    public static final String BATTERY_HEALTH_OVERHEAT = "Over Heat";
    public static final String BATTERY_HEALTH_OVER_VOLTAGE = "Over Voltage";
    public static final String BATTERY_HEALTH_UNKNOWN = "Unknown";
    public static final String BATTERY_HEALTH_UNSPECIFIED_FAILURE = "Unspecified failure";

    public static final String BATTERY_PLUGGED_AC = "Charging via AC";
    public static final String BATTERY_PLUGGED_USB = "Charging via USB";
    public static final String BATTERY_PLUGGED_WIRELESS = "Wireless";
    public static final String BATTERY_PLUGGED_UNKNOWN = "Unknown Source";

    public static final String RINGER_MODE_NORMAL = "Normal";
    public static final String RINGER_MODE_SILENT = "Silent";
    public static final String RINGER_MODE_VIBRATE = "Vibrate";

    public static final String PHONE_TYPE_GSM = "GSM";
    public static final String PHONE_TYPE_CDMA = "CDMA";
    public static final String PHONE_TYPE_NONE = "Unknown";

    public static final String NETWORK_TYPE_2G = "2G";
    public static final String NETWORK_TYPE_3G = "3G";
    public static final String NETWORK_TYPE_4G = "4G";
    public static final String NOT_FOUND_VAL = "VALUE_NOT_FOUND";
    public static final String ERROR = "ERROR";
    public static final String NETWORK_TYPE_WIFI_WIFIMAX = "WiFi";

}
