package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.adapter.JoinerItem;
import ccc.tcl.com.sprotappui.customui.RecycleViewDivider;
import ccc.tcl.com.sprotappui.customui.ToolBar;
import ccc.tcl.com.sprotappui.data.UserInfo;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.ActivityPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;
import ccc.tcl.com.sprotappui.utils.Util;

public class JoinerActivity extends BaseActivity {
    private static final String TAG = "JoinerActivity";
    private RecyclerView recyclerView;
    private ToolBar toolBar;
    private List<UserInfo> userList = new ArrayList<>();

    private String users;
    private JoinerItem adapter;

    private ActivityPresenter activityPresenter;
    private SportAppView<ResponseResult<List<UserInfo>>> sportAppView = new SportAppView<ResponseResult<List<UserInfo>>>() {
        @Override
        public void onSuccess(ResponseResult<List<UserInfo>> response) {
            if (response.isSuccess()){
                userList.clear();
                userList.addAll(response.getResult());
                adapter.notifyDataSetChanged();
            }
            else
                Toast.makeText(JoinerActivity.this, "获取数据失败:" + response.getMsg(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestError(String msg) {
            Toast.makeText(JoinerActivity.this, "请求错误:" + msg, Toast.LENGTH_SHORT).show();
            Log.e(TAG, "onRequestError: " + msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joiner);
        toolBar = (ToolBar) findViewById(R.id.toolbar);
        super.setToolBar(toolBar, R.string.joiner, true);

        Intent intent = getIntent();
        users = intent.getStringExtra("users");
        Log.d(TAG, "onCreate: ");
        initView();
    }

    @Override
    protected void onResume() {
        activityPresenter = new ActivityPresenter();
        activityPresenter.onCreate();
        activityPresenter.attachView(sportAppView);
        if (users != null){
            activityPresenter.getJoinerInfo(Util.stringToList(users));
        }
        super.onResume();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.joiner_recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false);
        adapter = new JoinerItem(userList);
        adapter.setListener(new JoinerItem.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent userInfo = new Intent(JoinerActivity.this,ContactActivity.class);
                Bundle data = new Bundle();
                data.putSerializable("userInfo",userList.get(position));
                userInfo.putExtras(data);
                startActivity(userInfo);
                Toast.makeText(JoinerActivity.this, userList.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL, 2, R.color.darkgrey));
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityPresenter.onStop();
    }
}
