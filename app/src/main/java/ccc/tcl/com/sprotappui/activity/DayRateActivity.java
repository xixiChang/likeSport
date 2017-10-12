package ccc.tcl.com.sprotappui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.adapter.DayRateItem;
import ccc.tcl.com.sprotappui.customui.RecycleViewDivider;
import ccc.tcl.com.sprotappui.customui.ToolBar;
import ccc.tcl.com.sprotappui.data.UserInfo;
import ccc.tcl.com.sprotappui.model.DayRate;
import ccc.tcl.com.sprotappui.model.RateItem;
import ccc.tcl.com.sprotappui.model.Record;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.RecordPresenter;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.UserPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;
import de.hdodenhof.circleimageview.CircleImageView;

import static ccc.tcl.com.sprotappui.R.id.toolbar;

public class DayRateActivity extends BaseActivity {
    private List<RateItem> rates;
    private ToolBar toolBar;
    private TextView dayDistance, dayConsume, userName;
    private CircleImageView headImage;
    private RecyclerView recyclerView;
    private Context context;
    private UserPresenter userPresenter;
    private RecordPresenter recordPresenter;
    private String[] data;
    private SportAppView recordView = new SportAppView<ResponseResult<List<RateItem>>>() {
        @Override
        public void onSuccess(ResponseResult<List<RateItem>> response) {
            if (response.isSuccess()){
                rates = response.getResult();
                setRecyclerViewAdapter();

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
        toolBar = (ToolBar) findViewById(toolbar);
        super.setToolBar(toolBar, R.string.day_rate, true);

        initView();
    }

    @Override
    protected void onResume() {
        userPresenter.onCreate();
        recordPresenter.onCreate();
        recordPresenter.attachView(recordView);
        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        recordPresenter.getRating();
        super.onResume();
    }

    private void initView() {
        dayDistance = (TextView) findViewById(R.id.day_distance);
        dayConsume = (TextView) findViewById(R.id.day_consume);
        userName = (TextView) findViewById(R.id.day_rate_user_name);
        headImage = (CircleImageView) findViewById(R.id.day_rate_user_photo);
        recyclerView = (RecyclerView) findViewById(R.id.day_rate_recycler);

        //initData();


        dayDistance.setText("12.1");
        dayConsume.setText("35465");
        userName.setText(data[0]);
        Glide.with(this).load(data[1]).into(headImage);
        //headImage.setImageResource(R.drawable.photo1);

        userPresenter = new UserPresenter();
        recordPresenter = new RecordPresenter();
    }

    private void initData() {
        rates = new ArrayList<>();
        DayRate dayRate;
        for (int i=0; i<6; i++){
            dayRate = new DayRate();
            dayRate.setRating((i+1) + "");
            dayRate.setImage_url(R.drawable.photo1 + "");
            dayRate.setUserName("用户"+ (i+1));
            dayRate.setUserDesc("用户"+ (i+1) + "的描述");
            dayRate.setUserDist(""+new Float((6-i)*6*0.8).floatValue());
            //rates.add(dayRate);
        }
    }

    private void setRecyclerViewAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        DayRateItem adapter = new DayRateItem(rates);
        recyclerView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, 2, R.color.darkgrey));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }
}
