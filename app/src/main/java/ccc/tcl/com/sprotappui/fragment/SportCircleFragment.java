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
import android.widget.Toast;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

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
    private static final String Request_Type_More = "onLoadMore";
    private static final String Request_Type_Flush = "onRefresh";


    private TwinklingRefreshLayout mPullToRefreshView;
    private RecyclerView recyclerView;
    private List<PlatFormActivity> platFormActivityList = new ArrayList<>();
    private Context context;
    private FMSportItem adapter;
    private ActivityPresenter activityPresenter;

    private SportAppView<ResponseResult<List<PlatFormActivity>>> appView = new SportAppView<ResponseResult<List<PlatFormActivity>>>() {
        @Override
        public void onSuccess(ResponseResult<List<PlatFormActivity>> response) {
            mPullToRefreshView.finishRefreshing();
            mPullToRefreshView.finishLoadmore();
            if (response.isSuccess()){
                /**
                 * 刷新
                 */
                if (Request_Type_Flush.equals(response.getType())){
                    platFormActivityList.clear();
                    platFormActivityList .addAll(response.getResult());
                }
                /**
                 * 加载更多
                 */
                else if (Request_Type_More.equals(response.getType())){
                    platFormActivityList .addAll(response.getResult());
                }
                adapter.notifyDataSetChanged();
                Log.d(TAG, "onSuccess: " + platFormActivityList.size());
            }
            else
                Toast.makeText(context,"数据加载失败:"+response.getMsg(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestError(String msg) {
            Toast.makeText(context,"网络链接失败:"+msg,Toast.LENGTH_SHORT).show();
        }
    };

    public SportCircleFragment() {
    }

    public static SportCircleFragment getInstance() {
        SportCircleFragment sf = new SportCircleFragment();
        return sf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        activityPresenter = new ActivityPresenter();
    }

    /**
     * fragment 生命周期
     */

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        activityPresenter.onCreate();
        activityPresenter.attachView(appView);
        activityPresenter.getAllByPage(0);
    }

    private void initData() {

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
        mPullToRefreshView = (TwinklingRefreshLayout) view.findViewById(R.id.fg_sport_circle_pull_to_refresh);
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


        mPullToRefreshView.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                activityPresenter.getAllByPage(0);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                activityPresenter.getAllByPage(platFormActivityList.size());
            }
        });


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        activityPresenter.onStop();
    }
}
