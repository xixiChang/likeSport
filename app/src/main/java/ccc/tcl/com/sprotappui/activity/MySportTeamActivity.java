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
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ccc.tcl.com.sprotappui.App;
import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.adapter.UserSportTeamItem;
import ccc.tcl.com.sprotappui.customui.ToolBar;
import ccc.tcl.com.sprotappui.model.PlatFormActivity;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.ActivityPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;

import static ccc.tcl.com.sprotappui.R.id.toolbar;

public class MySportTeamActivity extends BaseActivity {
    private Context context = this;
    private ToolBar toolBar;
    private RecyclerView recycler;
    private RelativeLayout layout;
    private ImageView noDataImage;
    private List<PlatFormActivity> sports = new ArrayList<>();
    private UserSportTeamItem adapter;
    ActivityPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sport_team);
        toolBar = (ToolBar) findViewById(toolbar);
        super.setToolBar(toolBar, R.string.user_sport_team, true);
        adapter = new UserSportTeamItem(sports);
        initView();
    }

    @Override
    protected void onResume() {
        presenter.onCreate();
        presenter.attachView(new SportAppView<ResponseResult<List<PlatFormActivity>>>() {
            @Override
            public void onSuccess(ResponseResult<List<PlatFormActivity>> response) {
                if (response.isSuccess()){
                    sports.clear();
                    sports.addAll(response.getResult());
                    if (!sports.isEmpty()) {
                        recycler.setVisibility(View.VISIBLE);
                        layout.setVisibility(View.GONE);
                    }
                    adapter.notifyDataSetChanged();

                }
                else
                    Toast.makeText(MySportTeamActivity.this,"获取数据失败"+response.getMsg(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestError(String msg) {
                Toast.makeText(MySportTeamActivity.this,"网络连接失败"+msg,Toast.LENGTH_SHORT).show();
            }
        });
        initRecycler();
        super.onResume();
    }

    private void initView() {
        presenter = new ActivityPresenter();
        noDataImage = (ImageView) findViewById(R.id.no_data_image);
        recycler = (RecyclerView) findViewById(R.id.recycler);
        layout = (RelativeLayout) findViewById(R.id.no_data_layout);

        noDataImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initRecycler();
            }
        });
    }

    private void initRecycler() {
        presenter.getMyActivity(App.userInfo.getId());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);

        adapter.setListener(new UserSportTeamItem.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (!sports.get(position).getPublish_user_id().equals(sports.get(position).getUser_id())){
                    Intent intent = new Intent(context, LayoutActivity.class);
                    Bundle data = new Bundle();
                    data.putParcelable("data",sports.get(position));
                    intent.putExtras(data);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(context, NewCreateActivity.class);
                    Bundle data = new Bundle();
                    data.putParcelable("data",sports.get(position));
                    intent.putExtras(data);
                    startActivity(intent);
                }

            }
        });
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(adapter);

    }

    private void initData() {

        PlatFormActivity userSport;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

        for (int i=0; i<18; i++){
            userSport = new PlatFormActivity();
            String imageID = getResources().getIdentifier(
                    "p" + ((i+1)>9 ? i-8 : i+1), "drawable", getPackageName()) + "";
            userSport.setName("这是活动"+ (i+1));
            userSport.setPublish_user_id(i < 4 ? userSport.getUser_id() : "sasd");
            userSport.setJoiner(userSport.getUser_id());
            userSport.setImage_url(imageID);
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
