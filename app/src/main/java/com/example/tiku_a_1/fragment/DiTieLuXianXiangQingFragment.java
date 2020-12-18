package com.example.tiku_a_1.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tiku_a_1.R;
import com.example.tiku_a_1.activity.HomeActivity;
import com.example.tiku_a_1.bean.DiTieLuXian;
import com.example.tiku_a_1.bean.DiTieLuXianZhanDian;
import com.example.tiku_a_1.net.OkHttpLo;
import com.example.tiku_a_1.net.OkHttpTo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class DiTieLuXianXiangQingFragment extends Fragment {
    private TextView fanHui;
    private TextView head;
    private ImageView map;
    private TextView start;
    private TextView end;
    private TextView xiangQing;
    private LinearLayout layout1;
    private DiTieLuXian diTieLuXian;
    private List<DiTieLuXianZhanDian> diTieLuXianZhanDians;
    private static DiTieLuXianXiangQingFragment fragment;


    public static DiTieLuXianXiangQingFragment newInstance(DiTieLuXian diTieLuXian) {
        if (fragment == null) {
            fragment = new DiTieLuXianXiangQingFragment();
            fragment.diTieLuXian = diTieLuXian;
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dtxq_layout, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        getAllStationById();
    }

    private void getAllStationById() {
        new OkHttpTo().setUrl("getAllStationById")
                .setJSONObject("id", diTieLuXian.getSubwayid())
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        diTieLuXianZhanDians = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(),
                                new TypeToken<List<DiTieLuXianZhanDian>>() {
                                }.getType());
                        initData();
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }

    private View view;
    private void initData() {
        DiTieLuXianZhanDian currentZhanDian = null;
        DiTieLuXianZhanDian targetZhanDian = null;
        for (DiTieLuXianZhanDian diTieLuXianZhanDian : diTieLuXianZhanDians) {

            ViewHolder holder;
            view = View.inflate(getContext(), R.layout.zhan_dian_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);

            if (diTieLuXianZhanDian.getStationname().equals(diTieLuXianZhanDians.get(0).getStationname())){
                holder.view1.setVisibility(View.GONE);
            }else if (diTieLuXianZhanDian.getStationname().equals(diTieLuXianZhanDians.get(diTieLuXianZhanDians.size() - 1).getStationname())){
                holder.view2.setVisibility(View.GONE);
            }

            holder.imageView2.setImageResource(R.drawable.heibiankuang_yuan);

            holder.text.setText(diTieLuXianZhanDian.getStationname());

            /**
             * @currentZhanDian 初始化currentZhanDian，并标记当前位置
             */
            if (diTieLuXian.getNextname().equals(diTieLuXianZhanDian.getStationname())) {
                currentZhanDian = diTieLuXianZhanDian;
                holder.imageView1.setImageResource(R.mipmap.dt);
            }

            /**
             * @targetZhanDian 初始化targetZhanDian，等待站台位置为红色
             */
            if ("建国门站".equals(diTieLuXianZhanDian.getStationname())) {
                targetZhanDian = diTieLuXianZhanDian;
                holder.text.setTextColor(Color.RED);
            }

            layout1.addView(view);

        }

        start.setText("始：" + diTieLuXianZhanDians.get(0).getStationname());
        end.setText("终：" + diTieLuXianZhanDians.get(diTieLuXianZhanDians.size() - 1).getStationname());
        xiangQing.setText("剩余时间：" + diTieLuXian.getTime() +
                "\n        间隔：" + diTieLuXian.getTime() +
                "\n剩余距离：" + (targetZhanDian.getDistance() - currentZhanDian.getDistance()) + "米");


    }

    static class ViewHolder {
        private ImageView imageView1;
        private ImageView imageView2;
        private TextView text;
        private View view1 , view2;

        public ViewHolder(View view){
            imageView1 = view.findViewById(R.id.image_view1);
            imageView2 = view.findViewById(R.id.image_view2);
            text = view.findViewById(R.id.text);
            view1 = view.findViewById(R.id.view1);
            view2 = view.findViewById(R.id.view2);
        }
    }

    private void initView(View view) {
        fanHui = view.findViewById(R.id.fan_hui);
        head = view.findViewById(R.id.head);
        map = view.findViewById(R.id.map);
        start = view.findViewById(R.id.start);
        end = view.findViewById(R.id.end);
        xiangQing = view.findViewById(R.id.xiang_qing);
        layout1 = view.findViewById(R.id.layout1);
        fanHui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).setFragment(DiTieChaXunFragment.newInstance());
                ((HomeActivity) getActivity()).getFragmentTransaction().remove(fragment);
                fragment = null;
            }
        });
    }
}
