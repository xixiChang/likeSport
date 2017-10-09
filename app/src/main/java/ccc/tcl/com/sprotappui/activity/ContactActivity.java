package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ccc.tcl.com.sprotappui.App;
import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.adapter.UserSportTeamItem;
import ccc.tcl.com.sprotappui.customui.ToolBar;
import ccc.tcl.com.sprotappui.data.UserInfo;
import ccc.tcl.com.sprotappui.model.PlatFormActivity;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.ActivityPresenter;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.RecordPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;
import ccc.tcl.com.sprotappui.utils.Util;
import de.hdodenhof.circleimageview.CircleImageView;

public class ContactActivity extends BaseActivity {
    private Toolbar toolBar;
    private UserInfo userInfo;
    private RecordPresenter recordPresenter;
    private ActivityPresenter activityPresenter;
    private List<PlatFormActivity> sports = new ArrayList<>();

    private CircleImageView userImage;
    private TextView signature;
    private TextView runDistance;
    private TextView walkDistance;
    private TextView rideDistance;
    private RecyclerView recycler;
    private UserSportTeamItem adapter;
    private SportAppView userView = new SportAppView() {
        @Override
        public void onSuccess(Object response) {

        }

        @Override
        public void onRequestError(String msg) {

        }
    };
    private SportAppView activityView = new SportAppView<ResponseResult<List<PlatFormActivity>>>() {
        @Override
        public void onSuccess(ResponseResult<List<PlatFormActivity>> response) {
            if (response.isSuccess()){
                sports.clear();
                sports.addAll(response.getResult());
                adapter.notifyDataSetChanged();
            }
            else
                Toast.makeText(ContactActivity.this,"获取数据失败"+response.getMsg(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestError(String msg) {
            Toast.makeText(ContactActivity.this,"网络连接失败"+msg,Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        toolBar = (Toolbar) findViewById(R.id.contact_toolbar);


        Intent intent0 = getIntent();
        userInfo = (UserInfo) intent0.getSerializableExtra("userInfo");
        super.setToolBar(toolBar, userInfo.getName(), true);
        recordPresenter = new RecordPresenter();
        activityPresenter = new ActivityPresenter();
        initView();
    }
    private void initView(){
        userImage = (CircleImageView) findViewById(R.id.user_image);
        signature = (TextView) findViewById(R.id.user_signature);
        runDistance = (TextView) findViewById(R.id.run_distance);
        walkDistance = (TextView) findViewById(R.id.walk_distance);
        runDistance = (TextView) findViewById(R.id.ride_distance);
        recycler = (RecyclerView) findViewById(R.id.user_activity);
        initRecycler();
//        Glide.with(this).load(userInfo.getImage_url()).into(userImage);
//        signature.setText(userInfo.getRetain());

    }

    private void initRecycler() {
        adapter = new UserSportTeamItem(sports);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);

        adapter.setListener(new UserSportTeamItem.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
//                if (!sports.get(position).getPublish_user_id().equals(sports.get(position).getUser_id())){
                    Intent intent = new Intent(ContactActivity.this, LayoutActivity.class);
                    Bundle data = new Bundle();
                    data.putParcelable("data",sports.get(position));
                    intent.putExtras(data);
                    startActivity(intent);
//                }
//                else {
//                    Intent intent = new Intent(ContactActivity.this, NewCreateActivity.class);
//                    Bundle data = new Bundle();
//                    data.putParcelable("data",sports.get(position));
//                    intent.putExtras(data);
//                    startActivity(intent);
//                }

            }
        });
        recycler.setLayoutManager(linearLayoutManager);
        recycler.setAdapter(adapter);

    }
    @Override
    protected void onResume() {
        recordPresenter.onCreate();
        activityPresenter.onCreate();
        recordPresenter.attachView(userView);
        activityPresenter.attachView(activityView);
        if (!Util.isEmpty(userInfo.getId()))
            recordPresenter.getTypeSum(userInfo.getId());
            //presenter;
        if (!Util.isEmpty(userInfo.getId()))
            activityPresenter.getMyActivity(userInfo.getId());
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_contacts_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        recordPresenter.onStop();
        activityPresenter.onStop();
        super.onDestroy();
    }
}
