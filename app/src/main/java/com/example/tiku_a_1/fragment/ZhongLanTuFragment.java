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
import com.example.tiku_a_1.adapter.ImageOnTouchListener;
import com.example.tiku_a_1.adapter.ZhongLanTuListViewAdapter;
import com.example.tiku_a_1.bean.Subways;
import com.example.tiku_a_1.net.OkHttpLo;
import com.example.tiku_a_1.net.OkHttpTo;
import com.example.tiku_a_1.util.ImageViewNet;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class ZhongLanTuFragment extends Fragment {
    private TextView fanHui;
    private TextView head;
    private ImageView map;
    private ImageViewNet imageView;
    private ListView listView;
    private List<Subways> subwaysList;
    private static ZhongLanTuFragment fragment;

    public static ZhongLanTuFragment newInstance(){
        if (fragment == null){
            fragment = new ZhongLanTuFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.zlt_layout, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        getAllSubways();
    }

    private void getAllSubways() {
        new OkHttpTo().setUrl("getAllSubways")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        subwaysList = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString() ,
                                new TypeToken<List<Subways>>(){}.getType());
                        getSubwaysImage(subwaysList.get(0).getId());
                        head.setText(subwaysList.get(0).getName());
                        showListView();
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }

    private void showListView() {
        listView.setAdapter(new ZhongLanTuListViewAdapter(getContext() , subwaysList));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                getSubwaysImage(subwaysList.get(position).getId());
                head.setText(subwaysList.get(position).getName());
            }
        });
    }

    private void getSubwaysImage(int id) {
        new OkHttpTo().setUrl("getSubwaysImage")
                .setJSONObject("id" , id)
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        imageView.setImageUrl(jsonObject.optString("image"));
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }

    private void initView(View view) {
        fanHui = view.findViewById(R.id.fan_hui);
        head = view.findViewById(R.id.head);
        map = view.findViewById(R.id.map);
        imageView = view.findViewById(R.id.image_view);
        listView = view.findViewById(R.id.list_view);
        fanHui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).setFragment(DiTieChaXunFragment.newInstance());
                ((HomeActivity) getActivity()).getFragmentTransaction().remove(fragment);
                fragment = null;
            }
        });
        imageView.setOnTouchListener(new ImageOnTouchListener(imageView));
    }
}
