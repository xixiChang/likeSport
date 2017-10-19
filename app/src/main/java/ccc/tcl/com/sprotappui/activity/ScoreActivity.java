package ccc.tcl.com.sprotappui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.util.StringUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.File;
import java.util.Map;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.customui.ToolBar;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.RecordPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;

import static ccc.tcl.com.sprotappui.R.id.toolbar;

public class ScoreActivity extends BaseActivity {
    private static final String VIEW_TYPE_WALK = "0";
    private static final String VIEW_TYPE_RUN = "1";
    private static final String TAG = "ScoreActivity";
    private TextView walkScore;
    private TextView walkTime;
    private TextView runScore;
    private TextView runTime;
    private ToolBar toolBar;
    private ImageView shareWalk;
    private ImageView shareRun;
    private RecordPresenter recordPresenter;
    private int ScoreOfWalk = 0,ScoreOfRun = 0;
    private String TimeOfWalk = "0秒",TimeOfRun = "0秒";
    private SportAppView<ResponseResult<Map<String,String>>> sportAppView
            = new SportAppView<ResponseResult<Map<String, String>>>() {
        @Override
        public void onSuccess(ResponseResult<Map<String, String>> response) {
            if (response.isSuccess()){
                if (response.getResult() == null)
                    return;
                if (VIEW_TYPE_WALK.equals(response.getType())){
                    Map<String, String> map = response.getResult();
                    int hour = Integer.parseInt(map.get("spent_time")) / 3600;
                    int minute = (Integer.parseInt(map.get("spent_time")) % 3600) / 60;
                    int second = (Integer.parseInt(map.get("spent_time")) % 3600) % 60;
                    TimeOfWalk = hour + "时" + minute + "分" + second + "秒";
                    ScoreOfWalk = Integer.parseInt(map.get("step"));
                    walkTime.setText(TimeOfWalk);
                    walkScore.setText(ScoreOfWalk);
                    Log.d(TAG, "onSuccess: type>>> 0" );
                    Log.d(TAG, "onSuccess: step>>> " + map.get("step"));
                    Log.d(TAG, "onSuccess: spent_time>>> " + map.get("spent_time"));
                }else if (VIEW_TYPE_RUN.equals(response.getType())){
                    Map<String, String> map = response.getResult();
                    int hour = Integer.parseInt(map.get("spent_time")) / 3600;
                    int minute = (Integer.parseInt(map.get("spent_time")) % 3600) / 60;
                    int second = (Integer.parseInt(map.get("spent_time")) % 3600) % 60;
                    ScoreOfRun = Integer.parseInt(map.get("distance"));
                    TimeOfRun = hour + "时" + minute + "分" + second + "秒";
                    runTime.setText(TimeOfRun);
                    runScore.setText(ScoreOfRun);
                    Log.d(TAG, "onSuccess: type>>> 1" );
                    Log.d(TAG, "onSuccess: step>>> " + map.get("step"));
                    Log.d(TAG, "onSuccess: spent_time>>> " + map.get("spent_time"));
                }
            }
        }

        @Override
        public void onRequestError(String msg) {
            Log.d(TAG, "onRequestError: "+msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        toolBar = (ToolBar) findViewById(toolbar);
        super.setToolBar(toolBar, R.string.user_score, true);
        UMShareAPI.get(this);
        walkScore = (TextView) findViewById(R.id.user_score_walk);
        runScore = (TextView) findViewById(R.id.user_score_run);
        walkTime = (TextView) findViewById(R.id.user_score_walk_time);
        runTime = (TextView) findViewById(R.id.user_score_run_time);
        shareWalk = (ImageView) findViewById(R.id.user_score_share_walk);
        shareRun = (ImageView) findViewById(R.id.user_score_share_run);
        shareWalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(ScoreActivity.this)
                        .withText("嘿，我一次行走了" + ScoreOfWalk + "步，用时" + TimeOfWalk+"\r\n ---分享自 爱运动 APP")
                        .setDisplayList(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .open();
            }
        });
        shareRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ShareAction(ScoreActivity.this)
                        .withText("嘿，我一次跑了" + ScoreOfRun + "米，用时" + TimeOfRun+"\r\n ---分享自 爱运动 APP")
                        .setDisplayList(SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .open();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        recordPresenter = new RecordPresenter();
        recordPresenter.onCreate();
        recordPresenter.attachView(sportAppView);
        recordPresenter.getMax(VIEW_TYPE_WALK);

        recordPresenter.getMax(VIEW_TYPE_RUN);
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Toast.makeText(ScoreActivity.this,throwable.toString(),Toast.LENGTH_LONG).show();
            //Toast.makeText(ScoreActivity.this,"未安装微信",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
        recordPresenter.onStop();
    }
}
