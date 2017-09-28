package ccc.tcl.com.sprotappui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.activity.LayoutActivity;
import ccc.tcl.com.sprotappui.adapter.FMSportItem;
import ccc.tcl.com.sprotappui.model.PlatFormActivity;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.ActivityPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;

public class SportCircleFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<PlatFormActivity> platFormActivityList = new ArrayList<>();
    private Context context;
    private String mTitle;
    private FMSportItem adapter;
    private ActivityPresenter downloadActivity;
    public SportCircleFragment() {
    }

    public static SportCircleFragment getInstance(String title) {
        SportCircleFragment sf = new SportCircleFragment();
        sf.mTitle = title;
        return sf;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

        initData();
    }

    @Override
    public void onResume() {
        downloadActivity = new ActivityPresenter();
        downloadActivity.onCreate();
        downloadActivity.attachView(new SportAppView<ResponseResult<List<PlatFormActivity>>>(){
            @Override
            public void onSuccess(ResponseResult<List<PlatFormActivity>> response) {
                if (response.isSuccess()){
                    platFormActivityList.clear();
                    platFormActivityList.addAll(response.getResult());
                    adapter.notifyDataSetChanged();
                }
                else
                    Toast.makeText(context,"数据加载失败:"+response.getMsg(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestError(String msg) {
                Toast.makeText(context,"网络链接失败:"+msg,Toast.LENGTH_SHORT).show();
                Log.d("qqqqq", "onError: "+msg);
            }
        });
        super.onResume();
    }

    private void initData() {

     //   downloadActivity.getAll();
        platFormActivityList.add(new PlatFormActivity());
//        platFormActivityList.add(new PlatFormActivity());
//        platFormActivityList.add(new PlatFormActivity());
//        platFormActivityList.add(new PlatFormActivity());
//        platFormActivityList.add(new PlatFormActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_sport_circle, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.fm_sport_recycler_view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        adapter = new FMSportItem(platFormActivityList);
        adapter.setListener(new FMSportItem.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(context, LayoutActivity.class);
                Bundle data = new Bundle();
                data.putParcelable("data",platFormActivityList.get(position));
                intent.putExtras(data);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }
    public void update(){
        downloadActivity.getAll();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        downloadActivity.onStop();
    }
}
