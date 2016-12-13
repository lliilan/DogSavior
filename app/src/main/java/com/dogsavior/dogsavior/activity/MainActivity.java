package com.dogsavior.dogsavior.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.dogsavior.dogsavior.R;
import com.dogsavior.dogsavior.fragment.FindFragment;
import com.dogsavior.dogsavior.fragment.HomeFragment;
import com.dogsavior.dogsavior.fragment.MyFragment;
import com.dogsavior.dogsavior.fragment.MessageFragment;
import com.dogsavior.dogsavior.fragment.NoteFragment;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private LinearLayout ll_home,ll_find,ll_note,ll_message,ll_my;
    private RadioButton rb_home,rb_find,rb_note,rb_message,rb_my;
    private HomeFragment homeFragment;
    private FindFragment findFragment;
    private NoteFragment noteFragment;
    private MessageFragment messageFragment;
    private MyFragment myFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //当API是21以上时，将采用透明状态栏
        if(Build.VERSION.SDK_INT >= 21){
            //设置导航栏颜色，这里设置的是透明
//            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            //设置状态栏颜色，这里设置的是APP颜色
            getWindow().setStatusBarColor(Color.parseColor("#ff8080"));
        }
        //查看APP最多可以使用多少内存（这个APP最多给了192MB内存使用）
//        int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
//        Log.d("TAG", "Max memory is " + maxMemory + "KB");
        initView();
    }

    private void initView() {
        rb_home = (RadioButton) findViewById(R.id.rb_main_home);
        rb_find = (RadioButton) findViewById(R.id.rb_main_find);
        rb_note = (RadioButton) findViewById(R.id.rb_main_note);
        rb_message = (RadioButton) findViewById(R.id.rb_main_message);
        rb_my = (RadioButton) findViewById(R.id.rb_main_my);

//        Drawable drawable1 = getResources().getDrawable(R.mipmap.frag_home1);
//        drawable1.setBounds(0, 0, 90, 90);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
//        rb_home.setCompoundDrawables(null, drawable1, null, null);//图标放在 左  上  右 下边
//        Drawable drawable2 = getResources().getDrawable(R.mipmap.frag_note1);
//        drawable2.setBounds(0, 0, 80, 80);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
//        tv2.setCompoundDrawables(null, drawable2, null, null);//图标放在 左  上  右 下边
//        Drawable drawable3 = getResources().getDrawable(R.mipmap.frag_my1);
//        drawable3.setBounds(0, 0, 90, 90);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
//        tv3.setCompoundDrawables(null, drawable3, null, null);//图标放在 左  上  右 下边

        bindView();
//        tv1.performClick();
    }

    private void bindView() {
        ll_home = (LinearLayout) findViewById(R.id.ll_home);
        ll_find = (LinearLayout) findViewById(R.id.ll_find);
        ll_note = (LinearLayout) findViewById(R.id.ll_note);
        ll_message = (LinearLayout) findViewById(R.id.ll_message);
        ll_my = (LinearLayout) findViewById(R.id.ll_my);

        rb_home.setOnClickListener(this);
        rb_find.setOnClickListener(this);
        rb_note.setOnClickListener(this);
        rb_message.setOnClickListener(this);
        rb_my.setOnClickListener(this);

//        tv1.setOnClickListener(this);
//        tv2.setOnClickListener(this);
//        tv3.setOnClickListener(this);
        setDefaultFragment();
    }

    private void setDefaultFragment() {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        homeFragment = new HomeFragment();
        transaction.replace(R.id.fl_content,homeFragment);
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (v.getId()){
            case R.id.rb_main_home:
                if (homeFragment == null){
                    homeFragment = new HomeFragment();
                }
                transaction.replace(R.id.fl_content,homeFragment);
                break;
            case R.id.rb_main_find:
                if (findFragment == null){
                    findFragment = new FindFragment();
                }
                transaction.replace(R.id.fl_content,findFragment);
                break;
            case R.id.rb_main_note:
                if (noteFragment == null){
                    noteFragment = new NoteFragment();
                }
                transaction.replace(R.id.fl_content,noteFragment);
                break;
            case R.id.rb_main_message:
                if (messageFragment == null){
                    messageFragment = new MessageFragment();
                }
                transaction.replace(R.id.fl_content,messageFragment);
                break;
            case R.id.rb_main_my:
                if (myFragment == null){
                    myFragment = new MyFragment();
                }
                transaction.replace(R.id.fl_content,myFragment);
                break;
        }
        transaction.commit();
    }
}
