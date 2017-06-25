package jagerfield.utilities.lib.NetworkUtil;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import jagerfield.utilities.lib.C;
import jagerfield.utilities.lib.PermissionsUtil.PermissionsUtil;

public class NetworkUtil
{
    public NetworkUtil()
    {  }

    public static NetworkUtil newInstance()
    {
        return new NetworkUtil();
    }

    /* Gets internet connectivity status and also pings to make sure it is available.
    *
    */
    public final String getInternetConnectionType(Activity activity)
    {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork == null)
        {
            return "TYPE_NOT_FOUND";
        }

        int conn = activeNetwork.getType();
        int type = C.TYPE_NOT_FOUND;

        if (conn == C.TYPE_WIFI)
        {
            type = C.TYPE_WIFI;
            Log.i(C.TAG_LIB, "Internet TYPE_WIFI");
        }
        else if (conn == C.TYPE_WIMAX)
        {
            type = C.TYPE_WIMAX;
            Log.i(C.TAG_LIB, "Internet TYPE_WIMAX");
        }
        else if (conn == C.TYPE_MOBILE)
        {
            type = C.TYPE_MOBILE;
            Log.i(C.TAG_LIB, "Internet TYPE_MOBILE");
        }
        else if (conn == C.TYPE_NOT_CONNECTED)
        {
            type = C.TYPE_NOT_CONNECTED;
            Log.i(C.TAG_LIB, "Internet TYPE_NOT_CONNECTED");
        }

        boolean result = pingGoogle(activity);

       if(!result)
        {
            return "TYPE_NOT_CONNECTED";
        }

