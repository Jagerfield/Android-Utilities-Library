package utilities.app.UtilitiesData;

import android.app.Activity;
import android.os.AsyncTask;

import utilities.app.FragmentConfigUtil;
import utilities.app.MainActivity;
import utilities.app.ViewPagerAdapter;

public class GetDataAsync extends AsyncTask<Void, Void, Void>
{
    private Activity activity;
    private String title;
    private ICallback client;
    private Exception exception;
    private FragmentConfigUtil.FragmentData fragmentData;

    public GetDataAsync(Activity activity, String title, ICallback client)
    {
        this.activity = activity;
        this.title = title;
        this.client = client;
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        client.updateFragmentData(fragmentData, exception);
    }

    @Override
    protected Void doInBackground(Void... voids)
    {
        try
        {
            fragmentData = FragmentConfigUtil.newInstance().getFragmentData(title, activity);
            Thread.sleep(400);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            exception = e;
        }
        return null;
    }

    public interface ICallback
    {
        void updateFragmentData(FragmentConfigUtil.FragmentData fragmentData, Exception e);
    }
}
