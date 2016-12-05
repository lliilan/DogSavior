package com.dogsavior.dogsavior.activity;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dogsavior.dogsavior.R;
import com.dogsavior.dogsavior.fragment.HomeFragment;
import com.dogsavior.dogsavior.fragment.MyFragment;
import com.dogsavior.dogsavior.fragment.TalkFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout home_ll,talk_ll,my_ll;
    private TextView tv1,tv2,tv3;
    private HomeFragment f1;
    private TalkFragment f2;
    private MyFragment f3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        Drawable drawable1 = getResources().getDrawable(R.mipmap.frag_home1);
        drawable1.setBounds(0, 0, 90, 90);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        tv1.setCompoundDrawables(null, drawable1, null, null);//图标放在 左  上  右 下边
        Drawable drawable2 = getResources().getDrawable(R.mipmap.frag_note1);
        drawable2.setBounds(0, 0, 80, 80);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        tv2.setCompoundDrawables(null, drawable2, null, null);//图标放在 左  上  右 下边
        Drawable drawable3 = getResources().getDrawable(R.mipmap.frag_my1);
        drawable3.setBounds(0, 0, 90, 90);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        tv3.setCompoundDrawables(null, drawable3, null, null);//图标放在 左  上  右 下边
        
        bindView();
        tv1.performClick();
    }

    private void bindView() {
        home_ll = (LinearLayout) findViewById(R.id.home_ll);
        talk_ll = (LinearLayout) findViewById(R.id.talk_ll);
        my_ll = (LinearLayout) findViewById(R.id.my_ll);

        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);

        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        f1 = new HomeFragment();
        transaction.replace(R.id.fl_content,f1);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (v.getId()){
            case R.id.tv1:
                if (f1 == null){
                    f1 = new HomeFragment();
                }
                transaction.replace(R.id.fl_content,f1);
                break;
            case R.id.tv2:
                if (f2 == null){
                    f2 = new TalkFragment();
                }
                transaction.replace(R.id.fl_content,f2);
                break;
            case R.id.tv3:
                if (f3 == null){
                    f3 = new MyFragment();
                }
                transaction.replace(R.id.fl_content,f3);
                break;
        }
        transaction.commit();
    }
}
