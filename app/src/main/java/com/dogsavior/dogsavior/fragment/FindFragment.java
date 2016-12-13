package com.dogsavior.dogsavior.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dogsavior.dogsavior.R;
import com.dogsavior.dogsavior.adapter.FindViewPagerAdapter;
import com.dogsavior.dogsavior.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KL on 2016/12/10 0010.
 */

public class FindFragment extends Fragment {

    private ViewPager viewPager;
    private PagerAdapter adapter;
//    private FindViewPagerAdapter adapter;
//    private List<View> viewlist = new ArrayList<View>();

    private int[] pic = {R.mipmap.slide1,R.mipmap.slide2,R.mipmap.slide3,R.mipmap.slide4,R.mipmap.slide3};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find,container,false);

        initView(view);
        return view;
    }

    private void initView(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.vp_find);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageMargin(30);
        viewPager.setAdapter(adapter = new PagerAdapter() {
            @Override
            public Object instantiateItem(ViewGroup container, int position)
            {
                ImageView view = new ImageView(getActivity());
                view.setImageResource(pic[position]);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object)
            {
                container.removeView((View) object);
            }

            @Override
            public int getCount() {
                return pic.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        });
//        for (int i = 0; i<pic.length; i++){
//            ImageView iv = new ImageView(getActivity());
//            iv.setImageResource(pic[i]);
//            viewlist.add(iv);
//        }
//        adapter = new FindViewPagerAdapter(viewlist);
//        viewPager.setAdapter(adapter);
//        viewPager.setCurrentItem(1);



    }
}
