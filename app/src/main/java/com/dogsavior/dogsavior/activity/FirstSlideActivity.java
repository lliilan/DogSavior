package com.dogsavior.dogsavior.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dogsavior.dogsavior.R;
import com.dogsavior.dogsavior.adapter.ViewPagerAdapter;
import com.dogsavior.dogsavior.util.PreferenceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KL on 2016/11/29 0029.
 */

public class FirstSlideActivity extends Activity implements View.OnClickListener,ViewPager.OnPageChangeListener{

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private List<View> viewList;
    private GestureDetector gestureDetector;

    //引导图片资源
    private static final int[] pics = {R.mipmap.pager1,R.mipmap.pager2,R.mipmap.pager3,R.mipmap.pager4};
    //底部点（dot）资源
    private ImageView[] dots;
    //记录当前选中位置
    private int currentIndex;
    //用于判断是否显示引导界面，默认为不显示
    private boolean isShow = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_slide_layout);

        isShow = PreferenceUtil.getBoolean(getApplication(),PreferenceUtil.SHOW_FIRST_SLIDE);
        if (isShow){
            IntetnMain();
        }else{
            viewList = new ArrayList<View>();
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);

            for (int i = 0;i<pics.length;i++){
                ImageView iv = new ImageView(this);
                iv.setLayoutParams(params);
                iv.setBackgroundResource(pics[i]);
                viewList.add(iv);
            }
            viewPager = (ViewPager) findViewById(R.id.viewpager);
            viewPagerAdapter = new ViewPagerAdapter(viewList);
            viewPager.setAdapter(viewPagerAdapter);
            viewPager.setOnPageChangeListener(this);

            initDots();
            slipToMain();
        }

    }

    /**
     * 初始化底部dot(点)
     */
    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        dots = new ImageView[pics.length];

        for (int i = 0; i < pics.length; i++) {
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setImageResource(R.mipmap.dot);//都设为灰色
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);//设置位置tag，方便取出与当前位置对应
        }

        currentIndex = 0;
        dots[currentIndex].setImageResource(R.mipmap.dot2);//设置为白色，即选中状态
    }

    private void IntetnMain(){
        startActivity(new Intent(getApplication(),MainActivity.class));
        finish();
    }

    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }

        viewPager.setCurrentItem(position);
    }

    private void setCurDot(int positon) {
        if (positon < 0 || positon > pics.length - 1 || currentIndex == positon) {
            return;
        }

        dots[positon].setImageResource(R.mipmap.dot2);
        dots[currentIndex].setImageResource(R.mipmap.dot);

        currentIndex = positon;
    }

    /**
     * 判断是否跳转到MainActivity
     */
    private void slipToMain(){
        gestureDetector = new GestureDetector(getApplication(),
                new GestureDetector.SimpleOnGestureListener(){
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                        if (currentIndex == 3){
                            if ((e1.getRawX() - e2.getRawX()) >= 0){
                                PreferenceUtil.setBoolean(getApplicationContext(), PreferenceUtil.SHOW_FIRST_SLIDE, true);
                                IntetnMain();
                                return true;
                            }
                        }
                        return false;
                    }
                });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (gestureDetector.onTouchEvent(event)) {
            event.setAction(MotionEvent.ACTION_CANCEL);
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public void onClick(View v) {
        int position = (Integer)v.getTag();
        setCurView(position);
        setCurDot(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        setCurDot(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
