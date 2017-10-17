package ccc.tcl.com.sprotappui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dl7.tag.TagView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import ccc.tcl.com.sprotappui.App;
import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.activity.CountTimerActivity;
import ccc.tcl.com.sprotappui.constant.Constant;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.RecordPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;
import ccc.tcl.com.sprotappui.utils.TimeTranslator;
import ccc.tcl.com.sprotappui.utils.Util;
import ccc.tcl.com.sprotappui.utils.Utility;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewSportFragment extends Fragment {
    private String sportType = "0";

    private RecordPresenter recordPresenter;

    private TextView distance, time, speed;
    private ImageView weatherIcon;
    private TextView temp, pm2, status;
    private TagView goRun;

    private List<Map<String, String>> result;

    final String key = "e84cede8d2f249b8b1454f3159bcc2a2";
    final String cityName = "huizhou";


    public NewSportFragment() {
    }

    public static NewSportFragment getInstance() {
        NewSportFragment sf = new NewSportFragment();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recordPresenter = new RecordPresenter();
    }


    private void initView(View view) {
        distance = (TextView) view.findViewById(R.id.daily_distance);
        time = (TextView) view.findViewById(R.id.fragment_sport_time);
        speed = (TextView) view.findViewById(R.id.fragment_sport_speed);

        weatherIcon = (ImageView) view.findViewById(R.id.weather_icon);
        temp = (TextView) view.findViewById(R.id.temp);
        pm2 = (TextView) view.findViewById(R.id.pm_text);
        status = (TextView) view.findViewById(R.id.weather_text);

        goRun = (TagView) view.findViewById(R.id.start_sport);

        goRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CountTimerActivity.class);
                intent.putExtra("FLAG", sportType);
                getActivity().startActivity(intent);
            }
        });

        sendRequest(cityName, key);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sport, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recordPresenter.onCreate();
        recordPresenter.attachView(sportAppView);
        recordPresenter.getTypeSumDay(App.userInfo.getId());
    }

    private SportAppView<ResponseResult<List<Map<String, String>>>> sportAppView
            = new SportAppView<ResponseResult<List<Map<String, String>>>>() {
        @Override
        public void onSuccess(ResponseResult<List<Map<String, String>>> response) {
            if (response.isSuccess()) {
                result = response.getResult();
                flushDataShow();
            } else
                Toast.makeText(getContext(), "获取运动数据失败:" + response.getMsg(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestError(String msg) {
            Toast.makeText(getContext(), "获取运动数据失败:" + msg, Toast.LENGTH_SHORT).show();
        }
    };

    private void flushDataShow() {
        if (result == null || result.size() == 0)
            return;
        for (Map<String, String> map : result) {
            if (sportType.equals(map.get("type"))) {
                String sD = map.get("distance");
                String sT = map.get("spent_time");
                distance.setText(sD);
                time.setText(TimeTranslator.secToTime(Integer.parseInt(sT)));

                speed.setText(Util.getSpeed(sD, sT));
                return;
            }
        }

        distance.setText("0");
        time.setText("00:00:00");
        speed.setText("N/A");
    }


    private void sendRequest(String cityName, String key) {
        final String address1 = "https://free-api.heweather.com/v5/aqi?city=shenzhen&key=" + key;
        final String address2 = "https://free-api.heweather.com/v5/now?city=" + cityName + "&key=" + key;

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                Request request1 = new Request.Builder().url(address1).build();
                Request request2 = new Request.Builder().url(address2).build();
                try {
                    Response response1 = client.newCall(request1).execute();
                    String responseData1 = response1.body().string();
                    String aqiData = Utility.handleAqiResponse(responseData1);
                    Response response2 = client.newCall(request2).execute();
                    String responseData2 = response2.body().string();
                    List<String> nowData ;
                    nowData = Utility.handleNowResponse(responseData2);
                    if (nowData == null || nowData.size() < 2)
                        return;
                    String txt = nowData.get(0);
                    String tmp = nowData.get(1);
                    showResponse(aqiData, txt, tmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void showResponse(final String aqiData, final String txt, final String tmp) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    pm2.setText(aqiData);
                    weatherIcon.setImageResource(Constant.weatherMap.get(txt));//天气图标显示
                    status.setText(txt);
                    temp.setText(tmp + "℃");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void setSport_Type(int position) {
        this.sportType = String.valueOf(position);
        flushDataShow();
    }
}
