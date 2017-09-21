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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.model.PlatFromActivity;

public class FinishCreateActivity extends BaseActivity {
    TextView startTime;
    TextView endTime;
    TextView time_text;
    EditText location;
    EditText distance;
    EditText note;
    ViewStub stub;
    LinearLayout ll = null;
    PlatFromActivity platFromActivity;
    int[] location_datePicker = new int[2];
    int[] start_textview = new int[2];
    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
    boolean set_start_time = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        super.setToolBar(toolbar, R.string.create_activity,true);
        Intent intent0= getIntent();
        platFromActivity = intent0.getParcelableExtra("data");
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

            }

        });
        startTime.setText(format.format(new Date().getTime()));
        endTime.setText(format.format(new Date().getTime()));
        stub = (ViewStub) findViewById(R.id.viewStub);
        stub.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, final View inflated) {//加载完成以后回调//下面的代码也可以写到inflate()返回以后调用
                ll = (LinearLayout) inflated;
                DatePicker picker = (DatePicker) findViewById(R.id.datePicker2);
                picker.init(2013, 1, 4, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        if (set_start_time)
                            startTime.setText(format.format(calendar.getTime()));
                        else if (!set_start_time)
                            endTime.setText(format.format(calendar.getTime()));
                        Toast.makeText(FinishCreateActivity.this, format.format(calendar.getTime()), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
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
                Intent intent = new Intent();
                Bundle data = new Bundle();
                platFromActivity.setStart_time(startTime.getText().toString());
                platFromActivity.setEnd_time(endTime.getText().toString());
                platFromActivity.setAddress(location.getText().toString());
                platFromActivity.setDistance(Integer.parseInt(distance.getText().toString()));
                platFromActivity.setNotes(note.getText().toString());
                data.putParcelable("data", platFromActivity);
                intent.putExtras(data);
                Log.d("", "onOptionsItemSelected: ");
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    /**
     * 点击空白处隐藏键盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            if (null!=ll)
                ll.getLocationInWindow(location_datePicker);
            time_text.getLocationInWindow(start_textview);
            Log.d("111", "dispatchTouchEvent: evY"+ev.getY());
            Log.d("111", "dispatchTouchEvent: viewY"+location_datePicker[1]);
            if (ll != null && (ev.getY() >  ll.getY()+location_datePicker[1] || ev.getY() < start_textview[1]))
                ll.setVisibility(View.GONE);
        }
        return super.dispatchTouchEvent(ev);
    }
}
