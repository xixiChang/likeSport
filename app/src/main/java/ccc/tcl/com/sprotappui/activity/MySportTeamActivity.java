package ccc.tcl.com.sprotappui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
import ccc.tcl.com.sprotappui.model.PlatFormActivity;
import ccc.tcl.com.sprotappui.model.UserSport;

import static ccc.tcl.com.sprotappui.R.id.toolbar;

public class MySportTeamActivity extends BaseActivity {
    private Context context = this;
    private ToolBar toolBar;
    private RecyclerView recycler;
    private RelativeLayout layout;
    private ImageView noDataImage;
    private List<PlatFormActivity> sports;

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
        adapter.setListener(new UserSportTeamItem.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, LayoutActivity.class);
                intent.putExtra("id",sports.get(position).getId());
                startActivity(intent);
            }
        });
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(adapter);
        recycler.setVisibility(View.VISIBLE);
        layout.setVisibility(View.GONE);
    }

    private void initData() {
        sports = new ArrayList<>();
        PlatFormActivity userSport;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        for (int i=0; i<18; i++){
            userSport = new PlatFormActivity();
            String imageID = getResources().getIdentifier(
                    "p" + ((i+1)>9 ? i-8 : i+1), "drawable", getPackageName()) + "";
            userSport.setName("这是活动"+ (i+1));
            userSport.setImage_url(imageID);
            //userSport.setBehavior(i%2 == 0 ? "发起人":"参与");
            userSport.setStatus( i < 2 ? "正在进行":"已结束");
            userSport.setStart_time(simpleDateFormat.format(new Date()));
            userSport.setEnd_time(simpleDateFormat.format(new Date()));
            userSport.setJoin_num((i+1) * 14 +3);
            sports.add(userSport);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_sport_team_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("", "onOptionsItemSelected: "+item.getItemId());
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.create_sport:
                Intent intent = new Intent(MySportTeamActivity.this,PickPictureActivity.class);
                startActivity(intent);
                //this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
