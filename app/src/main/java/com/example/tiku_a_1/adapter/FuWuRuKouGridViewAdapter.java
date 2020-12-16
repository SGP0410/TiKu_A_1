package com.example.tiku_a_1.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiku_a_1.R;
import com.example.tiku_a_1.bean.Service;
import com.example.tiku_a_1.util.OvalImageViewNet;

import java.util.List;

public class FuWuRuKouGridViewAdapter extends ArrayAdapter<Service> {

    public FuWuRuKouGridViewAdapter(@NonNull Context context, @NonNull List<Service> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Service service = getItem(position);
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.fwrk_grid_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (service.getIcon() ==null || service.getIcon().isEmpty()){
            holder.imageView.setImageResource(service.getWeight());
        }else {
            holder.imageView.setImageUrl(service.getIcon());
        }
        holder.text1.setText(service.getServiceName());
        return convertView;
    }

    static class ViewHolder{
        private OvalImageViewNet imageView;
        private TextView text1;

        public ViewHolder(View view){
            imageView = view.findViewById(R.id.image_view);
            text1 = view.findViewById(R.id.text1);
        }
    }
}
