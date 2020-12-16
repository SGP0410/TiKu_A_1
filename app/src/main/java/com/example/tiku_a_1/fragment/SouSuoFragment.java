package com.example.tiku_a_1.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tiku_a_1.R;
import com.example.tiku_a_1.activity.HomeActivity;
import com.example.tiku_a_1.adapter.SouSuoListViewAdapter;
import com.example.tiku_a_1.bean.News;
import com.example.tiku_a_1.bean.NewsDetails;
import com.example.tiku_a_1.net.OkHttpLo;
import com.example.tiku_a_1.net.OkHttpTo;
import com.example.tiku_a_1.util.MyListView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import okhttp3.Call;

public class SouSuoFragment extends Fragment implements View.OnClickListener {
    private HomeActivity homeActivity;
    private List<News> souSuoNews;
    private List<NewsDetails> souSuoNewsDetails;
    private String keys;
    private static SouSuoFragment fragment;
    private TextView fanHui;
    private TextView head;
    private MyListView listView;
    private SouSuoListViewAdapter adapter;
    private TextView text1;

    public static SouSuoFragment newInstance(String keys) {
        if (fragment == null) {
            fragment = new SouSuoFragment();
        }
        fragment.keys = keys;

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sousuo_layout, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        getNewsByKeys();
    }

    /**
     * @param hidden 隐藏返回true
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            homeActivity.showBottomNavigationView(false);
            getNewsByKeys();
        }
    }

    private void getNewsByKeys() {
        if (souSuoNews == null) {
            souSuoNews = new ArrayList<>();
        } else {
            souSuoNews.clear();
        }
        new OkHttpTo().setUrl("getNewsByKeys")
                .setJSONObject("keys", keys)
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        souSuoNews.addAll((Collection<? extends News>) new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(),
                                new TypeToken<List<News>>() {
                                }.getType()));
                        showListView();
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }

    private void showListView() {
        if (souSuoNews == null || souSuoNews.size() == 0){
            text1.setText("暂无相关新闻");
        }
        if (adapter == null) {
            adapter = new SouSuoListViewAdapter(getContext(), souSuoNews);
            listView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void initView(View view) {
        fanHui = view.findViewById(R.id.fan_hui);
        fanHui.setOnClickListener(this);
        head = view.findViewById(R.id.head);
        head.setText("");
        listView = view.findViewById(R.id.list_view);
        homeActivity = (HomeActivity) getActivity();
        homeActivity.showBottomNavigationView(false);
        text1 = view.findViewById(R.id.text1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fan_hui:
                homeActivity.setFragment(SYFragment.newInstance());
                break;
        }
    }
}
