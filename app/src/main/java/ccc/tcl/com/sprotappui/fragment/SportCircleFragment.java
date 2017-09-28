package ccc.tcl.com.sprotappui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

    private static final String TAG = "SportCircleFragment";

    private RecyclerView recyclerView;
    private List<PlatFormActivity> platFormActivityList = new ArrayList<>();
    private Context context;
    private String mTitle;
    private FMSportItem adapter;
    private ActivityPresenter activityPresenter;

    private SportAppView<ResponseResult<List<PlatFormActivity>>> appView = new SportAppView<ResponseResult<List<PlatFormActivity>>>() {
        @Override
        public void onSuccess(ResponseResult<List<PlatFormActivity>> response) {
            if (response.isSuccess()){
                platFormActivityList = response.getResult();
                adapter.notifyDataSetChanged();
                Log.d(TAG, "onSuccess: " + platFormActivityList.size());
            }
            else
                Log.d(TAG, "onSuccess: msg>>>>" + response.getMsg());
        }

        @Override
        public void onError(String msg) {
            Log.e(TAG, "onError: " + msg );
        }
    };

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
        activityPresenter = new ActivityPresenter();
    }

    /**
     * fragment 生命周期
     */
    @Override
    public void onResume() {
        super.onResume();
        activityPresenter.onCreate();
        activityPresenter.attachView(appView);
        activityPresenter.getAll();
    }

    private void initData() {
        platFormActivityList.add(new PlatFormActivity());
        platFormActivityList.add(new PlatFormActivity());
        platFormActivityList.add(new PlatFormActivity());
        platFormActivityList.add(new PlatFormActivity());
        platFormActivityList.add(new PlatFormActivity());
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
                intent.putExtra("id",position);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        activityPresenter.onStop();
    }
}
