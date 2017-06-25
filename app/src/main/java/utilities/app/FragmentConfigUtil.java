package utilities.app;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.support.v4.content.ContextCompat;
import java.util.ArrayList;

import utilities.app.UtilitiesData.BatteryUtilData;
import utilities.app.UtilitiesData.DeviceUtilData;
import utilities.app.UtilitiesData.MemoryUtilData;
import utilities.app.UtilitiesData.NetworkUtilData;
import utilities.app.UtilitiesData.PropertyModel;


public class FragmentConfigUtil
{
    public static final String PERMISSIONS_TITLE = "Permissions";
    public static final String MEMORY_INFO_TAB = "Memory Info";
    public static final String NETWORK_INFO_TITLE = "Network Info";
    public static final String DEVICE_INFO_TITLE = "Device Info";
    public static final String BATTERY_INFO_TITLE = "Battery Info";
    public static final String FRAGMENT_TITLE = "FRAGMENT_TITLE";
    public static final String TAG_LIB = "TAG_LIB";

    public static final String[] PERMISSIONS_ARRAY = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.BODY_SENSORS,
            Manifest.permission.USE_SIP,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    public static void showAlertMessage(final Activity activity, final String title, final String message) {
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setCancelable(true);
                    builder.setTitle(title);
                    builder.setMessage(message);
                    builder.show();
                }
            });
        }
    }

    public FragmentConfigUtil()
    {

    }

    public static FragmentConfigUtil newInstance()
    {
        FragmentConfigUtil obj = new FragmentConfigUtil();
        return obj;
    }

    public void addProperty(ArrayList<PropertyModel> properties, String name, String value)
    {
        if (name == null || name.isEmpty())
        {
            return;
        }

        PropertyModel property = new PropertyModel();
        property.setPropertyType(name);

        try
        {
            property.setValue(value);
            properties.add(property);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FragmentData getFragmentData(String title, Activity activity)
    {
        FragmentData fragmentData = new FragmentData(activity);

        switch(title)
        {
            case FragmentConfigUtil.MEMORY_INFO_TAB:
                fragmentData.propertiesList = MemoryUtilData.newInstance().getDeviceMemoryProperties(activity);
                fragmentData.valueTextColor = ContextCompat.getColor(activity, R.color.list_grey_800_50p);
                break;

            case FragmentConfigUtil.NETWORK_INFO_TITLE:
                fragmentData.propertiesList = NetworkUtilData.getInstance().getDeviceNetworkProperties(activity);
                fragmentData.valueTextColor = ContextCompat.getColor(activity, R.color.list_blue);
                break;

            case FragmentConfigUtil.DEVICE_INFO_TITLE:
                fragmentData.propertiesList = DeviceUtilData.getInstance().getDeviceProperties(activity);
                fragmentData.valueTextColor = ContextCompat.getColor(activity, R.color.list_green);
                break;

            case FragmentConfigUtil.BATTERY_INFO_TITLE:
                fragmentData.propertiesList = BatteryUtilData.getInstance().getDeviceBatteryProperties(activity);
                fragmentData.valueTextColor = ContextCompat.getColor(activity, R.color.list_reddish_orange);
                break;
            default:

                break;
        }

        return fragmentData;
    }

    public void addProperty(ArrayList<PropertyModel> properties, String name, String value, String suffex)
    {
        if (name == null || name.isEmpty())
        {
            return;
        }
        if (suffex==null || suffex.trim().isEmpty())
        {
            suffex="";
        }
        else
        {
            suffex = " " + suffex;
        }

        PropertyModel property = new PropertyModel();
        property.setPropertyType(name);
        try
        {
            property.setValue(value + suffex);
            properties.add(property);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProperty(ArrayList<PropertyModel> properties, String name, long value, int factor, String suffex)
    {
        if (name == null || name.isEmpty())
        {
            return;
        }
        if (suffex==null || suffex.trim().isEmpty())
        {
            suffex="";
        }
        else
        {
            suffex = " " + suffex;
        }

        PropertyModel property = new PropertyModel();
        property.setPropertyType(name);

        try
        {
            property.setValue(String.valueOf(value/factor) + suffex);
            properties.add(property);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProperty(ArrayList<PropertyModel> properties, String name, boolean value)
    {
        if (name == null || name.isEmpty())
        {
            return;
        }

        PropertyModel property = new PropertyModel();
        property.setPropertyType(name);

        try
        {
            String str = String.valueOf(value);
            char c = Character.toUpperCase(str.charAt(0));
            str = str.replaceFirst(Character.toString(str.charAt(0)), Character.toString(c));

            property.setValue(str);
            properties.add(property);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProperty(ArrayList<PropertyModel> properties, String name, float value)
    {
        if (name == null || name.isEmpty())
        {
            return;
        }

        PropertyModel property = new PropertyModel();
        property.setPropertyType(name);

        try
        {
            String str = String.valueOf(value);

            property.setValue(str);
            properties.add(property);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProperty(ArrayList<PropertyModel> properties, String name, int value)
    {
        if (name == null || name.isEmpty())
        {
            return;
        }

        PropertyModel property = new PropertyModel();
        property.setPropertyType(name);

        try
        {
            String str = String.valueOf(value);

            property.setValue(str);
            properties.add(property);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProperty(ArrayList<PropertyModel> properties, String name, Integer value)
    {
        if (name == null || name.isEmpty())
        {
            return;
        }

        PropertyModel property = new PropertyModel();
        property.setPropertyType(name);

        try
        {
            String str= "";

            if (value == null)
            {
                str = "Can't get value";
            }

            str = String.valueOf(value);

            property.setValue(str);
            properties.add(property);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addProperty(ArrayList<PropertyModel> properties, String name, Boolean value)
    {
        if (name == null || name.isEmpty())
        {
            return;
        }

        PropertyModel property = new PropertyModel();
        property.setPropertyType(name);

        try
        {
            String str = "";
            if (value==null)
            {
                str = "Can't read property";
            }
            else
            {
                str = String.valueOf(value);
                char c = Character.toUpperCase(str.charAt(0));
                str = str.replaceFirst(Character.toString(str.charAt(0)), Character.toString(c));
            }

            property.setValue(str);
            properties.add(property);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class FragmentData
    {
        private int valueTextColor = -1;
        private ArrayList<PropertyModel> propertiesList = new ArrayList<>();

        public FragmentData(Activity activity)
        {
            valueTextColor = ContextCompat.getColor(activity, R.color.sand);
        }

        public ArrayList<PropertyModel> getPropertiesList() {
            return propertiesList;
        }

        public void setPropertiesList(ArrayList<PropertyModel> propertiesList) {
            this.propertiesList = propertiesList;
        }

        public int getValueTextColor() {
            return valueTextColor;
        }

        public void setValueTextColor(int valueTextColor)
        {
            this.valueTextColor = valueTextColor;
        }

    }
}
