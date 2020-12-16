package com.example.tiku_a_1.net;

import android.graphics.Bitmap;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Response;

public interface OkHttpLoImage {
    void onResponse(Call call, Bitmap bitmap);
    void onFailure(Call call, IOException e);
}
