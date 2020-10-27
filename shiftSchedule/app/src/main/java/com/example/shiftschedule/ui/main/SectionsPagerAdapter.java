package com.example.shiftschedule.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.shiftschedule.F_schedule_day_view;
import com.example.shiftschedule.F_schedule_month_view;
import com.example.shiftschedule.F_schedule_week_view;
import com.example.shiftschedule.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    // TAB INSTANCES (TO CHANGE THE TEXT GO TO strings.xml and look up tab_text_#
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    // To switch between tabs
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new F_schedule_month_view();
                break;
            case 1:
                fragment = new F_schedule_week_view();
                break;
            case 2:
                fragment = new F_schedule_day_view();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // SHOW TOTAL OF 3 TABS IN TABBED ACTIVITY.
        return 3;
    }
}