        switch(type)
        {
            case C.TYPE_WIFI:
                return "TYPE_WIFI";
            case C.TYPE_WIMAX:
                return "TYPE_WIMAX";
            case C.TYPE_MOBILE:
                return "TYPE_MOBILE";
            case C.TYPE_NOT_CONNECTED:
                return "TYPE_NOT_CONNECTED";
            case C.TYPE_NOT_FOUND:
                return "TYPE_NOT_FOUND";
            default:
                return "TYPE_NOT_FOUND";
        }

    }

    public final boolean hasInternetConnection(Activity activity)
    {
        String type = getInternetConnectionType(activity);

        switch(type)
        {
            case "TYPE_WIFI":
                return true;
            case "TYPE_WIMAX":
                return true;
            case "TYPE_MOBILE":
                return true;
            case "TYPE_NOT_CONNECTED":
                return false;
            case "TYPE_NOT_FOUND":
                return false;
            default:
                return false;
        }
    }

    private final boolean pingGoogle(Activity activity)
    {
        int counter = 2;
        boolean result = false;

        if(isNetworkConnected(activity))
        {
            return true;
        }
        else
        {
            for (int i = 0; i <counter ; i++)
            {
                result = isNetworkConnected(activity);

                if (result)
                {
                    return true;
                }
                else
                {
                    Log.i(C.TAG_LIB, "Failed to ping Google " + i + " times");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            //Wait

                        }
                    }, 5000);
                }
            }
        }

        return result;
    }

    private final boolean isNetworkConnected(Activity activity)
    {
        boolean connected = false;
        final ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Activity.CONNECTIVITY_SERVICE);
        final NetworkInfo newtWorkInfo = connectivityManager.getActiveNetworkInfo();

        if (newtWorkInfo != null && newtWorkInfo.isConnectedOrConnecting())
        {
            connected = true;
        }
        else if (newtWorkInfo != null && newtWorkInfo.isConnected() && connectivityManager.getActiveNetworkInfo().isAvailable())
        {
            connected = true;
        }
        else if (newtWorkInfo != null && newtWorkInfo.isConnected())
        {
            try
            {
                URL url = new URL("http://www.google.com");
                HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                urlc.setConnectTimeout(3000);
                urlc.connect();
                if (urlc.getResponseCode() == 200)
                {
                    connected = true;
                }
            }
            catch (MalformedURLException e1)
            {
                e1.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

        }
        else if (connectivityManager != null)
        {
            final NetworkInfo[] netInfoAll = connectivityManager.getAllNetworkInfo();

            for (NetworkInfo network : netInfoAll)
            {
                System.out.println("get network type :::" + network.getTypeName());

                if ((network.getTypeName().equalsIgnoreCase("WIFI") || network.getTypeName().equalsIgnoreCase("MOBILE")) && network.isConnected() && network.isAvailable())
                {
                    connected = true;
                    if (connected)
                    {
                        break;
                    }
                }
            }
        }

        return connected;
    }

    public final boolean isNfcEnabled(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1) {
            NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
            return nfcAdapter != null && nfcAdapter.isEnabled();
        }
        return false;
    }

    public final boolean isNfcPresent(Activity activity)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1)
        {
            NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(activity);
            return nfcAdapter != null;
        }

        return false;
    }
    public final String getNetworkClass(Activity activity) {
        TelephonyManager mTelephonyManager = (TelephonyManager) activity.getSystemService(Activity.TELEPHONY_SERVICE);
        int networkType = mTelephonyManager.getNetworkType();
        switch (networkType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return C.NETWORK_TYPE_2G;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return C.NETWORK_TYPE_3G;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return C.NETWORK_TYPE_4G;
            default:
                return C.NOT_FOUND_VAL;
        }
    }

    public final Boolean isWifiEnabled(Activity activity)
    {
        if(!PermissionsUtil.newInstance(activity).isPermissionGranted(Manifest.permission.ACCESS_WIFI_STATE).isGranted())
        {
            Log.i(C.TAG_LIB, "Missing permission ACCESS_WIFI_STATE");
            return null;
        }

        WifiManager wifiManager = null;

        try
        {
            wifiManager = (WifiManager) activity.getApplicationContext().getSystemService(Activity.WIFI_SERVICE);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        if(wifiManager==null)
        {
            return false;
        }

        return wifiManager.isWifiEnabled();
    }

    public final String getIMEI(Activity activity)
    {
        if(!PermissionsUtil.newInstance(activity).isPermissionGranted(Manifest.permission.READ_PHONE_STATE).isGranted())
        {
            Log.i(C.TAG_LIB, "Missing permission READ_PHONE_STATE");
            return "Missing permission READ_PHONE_STATE";
        }

        TelephonyManager telephonyMgr = (TelephonyManager) activity.getSystemService(Activity.TELEPHONY_SERVICE);
        return telephonyMgr.getDeviceId();
    }

    public final String getIMSI(Activity activity)
    {
        if(!PermissionsUtil.newInstance(activity).isPermissionGranted(Manifest.permission.READ_PHONE_STATE).isGranted())
        {
            Log.i(C.TAG_LIB, "Missing permission  READ_PHONE_STATE");
            return "Missing permission READ_PHONE_STATE";
        }

        TelephonyManager telephonyMgr = (TelephonyManager) activity.getSystemService(Activity.TELEPHONY_SERVICE);
        
        return telephonyMgr.getSubscriberId();
    }

    public final String getPhoneType(Activity activity)
    {
        TelephonyManager tm = (TelephonyManager) activity.getSystemService(Activity.TELEPHONY_SERVICE);
        switch (tm.getPhoneType()) {
            case TelephonyManager.PHONE_TYPE_GSM:
                return C.PHONE_TYPE_GSM;
            case TelephonyManager.PHONE_TYPE_CDMA:
                return C.PHONE_TYPE_CDMA;
            case TelephonyManager.PHONE_TYPE_NONE:
            default:
                return C.PHONE_TYPE_NONE;
        }
    }

    public final String getPhoneNumber(Activity activity)
    {
        if(!PermissionsUtil.newInstance(activity).isPermissionGranted(Manifest.permission.READ_PHONE_STATE).isGranted())
        {
            Log.i(C.TAG_LIB, "Missing permission READ_PHONE_STATE");
            return "Missing permission READ_PHONE_STATE";
        }

        if(!PermissionsUtil.newInstance(activity).isPermissionGranted(Manifest.permission.READ_SMS).isGranted())
        {
            Log.i(C.TAG_LIB, "Missing permission READ_SMS");
            return "Missing permission READ_SMS";
        }

        Uri uriSMSURI = Uri.parse("content://sms/inbox");
        Cursor c = null;
        String phoneNumber="";

        try
        {
            c = activity.getContentResolver().query(uriSMSURI, null, null, null, null);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            Log.i(C.TAG_LIB, e.getMessage());
            return C.ERROR;
        }

        if (c==null){return C.ERROR;}

        if (c != null)
        {
            while (c.moveToNext()) {
                String body = c.getString(c.getColumnIndexOrThrow("body"));
                phoneNumber = c.getString(c.getColumnIndexOrThrow("address"));
            }

            c.close();
        }

        return phoneNumber;
    }

    public final String getOperator(Activity activity)
    {
        String operatorName;
        TelephonyManager telephonyManager =((TelephonyManager) activity.getSystemService(Activity.TELEPHONY_SERVICE));
        operatorName = telephonyManager.getNetworkOperatorName();
        if(operatorName == null || operatorName.trim().isEmpty())
        {
            operatorName = telephonyManager.getSimOperatorName();
        }

        return operatorName;
    }

    public final String getSimSerial(Activity activity)
    {
        if(!PermissionsUtil.newInstance(activity).isPermissionGranted(Manifest.permission.READ_PHONE_STATE).isGranted())
        {
            Log.i(C.TAG_LIB, "Missing permission READ_PHONE_STATE");
            return "Missing permission READ_PHONE_STATE";
        }

        TelephonyManager telephonyManager =((TelephonyManager) activity.getSystemService(Activity.TELEPHONY_SERVICE));
        return telephonyManager.getSimSerialNumber();
    }

    public final boolean isSimNetworkLocked(Activity activity) {
        TelephonyManager telephonyManager =((TelephonyManager) activity.getSystemService(Activity.TELEPHONY_SERVICE));
        return telephonyManager.getSimState() == TelephonyManager.SIM_STATE_NETWORK_LOCKED;
    }




    @SuppressWarnings("MissingPermission")
    public final String getBluetoothMAC(Activity activity)
    {
        if(!PermissionsUtil.newInstance(activity).isPermissionGranted(Manifest.permission.BLUETOOTH).isGranted())
        {
            Log.i(C.TAG_LIB, "Missing permission BLUETOOTH");
            return "Missing permission BLUETOOTH";
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            String mac = Settings.Secure.getString(activity.getContentResolver(), "bluetooth_address");
            return mac;
        }
        else
        {
            BluetoothAdapter bta = BluetoothAdapter.getDefaultAdapter();
            String result = bta != null ? bta.getAddress() : "00";
            return result;
        }
    }

    @SuppressWarnings("MissingPermission")
    public final String getWifiMacAddress(Activity activity)
    {
        if(!PermissionsUtil.newInstance(activity).isPermissionGranted(Manifest.permission.ACCESS_WIFI_STATE).isGranted())
        {
            Log.i(C.TAG_LIB, "Missing permission ACCESS_WIFI_STATE");
            return "Missing permission ACCESS_WIFI_STATE";
        }

        WifiManager manager = (WifiManager) activity.getSystemService(Activity.WIFI_SERVICE);
        WifiInfo info = manager.getConnectionInfo();
        String address = info.getMacAddress();
        return address;
    }
}
