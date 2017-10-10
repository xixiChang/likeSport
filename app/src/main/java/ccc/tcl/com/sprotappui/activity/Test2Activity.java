package ccc.tcl.com.sprotappui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
//import com.github.mikephil.charting.charts.BarChart;
//import com.github.mikephil.charting.components.Legend;
//import com.github.mikephil.charting.components.XAxis;
//import com.github.mikephil.charting.data.BarData;
//import com.github.mikephil.charting.data.BarDataSet;
//import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;
import java.util.List;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.customui.ToolBar;
import ccc.tcl.com.sprotappui.entity.TabEntity;
import ccc.tcl.com.sprotappui.utils.ViewFindUtils;

import static ccc.tcl.com.sprotappui.R.id.toolbar;

public class Test2Activity extends BaseActivity {
    private ToolBar toolBar;
    private CommonTabLayout mTabLayout;
    private View mDecorView;

    private String[] mTitles = {"周", "年", "总"};
    private int[] nullID = {0, 0, 0};
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
//    private BarChart chart;
//
//    private List<BarEntry> entries = new ArrayList<>();
//    private BarData barData;
//    private BarDataSet barDataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2);

        toolBar = (ToolBar) findViewById(toolbar);
        super.setToolBar(toolBar, R.string.user_score, true);

        initView();
        initData();
        setTabLayout();
    }

    private void initData() {
//        entries.add(new BarEntry(1f, 300f));
//        entries.add(new BarEntry(2f, 400f));
//        entries.add(new BarEntry(3f, 350f));
//        entries.add(new BarEntry(4f, 450f));
//        entries.add(new BarEntry(5f, 670f));
//        entries.add(new BarEntry(6f, 900f));
//        entries.add(new BarEntry(7f, 300f));
//        entries.add(new BarEntry(8f, 400f));
//        entries.add(new BarEntry(9f, 350f));
//        entries.add(new BarEntry(20f, 450f));
//        entries.add(new BarEntry(25f, 450f));
//        entries.add(new BarEntry(28f, 450f));
//        entries.add(new BarEntry(30f, 450f));
//
//        barDataSet = new BarDataSet(entries, null);
//        barDataSet.setColor(R.color.blueviolet);
//
//
//        barData = new BarData(barDataSet);
//        chart.setData(barData);
//        chart.invalidate();


        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], nullID[i], nullID[i]));
        }
    }

    private void initView() {
//        chart = (BarChart) findViewById(R.id.chart);
//        chart.setTouchEnabled(false);
//        chart.getXAxis().setDrawGridLines(false);
//        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
//        chart.getAxisLeft().setDrawGridLines(false);
//        chart.getAxisRight().setEnabled(false);
//
//        chart.setDescription(null);
//
//        Legend legend = chart.getLegend();
//        legend.setEnabled(false);
//        legend.setPosition(Legend.LegendPosition.ABOVE_CHART_CENTER);

        mTabLayout = (CommonTabLayout) findViewById(R.id.history_tab);
        mDecorView = getWindow().getDecorView();
        mTabLayout = ViewFindUtils.find(mDecorView, R.id.history_tab);

    }

    private void setTabLayout(){
        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                Toast.makeText(Test2Activity.this, position+"", Toast.LENGTH_SHORT).show();
//                getDataFromServer();
//                flushChart();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }

//    private void getDataFromServer() {
//        if (entries.size() > 0)
//            entries.remove(0);
//    }

//    private void flushChart() {
//        barDataSet.notifyDataSetChanged();
//        barData.notifyDataChanged();
//        chart.notifyDataSetChanged();
//        chart.invalidate();
//    }


}
