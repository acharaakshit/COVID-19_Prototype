package com.example.htc20;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MyStoreAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES =
            new int[] {R.string.qr_code, R.string.store_details, R.string.pending_orders};

    private Context myContext;
    int totalTabs;
    private String unique_id;
    private String location;
    private String name;

    public MyStoreAdapter(Context context, FragmentManager fm, int totalTabs, String unique_id, String name, String location) {
        super(fm);
        myContext = context;
        this.totalTabs = totalTabs;
        this.unique_id = unique_id;
        this.name = name;
        this.location = location;
    }

    // this is for fragment tabs
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                QRCodeFragment qr_fragment = new QRCodeFragment().newInstance(unique_id);
                return qr_fragment;
            case 1:
                StoreDetailsFragment store_details_fragment = new StoreDetailsFragment().newInstance(unique_id, location, name);
                return store_details_fragment;
            case 2:
                PendingOrdersFragment pending_fragment = new PendingOrdersFragment().newInstance(unique_id, "bellow");
                return pending_fragment;
            default:
                return null;
        }
    }
    // this counts total number of tabs
    @Override
    public int getCount() {
        return totalTabs;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return myContext.getResources().getString(TAB_TITLES[position]);
    }
}

