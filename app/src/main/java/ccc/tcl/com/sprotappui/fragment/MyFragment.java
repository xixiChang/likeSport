package ccc.tcl.com.sprotappui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.util.StringUtils;
import com.bumptech.glide.Glide;
import com.dl7.tag.TagView;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ccc.tcl.com.sprotappui.App;
import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.activity.ChangeUserActivity;
import ccc.tcl.com.sprotappui.activity.DayRateActivity;
import ccc.tcl.com.sprotappui.activity.MySportTeamActivity;
import ccc.tcl.com.sprotappui.activity.ScoreActivity;
import ccc.tcl.com.sprotappui.activity.SettingActivity;
import ccc.tcl.com.sprotappui.adapter.ChooseItem;
import ccc.tcl.com.sprotappui.customui.RecycleViewDivider;
import ccc.tcl.com.sprotappui.data.UserInfo;
import ccc.tcl.com.sprotappui.model.ChooseItemModel;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.RecordPresenter;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.UserPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;
import de.hdodenhof.circleimageview.CircleImageView;


public class MyFragment extends Fragment {

    private RecyclerView recyclerView;
    private static List<ChooseItemModel> itemsInfos;

    private TagView logout;

    private ActionSheetDialog logoutDialog;
    private Context context;

    private CircleImageView userPhoto;
    private TextView userName;
    private TextView totalDistance;
    private TextView totalTime;
    private TextView totalConsume;
    private UserInfo userInfo;
    private UserPresenter userPresenter;
    private SportAppView userView = new SportAppView<ResponseResult<UserInfo>>() {
        @Override
        public void onSuccess(ResponseResult<UserInfo> response) {
            if (response.isSuccess()){
                userInfo = response.getResult();
                Glide.with(MyFragment.this).load(userInfo.getImage_url()).into(userPhoto);
                userName.setText(userInfo.getName());
            }
            else
                Toast.makeText(context,"数据请求失败"+response.getMsg(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestError(String msg) {
            Toast.makeText(context,"数据连接失败"+msg,Toast.LENGTH_SHORT).show();
        }
    };
    private RecordPresenter recordPresenter;
    private SportAppView recordView = new SportAppView<ResponseResult<Map<String,String>>>() {
        @Override
        public void onSuccess(ResponseResult<Map<String,String>> response) {
            if (response.isSuccess()){
                if (response.getResult() == null)
                    return;
                totalDistance.setText(String.format("%.1f",Double.parseDouble(response.getResult().get("distance"))/1000));
                totalTime.setText(String.format("%.1f",Double.parseDouble(response.getResult().get("spent_time"))/3600));
                totalConsume.setText(response.getResult().get("calorie"));
            }
            else
                Toast.makeText(context,"数据请求失败"+response.getMsg(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestError(String msg) {
            Toast.makeText(context,"数据连接失败"+msg,Toast.LENGTH_SHORT).show();
        }
    };

    public MyFragment() {

    }

    static {
        itemsInfos = new ArrayList<>();
        itemsInfos.add(new ChooseItemModel(R.mipmap.grade, "成绩"));
        itemsInfos.add(new ChooseItemModel(R.mipmap.rating, "今日排行"));
        itemsInfos.add(new ChooseItemModel(R.mipmap.my_sport_circle, "我的运动圈"));
        itemsInfos.add(new ChooseItemModel(R.mipmap.setting, "设置"));
    }

    public static MyFragment getInstance() {
        return new MyFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        userPresenter = new UserPresenter();
        recordPresenter = new RecordPresenter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        initView(view);
        setRecyclerViewAdapter();
        return view;
    }

    private void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.choose_items);
        logout = (TagView) view.findViewById(R.id.user_logout_button);
        userPhoto = (CircleImageView) view.findViewById(R.id.user_photo);
        userName = (TextView) view.findViewById(R.id.user_name);
        totalDistance = (TextView) view.findViewById(R.id.whole_distance);
        totalTime = (TextView) view.findViewById(R.id.whole_spent_time);
        totalConsume = (TextView) view.findViewById(R.id.whole_consume);

        userPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ChangeUserActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutWindow();
            }
        });
        userPresenter.onCreate();
        userPresenter.attachView(userView);
        userPresenter.getUserInfo(App.userInfo.getId());

        recordPresenter.onCreate();
        recordPresenter.attachView(recordView);
        recordPresenter.getAllSum(App.userInfo.getId());
    }

    private void setRecyclerViewAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        ChooseItem chooseItem = new ChooseItem(itemsInfos);
        recyclerView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, 2, R.color.darkgrey));
        recyclerView.setLayoutManager(linearLayoutManager);
        chooseItem.setListener(new ChooseItem.OnRecyclerViewItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                handleItem(position);
            }
        });
        recyclerView.setAdapter(chooseItem);
    }

    private void logoutWindow() {
        if (logoutDialog == null) {
            logoutDialog = new ActionSheetDialog(context, new String[]{"退出"}, null);
            logoutDialog.cancelText("取消")
                    .isTitleShow(false)
                    .create();
            logoutDialog.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            if (logoutDialog.isShowing())
                                logoutDialog.superDismiss();
                            getActivity().finish();
                            break;
                        default:
                            break;
                    }
                }
            });
        }
        logoutDialog.show();
    }

    private void handleItem(int position){
        Intent intent = new Intent();
        switch (position){
            case 0:
                intent.setClass(context, ScoreActivity.class);
                break;
            case 1:
                if (userInfo==null || userInfo.getName() == null || userInfo.getName() == "")
                    return;
                intent.putExtra("data",new String[]{userInfo.getName(),userInfo.getImage_url()});
                intent.setClass(context, DayRateActivity.class);
                break;
            case 2:
                intent.setClass(context, MySportTeamActivity.class);
                break;
            case 3:
                intent.setClass(context, SettingActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }

}
