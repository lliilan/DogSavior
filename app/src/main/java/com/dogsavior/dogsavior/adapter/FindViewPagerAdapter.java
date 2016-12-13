package com.dogsavior.dogsavior.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by KL on 2016/12/13 0013.
 */

public class FindViewPagerAdapter extends PagerAdapter {

    private List<View> viewlist;

    public FindViewPagerAdapter(List<View> viewlist) {
        this.viewlist = viewlist;
    }
    @Override
    public int getCount() {
        return viewlist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewlist.get(position), 0);
        return viewlist.get(position);
    }
}
