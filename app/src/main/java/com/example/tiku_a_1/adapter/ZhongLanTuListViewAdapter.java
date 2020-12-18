package com.example.tiku_a_1.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiku_a_1.R;
import com.example.tiku_a_1.bean.Subways;

import java.util.List;

public class ZhongLanTuListViewAdapter extends ArrayAdapter<Subways> {

    public ZhongLanTuListViewAdapter(@NonNull Context context, @NonNull List<Subways> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Subways subways = getItem(position);
        ViewHolder holder;
        if (convertView == null){
            convertView = View.inflate(parent.getContext() , android.R.layout.simple_spinner_item , null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.textView.setText(subways.getName());

        return convertView;
    }

    static class ViewHolder{
        private TextView textView;

        public ViewHolder(View view){
            textView = view.findViewById(android.R.id.text1);
            textView.setTextColor(Color.BLACK);
            textView.setTextSize(16f);
            textView.setGravity(Gravity.CENTER);
            textView.setPadding(0 , 20 , 0 , 20);
        }
    }
}
