package utilities.app;

import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import jagerfield.utilities.lib.AppUtilities;
import jagerfield.utilities.lib.C;
import jagerfield.utilities.lib.PermissionsUtil.GuiDialog.PermissionsManager;
import jagerfield.utilities.lib.PermissionsUtil.PermissionsUtil;
import jagerfield.utilities.lib.PermissionsUtil.Results.IGetPermissionResult;
import utilities.app.Fragments.ShowInfoFragment;


public class MainActivity extends AppCompatActivity
{
    private TabLayout tabLayout;
    private ViewPager viewPager;

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
            permissionsUtil.requestPermissions(D.PERMISSIONS_ARRAY);
        }
        else if (!result.isGranted() && Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
        {
            /**
             * For SDK < M, there are permissions missing in the manifest
             */
            String missingPermissions = TextUtils.join(", ", result.getMissingInManifest_ForSdkBelowM()).trim();
            D.showAlertMessage(this, "Manifest Missing Permissions", missingPermissions);
            Log.e(D.TAG_LIB, "Following permissions are missing : " + missingPermissions);
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
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

//        viewPagerAdapter.addTab(D.PERMISSIONS_TITLE, new PermissionsFragment());
        viewPagerAdapter.addTab(D.MEMORY_INFO_TAB, ShowInfoFragment.newInstance(D.MEMORY_INFO_TAB));
        viewPagerAdapter.addTab(D.NETWORK_INFO_TITLE, ShowInfoFragment.newInstance(D.NETWORK_INFO_TITLE));
        viewPagerAdapter.addTab(D.DEVICE_INFO_TITLE, ShowInfoFragment.newInstance(D.DEVICE_INFO_TITLE));
        viewPagerAdapter.addTab(D.BATTERY_INFO_TITLE, ShowInfoFragment.newInstance(D.BATTERY_INFO_TITLE));

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        /**
         * Selected tab text color can be made from styles
         */
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab)
            {
                tab.getIcon().setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.white), PorterDuff.Mode.SRC_IN);
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

    public class ViewPagerAdapter extends FragmentPagerAdapter
    {
        private final ArrayList<FragModel> fragmentList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }


        @Override
        public Fragment getItem(int position)
        {
            return fragmentList.get(position).fragment;
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position)
        {

            return fragmentList.get(position).title;
        }

        public void addTab(String title, Fragment fragment)
        {
            fragmentList.add(new FragModel(title, fragment));
        }

        class FragModel
        {
            Fragment fragment;
            String title;

            public FragModel(String title, Fragment fragment) {
                this.fragment = fragment;
                this.title = title;
            }
        }
    }
}




















