package com.example.tiku_a_1.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class LBTViewPagerAdapter extends PagerAdapter {

    private List<View> views;
    private int countItem;
    public LBTViewPagerAdapter(List<View> views) {
        this.views = views;
    }


    /**
     *
     * @return  返回界面个数
     */
    @Override
    public int getCount() {
        if (views != null){
            return Integer.MAX_VALUE;
        }
        return 0;
    }


    /**
     * @ 判断对象是否生成界面
     * @param view
     * @param object
     * @return
     */
    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    /**
     * @    初始化position位置的界面
     * @param container
     * @param position
     * @return
     */
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        int newPosition = position % views.size();
        countItem = newPosition;
        container.addView(views.get(newPosition));
        return views.get(newPosition);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        int newPosition = position % views.size();
        container.removeView(views.get(newPosition));
    }

}
