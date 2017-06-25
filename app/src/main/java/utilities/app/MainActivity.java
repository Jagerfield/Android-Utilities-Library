package utilities.app;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import jagerfield.utilities.lib.AppUtilities;
import jagerfield.utilities.lib.C;
import jagerfield.utilities.lib.PermissionsUtil.GuiDialog.PermissionsManager;
import jagerfield.utilities.lib.PermissionsUtil.PermissionsUtil;
import jagerfield.utilities.lib.PermissionsUtil.Results.IGetPermissionResult;
import utilities.app.Fragments.DataFragment;

public class MainActivity extends AppCompatActivity
{
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private static int current_fragment_pos;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        /**
         * Check for missing permissions and request them
         */
        checkAppPermissions(C.PERMISSIONS_ARRAY);

        Snackbar.make(viewPager,"sdfs",Snackbar.LENGTH_LONG).show();
    }

    private void checkAppPermissions(String[] permissionsArray)
    {
        PermissionsUtil permissionsUtil = AppUtilities.getPermissionUtil(this);

        IGetPermissionResult result = permissionsUtil.getPermissionResults(permissionsArray);

        if (result == null) { return; }
        else if (result.isGranted())
        {
            /**
             * All Permissions are granted
             */
            launchViewPager();
        }
        else if (!result.isGranted() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            /**
             * There are missing permissions ask for them
             */
            permissionsUtil.requestPermissions(FragmentConfigUtil.PERMISSIONS_ARRAY);
        }
        else if (!result.isGranted() && Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            /**
             * For SDK < M, there are permissions missing in the manifest
             */
            String missingPermissions = TextUtils.join(", ", result.getMissingInManifest_ForSdkBelowM()).trim();
            FragmentConfigUtil.showAlertMessage(this, "Manifest Missing Permissions", missingPermissions);
            Log.e(FragmentConfigUtil.TAG_LIB, "Following permissions are missing : " + missingPermissions);
        }

    }

    /**
     * The "setUserInterface" func in the "PermissionsNotificationDialog" will respond to the
     * permissions requests and the call back is provided here
     *
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        PermissionsUtil permissionsUtil = AppUtilities.getPermissionUtil(this);

        if (requestCode == permissionsUtil.getPermissionsReqCodeId())
        {
            IGetPermissionResult result = null;
            result = permissionsUtil.getPermissionResults(permissions);

            if (result.isGranted())
            {
                launchViewPager();
                return;
            }

            PermissionsManager.getNewInstance(this, result, permissions, new PermissionsManager.PermissionsManagerCallback()
            {
                @Override
                public void onPermissionsGranted(IGetPermissionResult result) {

                    /**
                     * User accepted all requested permissions
                     */
                    launchViewPager();
                }

                @Override
                public void onPermissionsMissing(IGetPermissionResult result)
                {
                    Toast.makeText(MainActivity.this, "User didn't accept all permissions", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void launchViewPager()
    {
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

//      viewPagerAdapter.addTab(FragmentConfigUtil.PERMISSIONS_TITLE, new PermissionsFragment());
        viewPagerAdapter.addTab(FragmentConfigUtil.MEMORY_INFO_TAB, DataFragment.newInstance(FragmentConfigUtil.MEMORY_INFO_TAB));
        viewPagerAdapter.addTab(FragmentConfigUtil.NETWORK_INFO_TITLE, DataFragment.newInstance(FragmentConfigUtil.NETWORK_INFO_TITLE));
        viewPagerAdapter.addTab(FragmentConfigUtil.DEVICE_INFO_TITLE, DataFragment.newInstance(FragmentConfigUtil.DEVICE_INFO_TITLE));
        viewPagerAdapter.addTab(FragmentConfigUtil.BATTERY_INFO_TITLE, DataFragment.newInstance(FragmentConfigUtil.BATTERY_INFO_TITLE));

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                String str = "";
            }

            @Override
            public void onPageSelected(final int position)
            {
                if (viewPagerAdapter==null)
                {
                    return;
                }

                DataFragment fragment = (DataFragment) viewPagerAdapter.getFragmentModel(position).fragment;
                fragment.updateData(viewPagerAdapter.getFragmentModel(position).title);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
                String str = "";
            }
        });


        /**
         * Selected tab text color can be made from styles
         */
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                if (tab==null)
                {
                    return;
                }

                try
                {
                    tab.getIcon().setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), PorterDuff.Mode.SRC_IN);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab)
            {
                tab.getIcon().setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.unselected_tab), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab)
            {

            }
        });

        initiateTabIconColors();
    }

    private void initiateTabIconColors()
    {
        for (int i = 0; i <tabLayout.getTabCount() ; i++)
        {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);

            if (i==0)
            {
                tabLayout.getTabAt(i).getIcon().setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), PorterDuff.Mode.SRC_IN);
            }
            else
            {
                tabLayout.getTabAt(i).getIcon().setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.unselected_tab), PorterDuff.Mode.SRC_IN);
            }
        }
    }

    private int[] tabIcons = {
            R.drawable.permission_white,
            R.drawable.memory_white,
            R.drawable.network_white,
            R.drawable.device_white,
            R.drawable.battery_white
    };


}




















