package utilities.app.UtilitiesData;

import android.app.Activity;

import java.util.ArrayList;

import jagerfield.utilities.lib.AppUtilities;
import utilities.app.FragmentConfigUtil;

public class BatteryUtilData
{
    public BatteryUtilData()
    {

    }

    public static BatteryUtilData getInstance()
    {
        BatteryUtilData batteryUtilData = new BatteryUtilData();
        return batteryUtilData;
    }

    public ArrayList<PropertyModel> getDeviceBatteryProperties(Activity activity)
    {
        ArrayList<PropertyModel> properties = new ArrayList<>();
        FragmentConfigUtil fragmentConfigUtil = FragmentConfigUtil.newInstance();

        fragmentConfigUtil.addProperty(properties, "Battery Percent", AppUtilities.getBatteryUtil().getBatteryPercent(activity));

        fragmentConfigUtil.addProperty(properties, "Is Phone Charging", AppUtilities.getBatteryUtil().isPhoneCharging(activity));

        fragmentConfigUtil.addProperty(properties, "Battery Health", AppUtilities.getBatteryUtil().getBatteryHealth(activity));

        fragmentConfigUtil.addProperty(properties, "Battery Technology", AppUtilities.getBatteryUtil().getBatteryTechnology(activity));

        fragmentConfigUtil.addProperty(properties, "Battery Temperature", AppUtilities.getBatteryUtil().getBatteryTemperature(activity));

        fragmentConfigUtil.addProperty(properties, "Battery Voltage", AppUtilities.getBatteryUtil().getBatteryVoltage(activity));

        fragmentConfigUtil.addProperty(properties, "Charging Source", AppUtilities.getBatteryUtil().getChargingSource(activity));

        fragmentConfigUtil.addProperty(properties, "Is Battery Present", AppUtilities.getBatteryUtil().isBatteryPresent(activity));

        return properties;
    }

}


















