package com.example.tiku_a_1.util;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.example.tiku_a_1.AppClient;

public class MyUtil {
    private static Toast toast;

    @SuppressLint("ShowToast")
    public static void showToast(String s){
        if (toast == null){
            toast = Toast.makeText(AppClient.getContext() , s , Toast.LENGTH_SHORT);
        }else {
            toast.setText(s);
        }
        toast.show();
    }
}
