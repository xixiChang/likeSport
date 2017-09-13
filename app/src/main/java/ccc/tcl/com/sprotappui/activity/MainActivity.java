package ccc.tcl.com.sprotappui.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import java.util.ArrayList;
import java.util.List;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.adapter.ChooseItem;
import ccc.tcl.com.sprotappui.customui.RecycleViewDivider;
import ccc.tcl.com.sprotappui.model.ChooseItemModel;

@Deprecated
public class MainActivity extends BaseActivity {
    private RecyclerView recyclerView;
    private static List<ChooseItemModel> itemsInfos;

    private TextView logout;

    private ActionSheetDialog logoutDialog;
    private Context context;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        super.setToolBar(toolbar, R.string.activity_main_title);
        context = this;
        initView();
        setRecyclerViewAdapter();
    }

    static {
        itemsInfos = new ArrayList<>();
        itemsInfos.add(new ChooseItemModel(R.drawable.photo1, "排行"));
        itemsInfos.add(new ChooseItemModel(R.drawable.photo1, "个人记录"));
        itemsInfos.add(new ChooseItemModel(R.drawable.photo1, "我的运动团"));
        itemsInfos.add(new ChooseItemModel(R.drawable.photo1, "设置"));
    }

    private void setRecyclerViewAdapter() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false){
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        ChooseItem chooseItem = new ChooseItem(itemsInfos);
        recyclerView.addItemDecoration(new RecycleViewDivider(context, LinearLayoutManager.VERTICAL, 2, R.color.darkgrey));
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(chooseItem);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.choose_items);
//        logout = (TextView) findViewById(R.id.user_logout_button);

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                logoutWindow();
//            }
//        });
    }

//    private void logoutWindow() {
//        if (logoutDialog == null){
//            logoutDialog = new ActionSheetDialog(context, new String[]{"退出"}, null);
//            logoutDialog.cancelText("取消")
//                    .isTitleShow(false)
//                    .create();
//            logoutDialog.setOnOperItemClickL(new OnOperItemClickL() {
//                @Override
//                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    switch (position){
//                        case 0:
//                            if (logoutDialog.isShowing())
//                                logoutDialog.superDismiss();
//                            finish();
//                            break;
//                        default:
//                            break;
//                    }
//                }
//            });
//        }
//        logoutDialog.show();
//    }

}
