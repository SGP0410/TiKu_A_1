package com.example.tiku_a_1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tiku_a_1.R;
import com.example.tiku_a_1.activity.HomeActivity;
import com.example.tiku_a_1.adapter.XinWenZhuanLanListViewAdapter;
import com.example.tiku_a_1.bean.News;
import com.example.tiku_a_1.net.OkHttpLo;
import com.example.tiku_a_1.net.OkHttpTo;
import com.example.tiku_a_1.util.MyListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class XinWenZhuanLanFragment extends Fragment implements View.OnClickListener {
    private HomeActivity homeActivity;
    private String string;
    private static XinWenZhuanLanFragment fragment;
    private TextView fanHui;
    private TextView head;
    private MyListView listView;
    private List<News> news;
    private XinWenZhuanLanListViewAdapter adapter;


    public static XinWenZhuanLanFragment newInstance(String string) {
        if (fragment == null) {
            fragment = new XinWenZhuanLanFragment();
            fragment.string = string;
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xwzl_layout, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        head.setText(string);
        getNEWsList();
    }


    private void getNEWsList() {
        new OkHttpTo().setUrl("getNEWsList")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        news = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString() ,
                                new TypeToken<List<News>>(){}.getType());
                        showListView();
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }

    private void showListView() {
        adapter = new XinWenZhuanLanListViewAdapter(getContext(), news);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                homeActivity.setFragment(XinWenXiangQingFragment.newInstance(news.get(position).getNewsid() , "XWZL"));
            }
        });
    }



    private void initView(View view) {
        fanHui = view.findViewById(R.id.fan_hui);
        fanHui.setOnClickListener(this);
        head = view.findViewById(R.id.head);
        listView = view.findViewById(R.id.list_view);
        homeActivity = (HomeActivity) getActivity();
        homeActivity.showBottomNavigationView(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fan_hui:
                homeActivity.setFragment(SYFragment.newInstance());
                homeActivity.getFragmentTransaction().remove(fragment);
                fragment = null;
                break;
            default:
        }
    }
}
