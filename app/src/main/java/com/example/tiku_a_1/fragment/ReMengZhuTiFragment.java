package com.example.tiku_a_1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tiku_a_1.R;
import com.example.tiku_a_1.activity.HomeActivity;
import com.example.tiku_a_1.adapter.ReMengZhuTiListViewAdapter;
import com.example.tiku_a_1.bean.NewsDetails;
import com.example.tiku_a_1.net.OkHttpLo;
import com.example.tiku_a_1.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class ReMengZhuTiFragment extends Fragment {
    private HomeActivity homeActivity;
    private String headX , string;
    private TextView fanHui;
    private TextView head;
    private ListView listView;
    private static ReMengZhuTiFragment fragment;
    private List<NewsDetails> newsDetails;

    public static ReMengZhuTiFragment newInstance(String headX , String string){
        if (fragment == null){
            fragment = new ReMengZhuTiFragment();
            fragment.headX = headX;
            fragment.string = string;
        }
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rmzt_layout, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        getNewsInfoBySubject();
    }

    private void getNewsInfoBySubject() {
        new OkHttpTo().setUrl("getNewsInfoBySubject")
                .setJSONObject("subject" , headX)
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        newsDetails = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString() ,
                                new TypeToken<List<NewsDetails>>(){}.getType());
                        showListView();
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }

    private void showListView() {
        listView.setAdapter(new ReMengZhuTiListViewAdapter(getContext() ,newsDetails));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                homeActivity.setFragment(XinWenXiangQingFragment.newInstance(newsDetails.get(position).getNewsid() , "RMZT"));
            }
        });
    }

    private void initView(View view) {
        homeActivity = (HomeActivity) getActivity();
        homeActivity.showBottomNavigationView(false);
        fanHui = view.findViewById(R.id.fan_hui);
        head = view.findViewById(R.id.head);
        head.setText(headX);
        listView = view.findViewById(R.id.list_view);
        fanHui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (string.equals("SY")){
                    homeActivity.setFragment(SYFragment.newInstance());
                }
                homeActivity.getFragmentTransaction().remove(fragment);
                fragment = null;
            }
        });
    }
}
