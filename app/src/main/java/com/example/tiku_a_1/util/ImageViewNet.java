package com.example.tiku_a_1.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tiku_a_1.net.OkHttpLoImage;
import com.example.tiku_a_1.net.OkHttpToImage;

import java.io.IOException;

import okhttp3.Call;

public class ImageViewNet extends androidx.appcompat.widget.AppCompatImageView {
    public ImageViewNet(@NonNull Context context) {
        super(context);
    }

    public ImageViewNet(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageViewNet(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    public void setImageUrl(String url){
        new OkHttpToImage().setImageUrl(url)
                .setOkHttpLoImage(new OkHttpLoImage() {
                    @Override
                    public void onResponse(Call call, Bitmap bitmap) {
                        setImageBitmap(bitmap);
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }
}
