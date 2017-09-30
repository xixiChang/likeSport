package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewStub;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.model.PlatFormActivity;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.ActivityPresenter;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.FileUploadPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;

public class FinishCreateActivity extends BaseActivity {
    TextView startTime;
    TextView endTime;
    TextView time_text;
    EditText location;
    EditText distance;
    EditText note;
    ViewStub stub;
    DatePicker picker;
    LinearLayout ll = null;
    PlatFormActivity platFormActivity;
    FileUploadPresenter uploadImage;
    ActivityPresenter uploadActivity;
    int[] location_datePicker = new int[2];
    int[] start_textview = new int[2];
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    boolean set_start_time = true;
    String ImageUrl;
    private static final String TAG = "FinishCreateActivity";

    private String oriangal_image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        super.setToolBar(toolbar, R.string.create_activity,true);
        initView();
    }

    private void initView(){
        final Intent intent0= getIntent();
        platFormActivity = intent0.getParcelableExtra("data");
        oriangal_image_url = platFormActivity.getImage_url();
        time_text = (TextView) findViewById(R.id.textView);
        location = (EditText) findViewById(R.id.location);
        distance = (EditText) findViewById(R.id.distance);
        note = (EditText) findViewById(R.id.note);
        startTime = (TextView) findViewById(R.id.start_time);
        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_start_time = true;
                if (ll == null){//inflate只能调用一次，再次调用会报异常
                    stub.inflate();
                }else if(ll.getVisibility()==View.GONE){
                    ll.setVisibility(View.VISIBLE);
                    String start = startTime.getText().toString();
                    picker.updateDate(Integer.parseInt(start.substring(0,4)), Integer.parseInt(start.substring(5,7))-1, Integer.parseInt(start.substring(8)));
                }
                else if (ll.getVisibility() == View.VISIBLE){
                    String end = startTime.getText().toString();
                    picker.updateDate(Integer.parseInt(end.substring(0,4)), Integer.parseInt(end.substring(5,7))-1, Integer.parseInt(end.substring(8)));
                }
            }

        });
        endTime = (TextView) findViewById(R.id.end_time);
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_start_time = false;
                if (ll == null){//inflate只能调用一次，再次调用会报异常
                    stub.inflate();
                }else if(ll.getVisibility()==View.GONE){
                    ll.setVisibility(View.VISIBLE);
                }
                else if (ll.getVisibility() == View.VISIBLE){
                    String end = endTime.getText().toString();
                    picker.updateDate(Integer.parseInt(end.substring(0,4)), Integer.parseInt(end.substring(5,7))-1, Integer.parseInt(end.substring(8)));
                }

            }

        });
        final String currTime = format.format(new Date().getTime());
        startTime.setText(currTime);
        endTime.setText(currTime);
        stub = (ViewStub) findViewById(R.id.viewStub);
        stub.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, final View inflated) {//加载完成以后回调//下面的代码也可以写到inflate()返回以后调用
                ll = (LinearLayout) inflated;
                picker = (DatePicker) findViewById(R.id.datePicker2);
                picker.init(Integer.parseInt(currTime.substring(0,4)), Integer.parseInt(currTime.substring(5,7))-1, Integer.parseInt(currTime.substring(8)), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        if (set_start_time)
                            startTime.setText(format.format(calendar.getTime()));
                        else if (!set_start_time)
                            endTime.setText(format.format(calendar.getTime()));
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        uploadImage = new FileUploadPresenter();
        uploadImage.onCreate();
        uploadImage.attachView(new SportAppView<ResponseResult<String>>() {
            @Override
            public void onSuccess(ResponseResult<String> response) {
                if (response.isSuccess()){
                    platFormActivity.setStart_time(startTime.getText().toString());
                    platFormActivity.setEnd_time(endTime.getText().toString());
                    platFormActivity.setAddress(location.getText().toString());
                    platFormActivity.setDistance(Integer.parseInt(distance.getText().toString()));
                    platFormActivity.setNotes(note.getText().toString());
                    ImageUrl = response.getResult();
                    platFormActivity.setImage_url(response.getResult());
                    uploadActivity.uploadActivity(platFormActivity);
                    Toast.makeText(FinishCreateActivity.this,"图片上传成功",Toast.LENGTH_SHORT).show();
                }
                else{
                    platFormActivity.setImage_url(oriangal_image_url);
                    Toast.makeText(FinishCreateActivity.this,"图片上传失败: "+response.getMsg(),Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onRequestError(String msg) {
                Toast.makeText(FinishCreateActivity.this,"图片上传失败："+msg,Toast.LENGTH_SHORT).show();
            }
        });

        uploadActivity = new ActivityPresenter();
        uploadActivity.onCreate();
        uploadActivity.attachView(new SportAppView<ResponseResult>() {
            @Override
            public void onSuccess(ResponseResult response) {
                if (response.isSuccess()){
                    Intent intent = new Intent(FinishCreateActivity.this,NewCreateActivity.class);
                    Bundle data = new Bundle();

                    data.putParcelable("data",platFormActivity);
                    intent.putExtras(data);
                    startActivity(intent);
                    //finish();
                    Log.d(TAG, "onSuccess: "+response.getMsg());
                    Toast.makeText(FinishCreateActivity.this,"数据上传成功",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(FinishCreateActivity.this,"数据上传失败："+response.getMsg(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestError(String msg) {
                Toast.makeText(FinishCreateActivity.this,"网络链接失败："+msg,Toast.LENGTH_SHORT).show();
            }
        });
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_finish_create_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.finish:
                String locationText = location.getText().toString();
                String distanceText = distance.getText().toString();
                if (locationText.isEmpty() || distanceText.isEmpty()) {
                    break;
                }


                File image = new File(platFormActivity.getImage_url());

                uploadImage.upLoadFile(image,"activity");


                break;

        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 点击空白处隐藏键盘和日期选择器
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (null!=ll)
                ll.getLocationInWindow(location_datePicker);
            time_text.getLocationInWindow(start_textview);
            if (ll != null && (ev.getY() >  ll.getY()+location_datePicker[1] || ev.getY() < start_textview[1]))
                ll.setVisibility(View.GONE);
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        uploadImage.onStop();
        super.onDestroy();
    }
}
