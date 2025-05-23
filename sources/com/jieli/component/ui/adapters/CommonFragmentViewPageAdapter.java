package com.jieli.component.ui.adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import java.util.ArrayList;
import java.util.List;
/* loaded from: classes2.dex */
public class CommonFragmentViewPageAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public void setFragments(List<Fragment> list) {
        this.fragments = new ArrayList(list);
        notifyDataSetChanged();
    }

    public void add(Fragment fragment) {
        if (fragment == null || this.fragments.contains(fragment)) {
            return;
        }
        this.fragments.add(fragment);
        notifyDataSetChanged();
    }

    public void remove(Fragment fragment) {
        if (fragment == null || !this.fragments.contains(fragment)) {
            return;
        }
        this.fragments.remove(fragment);
        notifyDataSetChanged();
    }

    public void clean() {
        this.fragments.clear();
        notifyDataSetChanged();
    }

    public CommonFragmentViewPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.fragments = new ArrayList();
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter
    public Fragment getItem(int i) {
        return this.fragments.get(i);
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return this.fragments.size();
    }

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getItemPosition(Object obj) {
        return this.fragments.indexOf(obj) == -1 ? -2 : -1;
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter
    public long getItemId(int i) {
        if (this.fragments.get(i) != null) {
            i = this.fragments.get(i).hashCode();
        }
        return i;
    }
}
