package com.example.tiku_a_1.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiku_a_1.R;
import com.example.tiku_a_1.bean.News;
import com.example.tiku_a_1.bean.NewsDetails;
import com.example.tiku_a_1.net.OkHttpLo;
import com.example.tiku_a_1.net.OkHttpTo;
import com.example.tiku_a_1.util.ImageViewNet;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class XinWenZhuanLanListViewAdapter extends ArrayAdapter<News> {

    public XinWenZhuanLanListViewAdapter(@NonNull Context context, @NonNull List<News> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final News news = getItem(position);
        final ViewHolder holder;
        convertView = View.inflate(parent.getContext(), R.layout.xwzl_list_item, null);
        holder = new ViewHolder(convertView);
        holder.imageView.setImageUrl(news.getPicture());
        holder.text1.setText(news.getTitle());
        holder.text2.setText(news.getAbstractX());

        new OkHttpTo().setUrl("getNewsInfoById")
                .setJSONObject("newsid" , news.getNewsid())
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        final NewsDetails newsDetails = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").optJSONObject(0).toString() , NewsDetails.class);
                        new OkHttpTo().setUrl("getCommitById")
                                .setJSONObject("id" , news.getNewsid())
                                .setOkHttpLo(new OkHttpLo() {
                                    @Override
                                    public void onResponse(Call call, JSONObject jsonObject) {
                                        JSONArray jsonArray = jsonObject.optJSONArray("ROWS_DETAIL");
                                        holder.text3.setText("评论数："+jsonArray.length() +"    发布时间："+newsDetails.getPublicTime());
                                    }

                                    @Override
                                    public void onFailure(Call call, IOException e) {

                                    }
                                }).start();
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();

        return convertView;
    }

    static class ViewHolder{
        private ImageViewNet imageView;
        private TextView text1;
        private TextView text2;
        private TextView text3;

        public ViewHolder(View view){
            imageView = view.findViewById(R.id.image_view);
            text1 = view.findViewById(R.id.text1);
            text2 = view.findViewById(R.id.text2);
            text3 = view.findViewById(R.id.text3);
        }
    }
}
