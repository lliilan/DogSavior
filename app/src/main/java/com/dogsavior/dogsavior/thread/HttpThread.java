package com.dogsavior.dogsavior.thread;

import android.content.Context;
import android.os.Handler;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.dogsavior.dogsavior.R;


/**
 * Created by KL on 2016/12/8 0008.
 */

public class HttpThread extends Thread {

    private Handler handler;
    private ImageView imageView;
    private String url;
    private Context context;
    public HttpThread(String url, ImageView imageView, Handler handler, Context context){
        this.url = url;
        this.imageView = imageView;
        this.handler = handler;
        this.context = context;
    }

    private void doGet(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                RequestQueue mQueue = Volley.newRequestQueue(context);
                ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache());

                //第一个参数指定用于显示图片的ImageView控件，第二个参数指定加载图片的过程中显示的图片，第三个参数指定加载图片失败的情况下显示的图片
                ImageLoader.ImageListener listener = ImageLoader.getImageListener(imageView,
                        R.mipmap.loading,R.mipmap.load_fail);
                imageLoader.get(url,listener);
            }
        });
    }

    @Override
    public void run() {
        doGet();
    }
}
