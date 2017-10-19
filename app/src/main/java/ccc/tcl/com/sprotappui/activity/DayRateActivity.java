package ccc.tcl.com.sprotappui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.adapter.DayRateItem;
import ccc.tcl.com.sprotappui.customui.RecycleViewDivider;
import ccc.tcl.com.sprotappui.model.RateItem;
import ccc.tcl.com.sprotappui.model.Record;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.RecordPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;
import de.hdodenhof.circleimageview.CircleImageView;

public class DayRateActivity extends BaseActivity {
    private List<RateItem> rates = new ArrayList<>();
    private Toolbar toolBar;
    private TextView dayDistance, dayConsume, userName;
    private CircleImageView headImage;
    private RecyclerView recyclerView;
    private Context context;

    private RecordPresenter myRecordPresenter;
    private RecordPresenter ratingRecordPresenter;
    private String[] data;
    private DayRateItem adapter;
    private SportAppView ratingList = new SportAppView<ResponseResult<List<RateItem>>>() {
        @Override
        public void onSuccess(ResponseResult<List<RateItem>> response) {
            if (response.isSuccess()){
                rates.clear();
                if (response.getResult() == null || response.getResult().size() == 0){
                    RateItem item = new RateItem();
                    item.setRetain("今天还没有人上传记录哦～");
                    rates.add(item);
                    return;
                }

                rates.addAll(response.getResult());
                adapter.notifyDataSetChanged();
            }
            else {
                Toast.makeText(DayRateActivity.this, "未获取到数据:" + response.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onRequestError(String msg) {
            Toast.makeText(DayRateActivity.this, "未获取到数据:" + msg, Toast.LENGTH_SHORT).show();
        }
    };

    private SportAppView myRecord = new SportAppView<ResponseResult<List<Record>>>() {
        @Override
        public void onSuccess(ResponseResult<List<Record>> response) {
            if (response.isSuccess()){
                int distance = 0 , calorie =0;
                if (response.getResult() == null)
                    return;
                for (int i = 0;i < response.getResult().size();i++){
                    distance += response.getResult().get(i).getDistance();
                    calorie += response.getResult().get(i).getCalorie();
                }
                dayDistance.setText(distance+"");
                dayConsume.setText(calorie+"");
            }
        }

        @Override
        public void onRequestError(String msg) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_rate);
        Intent intent = getIntent();
        data = intent.getStringArrayExtra("data");
        context = this;
        toolBar = (Toolbar) findViewById(R.id.contact_toolbar);
        super.setToolBar(toolBar, R.string.day_rate, true);
        this.getWindow().setStatusBarColor(getResources().getColor(R.color.transparent_color,null));
        //StatusBarCompat.compat(this, getResources().getColor(R.color.status_bar_color));
        initView();
    }

    @Override
    protected void onResume() {
        myRecordPresenter.onCreate();
        ratingRecordPresenter.onCreate();
        myRecordPresenter.attachView(myRecord);
        ratingRecordPresenter.attachView(ratingList);
        myRecordPresenter.getDayAll(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
        ratingRecordPresenter.getRating();
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        super.onResume();
    }

    private void initView() {
        dayDistance = (TextView) findViewById(R.id.day_distance);
        dayConsume = (TextView) findViewById(R.id.day_consume);
        userName = (TextView) findViewById(R.id.day_rate_user_name);
        headImage = (CircleImageView) findViewById(R.id.day_rate_user_photo);
        recyclerView = (RecyclerView) findViewById(R.id.day_rate_recycler);

        //initData();

        userName.setText(data[0]);
        Glide.with(this).load(data[1]).into(headImage);
        //headImage.setImageResource(R.drawable.photo1);
        myRecordPresenter = new RecordPresenter();
        ratingRecordPresenter = new RecordPresenter();
        setRecyclerViewAdapter();

    }


    private void setRecyclerViewAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        adapter = new DayRateItem(rates);
        recyclerView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, 2, R.color.darkgrey));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        myRecordPresenter.onStop();
        ratingRecordPresenter.onStop();
    }
}
