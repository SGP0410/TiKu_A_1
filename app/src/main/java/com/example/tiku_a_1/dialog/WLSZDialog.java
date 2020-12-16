package com.example.tiku_a_1.dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.tiku_a_1.AppClient;
import com.example.tiku_a_1.R;
import com.example.tiku_a_1.util.MyUtil;

public class WLSZDialog extends DialogFragment implements View.OnClickListener {
    private EditText ip;
    private EditText dk;
    private TextView qx;
    private TextView qd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        return inflater.inflate(R.layout.wlsz_dialog_layout, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
    }

    private void initView(View view) {
        ip = view.findViewById(R.id.ip);
        ip.setText(AppClient.getIp());
        dk = view.findViewById(R.id.dk);
        dk.setText(AppClient.getDk());
        qx = view.findViewById(R.id.qx);
        qx.setOnClickListener(this);
        qd = view.findViewById(R.id.qd);
        qd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.qd:
                String ip = this.ip.getText().toString();
                String dk = this.dk.getText().toString();
                if (ip.isEmpty()){
                    MyUtil.showToast("IP不能为空");
                }else if (dk.isEmpty()){
                    MyUtil.showToast("端口号不能为空");
                }else {
                    getDialog().dismiss();
                    AppClient.setIp(ip);
                    AppClient.setDk(dk);
                }
                break;
            case R.id.qx:
                getDialog().dismiss();
                break;
            default:
        }
    }
}
