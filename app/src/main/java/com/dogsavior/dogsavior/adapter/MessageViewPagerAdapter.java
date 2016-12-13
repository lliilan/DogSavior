package com.dogsavior.dogsavior.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by KL on 2016/12/13 0013.
 */

public class MessageViewPagerAdapter extends PagerAdapter{

        public List<View> mListViews ;  //界面数据源
        private Context content;
        public LayoutInflater mInflater;

        public MessageViewPagerAdapter(List<View> mListViews ){
            this.mListViews=mListViews;
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view ==(object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager)container).removeView(mListViews.get(position));    //清除当前viewpager
        }

        @Override
        public Object instantiateItem(View container, int position) {
            ((ViewPager)container).addView(mListViews.get(position),0);  //添加viewpager
            return mListViews.get(position);
        }
}
