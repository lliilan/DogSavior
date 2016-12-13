package com.dogsavior.dogsavior.customsview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dogsavior.dogsavior.R;
import com.dogsavior.dogsavior.thread.HttpThread;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Banner(广告)封装
 * Created by KL on 2016/12/8 0008.
 */

public class BannerView extends RelativeLayout {

    private Handler handler = new Handler();
    private ViewPager mViewPager;
    private LinearLayout mLinearLayout;
    private Context mContext;
    private ImageView[] mIndicator;     //用于存储要播放的图片
    private Handler mHandler = new Handler();
    final List<String> mList = new ArrayList<>();
    //储存textview中的文字内容
    private List<String> mTextList;
    private TextView mtextView;

    private OnBannerItemClickListener mOnBannerItemClickListener;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            //设置将要显示的页面
            mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            //设置循环
            //postDelayed是一种可以创建多线程消息的方法
            mHandler.postDelayed(mRunnable, 5000);
        }
    };

    //记录有多少个需要轮播的图片
    private int mItemCount;

    public interface OnBannerItemClickListener {
        void onClick(int position);
    }

    public BannerView(Context context) {
        this(context, null);
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        //初始化布局
        init();
    }

    private void init() {
        View.inflate(mContext, R.layout.banner_layout, this);
        // 取到布局中的控件
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mLinearLayout = (LinearLayout) findViewById(R.id.ll_points);
    }

    /**
     * 给banner中的viewpager设置数据
     * 当给viewPager设置数据的时候，会自动给viewPager添加一个适配器
     *
     * @param list
     */
    public void setList(List<String> list,List<String> textList) {
        if (mList.size() == 0) {
            mList.addAll(list);
            mItemCount = mList.size();
            initView();
        }
        mTextList = textList;
    }

    /**
     * banner item的点击监听
     *
     * @param onBannerItemClickListener
     */
    public void setOnBannerItemClickListener(OnBannerItemClickListener onBannerItemClickListener) {
        mOnBannerItemClickListener = onBannerItemClickListener;
    }

    private void initView() {
        // 给viewpager设置adapter
        BannerPagerAdapter bannerPagerAdapter = new BannerPagerAdapter(mList, mContext);
        mViewPager.setAdapter(bannerPagerAdapter);
        // 初始化底部点指示器
        initIndicator(mList, mContext);
        mViewPager.setCurrentItem(500 * mItemCount);

        // 给viewpager设置滑动监听
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //改变底部点的样子
                switchIndicator(position % mItemCount);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 设置触摸事件，按下就停止自动变换图片，手指抬起就开始继续变化
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                cancelRecycle();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                startRecycle();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 初始化底部点指示器
     *
     * @param list
     * @param context
     */
    private void initIndicator(List<String> list, Context context) {
        mIndicator = new ImageView[mItemCount];
        for (int i = 0; i < mIndicator.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(12, 12);
            //设置页边空白（左右留白）
            params.setMargins(6, 0, 6, 0);
            ImageView imageView = new ImageView(context);
            mIndicator[i] = imageView;
            if (i == 0) {
                mIndicator[i].setBackgroundResource(R.drawable.dot_focused);
            } else {
                mIndicator[i].setBackgroundResource(R.drawable.dot_narmal);
            }
            mLinearLayout.addView(imageView, params);
        }
        if (mItemCount == 1) {
            mLinearLayout.setVisibility(View.GONE);
        } else {
            mLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    //为每个底部点设置图片样子，被选中为白色，未被选中为半透明
    private void switchIndicator(int selectItems) {
        for (int i = 0; i < mIndicator.length; i++) {
            if (i == selectItems) {
                mIndicator[i].setBackgroundResource(R.drawable.dot_focused);
            } else {
                mIndicator[i].setBackgroundResource(R.drawable.dot_narmal);
            }
        }
    }

    /**
     * 开始循环
     * 利用Handler和Runable创建一个循环
     */
    private void startRecycle() {
        //5秒后调用 mRunnable 对象
        mHandler.postDelayed(mRunnable, 5000);
    }

    /**
     * 停止循环
     */
    private void cancelRecycle() {
        mHandler.removeCallbacks(mRunnable);
    }

    @Override
    protected void onWindowVisibilityChanged(int visibility) {
        super.onWindowVisibilityChanged(visibility);
        if (visibility == VISIBLE) {
            startRecycle();
        } else {
            cancelRecycle();
        }
    }

    private class BannerPagerAdapter extends PagerAdapter {
        //储存要播放的图片的URL地址
        private List<String> imagesUrl;
        private Context context;

        public BannerPagerAdapter(List<String> imagesUrl, Context context) {
            this.imagesUrl = imagesUrl;
            this.context = context;
        }

        @Override
        public int getCount() {
            //将播放设置为一个极大值
            return mItemCount == 1 ? 1 : Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View ret = null;
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            //联网取图片，根据自己的情况修改,这里我们用volley加载图片
            new HttpThread(imagesUrl.get(position % mItemCount),imageView,handler,context).start();
            mtextView = (TextView) findViewById(R.id.tv);
            mtextView.setText(mTextList.get(position % mItemCount).toString());
            ret = imageView;
            container.addView(ret);
            ret.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnBannerItemClickListener.onClick(position);
                }
            });
            return ret;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
