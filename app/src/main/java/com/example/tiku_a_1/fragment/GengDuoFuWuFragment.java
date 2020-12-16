package com.example.tiku_a_1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tiku_a_1.R;
import com.example.tiku_a_1.activity.HomeActivity;
import com.example.tiku_a_1.adapter.FuWuRuKouGridViewAdapter;
import com.example.tiku_a_1.bean.Service;

import java.util.List;

public class GengDuoFuWuFragment extends Fragment {
    private List<Service> services;
    private static GengDuoFuWuFragment fuWuFragment;
    private TextView fanHui;
    private TextView head;
    private GridView gridView;

    public static GengDuoFuWuFragment newInstance(List<Service> services) {
        if (fuWuFragment == null) {
            fuWuFragment = new GengDuoFuWuFragment();
        }
        fuWuFragment.services = services;
        return fuWuFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.gdfw_layout, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        showGridView();
    }

    private void showGridView() {
        gridView.setAdapter(new FuWuRuKouGridViewAdapter(getContext() , services));
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden){
            ((HomeActivity) getActivity()).showBottomNavigationView(false);
        }
    }

    private void initView(View view) {
        ((HomeActivity) getActivity()).showBottomNavigationView(false);
        fanHui = view.findViewById(R.id.fan_hui);
        head = view.findViewById(R.id.head);
        head.setText("更多服务");
        gridView = view.findViewById(R.id.grid_view);
        fanHui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).setFragment(SYFragment.newInstance());
            }
        });
    }
}
