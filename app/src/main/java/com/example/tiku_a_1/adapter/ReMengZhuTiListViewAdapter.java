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

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;

public class ReMengZhuTiListViewAdapter extends ArrayAdapter<NewsDetails> {

    public ReMengZhuTiListViewAdapter(@NonNull Context context, @NonNull List<NewsDetails> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NewsDetails newsDetails = getItem(position);
        final ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.sousuo_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text2.setText("观看人数："+newsDetails.getAudienceCount() +"    点赞人数："+newsDetails.getPraiseCount());

        new OkHttpTo().setUrl("getNEWSContent")
                .setJSONObject("newsid" , newsDetails.getNewsid())
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        News news = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").optJSONObject(0).toString() , News.class);
                        holder.imageView.setImageUrl(news.getPicture());
                        holder.text1.setText(news.getTitle());
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

        public ViewHolder(View view){
            imageView = view.findViewById(R.id.image_view);
            text1 = view.findViewById(R.id.text1);
            text2 = view.findViewById(R.id.text2);
        }

    }
}
