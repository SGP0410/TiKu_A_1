package com.example.tiku_a_1.adapter;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.example.tiku_a_1.util.ImageViewNet;

public class ImageOnTouchListener implements View.OnTouchListener {
    private ImageView imageView;
    private Matrix matrix1 = new Matrix() , matrix = new Matrix();
    private PointF pointF , start;
    private final int DOWN1 = 1 , DOWN2 = 2;
    private int MOVE;
    private float dis;

    public ImageOnTouchListener(ImageView imageView){
        this.imageView = imageView;
    }

    private float tance(MotionEvent event){
        float dx = event.getX(1) - event.getX(0);
        float dy = event.getY(1) - event.getY(0);
        return (float) Math.sqrt(dx * dx + dy * dy);
    }



    @Override
    public boolean onTouch(View v, MotionEvent event) {
        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        switch (event.getAction()&MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                MOVE = DOWN1;
                matrix.set(imageView.getImageMatrix());
                matrix1.set(imageView.getImageMatrix());
                start = new PointF(event.getX() , event.getY());
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                MOVE = DOWN2;
                dis = tance(event);
                if (dis > 10f){
                    float dx = (event.getX(0) + event.getX(1)) / 2;
                    float dy = (event.getY(0) + event.getY(1)) / 2;
                    matrix1.set(imageView.getImageMatrix());
                    pointF = new PointF(dx , dy);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (MOVE == DOWN1){
                    matrix.set(matrix1);
                    matrix.postTranslate(event.getX() - start.x , event.getY() - start.y);
                }else if (MOVE == DOWN2){
                    float end = tance(event);
                    if (end > 10f){
                        float scale = end / dis;
                        matrix.set(matrix1);
                        matrix.postScale(scale , scale , pointF.x , pointF.y);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                MOVE = 0;
                break;
            default:
        }
        imageView.setImageMatrix(matrix);
        return true;
    }
}
