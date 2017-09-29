package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dl7.tag.TagView;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.constant.URLConstant;
import ccc.tcl.com.sprotappui.model.PlatFormActivity;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.ActivityPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;
import de.hdodenhof.circleimageview.CircleImageView;

public class LayoutActivity extends BaseActivity implements View.OnClickListener{
    CircleImageView imageView;
    ImageView photo;
    TextView name;
    TextView hotValue;
    TextView startTime;
    TextView endTime;
    TextView address;
    TextView distance;
    TextView details;
    TagView joinButton;
    ActivityPresenter loadPresenter;
    ActivityPresenter joinPresenter;
    PlatFormActivity activity;

    private SportAppView load = new SportAppView<ResponseResult<PlatFormActivity>>() {
        @Override
        public void onSuccess(ResponseResult<PlatFormActivity> response) {
            if (response.isSuccess()){
                if (response.getType().equals("details"))
                    handleActivityDetails(response.getResult());
                if (response.getType().equals("join"))
                    Toast.makeText(LayoutActivity.this, "已加入肯德基豪华午餐", Toast.LENGTH_SHORT).show();
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
    private SportAppView join = new SportAppView<ResponseResult>() {
        @Override
        public void onSuccess(ResponseResult response) {
            if (response.isSuccess())
                Toast.makeText(LayoutActivity.this, "已加入肯德基豪华午餐", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(LayoutActivity.this, "参加活动失败" + response.getMsg(), Toast.LENGTH_SHORT).show();
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
        joinButton = (TagView) findViewById(R.id.join);
        imageView = (CircleImageView) findViewById(R.id.circleImageView1);
        photo = (ImageView) findViewById(R.id.news_details_photo);
        name = (TextView) findViewById(R.id.item_fm_sport_name);
        hotValue = (TextView) findViewById(R.id.item_fm_sport_hot_value);

        startTime = (TextView) findViewById(R.id.start_time_show);
        endTime = (TextView) findViewById(R.id.end_time_show);
        distance = (TextView) findViewById(R.id.distance_show);
        address = (TextView) findViewById(R.id.location_show);
        details = (TextView) findViewById(R.id.detail_show);

        Intent intent = getIntent();
        activity = intent.getParcelableExtra("data");
        name.setText(activity.getName());
        Glide.with(this).load(activity.getImage_url()).into(photo);
        hotValue.setText(activity.getHot_value());
        loadPresenter = new ActivityPresenter();
        joinPresenter = new ActivityPresenter();
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
        joinPresenter.onCreate();
        joinPresenter.attachView(join);
        loadPresenter.getActivity(String.valueOf(activity.getAt_server_id()));
        super.onResume();
    }

    private void handleActivityDetails(PlatFormActivity result) {
        startTime.setText(result.getStart_time());
        endTime.setText(result.getEnd_time());
        distance.setText(result.getDistance()+"");
        address.setText(result.getAddress());
        details.setText(result.getDetails());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.join:
                loadPresenter.joinActivity(String.valueOf(activity.getAt_server_id()));
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

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };

    @Override
    protected void onDestroy() {
        loadPresenter.onStop();
        UMShareAPI.get(this).release();
        super.onDestroy();
    }

    private void initView(){
        joinButton = (TagView) findViewById(R.id.join);
        imageView = (CircleImageView) findViewById(R.id.circleImageView1);
        photo = (ImageView) findViewById(R.id.news_details_photo);
        name = (TextView) findViewById(R.id.item_fm_sport_name);
        hotValue = (TextView) findViewById(R.id.item_fm_sport_hot_value);
        startTime = (TextView) findViewById(R.id.start_time);
        endTime = (TextView) findViewById(R.id.end_time);
        distance = (TextView) findViewById(R.id.distance);
        address = (TextView) findViewById(R.id.location);
        details = (TextView) findViewById(R.id.detail);

        Intent intent = getIntent();
        activity = intent.getParcelableExtra("data");
        name.setText(activity.getName());
        Glide.with(this).load(activity.getImage_url()).into(photo);
        hotValue.setText(activity.getHot_value());
        loadPresenter = new ActivityPresenter();

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
