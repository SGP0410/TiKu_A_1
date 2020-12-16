package com.example.tiku_a_1.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class OvalImageViewNet extends ImageViewNet {
    public OvalImageViewNet(@NonNull Context context) {
        super(context);
    }

    public OvalImageViewNet(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public OvalImageViewNet(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int width , height;

    @SuppressLint("DrawAllocation")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        path.addOval(0 , 0 , width , height , Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }
}
