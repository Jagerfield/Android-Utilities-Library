package utilities.app.UtilitiesData;

import android.app.Activity;
import java.util.ArrayList;
import jagerfield.utilities.lib.AppUtilities;
import utilities.app.FragmentConfigUtil;

public class MemoryUtilData
{
    public MemoryUtilData()
    {

    }

    public static MemoryUtilData newInstance()
    {
        MemoryUtilData memoryUtilData = new MemoryUtilData();
        return memoryUtilData;
    }

    public ArrayList<PropertyModel> getDeviceMemoryProperties(Activity activity)
    {
        ArrayList<PropertyModel> properties = new ArrayList<>();
        FragmentConfigUtil fragmentConfigUtil = FragmentConfigUtil.newInstance();
        int factor = 1000000;

        fragmentConfigUtil.addProperty(properties, "Has External SD Card", AppUtilities.getMemoryUtil().hasExternalSDCard());

        fragmentConfigUtil.addProperty(properties, "Total RAM", AppUtilities.getMemoryUtil().getTotalRAM(activity), factor, "MB");

        fragmentConfigUtil.addProperty(properties, "Available Internal Memory Size", AppUtilities.getMemoryUtil().getAvailableInternalMemorySize(activity), factor, "MB");

        fragmentConfigUtil.addProperty(properties, "Total Internal Memory Size", AppUtilities.getMemoryUtil().getTotalInternalMemorySize(), factor, "MB");

        fragmentConfigUtil.addProperty(properties, "Available External Memory Size", AppUtilities.getMemoryUtil().getAvailableExternalMemorySize(), factor, "MB");

        fragmentConfigUtil.addProperty(properties, "Total External Memory Size", AppUtilities.getMemoryUtil().getTotalExternalMemorySize(), factor, "MB");

        return properties;
    }

}
