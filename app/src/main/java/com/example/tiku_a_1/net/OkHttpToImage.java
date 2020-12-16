package com.example.tiku_a_1.net;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpToImage extends Thread{
    private String Url;
    private OkHttpClient client;
    private OkHttpLoImage okHttpLoImage;
    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 1){
                okHttpLoImage.onResponse(null , (Bitmap) msg.obj);
            }else {
                okHttpLoImage.onFailure(null , (IOException) msg.obj);
            }
            return false;
        }
    });

    public OkHttpToImage setImageUrl(String url){
        Url = url;
        Log.d("Url" , "-----------"+url);
        return this;
    }

    public OkHttpToImage setOkHttpLoImage(OkHttpLoImage okHttpLoImage){
        this.okHttpLoImage = okHttpLoImage;
        return this;
    }

    @Override
    public void run() {
        super.run();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(Url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 2;
                message.obj = e;
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message message = new Message();
                message.what = 1;
                InputStream inputStream = response.body().byteStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                message.obj = bitmap;
                handler.sendMessage(message);
            }
        });
    }
}
