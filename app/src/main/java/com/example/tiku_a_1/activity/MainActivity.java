package com.example.tiku_a_1.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.tiku_a_1.R;
import com.example.tiku_a_1.adapter.ViewPagerAdapter;
import com.example.tiku_a_1.fragment.YingDaoYeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<YingDaoYeFragment> fragments;
    private ViewPager viewPager;
    private int[] arr = {R.mipmap.yingdao1, R.mipmap.yingdao2, R.mipmap.yingdao3, R.mipmap.yingdao4, R.mipmap.yingdao5};
    private LinearLayout layout1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        showViewPager();
    }

    private void showViewPager() {
        fragments = new ArrayList<>();
        for (int i: arr) {
            if (i != R.mipmap.yingdao5){
                fragments.add(new YingDaoYeFragment(i , false));
            }else {
                fragments.add(new YingDaoYeFragment(i , true));
            }
        }
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager() , FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT , fragments));
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(fragments.size());
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setLayout(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        showLayout();
    }

    private void setLayout(int position) {
        for (int i = 0; i < layout1.getChildCount(); i++) {
            if (i == position){
                layout1.getChildAt(i).setLayoutParams(new LinearLayout.LayoutParams(110 , 30));
            }else {
                layout1.getChildAt(i).setLayoutParams(new LinearLayout.LayoutParams(100 , 20));
            }
        }
    }

    private void showLayout() {
        for (int i = 0; i < fragments.size(); i++) {
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(R.drawable.bai_yuan_dian);
            if (i == 0){
                imageView.setLayoutParams(new LinearLayout.LayoutParams(110 , 30));
            }else {
                imageView.setLayoutParams(new LinearLayout.LayoutParams(100 , 20));
            }
            imageView.setPadding(40 , 0 , 40 , 0);
            layout1.addView(imageView);
        }
    }

    private void initView() {
        viewPager = findViewById(R.id.view_pager);
        layout1 = findViewById(R.id.layout1);
    }
}