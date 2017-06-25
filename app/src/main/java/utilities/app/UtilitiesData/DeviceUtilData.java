package utilities.app.UtilitiesData;

import android.app.Activity;
import java.util.ArrayList;
import jagerfield.utilities.lib.AppUtilities;
import utilities.app.FragmentConfigUtil;

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
        FragmentConfigUtil fragmentConfigUtil = FragmentConfigUtil.newInstance();

        fragmentConfigUtil.addProperty(properties, "Release Build Version", AppUtilities.getDeviceUtil().getReleaseBuildVersion());

        fragmentConfigUtil.addProperty(properties, "Device Name", AppUtilities.getDeviceUtil().getDeviceName());

        fragmentConfigUtil.addProperty(properties, "Build Version Code Name", AppUtilities.getDeviceUtil().getBuildVersionCodeName());

        fragmentConfigUtil.addProperty(properties, "Manufacturer", AppUtilities.getDeviceUtil().getManufacturer());

        fragmentConfigUtil.addProperty(properties, "Model", AppUtilities.getDeviceUtil().getModel());

        fragmentConfigUtil.addProperty(properties, "Product", AppUtilities.getDeviceUtil().getProduct());

        fragmentConfigUtil.addProperty(properties, "Finger print", AppUtilities.getDeviceUtil().getFingerprint());

        fragmentConfigUtil.addProperty(properties, "Hardware", AppUtilities.getDeviceUtil().getHardware());

        fragmentConfigUtil.addProperty(properties, "RadioVer", AppUtilities.getDeviceUtil().getRadioVer());

        fragmentConfigUtil.addProperty(properties, "Device", AppUtilities.getDeviceUtil().getDevice());

        fragmentConfigUtil.addProperty(properties, "Board", AppUtilities.getDeviceUtil().getBoard());

        fragmentConfigUtil.addProperty(properties, "Display Version", AppUtilities.getDeviceUtil().getDisplayVersion());

        fragmentConfigUtil.addProperty(properties, "Build Host", AppUtilities.getDeviceUtil().getBuildHost());

        fragmentConfigUtil.addProperty(properties, "Build Time", AppUtilities.getDeviceUtil().getBuildTime(), 1, "sec");

        fragmentConfigUtil.addProperty(properties, "Build User", AppUtilities.getDeviceUtil().getBuildUser());

        fragmentConfigUtil.addProperty(properties, "Serial Number", AppUtilities.getDeviceUtil().getSerial());

        fragmentConfigUtil.addProperty(properties, "OS Version", AppUtilities.getDeviceUtil().getOSVersion());

        fragmentConfigUtil.addProperty(properties, "Language", AppUtilities.getDeviceUtil().getLanguage());

        fragmentConfigUtil.addProperty(properties, "Sdk Version", AppUtilities.getDeviceUtil().getSdkVersion(), 1, "");

        fragmentConfigUtil.addProperty(properties, "Screen Density", AppUtilities.getDeviceUtil().getScreenDensity(activity));

        fragmentConfigUtil.addProperty(properties, "Screen Height", AppUtilities.getDeviceUtil().getScreenHeight(activity), 1 , "Pixels");

        fragmentConfigUtil.addProperty(properties, "Screen Width", AppUtilities.getDeviceUtil().getScreenWidth(activity), 1 , "Pixels");

        fragmentConfigUtil.addProperty(properties, "Version Name", AppUtilities.getDeviceUtil().getVersionName(activity));

        fragmentConfigUtil.addProperty(properties, "Version Code", AppUtilities.getDeviceUtil().getVersionCode(activity));

        fragmentConfigUtil.addProperty(properties, "Package Name", AppUtilities.getDeviceUtil().getPackageName(activity));

        fragmentConfigUtil.addProperty(properties, "Activity Name", AppUtilities.getDeviceUtil().getActivityName(activity));

        fragmentConfigUtil.addProperty(properties, "App Name", AppUtilities.getDeviceUtil().getAppName(activity));

        fragmentConfigUtil.addProperty(properties, "Is Running On Emulator", AppUtilities.getDeviceUtil().isRunningOnEmulator());

        fragmentConfigUtil.addProperty(properties, "Device Ringer Mode", AppUtilities.getDeviceUtil().getDeviceRingerMode(activity));

        fragmentConfigUtil.addProperty(properties, "Is Device Rooted", AppUtilities.getDeviceUtil().isDeviceRooted());

        fragmentConfigUtil.addProperty(properties, "Android Id", AppUtilities.getDeviceUtil().getAndroidId(activity));

        fragmentConfigUtil.addProperty(properties, "Install Source", AppUtilities.getDeviceUtil().getInstallSource(activity));

        fragmentConfigUtil.addProperty(properties, "User Agent", AppUtilities.getDeviceUtil().getUserAgent(activity));

        fragmentConfigUtil.addProperty(properties, "Is App Installed", AppUtilities.getDeviceUtil().isAppInstalled(AppUtilities.getDeviceUtil().getPackageName(activity), activity));

        fragmentConfigUtil.addProperty(properties, "GSF Id", AppUtilities.getDeviceUtil().getGSFId(activity));

        return properties;
    }

}












