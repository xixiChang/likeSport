package ccc.tcl.com.sprotappui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.util.Util;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.data.UserInfo;
import ccc.tcl.com.sprotappui.model.PlatFormActivity;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.ActivityPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;
import de.hdodenhof.circleimageview.CircleImageView;

public class NewCreateActivity extends BaseActivity implements View.OnClickListener{
    private TextView name;
    private TextView hotValue;
    private TextView startTime;
    private TextView endTime;
    private TextView distance;
    private TextView location;
    private TextView detail;
    private TextView leftTime;
    private TextView joiner;
    private ImageView sportIamge;
    private CircleImageView publisher;
    private CircleImageView joiner0;
    private CircleImageView joiner1;
    private CircleImageView joiner2;
    private CircleImageView joiner3;
    private PlatFormActivity sport;

    private View changeView;
    private View cancelView;
    private TextView changeStart;
    private TextView changeEnd;
    private EditText changeReason;
    private  EditText cancelReason;
    private TextView reason;
    private boolean set_start_time = true;
    private ViewStub stub;
    private LinearLayout ll = null;
    private DatePicker picker;
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private ActionSheetDialog logoutDialog;
    private ActivityPresenter detailsPresenter;
    private ActivityPresenter joinersPresenter;
    private String changeStartTemp;
    private String changeEndTemp;
    private String reasonTemp;
    private FrameLayout head_images;

