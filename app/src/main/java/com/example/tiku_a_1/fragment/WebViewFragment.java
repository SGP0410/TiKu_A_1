package com.example.tiku_a_1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tiku_a_1.R;
import com.example.tiku_a_1.activity.HomeActivity;

public class WebViewFragment extends Fragment {
    private TextView fanHui;
    private TextView head;
    private WebView webView;
    private String url;
    private String headX;
    private HomeActivity homeActivity;
    private static WebViewFragment fragment;

    public static WebViewFragment newInstance(String url , String headX){
        if (fragment == null){
            fragment = new WebViewFragment();
            fragment.url = url;
            fragment.headX = headX;
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.web_view_layout, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        webView.loadUrl(url);
    }

    private void initView(View view) {
        homeActivity = (HomeActivity) getActivity();
        fanHui = view.findViewById(R.id.fan_hui);
        head = view.findViewById(R.id.head);
        webView = view.findViewById(R.id.web_view);
        fanHui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (headX.equals("SY")){
                    homeActivity.setFragment(SYFragment.newInstance());
                }else if (headX.equals("GDFW")){
                    homeActivity.setFragment(GengDuoFuWuFragment.newInstance(null));
                }
                homeActivity.getFragmentTransaction().remove(fragment);
                fragment = null;
            }
        });
    }
}
