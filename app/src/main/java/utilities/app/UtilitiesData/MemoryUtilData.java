package utilities.app.UtilitiesData;

import android.app.Activity;
import java.util.ArrayList;
import jagerfield.utilities.lib.AppUtilities;
import utilities.app.D;

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
        D d = D.newInstance();
        int factor = 1000000;

        d.addProperty(properties, "Has External SD Card", AppUtilities.getMemoryUtil().hasExternalSDCard());

        d.addProperty(properties, "Total RAM", AppUtilities.getMemoryUtil().getTotalRAM(activity), factor, "MB");

        d.addProperty(properties, "Available Internal Memory Size", AppUtilities.getMemoryUtil().getAvailableInternalMemorySize(activity), factor, "MB");

        d.addProperty(properties, "Total Internal Memory Size", AppUtilities.getMemoryUtil().getTotalInternalMemorySize(), factor, "MB");

        d.addProperty(properties, "Available External Memory Size", AppUtilities.getMemoryUtil().getAvailableExternalMemorySize(), factor, "MB");

        d.addProperty(properties, "Total External Memory Size", AppUtilities.getMemoryUtil().getTotalExternalMemorySize(), factor, "MB");

        return properties;
    }

}
