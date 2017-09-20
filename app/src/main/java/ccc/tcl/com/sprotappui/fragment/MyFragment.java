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

import com.dl7.tag.TagView;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import java.util.ArrayList;
import java.util.List;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.activity.DayRateActivity;
import ccc.tcl.com.sprotappui.activity.MySportTeamActivity;
import ccc.tcl.com.sprotappui.activity.ScoreActivity;
import ccc.tcl.com.sprotappui.activity.SettingActivity;
import ccc.tcl.com.sprotappui.adapter.ChooseItem;
import ccc.tcl.com.sprotappui.customui.RecycleViewDivider;
import ccc.tcl.com.sprotappui.model.ChooseItemModel;


public class MyFragment extends Fragment {

    private RecyclerView recyclerView;
    private static List<ChooseItemModel> itemsInfos;

    private TagView logout;

    private ActionSheetDialog logoutDialog;
    private Context context;


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

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutWindow();
            }
        });
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
