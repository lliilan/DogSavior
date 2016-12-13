package com.dogsavior.dogsavior.thread;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.widget.ImageView;

import com.dogsavior.dogsavior.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by KL on 2016/12/11 0011.
 */

public class BitmapThread extends Thread{

    private ImageView iv;
    private Handler handler;
    private Context context;

    public BitmapThread(ImageView iv, Handler handler, Context context){
        this.iv = iv;
        this.handler = handler;
        this.context = context;
    }

    private final int[] pics = {R.mipmap.pager1,R.mipmap.pager2,R.mipmap.pager3,R.mipmap.pager4};

    private void BitmapDraw(){
        for (int i = 0;i<pics.length;i++){
            BitmapFactory.Options opt = new BitmapFactory.Options();
            opt.inPreferredConfig = Bitmap.Config.RGB_565;
            opt.inPurgeable = true;
            opt.inInputShareable = true;
            InputStream is = context.getResources().openRawResource(pics[i]);
            Bitmap bitmap = BitmapFactory.decodeStream(is, null, opt);
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            final BitmapDrawable bitmapDrawable = new BitmapDrawable(context.getResources(),bitmap);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    iv.setBackgroundDrawable(bitmapDrawable);
                }
            });
        }
    }

    @Override
    public void run() {
        BitmapDraw();
    }
}
