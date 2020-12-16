package com.example.tiku_a_1.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.tiku_a_1.R;
import com.example.tiku_a_1.fragment.GRZXFragment;
import com.example.tiku_a_1.fragment.QBFWFragment;
import com.example.tiku_a_1.fragment.SYFragment;
import com.example.tiku_a_1.fragment.XWFragment;
import com.example.tiku_a_1.fragment.YingDaoYeFragment;
import com.example.tiku_a_1.fragment.ZHDJFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    private BottomNavigationView bnv;
    private Fragment currentFragment;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        setFragment(SYFragment.newInstance());
    }

    //控制底部导航栏是否显示
    public void showBottomNavigationView(boolean isShow){
        if (isShow){
            bnv.setVisibility(View.VISIBLE);
        }else {
            bnv.setVisibility(View.GONE);
        }
    }

    public FragmentTransaction getFragmentTransaction(){
        return fragmentTransaction;
    }

    public void setFragment(Fragment fragment){
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (fragment.isAdded()){
            fragmentTransaction.hide(currentFragment).show(fragment);   //如果此fragment已经添加进fragmentTransaction，则隐藏当前fragment显示此fragment
        }else {
            if (currentFragment != null){
                fragmentTransaction.hide(currentFragment);              //如果当前显示了fragment则隐藏当前fragment，添加新的fragment
            }
            fragmentTransaction.add(R.id.frame_layout , fragment , fragment.getTag());
        }
        currentFragment = fragment;
        fragmentTransaction.commit();
    }

    private void initView() {
        bnv = findViewById(R.id.bnv);
        bnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.sy:
                        if (!currentFragment.equals(SYFragment.newInstance())){
                            setFragment(SYFragment.newInstance());
                        }
                        break;
                    case R.id.qbfw:
                        if (!currentFragment.equals(QBFWFragment.newInstance())){
                            setFragment(QBFWFragment.newInstance());
                        }
                        break;
                    case R.id.zhdj:
                        if (!currentFragment.equals(ZHDJFragment.newInstance())){
                            setFragment(ZHDJFragment.newInstance());
                        }
                        break;
                    case R.id.xw:
                        if (!currentFragment.equals(XWFragment.newInstance())){
                            setFragment(XWFragment.newInstance());
                        }
                        break;
                    case R.id.grzx:
                        if (!currentFragment.equals(GRZXFragment.newInstance())){
                            setFragment(GRZXFragment.newInstance());
                        }
                        break;
                    default:
                }
                return true;
            }
        });
    }
}