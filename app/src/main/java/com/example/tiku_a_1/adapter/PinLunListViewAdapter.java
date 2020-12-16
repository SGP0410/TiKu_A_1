package com.example.tiku_a_1.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiku_a_1.R;
import com.example.tiku_a_1.bean.Comment;

import java.util.List;

public class PinLunListViewAdapter extends ArrayAdapter<Comment> {

    public PinLunListViewAdapter(@NonNull Context context, @NonNull List<Comment> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        Comment comment = getItem(position);
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.pinlun_item, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.text1.setText(comment.getCommit());
        holder.text2.setText("评论人："+comment.getReviewer()+"    评论时间："+comment.getCommitTime());

        return convertView;
    }

    static class ViewHolder{
        private TextView text1;
        private TextView text2;

        public ViewHolder(View view){
            text1 = view.findViewById(R.id.text1);
            text2 = view.findViewById(R.id.text2);
        }
    }

}