    private SportAppView detailsView = new SportAppView<ResponseResult<PlatFormActivity>>() {
        @Override
        public void onSuccess(ResponseResult<PlatFormActivity> response) {
            if (response.isSuccess()){
                //取消活动
                if (response.getType().equals("cancel")) {
                    sport.setStatus("3");
                    sport.setReason(reasonTemp);
                    updateData();
                    Toast.makeText(NewCreateActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                    //finish();
                }
                //改期
                else if (response.getType().equals("delay")){
                    sport.setStart_time(changeStartTemp);
                    sport.setEnd_time(changeEndTemp);
                    sport.setStatus("1");
                    sport.setReason(reasonTemp);
                    updateData();
                    Toast.makeText(NewCreateActivity.this, "操作成功", Toast.LENGTH_SHORT).show();
                }
                //请求数据
                else if (response.getType().equals("details")){
                    sport = response.getResult();
                    if (sport.getJoiner() != null)
                        joinersPresenter.getJoinerInfo(ccc.tcl.com.sprotappui.utils.Util.stringToList(sport.getJoiner()));
                    updateData();
                }

            }

            else
                Toast.makeText(NewCreateActivity.this,"操作失败"+response.getMsg(),Toast.LENGTH_SHORT).show();
            //finish();
        }

        @Override
        public void onRequestError(String msg) {
            Toast.makeText(NewCreateActivity.this,"网络连接失败"+msg,Toast.LENGTH_SHORT).show();
        }
    };

    private SportAppView joinersView = new SportAppView<ResponseResult<List<UserInfo>>>() {
        @Override
        public void onSuccess(ResponseResult<List<UserInfo>> response) {
            if (response.isSuccess()){
                Glide.with(NewCreateActivity.this).load(response.getResult().get(0).getImage_url()).into(publisher);
                int i = response.getResult().size();
                if (i == 2){
                    joiner0.setVisibility(View.VISIBLE);
                    Glide.with(NewCreateActivity.this).load(response.getResult().get(1).getImage_url()).into(joiner0);
                }
                if (i == 3){
                    joiner0.setVisibility(View.VISIBLE);
                    joiner1.setVisibility(View.VISIBLE);
                    Glide.with(NewCreateActivity.this).load(response.getResult().get(1).getImage_url()).into(joiner0);
                    Glide.with(NewCreateActivity.this).load(response.getResult().get(2).getImage_url()).into(joiner1);
                }
                if (i == 4){
                    joiner0.setVisibility(View.VISIBLE);
                    joiner1.setVisibility(View.VISIBLE);
                    joiner2.setVisibility(View.VISIBLE);
                    Glide.with(NewCreateActivity.this).load(response.getResult().get(1).getImage_url()).into(joiner0);
                    Glide.with(NewCreateActivity.this).load(response.getResult().get(2).getImage_url()).into(joiner1);
                    Glide.with(NewCreateActivity.this).load(response.getResult().get(2).getImage_url()).into(joiner2);
                }
                if (i == 5){
                    joiner0.setVisibility(View.VISIBLE);
                    joiner1.setVisibility(View.VISIBLE);
                    joiner2.setVisibility(View.VISIBLE);
                    joiner3.setVisibility(View.VISIBLE);
                    Glide.with(NewCreateActivity.this).load(response.getResult().get(1).getImage_url()).into(joiner0);
                    Glide.with(NewCreateActivity.this).load(response.getResult().get(2).getImage_url()).into(joiner1);
                    Glide.with(NewCreateActivity.this).load(response.getResult().get(2).getImage_url()).into(joiner2);
                    Glide.with(NewCreateActivity.this).load(response.getResult().get(2).getImage_url()).into(joiner3);
                }
            }

            else
                Toast.makeText(NewCreateActivity.this,"获取数据失败"+response.getMsg(),Toast.LENGTH_SHORT).show();
            //finish();
        }

        @Override
        public void onRequestError(String msg) {
            Toast.makeText(NewCreateActivity.this,"网络连接失败"+msg,Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_create);
        Intent intent = getIntent();
        sport = intent.getParcelableExtra("data");
        Toolbar toolbar = (Toolbar) findViewById(R.id.news_details_toolbar);
        super.setToolBar(toolbar, " ",true);
        name = (TextView) findViewById(R.id.item_fm_sport_name);
        sportIamge = (ImageView) findViewById(R.id.news_details_photo);
        hotValue = (TextView) findViewById(R.id.item_fm_sport_hot_value);
        startTime = (TextView) findViewById(R.id.start_time_show);
        endTime = (TextView) findViewById(R.id.end_time_show);
        distance = (TextView) findViewById(R.id.distance_show);
        location = (TextView) findViewById(R.id.location_show);
        detail =(TextView) findViewById(R.id.detail_show);
        reason = (TextView) findViewById(R.id.update);
        leftTime = (TextView) findViewById(R.id.item_fm_sport_left_time);
        joiner = (TextView) findViewById(R.id.joiner_num);
        publisher = (CircleImageView) findViewById(R.id.publisher);
        joiner0 = (CircleImageView) findViewById(R.id.joiner0);
        joiner1 = (CircleImageView) findViewById(R.id.joiner1);
        joiner2 = (CircleImageView) findViewById(R.id.joiner2);
        joiner3 = (CircleImageView) findViewById(R.id.joiner3);
        head_images = (FrameLayout) findViewById(R.id.head_images);
        head_images.setOnClickListener(this);
        updateData();

        detailsPresenter = new ActivityPresenter();
        joinersPresenter = new ActivityPresenter();
    }

    @Override
    protected void onResume() {
        detailsPresenter.onCreate();
        joinersPresenter.onCreate();
        detailsPresenter.attachView(detailsView);
        joinersPresenter.attachView(joinersView);
        if (sport.getAddress() == null)
            detailsPresenter.getActivity(String.valueOf(sport.getAt_server_id()));

        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("", "onCreateOptionsMenu: "+sport.getUser_id()+"--"+sport.getPublish_user_id());
        getMenuInflater().inflate(R.menu.activity_home_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.more:
                logoutWindow();
                return true;
            case android.R.id.home:
                finish();
            default:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_images:
                Intent intent = new Intent(this,JoinerActivity.class);
                intent.putExtra("users",sport.getJoiner());
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    private void updateData(){
        name.setText(sport.getName());
        hotValue.setText(sport.getHot_value());
        startTime.setText(sport.getStart_time());
        endTime.setText(sport.getEnd_time());
        distance.setText(sport.getDistance() + "KM");
        location.setText(sport.getAddress());
        detail.setText(sport.getDetails());
        leftTime.setText(sport.getLeft_time()+"天");
        joiner.setText("参与者（" + sport.getJoin_num() + " / "+ sport.getJoin_num_all() + "）");
        Glide.with(this).load(sport.getImage_url()).into(sportIamge);
        Glide.with(this).load("").into(publisher);
        reason.setVisibility(View.GONE);
        if (sport.getReason() != null && sport.getStatus().equals("1")) {
            reason.setText("由于 " + sport.getReason() + ",活动已改期。");
            reason.setVisibility(View.VISIBLE);
        }
        if (sport.getReason() != null && sport.getStatus().equals("3")) {
            reason.setText("由于 " + sport.getReason() + ",活动已取消。");
            reason.setVisibility(View.VISIBLE);
        }
    }
    private void logoutWindow() {
        if (logoutDialog == null) {
            logoutDialog = new ActionSheetDialog(this, new String[]{"分享","改期","取消活动"}, null);
            logoutDialog.cancelText("哎呀，点错了")
                    .isTitleShow(false)
                    .create();
            logoutDialog.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            logoutDialog.cancel();
                            new ShareAction(NewCreateActivity.this)
                                    .withText("hello")
                                    //.withMedia(new UMImage(LayoutActivity.this,new File("")))
                                    .setDisplayList(SHARE_MEDIA.WEIXIN)
                                    .setCallback(umShareListener)
                                    .open();
                            break;
                        case 1:
                            logoutDialog.cancel();
                            initChangeDialog();
                            if (ll != null) {
                                stub.inflate();
                                ll.setVisibility(View.GONE);
                            }
                            new AlertDialog.Builder(NewCreateActivity.this).setView(changeView)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            changeStartTemp = changeStart.getText().toString();
                                            changeEndTemp = changeEnd.getText().toString();
                                            reasonTemp = changeReason.getText().toString();
                                            detailsPresenter.delayActivity(sport.getAt_server_id()+"",reasonTemp,changeStartTemp,changeEndTemp);

                                        }
                                    })
                                    .setNegativeButton("取消",null).create().show();

                            break;
                        case 2:
                            logoutDialog.cancel();
                            initCancelDialog();

                            new AlertDialog.Builder(NewCreateActivity.this).setView(cancelView)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            reasonTemp = cancelReason.getText().toString();
                                            detailsPresenter.cancelActivity(""+sport.getAt_server_id(),""+cancelReason.getText().toString());

                                        }
                                    })
                                    .setNegativeButton("取消",null).create().show();


