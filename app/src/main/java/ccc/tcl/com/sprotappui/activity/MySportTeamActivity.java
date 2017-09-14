package ccc.tcl.com.sprotappui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.adapter.UserSportTeamItem;
import ccc.tcl.com.sprotappui.customui.ToolBar;
import ccc.tcl.com.sprotappui.model.UserSport;

import static ccc.tcl.com.sprotappui.R.id.toolbar;

public class MySportTeamActivity extends BaseActivity {
    private Context context = this;
    private ToolBar toolBar;
    private RecyclerView recycler;
    private RelativeLayout layout;
    private ImageView noDataImage;
    private List<UserSport> sports;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sport_team);
        toolBar = (ToolBar) findViewById(toolbar);
        super.setToolBar(toolBar, R.string.user_sport_team, true);
        initView();
    }

    private void initView() {
        noDataImage = (ImageView) findViewById(R.id.no_data_image);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        layout = (RelativeLayout) findViewById(R.id.no_data_layout);
        noDataImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRecycler();
            }
        });
    }

    private void showRecycler() {
        initData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        UserSportTeamItem adapter = new UserSportTeamItem(sports);
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(adapter);
        recycler.setVisibility(View.VISIBLE);
        layout.setVisibility(View.GONE);
    }

    private void initData() {
        sports = new ArrayList<>();
        UserSport userSport;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        for (int i=0; i<18; i++){
            userSport = new UserSport();
            String imageID = getResources().getIdentifier(
                    "p" + ((i+1)>9 ? i-8 : i+1), "drawable", getPackageName()) + "";
            userSport.setName("这是活动"+ (i+1));
            userSport.setImageUrl(imageID);
            userSport.setBehavior(i%2 == 0 ? "发起人":"参与");
            userSport.setStatus( i < 2 ? "正在进行":"已结束");
            userSport.setTime(simpleDateFormat.format(new Date()) + "-" +
                    simpleDateFormat.format(new Date()));
            userSport.setValue((i+1) * 14 +3 + "参与");
            sports.add(userSport);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sport_team_toolbar, menu);
        return true;
    }
}
