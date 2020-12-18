package com.example.tiku_a_1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tiku_a_1.R;
import com.example.tiku_a_1.activity.HomeActivity;
import com.example.tiku_a_1.adapter.DiTieChaXunListViewAdapter;
import com.example.tiku_a_1.bean.DiTieLuXian;
import com.example.tiku_a_1.net.OkHttpLo;
import com.example.tiku_a_1.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class  DiTieChaXunFragment extends Fragment implements View.OnClickListener {
    private TextView fanHui;
    private TextView head;
    private ImageView map;
    private TextView dqwz;
    private ListView listView;
    private HomeActivity homeActivity;
    private List<DiTieLuXian> diTieLuXians;
    private static DiTieChaXunFragment fragment;

    public static DiTieChaXunFragment newInstance(){
        if (fragment == null){
            fragment = new DiTieChaXunFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dtcx_layout, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        getSubwaysByStation();
    }

    private void getSubwaysByStation() {
        new OkHttpTo().setUrl("getSubwaysByStation")
                .setJSONObject("stationName" , "建国门站")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        diTieLuXians = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString() ,
                                new TypeToken<List<DiTieLuXian>>(){}.getType());
                        showLiastView();
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }

    private void showLiastView() {
        listView.setAdapter(new DiTieChaXunListViewAdapter(getContext() ,diTieLuXians));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                homeActivity.setFragment(DiTieLuXianXiangQingFragment.newInstance(diTieLuXians.get(position)));
            }
        });
    }

    private void initView(View view) {
        homeActivity = (HomeActivity) getActivity();
        homeActivity.showBottomNavigationView(false);
        fanHui = view.findViewById(R.id.fan_hui);
        fanHui.setOnClickListener(this);
        head = view.findViewById(R.id.head);
        head.setText("地铁查询");
        map = view.findViewById(R.id.map);
        map.setImageResource(R.mipmap.map);
        map.setOnClickListener(this);
        dqwz = view.findViewById(R.id.dqwz);
        dqwz.setText("当前位置：建国门站");
        listView = view.findViewById(R.id.list_view);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fan_hui:
                homeActivity.setFragment(GengDuoFuWuFragment.newInstance(null));
                homeActivity.getFragmentTransaction().remove(fragment);
                fanHui = null;
                break;
            case R.id.map:
                homeActivity.setFragment(ZhongLanTuFragment.newInstance());
                break;
            default:
        }
    }
}
