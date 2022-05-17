package com.supersweet.luck.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.supersweet.luck.fragment.RuleEngineFragment;


/**
 * 场景页面 ViewPager 的adapter
 */
public class RuleEnginePagerAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;

    public RuleEnginePagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        fragments = new Fragment[]{new RuleEngineFragment(11111)};
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0)
            return "一键联动";
        if (position == 1)
            return "自动化";
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
