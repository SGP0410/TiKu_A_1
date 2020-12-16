package com.example.tiku_a_1.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiku_a_1.R;
import com.example.tiku_a_1.util.RoundRectImageViewNet;

public class ReMengZhuTiGridViewAdapter extends ArrayAdapter<String> {

    public ReMengZhuTiGridViewAdapter(@NonNull Context context, @NonNull String[] objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String string = getItem(position);
        ViewsHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.rmzt_grid_iteam, null);
            holder = new ViewsHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewsHolder) convertView.getTag();
        }

        holder.text.setText(string);

        assert string != null;
        switch (string){
            case "电影":
                holder.imageView.setImageResource(R.mipmap.dy);
                break;
            case "国庆专题":
                holder.imageView.setImageResource(R.mipmap.gqzt);
                break;
            case "抗肺炎":
                holder.imageView.setImageResource(R.mipmap.kfy);
                break;
            case "烈士纪念日":
                holder.imageView.setImageResource(R.mipmap.lsjnr);
                break;
        }


        return convertView;
    }

    static class ViewsHolder{
        private RoundRectImageViewNet imageView;
        private TextView text;

        public ViewsHolder(View view){
            imageView = view.findViewById(R.id.image_view);
            text = view.findViewById(R.id.text);
        }
    }
}
