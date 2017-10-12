package ccc.tcl.com.sprotappui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import ccc.tcl.com.sprotappui.App;
import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.RecordPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;
import ccc.tcl.com.sprotappui.utils.TimeTranslator;
import ccc.tcl.com.sprotappui.utils.Util;

public class SportFragment extends Fragment {
    private static final String Type_Walk = "0";
    private static final String Type_Run = "1";
    private static final String Type_Ride = "2";

    private RecordPresenter recordPresenter;

    private TextView diatance, time, speed;


    public SportFragment() {
    }

    public static SportFragment getInstance() {
        SportFragment sf = new SportFragment();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recordPresenter = new RecordPresenter();
    }

    private void initView(View view) {
        diatance = (TextView) view.findViewById(R.id.daily_distance);
        time = (TextView) view.findViewById(R.id.fragment_sport_time);
        speed = (TextView) view.findViewById(R.id.fragment_sport_speed);
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
        //recordPresenter.getTypeSumDay(App.userInfo.getId());
    }

    private SportAppView<ResponseResult<List<Map<String, String>>>> sportAppView
            = new SportAppView<ResponseResult<List<Map<String, String>>>>() {
        @Override
        public void onSuccess(ResponseResult<List<Map<String, String>>> response) {
            if (response.isSuccess()) {
                flushDataShow(response.getResult());
                Toast.makeText(getContext(), "获取数据成功:" + response.getMsg(), Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getContext(), "获取数据失败:" + response.getMsg(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestError(String msg) {

        }
    };

    private void flushDataShow(List<Map<String, String>> result) {
        if (result == null || result.size() == 0)
            return;

        for (Map<String, String> map : result) {
            if (Type_Walk.equals(map.get("type"))) {
                String sD = map.get("distance");
                String sT = map.get("spent_time");
                diatance.setText(sD);
                time.setText(TimeTranslator.secToTime(Integer.parseInt(sT)));
                speed.setText(Util.getSpeed(sD, sT));
            }
        }

    }

}
