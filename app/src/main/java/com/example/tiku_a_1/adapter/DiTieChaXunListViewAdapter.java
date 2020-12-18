package com.example.tiku_a_1.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiku_a_1.R;
import com.example.tiku_a_1.bean.DiTieLuXian;

import java.util.List;

public class DiTieChaXunListViewAdapter extends ArrayAdapter<DiTieLuXian> {

    public DiTieChaXunListViewAdapter(@NonNull Context context, @NonNull List<DiTieLuXian> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        DiTieLuXian diTieLuXian = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.dtlx_list_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(diTieLuXian.getName());
        holder.nextName.setText(diTieLuXian.getNextname());
        holder.time.setText(diTieLuXian.getTime()+"");

        return convertView;
    }

    static class ViewHolder{
        private TextView name;
        private TextView nextName;
        private TextView time;

        public ViewHolder(View view){
            name = view.findViewById(R.id.name);
            nextName = view.findViewById(R.id.next_name);
            time = view.findViewById(R.id.time);
        }
    }
}
