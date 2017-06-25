package utilities.app.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import utilities.app.FragmentConfigUtil;
import utilities.app.MainActivity;
import utilities.app.R;
import utilities.app.UtilitiesData.GetDataAsync;
import utilities.app.UtilitiesData.PropertyModel;


public class DataFragment extends Fragment
{
    private String title;
    private RecyclerView recyclerView;
    private DeviceInfoListViewAdapter adapter;

    public DataFragment()
    {    }

    public static DataFragment newInstance(String title)
    {
        DataFragment fragment = new DataFragment();
        Bundle args = new Bundle();
        args.putString(FragmentConfigUtil.FRAGMENT_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    public void updateData(String title)
    {
       new GetDataAsync(getActivity(), title, new GetDataAsync.ICallback()
       {
           @Override
           public void updateFragmentData(FragmentConfigUtil.FragmentData fragmentData, Exception e)
           {
               if (adapter==null || fragmentData==null || e!=null)
               {
                   return;
               }
               adapter.updateList(fragmentData);
           }
       }).execute();
//        FragmentConfigUtil.FragmentData updateFragmentData = FragmentConfigUtil.newInstance().getFragmentData(title, getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_deviceinfo_list, container, false);
        Context context = view.getContext();

        FragmentConfigUtil.FragmentData fragmentData = null;

        if (getArguments() != null)
        {
            title = getArguments().getString(FragmentConfigUtil.FRAGMENT_TITLE, "");
            fragmentData = FragmentConfigUtil.newInstance().getFragmentData(title, getActivity());
        }

        recyclerView = (RecyclerView) view.findViewById(R.id.deviceInfolist);
        recyclerView.setLayoutManager(new GridLayoutManager(context, 2));
        adapter = new DeviceInfoListViewAdapter(fragmentData);
        recyclerView.setAdapter(adapter);

        return view;
    }


    /**
 * Fragment List Adapter
 */
private class DeviceInfoListViewAdapter extends RecyclerView.Adapter<DeviceInfoListViewAdapter.ViewHolder>
{
    private ArrayList<PropertyModel> itemsList = new ArrayList<>();
    private FragmentConfigUtil.FragmentData fragmentData;

    public DeviceInfoListViewAdapter(FragmentConfigUtil.FragmentData fragmentData)
    {
        this.itemsList = fragmentData.getPropertiesList();
        this.fragmentData = fragmentData;
    }

    @Override
    public DeviceInfoListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_deviceinfo_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DeviceInfoListViewAdapter.ViewHolder holder, final int position)
    {
        holder.vhObject = itemsList.get(position);
        holder.propertyType.setText(holder.vhObject.getPropertyType());
        holder.value.setText(holder.vhObject.getValue());
        holder.value.setBackgroundColor(fragmentData.getValueTextColor());
        holder.value.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public void updateList(FragmentConfigUtil.FragmentData fragmentData)
    {
        this.itemsList = fragmentData.getPropertiesList();
        this.fragmentData = fragmentData;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View hView;
        public final TextView propertyType;
        public final TextView value;
        public PropertyModel vhObject;

        public ViewHolder(View view)
        {
            super(view);
            hView = view;
            propertyType = (TextView) view.findViewById(R.id.propertyType);
            value = (TextView) view.findViewById(R.id.propertyValue);
        }
    }


}



}


