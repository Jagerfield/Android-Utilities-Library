package utilities.app.UtilitiesData;

import android.app.Activity;
import java.util.ArrayList;
import jagerfield.utilities.lib.AppUtilities;
import utilities.app.FragmentConfigUtil;

public class NetworkUtilData
{
    public NetworkUtilData()
    {
        
    }

    public static NetworkUtilData getInstance()
    {
        NetworkUtilData networkUtilData = new NetworkUtilData();
        return networkUtilData;
    }

    public ArrayList<PropertyModel> getDeviceNetworkProperties(Activity activity)
    {
        ArrayList<PropertyModel> properties = new ArrayList<>();
        FragmentConfigUtil fragmentConfigUtil = FragmentConfigUtil.newInstance();

        fragmentConfigUtil.addProperty(properties, "Internet Connection Status", AppUtilities.getNetworkUtil().getInternetConnectionType(activity));

        fragmentConfigUtil.addProperty(properties, "Is Nfc Present", AppUtilities.getNetworkUtil().isNfcPresent(activity));

        fragmentConfigUtil.addProperty(properties, "Is Nfc Enabled", AppUtilities.getNetworkUtil().isNfcEnabled(activity));

        fragmentConfigUtil.addProperty(properties, "Is Wifi Enabled", AppUtilities.getNetworkUtil().isWifiEnabled(activity));

        fragmentConfigUtil.addProperty(properties, "Network Class", AppUtilities.getNetworkUtil().getNetworkClass(activity));

        fragmentConfigUtil.addProperty(properties, "IMEI", AppUtilities.getNetworkUtil().getIMEI(activity));

        fragmentConfigUtil.addProperty(properties, "IMSI", AppUtilities.getNetworkUtil().getIMSI(activity));

        fragmentConfigUtil.addProperty(properties, "Phone Type", AppUtilities.getNetworkUtil().getPhoneType(activity));

        fragmentConfigUtil.addProperty(properties, "Phone Number", AppUtilities.getNetworkUtil().getPhoneNumber(activity));

        fragmentConfigUtil.addProperty(properties, "Operator", AppUtilities.getNetworkUtil().getOperator(activity));

        fragmentConfigUtil.addProperty(properties, "Sim Serial", AppUtilities.getNetworkUtil().getSimSerial(activity));

        fragmentConfigUtil.addProperty(properties, "Is Sim Network Locked", AppUtilities.getNetworkUtil().isSimNetworkLocked(activity));

        fragmentConfigUtil.addProperty(properties, "Bluetooth MAC", AppUtilities.getNetworkUtil().getBluetoothMAC(activity));

        fragmentConfigUtil.addProperty(properties, "Wifi Mac Address", AppUtilities.getNetworkUtil().getWifiMacAddress(activity));

        return properties;
    }

}













