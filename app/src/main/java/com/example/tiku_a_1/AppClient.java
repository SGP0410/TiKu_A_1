package com.example.tiku_a_1;

import android.app.Application;
import android.content.Context;

public class AppClient extends Application {
    private static String ip = "192.168.155.202";
    private static String dk = "8080";
    private static Context context;
    private static String userName = "";

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        AppClient.userName = userName;
    }

    public static Context getContext() {
        return context;
    }

    public static String getIp() {
        return ip;
    }

    public static void setIp(String ip) {
        AppClient.ip = ip;
    }

    public static String getDk() {
        return dk;
    }

    public static void setDk(String dk) {
        AppClient.dk = dk;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
