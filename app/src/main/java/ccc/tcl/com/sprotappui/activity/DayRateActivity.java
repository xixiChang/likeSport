package ccc.tcl.com.sprotappui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.adapter.DayRateItem;
import ccc.tcl.com.sprotappui.customui.RecycleViewDivider;
import ccc.tcl.com.sprotappui.customui.ToolBar;
import ccc.tcl.com.sprotappui.model.DayRate;
import de.hdodenhof.circleimageview.CircleImageView;

import static ccc.tcl.com.sprotappui.R.id.toolbar;

public class DayRateActivity extends BaseActivity {
    private List<DayRate> rates;
    private ToolBar toolBar;
    private TextView dayDistance, dayConsume, userName;
    private CircleImageView headImage;
    private RecyclerView recyclerView;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_rate);
        context = this;
        toolBar = (ToolBar) findViewById(toolbar);
        this.setToolBar(toolBar, R.string.day_rate);

        initView();
    }

    private void initView() {
        dayDistance = (TextView) findViewById(R.id.day_distance);
        dayConsume = (TextView) findViewById(R.id.day_consume);
        userName = (TextView) findViewById(R.id.day_rate_user_name);
        headImage = (CircleImageView) findViewById(R.id.day_rate_user_photo);
        recyclerView = (RecyclerView) findViewById(R.id.day_rate_recycler);

        initData();
        setRecyclerViewAdapter();

        dayDistance.setText("12.1");
        dayConsume.setText("35465");
        userName.setText("小狐丸");
        headImage.setImageResource(R.drawable.photo1);
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
            rates.add(dayRate);
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
