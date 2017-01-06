package jagerfield.utilities.lib.DeviceUtil;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Point;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import java.io.File;
import java.util.Locale;
import jagerfield.utilities.lib.C;


public class DeviceUtil
{
    public static DeviceUtil newInstance()
    {
        return new DeviceUtil();
    }

    public final String getDeviceName()
    {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer))
        {
            return model;
        }
        else
        {
            return manufacturer + " " + model;
        }
    }

    public final String getReleaseBuildVersion() {
        return Build.VERSION.RELEASE;
    }

    public final String getBuildVersionCodeName() {
        return Build.VERSION.CODENAME;
    }

    public final String getManufacturer() {
        return Build.MANUFACTURER;
    }

    public final String getModel() {
        return Build.MODEL;
    }


    public String getProduct() {
        return Build.PRODUCT;
    }

    public final String getFingerprint() {
        return Build.FINGERPRINT;
    }

    public final String getHardware() {
        return Build.HARDWARE;
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public final String getRadioVer()
    {
        return  Build.getRadioVersion();
    }

    public final String getDevice() {
        return Build.DEVICE;
    }

    public final String getBoard() {
        return Build.BOARD;
    }

    public final String getDisplayVersion() {
        return Build.DISPLAY;
    }

    public final String getBuildBrand() {
        return Build.BRAND;
    }

    public final String getBuildHost() {
        return Build.HOST;
    }

    public final long getBuildTime() {
        return Build.TIME;
    }

    public final String getBuildUser() {
        return Build.USER;
    }

    public final String getSerial() {
        return Build.SERIAL;
    }

    public final String getOSVersion() {
        return Build.VERSION.RELEASE;
    }

    public final String getLanguage() {
        return Locale.getDefault().getLanguage();
    }

    public final int getSdkVersion() {
        return Build.VERSION.SDK_INT;
    }

    public String getScreenDensity(Activity activity)
    {
        int density = activity.getResources().getDisplayMetrics().densityDpi;
        String scrType = "";
        switch (density) {
            case DisplayMetrics.DENSITY_LOW:
                scrType = "ldpi";
                break;
            case DisplayMetrics.DENSITY_MEDIUM:
                scrType = "mdpi";
                break;
            case DisplayMetrics.DENSITY_HIGH:
                scrType = "hdpi";
                break;
            case DisplayMetrics.DENSITY_XHIGH:
                scrType = "xhdpi";
                break;
            default:
                scrType = "other";
                break;
        }
        return scrType;
    }

    public int getScreenHeight(Activity activity)
    {
        int height = 0;
        WindowManager wm = (WindowManager) activity.getSystemService(Activity.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT > 12)
        {
            Point size = new Point();
            display.getSize(size);
            height = size.y;
        }
        else
        {
            height = display.getHeight();
        }
        return height;
    }

    public int getScreenWidth(Activity activity)
    {
        int width = 0;
        WindowManager wm = (WindowManager) activity.getSystemService(Activity.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if (Build.VERSION.SDK_INT > 12) {
            Point size = new Point();
            display.getSize(size);
            width = size.x;
        } else {
            width = display.getWidth();  // deprecated
        }
        return width;
    }

    public String getVersionName(Activity activity)
    {
        PackageInfo pInfo;
        try {
            pInfo = activity.getPackageManager().getPackageInfo(
                    activity.getPackageName(), 0);
            return pInfo.versionName;
        } catch (Exception e1) {
            return null;
        }
    }

    public Integer getVersionCode(Activity activity)
    {
        PackageInfo pInfo;
        try
        {
            pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            return pInfo.versionCode;
        }
        catch (Exception e1)
        {
            return null;
        }
    }

    public String getPackageName(Activity activity) {
        return activity.getPackageName();
    }

    public String getActivityName(Activity activity) {
        return activity.getClass().getSimpleName();
    }

    public String getAppName(Activity activity)
    {
        PackageManager packageManager = activity.getPackageManager();
        ApplicationInfo applicationInfo = null;
        try
        {
            applicationInfo = packageManager.getApplicationInfo(activity.getApplicationInfo().packageName, 0);
        }
        catch (final PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
        return (String) (applicationInfo != null ? packageManager.getApplicationLabel(applicationInfo) : C.NOT_FOUND_VAL);
    }

    public boolean isRunningOnEmulator() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT)
                || Build.PRODUCT.contains("vbox86p")
                || Build.DEVICE.contains("vbox86p")
                || Build.HARDWARE.contains("vbox86");
    }

    public String getDeviceRingerMode(Activity activity) {
        AudioManager audioManager = (AudioManager) activity.getSystemService(Activity.AUDIO_SERVICE);
        switch (audioManager.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                return C.RINGER_MODE_SILENT;
            case AudioManager.RINGER_MODE_VIBRATE:
                return C.RINGER_MODE_VIBRATE;
            default:
                return C.RINGER_MODE_NORMAL;
        }
    }

    public final boolean isDeviceRooted()
    {
        String[] paths = { "/system/app/Superuser.apk", "/sbin/su", "/system/bin/su", "/system/xbin/su", "/data/local/xbin/su", "/data/local/bin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su", "/su/bin/su"};
        for (String path : paths) {
            if (new File(path).exists()) return true;
        }
        return false;
    }

    public final String getAndroidId(Activity activity) {
        String androidId = Settings.Secure.getString(activity.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return androidId;
    }

    public String getInstallSource(Activity activity){
        PackageManager pm = activity.getPackageManager();
        String installationSource = pm.getInstallerPackageName(activity.getPackageName());
        return installationSource;
    }

    public final String getUserAgent(Activity activity) {
        final String systemUa = System.getProperty("http.agent");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return WebSettings.getDefaultUserAgent(activity) + "__" + systemUa;
        }
        return new WebView(activity).getSettings().getUserAgentString() + "__" + systemUa;
    }

    public final String getGSFId(Activity activity)
    {
        Uri URI = Uri.parse("content://com.google.android.gsf.gservices");
        String ID_KEY = "android_id";
        String params[] = {ID_KEY};

        Cursor c = null;

        try
        {
            c = activity.getContentResolver().query(URI, null, null, params, null);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Log.i(C.TAG_LIB, "Missing permission " + "com.google.android.providers.gsf.permission.READ_GSERVICES");
            return C.ERROR;
        }

        if (c==null){return C.ERROR;}

        if (!c.moveToFirst() || c.getColumnCount() < 2)
        {
            c.close();
            return C.NOT_FOUND_VAL;
        }
        try
        {
            String gsfId =  Long.toHexString(Long.parseLong(c.getString(1)));
            c.close();
            return gsfId;
        }
        catch (NumberFormatException e)
        {
            c.close();
            return C.NOT_FOUND_VAL;
        }
    }

    public final boolean isAppInstalled(String packageName, Activity activity)
    {
        return activity.getPackageManager().getLaunchIntentForPackage(packageName) != null;
    }
}