                        default:
                            break;
                    }
                }
            });
        }
        logoutDialog.show();
    }

    private void initChangeDialog(){
        changeView = getLayoutInflater().inflate(R.layout.dialog_time_change,null);
        changeStart = (TextView) changeView.findViewById(R.id.start_time);
        changeEnd = (TextView) changeView.findViewById(R.id.end_time);
        stub = (ViewStub) changeView.findViewById(R.id.viewStub);
        changeReason = (EditText) changeView.findViewById(R.id.reason);
        final InputMethodManager imm = (InputMethodManager) NewCreateActivity.this.getSystemService(INPUT_METHOD_SERVICE);
        changeStart.setText(startTime.getText());
        changeStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_start_time = true;
                imm.hideSoftInputFromWindow(changeReason.getWindowToken(), 0);
                if (ll == null){//inflate只能调用一次，再次调用会报异常
                    stub.inflate();
                }else if(ll.getVisibility()==View.GONE){
                    ll.setVisibility(View.VISIBLE);

                }
                else if (ll.getVisibility() == View.VISIBLE){
                    ll.setVisibility(View.GONE);
                    String start = changeStart.getText().toString();
                    picker.updateDate(Integer.parseInt(start.substring(0,4)), Integer.parseInt(start.substring(5,7))-1, Integer.parseInt(start.substring(8)));
                    ll.setVisibility(View.VISIBLE);
                }


            }

        });
        changeEnd.setText(endTime.getText());
        changeEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_start_time = false;
                imm.hideSoftInputFromWindow(changeReason.getWindowToken(), 0);
                if (ll == null){//inflate只能调用一次，再次调用会报异常
                    stub.inflate();
                }else if(ll.getVisibility()==View.GONE){
                    ll.setVisibility(View.VISIBLE);


                }
                else if (ll.getVisibility() == View.VISIBLE){
                    ll.setVisibility(View.GONE);
                    String end = changeEnd.getText().toString();
                    picker.updateDate(Integer.parseInt(end.substring(0,4)), Integer.parseInt(end.substring(5,7))-1, Integer.parseInt(end.substring(8)));
                    ll.setVisibility(View.VISIBLE);
                }

            }

        });

        changeReason.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ll != null && ll.getVisibility() == View.VISIBLE)
                    ll.setVisibility(View.GONE);
            }
        });
        stub.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, final View inflated) {//加载完成以后回调//下面的代码也可以写到inflate()返回以后调用
                ll = (LinearLayout) inflated;
                Date curr = new Date();
                final String currTime = format.format(curr.getTime());
                picker = (DatePicker) ll.findViewById(R.id.datePicker2);
                picker.init(Integer.parseInt(currTime.substring(0,4)), Integer.parseInt(currTime.substring(5,7))-1, Integer.parseInt(currTime.substring(8)), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        if (set_start_time)
                            changeStart.setText(format.format(calendar.getTime()));
                        else if (!set_start_time)
                            changeEnd.setText(format.format(calendar.getTime()));
                    }
                });
                picker.setMinDate(curr.getTime());
            }
        });
    }

    private void initCancelDialog(){
        cancelView = getLayoutInflater().inflate(R.layout.dialog_cancel_activity,null);
        cancelReason = (EditText) cancelView.findViewById(R.id.cancel_reason);
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
}
