package ccc.tcl.com.sprotappui.fragment;

import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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


@Deprecated
public class SportFragment extends Fragment {
    private String sportType = "0";

    private static final String TAG = "SportFragment";
    private int spinnerFlag;
    private Intent intent, r_Intent;
    final String key = "e84cede8d2f249b8b1454f3159bcc2a2";
    final String cityName = "huizhou";
    private RecordPresenter recordPresenter;
    //控件
    TextView textView1, textView2, textView3, tv, tw1, tw2, tw3;
    ImageView iv;
    Button goRun, rButton;
    View v;
    Spinner spinner;
    private static final int VIEW_TYPE_RUN = 0;
    private static final int VIEW_TYPE_WALK = 1;
    private static final int VIEW_TYPE_RIDE = 2;


    private List<Map<String, String>> result;

    public static SportFragment getInstance() {
        SportFragment sf = new SportFragment();
        return sf;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fr_simple_card, null);
        init();
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private SportAppView<ResponseResult<List<Map<String, String>>>> sportAppView
            = new SportAppView<ResponseResult<List<Map<String, String>>>>() {
        @Override
        public void onSuccess(ResponseResult<List<Map<String, String>>> response) {

            if (response.isSuccess()) {
                result = response.getResult();
                flushDataShow();
                switch (spinnerFlag) {
                    case 0:
                        tv.setText("公里");
                        break;
                    case 1:
                        tv.setText("步数");
                        break;
                    case 2:
                        tv.setText("公里");
                        break;
                    default:
                        break;
                }
            }
        }

        @Override
        public void onRequestError(String msg) {
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        recordPresenter.onStop();
    }


    private void init() {
        //显示
        textView1 = (TextView) v.findViewById(R.id.DISTANCE);
        textView2 = (TextView) v.findViewById(R.id.TIME);
        textView3 = (TextView) v.findViewById(R.id.SPEED);
        tv = (TextView) v.findViewById(R.id.tv_change);
        goRun = (Button) v.findViewById(R.id.pause);

        tw1 = (TextView) v.findViewById(R.id.QUALITY);//空气质量
        tw2 = (TextView) v.findViewById(R.id.TXT1);//天气状况
        iv = (ImageView) v.findViewById(R.id.TXT);//天气状况
        tw3 = (TextView) v.findViewById(R.id.TEP);//温度
        sendRequst(cityName, key);
//************************************************************************************************
//        rButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                r_Intent = new Intent(getContext(), RecordActivity.class);
//                getActivity().startActivity(r_Intent);
//            }
//        });

        final SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");//获取日期
        recordPresenter = new RecordPresenter();
        recordPresenter.onCreate();
        recordPresenter.attachView(sportAppView);

        //开始按键事件触发

        goRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getContext(), CountTimerActivity.class);
                intent.putExtra("FLAG", spinnerFlag);
                Log.d("tag", "onClick: " + spinnerFlag);
                getActivity().startActivity(intent);
            }
        });
    }


    //请求获取天气数据
    private void sendRequst(String cityName, String key) {
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
                    List<String> nowData;
                    nowData = Utility.handleNowResponse(responseData2);
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
                    tw1.setText(aqiData);
                    iv.setImageResource(Constant.weatherMap.get(txt));//天气图标显示
                    tw2.setText(txt);
                    tw3.setText(tmp + "℃");
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

    private void flushDataShow() {
        if (result == null || result.size() == 0)
            return;
        for (Map<String, String> map : result) {
            if (sportType.equals(map.get("type"))) {
                String sD = map.get("distance");
                String sT = map.get("spent_time");
                textView1.setText(sD);
                textView2.setText(TimeTranslator.secToTime(Integer.parseInt(sT)));
                textView3.setText(Util.getSpeed(sD, sT));
                return;
            }
        }

        textView1.setText("0");
        textView2.setText("00:00:00");
        textView3.setText("N/A");
    }
}