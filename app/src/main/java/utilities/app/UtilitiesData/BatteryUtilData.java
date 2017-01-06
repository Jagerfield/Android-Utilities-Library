package utilities.app.UtilitiesData;

import android.app.Activity;

import java.util.ArrayList;

import jagerfield.utilities.lib.AppUtilities;
import utilities.app.D;

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
        D d = D.newInstance();

        d.addProperty(properties, "Battery Percent", AppUtilities.getBatteryUtil().getBatteryPercent(activity));

        d.addProperty(properties, "Is Phone Charging", AppUtilities.getBatteryUtil().isPhoneCharging(activity));

        d.addProperty(properties, "Battery Health", AppUtilities.getBatteryUtil().getBatteryHealth(activity));

        d.addProperty(properties, "Battery Technology", AppUtilities.getBatteryUtil().getBatteryTechnology(activity));

        d.addProperty(properties, "Battery Temperature", AppUtilities.getBatteryUtil().getBatteryTemperature(activity));

        d.addProperty(properties, "Battery Voltage", AppUtilities.getBatteryUtil().getBatteryVoltage(activity));

        d.addProperty(properties, "Charging Source", AppUtilities.getBatteryUtil().getChargingSource(activity));

        d.addProperty(properties, "Is Battery Present", AppUtilities.getBatteryUtil().isBatteryPresent(activity));

        return properties;
    }

}


















