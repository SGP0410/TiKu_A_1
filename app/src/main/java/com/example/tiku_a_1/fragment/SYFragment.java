package com.example.tiku_a_1.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.tiku_a_1.R;
import com.example.tiku_a_1.activity.HomeActivity;
import com.example.tiku_a_1.adapter.FuWuRuKouGridViewAdapter;
import com.example.tiku_a_1.adapter.LBTViewPagerAdapter;
import com.example.tiku_a_1.adapter.ReMengZhuTiGridViewAdapter;
import com.example.tiku_a_1.bean.LBTImage;
import com.example.tiku_a_1.bean.Service;
import com.example.tiku_a_1.net.OkHttpLo;
import com.example.tiku_a_1.net.OkHttpTo;
import com.example.tiku_a_1.util.ImageViewNet;
import com.example.tiku_a_1.util.MyUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class SYFragment extends Fragment implements View.OnClickListener {
    private static SYFragment fragment;
    private EditText etxSs;
    private Button souSuo;
    private Button sz;
    private Button yq;
    private Button yl;
    private ViewPager viewPager;
    private GridView gridView1;
    private GridView gridView2;
    private HomeActivity homeActivity;
    private List<LBTImage> lbtImages;
    private List<View> views;
    private boolean isLoop;
    private List<String> serviceTypes;
    private List<Service> serviceList;
    private Map<Integer, Integer> serviceWeight;
    private String[] subjects;
    private Thread thread;

    public static SYFragment newInstance() {
        if (fragment == null) {
            fragment = new SYFragment();
        }
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sy_layout, null);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        getImages();
        getAllServiceType();
        getAllSubject();
    }

    private void getAllSubject() {
        new OkHttpTo().setUrl("getAllSubject")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        String s = jsonObject.optString("ROWS_DETAIL");
                        s = s.substring(1, s.length());
                        s = s.substring(0, s.length() - 1);
                        subjects = s.split(", ");
                        showGridView2();
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }

    private void showGridView2() {
        gridView2.setAdapter(new ReMengZhuTiGridViewAdapter(getContext(), subjects));
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                homeActivity.setFragment(ReMengZhuTiFragment.newInstance(subjects[position], "SY"));
            }
        });
    }

    /**
     * @ 获取服务类型
     */
    private void getAllServiceType() {
        new OkHttpTo().setUrl("getAllServiceType")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        serviceTypes = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(),
                                new TypeToken<List<String>>() {
                                }.getType());
                        getServiceByType();
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }

    private void getServiceByType() {
        if (serviceList == null) {
            serviceList = new ArrayList<>();
        }
        for (final String s : serviceTypes) {
            new OkHttpTo().setUrl("getServiceByType")
                    .setJSONObject("serviceType", s)
                    .setOkHttpLo(new OkHttpLo() {
                        @Override
                        public void onResponse(Call call, JSONObject jsonObject) {
                            serviceList.addAll((Collection<? extends Service>) new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(),
                                    new TypeToken<List<Service>>() {
                                    }.getType()));
                            if (s.equals(serviceTypes.get(serviceTypes.size() - 1))) {
                                getServiceInOrder();
                            }
                        }

                        @Override
                        public void onFailure(Call call, IOException e) {

                        }
                    }).start();
        }
    }

    private void getServiceInOrder() {
        serviceWeight = new HashMap<>();
        new OkHttpTo().setUrl("getServiceInOrder")
                .setJSONObject("order", 1)
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        JSONArray jsonArray = jsonObject.optJSONArray("ROWS_DETAIL");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.optJSONObject(i);
                            serviceWeight.put(jsonObject1.optInt("id"), jsonObject1.optInt("weight"));
                        }
                        showGridView1();
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }

    private void showGridView1() {
        for (Service s : serviceList) {
            s.setWeight(serviceWeight.get(s.getServiceid()));
        }

        Collections.sort(serviceList, new Comparator<Service>() {
            @Override
            public int compare(Service o1, Service o2) {
                return o2.getWeight() - o1.getWeight();
            }
        });

        final List<Service> services = new ArrayList<>();

        for (int i = 0; i < 9; i++) {
            services.add(serviceList.get(i));
        }
        Service service = new Service();
        service.setWeight(R.mipmap.gdfw);
        service.setServiceName("更多服务");
        services.add(service);
        gridView1.setAdapter(new FuWuRuKouGridViewAdapter(getContext(), services));
        gridView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (services.get(position).getServiceName().equals("更多服务")) {
                    homeActivity.setFragment(GengDuoFuWuFragment.newInstance(serviceList));
                }else {
                    homeActivity.setFragment(WebViewFragment.newInstance(services.get(position).getUrl() , "SY"));
                }
            }
        });
    }

    /**
     * @获取轮播图的图片
     */
    private void getImages() {
        new OkHttpTo().setUrl("getImages")
                .setOkHttpLo(new OkHttpLo() {
                    @Override
                    public void onResponse(Call call, JSONObject jsonObject) {
                        lbtImages = new Gson().fromJson(jsonObject.optJSONArray("ROWS_DETAIL").toString(),
                                new TypeToken<List<LBTImage>>() {
                                }.getType());
                        showViewPager();
                    }

                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                }).start();
    }

    private void showViewPager() {
        views = new ArrayList<>();
        for (final LBTImage l : lbtImages) {
            ImageViewNet imageViewNet = new ImageViewNet(getContext());
            imageViewNet.setImageUrl(l.getPath());
            imageViewNet.setScaleType(ImageView.ScaleType.FIT_XY);
            imageViewNet.setLayoutParams(new LinearLayout.LayoutParams(1080, 230));
            imageViewNet.setOnClickListener(this);
            imageViewNet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    homeActivity.setFragment(XinWenXiangQingFragment.newInstance(l.getNewid() + "", "SY"));
                }
            });
            views.add(imageViewNet);
        }
        LBTViewPagerAdapter adapter = new LBTViewPagerAdapter(views);
        viewPager.setAdapter(adapter);
        loopViewPager();
    }

    private MyThread myThread;
    private void loopViewPager() {
        isLoop = true;
        if (myThread==null){
            myThread = new MyThread();
            myThread.start();
        }
    }

    class MyThread extends Thread{
        @Override
        public void run() {
            super.run();
            while (isLoop) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("thread", "---------------" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                homeActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    }
                });
            }
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        homeActivity.showBottomNavigationView(true);
        if (hidden) {
            isLoop =false;
            myThread.interrupt();
            myThread = null;
        } else {
            loopViewPager();
        }
    }

    private void initView(View view) {
        etxSs = view.findViewById(R.id.etx_ss);
        souSuo = view.findViewById(R.id.sou_suo);
        souSuo.setOnClickListener(this);
        sz = view.findViewById(R.id.sz);
        sz.setOnClickListener(this);
        yq = view.findViewById(R.id.yq);
        yq.setOnClickListener(this);
        yl = view.findViewById(R.id.yl);
        yl.setOnClickListener(this);
        viewPager = view.findViewById(R.id.view_pager);
        gridView1 = view.findViewById(R.id.grid_view1);
        gridView2 = view.findViewById(R.id.grid_view2);
        homeActivity = (HomeActivity) getActivity();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sou_suo:
                if (etxSs.getText().toString().isEmpty()) {
                    MyUtil.showToast("搜索内容不能为空");
                } else {
                    //关闭弹出的键盘
                    ((InputMethodManager) homeActivity.getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(getActivity().getCurrentFocus()
                                            .getWindowToken(),
                                    InputMethodManager.HIDE_NOT_ALWAYS);
                    homeActivity.setFragment(SouSuoFragment.newInstance(etxSs.getText().toString()));
                    etxSs.setText("");
                }
                break;
            case R.id.sz:
                homeActivity.setFragment(XinWenZhuanLanFragment.newInstance("时政"));
                break;
            case R.id.yq:
                homeActivity.setFragment(XinWenZhuanLanFragment.newInstance("疫情"));
                break;
            case R.id.yl:
                homeActivity.setFragment(XinWenZhuanLanFragment.newInstance("娱乐"));
                break;
            default:
        }
    }

}
