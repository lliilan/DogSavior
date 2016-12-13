package com.dogsavior.dogsavior.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by KL on 2016/12/8 0008.
 */

public class BannerViewURL {

    private String[] url;
    private String[] text;
    private List<String> textList;
    private List<String> list;

    public String[] getUrl() {
        return url;
    }

    public void setUrl(String[] url) {
        this.url = url;
        list = new ArrayList<String>();
        for (int i = 0;i < url.length;i++){
            list.add(url[i]);
        }
    }

    public void setText(String[] text){
        this.text = text;
        textList = new ArrayList<String>();
        for (int i = 0;i < text.length;i++){
            textList.add(text[i]);
        }
    }

    public List<String> getList() {
        return list;
    }
    public List<String> getTextList(){
        return textList;
    }
}
