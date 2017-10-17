package ccc.tcl.com.sprotappui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ccc.tcl.com.sprotappui.App;
import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.customui.ToolBar;
import ccc.tcl.com.sprotappui.entity.TabEntity;

import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.RecordPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;
import ccc.tcl.com.sprotappui.utils.ViewFindUtils;

import static ccc.tcl.com.sprotappui.R.id.toolbar;

public class RecordActivity extends BaseActivity {
    private ToolBar toolBar;
    private CommonTabLayout mTabLayout;
    private View mDecorView;

    private String[] mTitles = {"周", "月", "总"};
    private int[] nullID = {0, 0, 0};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private BarChart chart;
    private TextView t1, t2, t3, t4;

    private List<BarEntry> entries = new ArrayList<>();
    private List<BarEntry> entries0 = new ArrayList<>();
    private List<BarEntry> entries1 = new ArrayList<>();
    private List<BarEntry> entries2 = new ArrayList<>();
    private BarData barData;
    private BarDataSet barDataSet;

    private RecordPresenter recordPresenter1;
    private RecordPresenter recordPresenter2;
    private RecordPresenter recordPresenter3;
    private final static String TAG = "Test2Activity";
    List<Map<String, String>> lisMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record2);

        toolBar = (ToolBar) findViewById(toolbar);
        super.setToolBar(toolBar, R.string.user_score, true);
        t1 = (TextView) findViewById(R.id.RECORD_DISTANCE);
        t2 = (TextView) findViewById(R.id.RECORD_TIME);
        t3 = (TextView) findViewById(R.id.RECORD_P_SPEED);
        t4 = (TextView) findViewById(R.id.RECORD_C);

        initView();
        initData();
        setTabLayout();
    }

    private void initData() {
        recordPresenter1 = new RecordPresenter();
        recordPresenter2 = new RecordPresenter();
        recordPresenter3 = new RecordPresenter();
        recordPresenter1.onCreate();
        recordPresenter2.onCreate();
        recordPresenter3.onCreate();
        recordPresenter1.getAllSum(App.userInfo.getId());
        recordPresenter1.attachView(sportAppView1);
        recordPresenter2.getByWeekly();
        recordPresenter2.attachView(sportAppView2);
        recordPresenter3.getByMonthly();
        recordPresenter3.attachView(sportAppView3);

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], nullID[i], nullID[i]));
        }
    }

    private void initView() {
        chart = (BarChart) findViewById(R.id.chart);
        chart.setTouchEnabled(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setEnabled(false);
        chart.setDescription(null);
        chart.getXAxis().setLabelCount(entries.size());
        Legend legend = chart.getLegend();
        legend.setEnabled(false);

        mTabLayout = (CommonTabLayout) findViewById(R.id.history_tab);
        mDecorView = getWindow().getDecorView();
        mTabLayout = ViewFindUtils.find(mDecorView, R.id.history_tab);

    }

    private void setTabLayout(){
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                getDataFromServer(position);
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

    private void getDataFromServer(int position) {
//        if (entries.size() > 0)
//            entries.remove(0);
        switch (position){
            case 0:
                entries=entries0;
                break;
            case 1:
                entries=entries1;
                break;
            case 2:
                entries=entries2;
                break;

        }
        flushChart();
    }

    private void flushChart() {
        Log.d(TAG, "flushChart: "+entries.size());
//        barDataSet.notifyDataSetChanged();
//        barData.notifyDataChanged();
//        chart.notifyDataSetChanged();
//        chart.invalidate();
        barDataSet = new BarDataSet(entries, null);
        barDataSet.setColor(R.color.blueviolet);

        barData = new BarData(barDataSet);
        chart.setData(barData);
        chart.invalidate();

    }
    private SportAppView<ResponseResult<Map<String, String>>> sportAppView1
            = new SportAppView<ResponseResult<Map<String, String>>>() {
        @Override
        public void onSuccess(ResponseResult<Map<String, String>> response) {
            if (response.isSuccess()) {
                Log.d(TAG, "onSuccess: 请求成功");
                Map<String, String> map = response.getResult();
                //T
                Log.d(TAG, "onSuccess: " + map.get("step"));
//                xTotal.add("1");
//                yTotal.add(map.get("step"));//总记录

                entries2.add(new BarEntry(1f,
                        Float.valueOf(map.get("step"))));
                t1.setText(map.get("step"));
                t2.setText(map.get("distance"));
                t3.setText(map.get("spent_time"));
                t4.setText(map.get("calorie"));
            }
        }

        @Override
        public void onRequestError(String msg) {
            Log.d(TAG, "onSuccess: 请求失败");

        }
    };
    private SportAppView<ResponseResult<List<Map<String, String>>>> sportAppView2
            = new SportAppView<ResponseResult<List<Map<String, String>>>>() {
        @Override
        public void onSuccess(ResponseResult<List<Map<String, String>>> response) {
            if (response.isSuccess()) {
                lisMap = response.getResult();
                for (int i = 0; i < lisMap.size(); i++) {

                    entries0.add(new BarEntry(Float.valueOf(lisMap.get(i).get("week")),
                            Float.valueOf(lisMap.get(i).get("step"))));

                    barDataSet = new BarDataSet(entries0, null);
                    barDataSet.setColor(R.color.blueviolet);
                    barData = new BarData(barDataSet);
                    chart.setData(barData);
                    chart.invalidate();

                }
            }
        }

        @Override
        public void onRequestError(String msg) {
        }
    };
    private SportAppView<ResponseResult<List<Map<String, String>>>> sportAppView3
            = new SportAppView<ResponseResult<List<Map<String, String>>>>() {
        @Override
        public void onSuccess(ResponseResult<List<Map<String, String>>> response) {
            if (response.isSuccess()) {
                lisMap = response.getResult();
                for (int i = 0; i < lisMap.size(); i++) {
                    entries1.add(new BarEntry(Float.valueOf(lisMap.get(i).get("month")),
                            Float.valueOf(lisMap.get(i).get("step"))));
                }
            }
        }

        @Override
        public void onRequestError(String msg) {
        }
    };
}

