package com.example.tiku_a_1.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tiku_a_1.R;
import com.example.tiku_a_1.activity.HomeActivity;
import com.example.tiku_a_1.dialog.WLSZDialog;

public class YingDaoYeFragment extends Fragment implements View.OnClickListener {
    private ImageView imageView;
    private LinearLayout layout1;
    private Button btn1;
    private Button btn2;
    private int image;
    private boolean isShowLayout;

    public YingDaoYeFragment(int image, boolean isShowLayout) {
        this.image = image;
        this.isShowLayout = isShowLayout;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ydy_layout, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        showLayout();
    }

    private void showLayout() {
        if (isShowLayout){
            layout1.setVisibility(View.VISIBLE);
            btn1.setOnClickListener(this);
            btn2.setOnClickListener(this);
        }else {
            layout1.setVisibility(View.GONE);
        }
    }


    private void initView(View view) {
        imageView = view.findViewById(R.id.image_view);
        imageView.setImageResource(image);
        layout1 = view.findViewById(R.id.layout1);
        btn1 = view.findViewById(R.id.btn1);
        btn2 = view.findViewById(R.id.btn2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn1:
                assert getFragmentManager() != null;
                new WLSZDialog().show(getFragmentManager() , WLSZDialog.class.getName());
                break;
            case R.id.btn2:
                getActivity().startActivity(new Intent(getContext() , HomeActivity.class));
                getActivity().finish();
                break;
            default:
        }
    }
}
