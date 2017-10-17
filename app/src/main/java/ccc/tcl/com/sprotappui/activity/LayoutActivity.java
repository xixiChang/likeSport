package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dl7.tag.TagView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.List;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.data.UserInfo;
import ccc.tcl.com.sprotappui.model.PlatFormActivity;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.ActivityPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;
import ccc.tcl.com.sprotappui.utils.Util;
import de.hdodenhof.circleimageview.CircleImageView;

public class LayoutActivity extends BaseActivity implements View.OnClickListener{
    CircleImageView publisherImage;
    CircleImageView joiner0;
    CircleImageView joiner1;
    CircleImageView joiner2;
    CircleImageView joiner3;
    CircleImageView moreJoiners;
    ImageView photo;
    TextView name;
    TextView hotValue;
    TextView startTime;
    TextView endTime;
    TextView leftTime;
    TextView address;
    TextView distance;
    TextView details;
    TextView joinerNum;
    TagView joinButton;
    ActivityPresenter loadPresenter;
    ActivityPresenter joinerInfoPresenter;
    PlatFormActivity activity;
    FrameLayout headImages;
    private List<ResolveInfo> apps;
    private SportAppView load = new SportAppView<ResponseResult<PlatFormActivity>>() {
        @Override
        public void onSuccess(ResponseResult<PlatFormActivity> response) {
            if (response.isSuccess()){
                if (response.getType().equals("details"))
                    handleActivityDetails(response.getResult());
                if (response.getType().equals("join")) {
                    joinButton.setText("已参加");
                    joinButton.setEnabled(false);
                    joinButton.setBgColor(R.color.red);
                    joinButton.setBgColorChecked(R.color.red);
                }
            }
            else if (!response.isSuccess()) {
                if (response.getType().equals("details"))
                    Toast.makeText(LayoutActivity.this, "数据加载失败：" + response.getMsg(), Toast.LENGTH_SHORT).show();
                if (response.getType().equals("join"))
                    Toast.makeText(LayoutActivity.this, "参加活动失败", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onRequestError(String msg) {
            Toast.makeText(LayoutActivity.this,"网络连接失败:"+msg,Toast.LENGTH_SHORT).show();
        }
    };

    private SportAppView join = new SportAppView<ResponseResult<List<UserInfo>>>() {
        @Override
        public void onSuccess(ResponseResult<List<UserInfo>> response) {
            if (response.isSuccess()){
                int i = response.getResult().size();
                Glide.with(LayoutActivity.this).load(response.getResult().get(0).getImage_url()).into(publisherImage);
                if (i == 2){
                    joiner0.setVisibility(View.VISIBLE);
                    Glide.with(LayoutActivity.this).load(response.getResult().get(1).getImage_url()).into(joiner0);
                }
                if (i == 3){
                    joiner0.setVisibility(View.VISIBLE);
                    joiner1.setVisibility(View.VISIBLE);
                    Glide.with(LayoutActivity.this).load(response.getResult().get(1).getImage_url()).into(joiner0);
                    Glide.with(LayoutActivity.this).load(response.getResult().get(2).getImage_url()).into(joiner1);
                }
                if (i == 4){
                    joiner0.setVisibility(View.VISIBLE);
                    joiner1.setVisibility(View.VISIBLE);
                    joiner2.setVisibility(View.VISIBLE);
                    Glide.with(LayoutActivity.this).load(response.getResult().get(1).getImage_url()).into(joiner0);
                    Glide.with(LayoutActivity.this).load(response.getResult().get(2).getImage_url()).into(joiner1);
                    Glide.with(LayoutActivity.this).load(response.getResult().get(2).getImage_url()).into(joiner2);
                }
                if (i == 5){
                    joiner0.setVisibility(View.VISIBLE);
                    joiner1.setVisibility(View.VISIBLE);
                    joiner2.setVisibility(View.VISIBLE);
                    joiner3.setVisibility(View.VISIBLE);
                    moreJoiners.setVisibility(View.VISIBLE);
                    Glide.with(LayoutActivity.this).load(response.getResult().get(1).getImage_url()).into(joiner0);
                    Glide.with(LayoutActivity.this).load(response.getResult().get(2).getImage_url()).into(joiner1);
                    Glide.with(LayoutActivity.this).load(response.getResult().get(2).getImage_url()).into(joiner2);
                    Glide.with(LayoutActivity.this).load(response.getResult().get(2).getImage_url()).into(joiner3);
                }
            }
            else
                Toast.makeText(LayoutActivity.this, "获取数据失败" + response.getMsg(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onRequestError(String msg) {
            Toast.makeText(LayoutActivity.this, "网络连接失败" + msg, Toast.LENGTH_SHORT).show();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.news_details_toolbar);
        super.setToolBar(toolbar, R.string.activity_main_title,true);

        Intent intent = getIntent();
        activity = intent.getParcelableExtra("data");
        initView();

        //loadApps();
//        startTime.setText("111");
//        endTime.setText("111");
//        distance.setText("111");
//        address.setText("111");
//        details.setText("111");
        //initView();
    }

    @Override
    protected void onResume() {
        loadPresenter.onCreate();
        loadPresenter.attachView(load);
        joinerInfoPresenter.onCreate();
        joinerInfoPresenter.attachView(join);
        loadPresenter.getActivity(String.valueOf(activity.getAt_server_id()));
        //loadPresenter.getJoinerInfo();
        super.onResume();
    }

    private void handleActivityDetails(PlatFormActivity result) {
        activity = result;
        startTime.setText(result.getStart_time());
        endTime.setText(result.getEnd_time());
        leftTime.setText(result.getLeft_time()+"天");
        distance.setText(result.getDistance()+"");
        address.setText(result.getAddress());
        details.setText(result.getDetails());
        joinerNum.setText("参与者 （" + result.getJoin_num() + " / " + result.getJoin_num_all() + "）");
        if (result.getJoiner().contains(result.getUser_id())) {
            joinButton.setText("已参加");
            //joinButton.setClickable(false);
            joinButton.setEnabled(false);
            joinButton.setBgColor(R.color.red);
            joinButton.setBgColorChecked(R.color.red);
        }
        List<String> users = Util.stringToList(activity.getJoiner());
        joinerInfoPresenter.getJoinerInfo(users);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.join:
                loadPresenter.joinActivity(String.valueOf(activity.getAt_server_id()));
                break;
            case R.id.head_images:
                Intent intent = new Intent(this,JoinerActivity.class);
                intent.putExtra("users",activity.getJoiner());
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.share_activity:
                new ShareAction(LayoutActivity.this)
                        .withText("hello")
                        //.withMedia(new UMImage(LayoutActivity.this,new File("")))
                        .setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .open();
        }
        return super.onOptionsItemSelected(item);
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
            Log.d("lay111", "onStart: "+share_media.getsharestyle(true));
            loadApps();
        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {
            Log.d("lay1111", "onError: "+throwable);
        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {
            Log.d("lay1111", "onCancel: "+share_media);
        }
    };

    @Override
    protected void onDestroy() {
        loadPresenter.onStop();
        UMShareAPI.get(this).release();
        super.onDestroy();
    }
    private void loadApps() {
        apps = new ArrayList<>();
        Intent intent = new Intent(Intent.ACTION_MAIN, null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        apps = getPackageManager().queryIntentActivities(intent, 0);
        //for循环遍历ResolveInfo对象获取包名和类名
        for (int i = 0; i < apps.size(); i++) {
            ResolveInfo info = apps.get(i);
            String packageName = info.activityInfo.packageName;
            CharSequence cls = info.activityInfo.name;
            CharSequence name = info.activityInfo.loadLabel(getPackageManager());
            Log.e("ddddddd",name+"----"+packageName+"----"+cls);
        }
    }

    private void initView(){
        joinButton = (TagView) findViewById(R.id.join);
        joinButton.setOnClickListener(this);
        headImages = (FrameLayout) findViewById(R.id.head_images);
        headImages.setOnClickListener(this);
        publisherImage = (CircleImageView) findViewById(R.id.publisher);
        joiner0 = (CircleImageView) findViewById(R.id.joiner0);
        joiner1 = (CircleImageView) findViewById(R.id.joiner1);
        joiner2 = (CircleImageView) findViewById(R.id.joiner2);
        joiner3 = (CircleImageView) findViewById(R.id.joiner3);
        moreJoiners = (CircleImageView) findViewById(R.id.moreJoiners);
        photo = (ImageView) findViewById(R.id.news_details_photo);
        name = (TextView) findViewById(R.id.item_fm_sport_name);
        hotValue = (TextView) findViewById(R.id.item_fm_sport_hot_value);

        startTime = (TextView) findViewById(R.id.start_time_show);
        endTime = (TextView) findViewById(R.id.end_time_show);
        leftTime = (TextView) findViewById(R.id.item_fm_sport_left_time);
        distance = (TextView) findViewById(R.id.distance_show);
        address = (TextView) findViewById(R.id.location_show);
        details = (TextView) findViewById(R.id.detail_show);
        joinerNum = (TextView) findViewById(R.id.joiner_num);


        name.setText(activity.getName());
        Glide.with(this).load(activity.getImage_url()).into(photo);
        hotValue.setText(activity.getHot_value());
        loadPresenter = new ActivityPresenter();
        joinerInfoPresenter = new ActivityPresenter();
        UMShareAPI.get(this);

//        Resources resources = getResources();
//        Drawable[] drawables = new Drawable[4];
//        drawables[0] = resources.getDrawable(R.drawable.head2,null);
//        drawables[1] = resources.getDrawable(R.drawable.head3,null);
//        drawables[2] = resources.getDrawable(R.drawable.head4,null);
//        drawables[3] = resources.getDrawable(R.drawable.head5,null);
//        LayerDrawable layerDrawable = new LayerDrawable(drawables);


//        if (!activity.getJoiner().contains(activity.getUser_id()))
//            join.setOnClickListener(this);
//        else
//            join.setVisibility(View.GONE);
    }
}
