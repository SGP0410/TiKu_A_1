package com.example.tiku_a_1.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.tiku_a_1.AppClient;
import com.example.tiku_a_1.R;
import com.example.tiku_a_1.activity.HomeActivity;
import com.example.tiku_a_1.adapter.PinLunListViewAdapter;
import com.example.tiku_a_1.adapter.XinWenTuiJianAdapter;
import com.example.tiku_a_1.bean.Comment;
import com.example.tiku_a_1.bean.News;
import com.example.tiku_a_1.net.OkHttpLo;
import com.example.tiku_a_1.net.OkHttpTo;
import com.example.tiku_a_1.util.ImageViewNet;
import com.example.tiku_a_1.util.MyListView;
import com.example.tiku_a_1.util.MyUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

public class XinWenXiangQingFragment extends Fragment implements View.OnClickListener {
    private HomeActivity homeActivity;
    private String newsId;
    private static XinWenXiangQingFragment fragment;
    private TextView fanHui;
    private TextView head;
    private ImageViewNet imageView;
    private TextView text1;
    private TextView text2;
    private MyListView listView1;
    private EditText editText;
    private Button btn;
    private TextView pls;
    private MyListView listView2;
    private String[] newsIds;
    private List<String> newsIdList;
    private List<Comment> comments;
    private String headString;

    public static XinWenXiangQingFragment newInstance(String newsId, String head) {
        fragment = new XinWenXiangQingFragment();
        fragment.newsId = newsId;
        fragment.headString = head;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.xwxq_layout, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        initData();
    }

    private void initData() {
        homeActivity.showBottomNavigationView(false);
        getNEWSContent();
        getRecommend();
        getCommitById();
    }

    private void getCommitById() {
        new OkHttpTo().setUrl("getCommitById")
                .setJSONObject("id", newsId)
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        comments = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(),
                                new TypeToken<List<Comment>>() {
                                }.getType());

                        pls.setText("评论数："+comments.size());
                        listView2.setAdapter(new PinLunListViewAdapter(getActivity(), comments));
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }

    private void getRecommend() {
        new OkHttpTo().setUrl("getRecommend")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        JSONArray jsonArray = jsonObject.optJSONArray("ROWS_DETAIL");
                        newsIds = new String[jsonArray.length()];
                        for (int i = 0; i < jsonArray.length(); i++) {
                            newsIds[i] = jsonArray.optJSONObject(i).optString("newsid");
                        }

                        showTuiJianListView();
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }


    private void showTuiJianListView() {
        newsIdList = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            int rad = ((int) (Math.random() * 100)) % newsIds.length;
            newsIdList.add(newsIds[rad]);
        }
        listView1.setAdapter(new XinWenTuiJianAdapter(getActivity(), newsIdList));
        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                homeActivity.getFragmentTransaction().remove(fragment);
                fragment = null;
                homeActivity.setFragment(XinWenXiangQingFragment.newInstance(newsIdList.get(position) , headString));
            }
        });
    }

    private void getNEWSContent() {
        new OkHttpTo().setUrl("getNEWSContent")
                .setJSONObject("newsid", newsId)
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        News news = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").optJSONObject(0).toString(), News.class);
                        imageView.setImageUrl(news.getPicture());
                        text1.setText(news.getTitle());
                        text2.setText(news.getAbstractX());
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }

    private void initView(View view) {
        homeActivity = (HomeActivity) getActivity();
        fanHui = view.findViewById(R.id.fan_hui);
        fanHui.setOnClickListener(this);
        head = view.findViewById(R.id.head);
        imageView = view.findViewById(R.id.image_view);
        text1 = view.findViewById(R.id.text1);
        text2 = view.findViewById(R.id.text2);
        listView1 = view.findViewById(R.id.list_view1);
        editText = view.findViewById(R.id.edit_text);
        btn = view.findViewById(R.id.btn);
        btn.setOnClickListener(this);
        pls = view.findViewById(R.id.pls);
        listView2 = view.findViewById(R.id.list_view2);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fan_hui:
                switch (headString){
                    case "SY":
                        homeActivity.setFragment(SYFragment.newInstance());
                        break;
                    case "XWZL":
                        homeActivity.setFragment(XinWenZhuanLanFragment.newInstance(""));
                        break;
                    case "RMZT":
                        homeActivity.setFragment(ReMengZhuTiFragment.newInstance("" , ""));
                    default:
                }
                homeActivity.getFragmentTransaction().remove(fragment);
                fragment = null;
                break;
            case R.id.btn:
                if (editText.getText().toString().isEmpty()) {
                    MyUtil.showToast("评论不能为空");
                } else if (AppClient.getUserName().isEmpty()) {
                    MyUtil.showToast("请先登录");
                } else {
                    publicComit();
                }
                break;
            default:
        }
    }

    private void publicComit() {
        new OkHttpTo().setUrl("publicComit")
                .setJSONObject("username", AppClient.getUserName())
                .setJSONObject("newsid", newsId)
                .setJSONObject("commit", editText.getText().toString().trim())
                .setJSONObject("commitTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        if (jsonObject.optString("RESULT").equals("S")) {
                            MyUtil.showToast("提交成功");
                            getCommitById();
                        }
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }
}
