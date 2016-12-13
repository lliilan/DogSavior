package com.dogsavior.dogsavior.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.dogsavior.dogsavior.R;
import com.dogsavior.dogsavior.customsview.BannerView;
import com.dogsavior.dogsavior.customsview.MyViewPager;
import com.dogsavior.dogsavior.model.BannerViewURL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by KL on 2016/12/5 0005.
 */

public class HomeFragment extends Fragment{

    //自定义的广告图片轮播组件
    private BannerView bannerView;
    private ListView lv;
    private SimpleAdapter adapter;
    private List<Map<String ,Object>> list = new ArrayList<Map<String ,Object>>();
    private EditText et_title_search;

    private MyViewPager viewPager;
    private int[] pic;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        initBannerView(view);
        initView(view);
//        initViewPager(view);

        return view;
    }

    private void initViewPager(View view) {
        pic = new int[] {R.mipmap.slide1,R.mipmap.slide2,R.mipmap.slide3,R.mipmap.slide4,R.mipmap.pager4};
        viewPager = (MyViewPager) view.findViewById(R.id.vp_home);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return pic.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return object == view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View)object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(getActivity());
                imageView.setImageResource(pic[position]);
                container.addView(imageView);
                viewPager.setObjectForPosition(imageView,position);
                return imageView;
            }
        });
    }

    private void initView(View view) {
        lv = (ListView) view.findViewById(R.id.lv);
        et_title_search = (EditText) view.findViewById(R.id.et_title_search);

        adapter = new SimpleAdapter(getActivity(),getDate(),R.layout.item_lv_fragment1,
                new String[]{"user_icon","user_name","note_time","note_text"},
                new int[]{R.id.iv_user_icon,R.id.tv_username,R.id.tv_note_time,R.id.tv_note_text});
        lv.setAdapter(adapter);
    }

    private List<Map<String ,Object>> getDate() {
        //往数组里面添加数据，然后往map里面放，
        int[] user_icon = new int[]{R.mipmap.ic_launcher,R.mipmap.slide1,R.mipmap.ic_launcher,
                R.mipmap.ic_launcher,R.mipmap.ic_launcher};
        String[] user_name = new String[]{"一只羊","两只羊","三只羊","四只羊","五只羊"};
        String[] note_time = new String[]{"10分钟前","20分钟前","30分钟前","40分钟前","50分钟前"};
        String[] note_text = new String[]{"你好1","你好2","你好3","你好4","你好5"};

        for (int i = 0; i < user_icon.length; i++){
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("user_icon",user_icon[i]);
            map.put("user_name",user_name[i]);
            map.put("note_time",note_time[i]);
            map.put("note_text",note_text[i]);

            list.add(map);
        }
        return list;
    }

    private void initBannerView(View view) {
        bannerView = (BannerView) view.findViewById(R.id.banner_view);
        BannerViewURL bannerURL = new BannerViewURL();
        String[] url = new String[] {"http://pic37.nipic.com/20140102/14882888_164840920000_2.jpg",
                "http://img.ivsky.com/img/tupian/pic/201008/05/chongwugou-001.jpg",
                "http://img.ivsky.com/img/tupian/pic/201008/05/chongwugou-002.jpg",
                "http://img.ivsky.com/img/tupian/pic/201008/05/chongwugou-003.jpg",
                "http://img.ivsky.com/img/tupian/pic/201008/05/chongwugou-004.jpg"};
        String [] text = new String[] {"测试文字1",
                "测试文字2",
                "测试文字3",
                "测试文字4",
                "测试文字5"};
        bannerURL.setUrl(url);
        bannerURL.setText(text);

        bannerView.setList(bannerURL.getList(),bannerURL.getTextList());

    }
}

