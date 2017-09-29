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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.model.PlatFormActivity;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.ActivityPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;

public class NewCreateActivity extends BaseActivity {
    TextView startTime;
    TextView endTime;
    TextView distance;
    TextView location;
    TextView detail;
    PlatFormActivity sport;

    View changeView;
    View cancelView;
    TextView changeStart;
    TextView changeEnd;
    EditText changeReason;
    EditText cancelReason;
    boolean set_start_time = true;
    ViewStub stub;
    LinearLayout ll = null;
    DatePicker picker;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    private ActionSheetDialog logoutDialog;
    ActivityPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_create);
        Intent intent = getIntent();
        sport = intent.getParcelableExtra("data");
        Toolbar toolbar = (Toolbar) findViewById(R.id.news_details_toolbar);
        super.setToolBar(toolbar, " ",true);
        startTime = (TextView) findViewById(R.id.start_time_show);
        endTime = (TextView) findViewById(R.id.end_time_show);
        distance = (TextView) findViewById(R.id.distance_show);
        location = (TextView) findViewById(R.id.location_show);
        detail =(TextView) findViewById(R.id.detail_show);

        startTime.setText(sport.getStart_time());
        endTime.setText(sport.getEnd_time());
        distance.setText(sport.getDistance() + "KM");
        location.setText(sport.getAddress());
        detail.setText(sport.getDetails());

        presenter = new ActivityPresenter();
    }

    @Override
    protected void onResume() {
        presenter.onCreate();
        presenter.attachView(new SportAppView<ResponseResult>() {
            @Override
            public void onSuccess(ResponseResult response) {
                if (response.isSuccess())
                    Toast.makeText(NewCreateActivity.this,"操作成功",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(NewCreateActivity.this,"操作失败"+response.getMsg(),Toast.LENGTH_SHORT).show();
                //finish();
            }

            @Override
            public void onRequestError(String msg) {
                Toast.makeText(NewCreateActivity.this,"操作失败"+msg,Toast.LENGTH_SHORT).show();
            }
        });
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

    private void logoutWindow() {
        if (logoutDialog == null) {
            logoutDialog = new ActionSheetDialog(this, new String[]{"改期","取消活动"}, null);
            logoutDialog.cancelText("哎呀，点错了")
                    .isTitleShow(false)
                    .create();
            logoutDialog.setOnOperItemClickL(new OnOperItemClickL() {
                @Override
                public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            logoutDialog.cancel();
                            initChangeDialog();
                            new AlertDialog.Builder(NewCreateActivity.this).setView(changeView)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            presenter.delayActivity(sport.getAt_server_id()+"",changeReason.getText().toString(),startTime.getText().toString()
                                                    ,endTime.getText().toString());
                                            //Toast.makeText(NewCreateActivity.this,startTime.getText()+"--"+endTime.getText(),Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .setNegativeButton("取消",null).create().show();

                            break;
                        case 1:
                            logoutDialog.cancel();
                            new AlertDialog.Builder(NewCreateActivity.this).setMessage(R.string.cancelActivity)
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            presenter.cancelActivity(""+sport.getAt_server_id()," ");
                                            //Toast.makeText(NewCreateActivity.this,startTime.getText()+"--"+endTime.getText(),Toast.LENGTH_SHORT).show();
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
                final String currTime = format.format(new Date().getTime());
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
            }
        });
    }

    private void initCancelDialog(){

    }
}
