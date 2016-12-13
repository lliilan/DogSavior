package com.dogsavior.dogsavior.fragment;

import android.app.Fragment;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.dogsavior.dogsavior.R;
import com.dogsavior.dogsavior.adapter.MessageViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KL on 2016/12/5 0005.
 */

public class MessageFragment extends Fragment {

    private ViewPager mPager;
    private List<View> listViews;
    private ImageView cursor;
    private TextView t1, t2, t3;
    private int offset = 0;
    private int currIndex = 0;
    private int bmpW;
    private View view1,view2,view3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message,container,false);
        InitImageView(view);
        InitTextView(view);
        InitViewPager(view);

        return view;
    }

        private void InitTextView(View view) {
            t1 = (TextView) view.findViewById(R.id.text1);
            t2 = (TextView) view.findViewById(R.id.text2);
            t3 = (TextView) view.findViewById(R.id.text3);

            t1.setText("动态");
            t2.setText("消息");
            t3.setText("聊天");

            t1.setOnClickListener(new MyOnClickListener(0));
            t2.setOnClickListener(new MyOnClickListener(1));
            t3.setOnClickListener(new MyOnClickListener(2));
        }
        private void InitViewPager(View view) {
            mPager = (ViewPager) view.findViewById(R.id.vp_message);
            listViews = new ArrayList<View>();
            LayoutInflater mInflater = getActivity().getLayoutInflater();

            view1 = mInflater.inflate(R.layout.fragment_my, null);
            view2 = mInflater.inflate(R.layout.fragment_my, null);
            view3 = mInflater.inflate(R.layout.fragment_my, null);

            listViews.add(view1);
            listViews.add(view2);
            listViews.add(view3);

            mPager.setAdapter(new MessageViewPagerAdapter(listViews));
            mPager.setCurrentItem(0);
            mPager.setOnPageChangeListener(new MyOnPageChangeListener());
        }

        private void InitImageView(View view) {
            cursor = (ImageView) view.findViewById(R.id.cursor);
            bmpW = BitmapFactory.decodeResource(getResources(), R.mipmap.a)
                    .getWidth();
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            int screenW = dm.widthPixels;//
            offset = (screenW / 3 - bmpW) / 2;
            Matrix matrix = new Matrix();
            matrix.postTranslate(offset, 0);
            cursor.setImageMatrix(matrix);
        }

        public class MyOnClickListener implements View.OnClickListener {
            private int index = 0;

            public MyOnClickListener(int i) {
                index = i;
            }

            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(index);
            }
        }
        public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

            int one = offset * 2 + bmpW;
            int two = one * 2;
            @Override
            public void onPageSelected(int arg0) {
                Animation animation = null;
                switch (arg0) {
                    case 0:
                        if (currIndex == 1) {
                            animation = new TranslateAnimation(one, 0, 0, 0);
                        } else if (currIndex == 2) {
                            animation = new TranslateAnimation(two, 0, 0, 0);
                        }
                        break;
                    case 1:
                        if (currIndex == 0) {
                            animation = new TranslateAnimation(offset, one, 0, 0);
                        } else if (currIndex == 2) {
                            animation = new TranslateAnimation(two, one, 0, 0);
                        }
                        break;
                    case 2:
                        if (currIndex == 0) {
                            animation = new TranslateAnimation(offset, two, 0, 0);
                        } else if (currIndex == 1) {
                            animation = new TranslateAnimation(one, two, 0, 0);
                        }
                        break;
                }
                currIndex = arg0;
                animation.setFillAfter(true);
                animation.setDuration(300);
                cursor.startAnimation(animation);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        }

}
