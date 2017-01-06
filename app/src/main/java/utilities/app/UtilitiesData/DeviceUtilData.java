package utilities.app.UtilitiesData;

import android.app.Activity;
import java.util.ArrayList;
import jagerfield.utilities.lib.AppUtilities;
import utilities.app.D;

public class DeviceUtilData
{
    public DeviceUtilData()
    {
        
    }

    public static DeviceUtilData getInstance()
    {
        DeviceUtilData deviceUtilData = new DeviceUtilData();
        return deviceUtilData;
    }

    public ArrayList<PropertyModel> getDeviceProperties(Activity activity)
    {
        ArrayList<PropertyModel> properties = new ArrayList<>();
        D d = D.newInstance();

        d.addProperty(properties, "Release Build Version", AppUtilities.getDeviceUtil().getReleaseBuildVersion());

        d.addProperty(properties, "Device Name", AppUtilities.getDeviceUtil().getDeviceName());

        d.addProperty(properties, "Build Version Code Name", AppUtilities.getDeviceUtil().getBuildVersionCodeName());

        d.addProperty(properties, "Manufacturer", AppUtilities.getDeviceUtil().getManufacturer());

        d.addProperty(properties, "Model", AppUtilities.getDeviceUtil().getModel());

        d.addProperty(properties, "Product", AppUtilities.getDeviceUtil().getProduct());

        d.addProperty(properties, "Finger print", AppUtilities.getDeviceUtil().getFingerprint());

        d.addProperty(properties, "Hardware", AppUtilities.getDeviceUtil().getHardware());

        d.addProperty(properties, "RadioVer", AppUtilities.getDeviceUtil().getRadioVer());

        d.addProperty(properties, "Device", AppUtilities.getDeviceUtil().getDevice());

        d.addProperty(properties, "Board", AppUtilities.getDeviceUtil().getBoard());

        d.addProperty(properties, "Display Version", AppUtilities.getDeviceUtil().getDisplayVersion());

        d.addProperty(properties, "Build Host", AppUtilities.getDeviceUtil().getBuildHost());

        d.addProperty(properties, "Build Time", AppUtilities.getDeviceUtil().getBuildTime(), 1, "sec");

        d.addProperty(properties, "Build User", AppUtilities.getDeviceUtil().getBuildUser());

        d.addProperty(properties, "Serial Number", AppUtilities.getDeviceUtil().getSerial());

        d.addProperty(properties, "OS Version", AppUtilities.getDeviceUtil().getOSVersion());

        d.addProperty(properties, "Language", AppUtilities.getDeviceUtil().getLanguage());

        d.addProperty(properties, "Sdk Version", AppUtilities.getDeviceUtil().getSdkVersion(), 1, "");

        d.addProperty(properties, "Screen Density", AppUtilities.getDeviceUtil().getScreenDensity(activity));

        d.addProperty(properties, "Screen Height", AppUtilities.getDeviceUtil().getScreenHeight(activity), 1 , "Pixels");

        d.addProperty(properties, "Screen Width", AppUtilities.getDeviceUtil().getScreenWidth(activity), 1 , "Pixels");

        d.addProperty(properties, "Version Name", AppUtilities.getDeviceUtil().getVersionName(activity));

        d.addProperty(properties, "Version Code", AppUtilities.getDeviceUtil().getVersionCode(activity));

        d.addProperty(properties, "Package Name", AppUtilities.getDeviceUtil().getPackageName(activity));

        d.addProperty(properties, "Activity Name", AppUtilities.getDeviceUtil().getActivityName(activity));

        d.addProperty(properties, "App Name", AppUtilities.getDeviceUtil().getAppName(activity));

        d.addProperty(properties, "Is Running On Emulator", AppUtilities.getDeviceUtil().isRunningOnEmulator());

        d.addProperty(properties, "Device Ringer Mode", AppUtilities.getDeviceUtil().getDeviceRingerMode(activity));

        d.addProperty(properties, "Is Device Rooted", AppUtilities.getDeviceUtil().isDeviceRooted());

        d.addProperty(properties, "Android Id", AppUtilities.getDeviceUtil().getAndroidId(activity));

        d.addProperty(properties, "Install Source", AppUtilities.getDeviceUtil().getInstallSource(activity));

        d.addProperty(properties, "User Agent", AppUtilities.getDeviceUtil().getUserAgent(activity));

        d.addProperty(properties, "Is App Installed", AppUtilities.getDeviceUtil().isAppInstalled(AppUtilities.getDeviceUtil().getPackageName(activity), activity));

        d.addProperty(properties, "GSF Id", AppUtilities.getDeviceUtil().getGSFId(activity));

        return properties;
    }

}












