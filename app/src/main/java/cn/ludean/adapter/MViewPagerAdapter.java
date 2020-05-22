package cn.ludean.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class MViewPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;
    private ArrayList<String> tabs;

    public MViewPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    public MViewPagerAdapter(FragmentManager fm, ArrayList<String> tabs, ArrayList<Fragment> fragments) {
        super(fm);
        this.tabs = tabs;
        this.fragments = fragments;
    }
    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (tabs!=null){
            return tabs.get(position);
        }
        return super.getPageTitle(position);
    }

}
