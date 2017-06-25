package utilities.app;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.LinkedList;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
    private LinkedList<FragmentModel> fragmentList = new LinkedList<>();

    public ViewPagerAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragmentList.get(position).fragment;
    }

    public FragmentModel getFragmentModel(int position)
    {
        return fragmentList.get(position);
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
        fragmentList.add(new FragmentModel(title, fragment));
    }

    public class FragmentModel
    {
        public Fragment fragment;
        public String title;

        public FragmentModel(String title, Fragment fragment)
        {
            this.fragment = fragment;
            this.title = title;
        }
    }
}