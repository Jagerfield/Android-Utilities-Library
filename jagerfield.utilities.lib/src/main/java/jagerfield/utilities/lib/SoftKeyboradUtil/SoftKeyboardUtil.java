package jagerfield.utilities.lib.SoftKeyboradUtil;

import android.app.Activity;
import android.view.inputmethod.InputMethodManager;

public class SoftKeyboardUtil
{
    public SoftKeyboardUtil() {
    }

    public static SoftKeyboardUtil newInstance()
    {
        return new SoftKeyboardUtil();
    }

    public void setSoftKeyboard(Activity activity, boolean mode)
    {
        if (activity == null)
        {
            return;
        }

        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(activity.INPUT_METHOD_SERVICE);

        if (mode)
        {
            inputMethodManager.toggleSoftInput(0, InputMethodManager.SHOW_IMPLICIT);
        }
        else {
            if (inputMethodManager != null)
            {
                if (activity == null)
                    return;
                if (activity.getCurrentFocus() == null)
                    return;
                if (activity.getCurrentFocus().getWindowToken() == null)
                    return;
                inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
            }
        }
    }
}
