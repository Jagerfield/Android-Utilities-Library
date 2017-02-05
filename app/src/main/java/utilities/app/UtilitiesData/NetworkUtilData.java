package utilities.app.UtilitiesData;

import android.app.Activity;
import java.util.ArrayList;
import jagerfield.utilities.lib.AppUtilities;
import utilities.app.D;

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
        D d = D.newInstance();

        d.addProperty(properties, "Internet Connection Status", AppUtilities.getNetworkUtil().getInternetConnectionType(activity));

        d.addProperty(properties, "Is Nfc Present", AppUtilities.getNetworkUtil().isNfcPresent(activity));

        d.addProperty(properties, "Is Nfc Enabled", AppUtilities.getNetworkUtil().isNfcEnabled(activity));

        d.addProperty(properties, "Is Wifi Enabled", AppUtilities.getNetworkUtil().isWifiEnabled(activity));

        d.addProperty(properties, "Network Class", AppUtilities.getNetworkUtil().getNetworkClass(activity));

        d.addProperty(properties, "IMEI", AppUtilities.getNetworkUtil().getIMEI(activity));

        d.addProperty(properties, "IMSI", AppUtilities.getNetworkUtil().getIMSI(activity));

        d.addProperty(properties, "Phone Type", AppUtilities.getNetworkUtil().getPhoneType(activity));

        d.addProperty(properties, "Phone Number", AppUtilities.getNetworkUtil().getPhoneNumber(activity));

        d.addProperty(properties, "Operator", AppUtilities.getNetworkUtil().getOperator(activity));

        d.addProperty(properties, "Sim Serial", AppUtilities.getNetworkUtil().getSimSerial(activity));

        d.addProperty(properties, "Is Sim Network Locked", AppUtilities.getNetworkUtil().isSimNetworkLocked(activity));

        d.addProperty(properties, "Bluetooth MAC", AppUtilities.getNetworkUtil().getBluetoothMAC(activity));

        d.addProperty(properties, "Wifi Mac Address", AppUtilities.getNetworkUtil().getWifiMacAddress(activity));

        return properties;
    }

}













