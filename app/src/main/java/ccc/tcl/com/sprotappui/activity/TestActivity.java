package ccc.tcl.com.sprotappui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.model.Record;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.FileUploadPresenter;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.RecordPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;

@Deprecated
public class TestActivity extends BaseActivity {
    private Button start;
    private EditText aId;
    private Record record;
    private SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final String TAG = "TestActivity";

    private RecordPresenter recordPresenter;
    private FileUploadPresenter fileUploadPresenter;
    private File file = new File("/storage/emulated/0/DCIM/IMG.jpg");
    private SportAppView<ResponseResult> sportAppView = new SportAppView<ResponseResult>() {
        @Override
        public void onSuccess(ResponseResult response) {
            //判断response.ｉsSuccess
            Log.d(TAG, "onSuccess: " + response.toString());
        }

        @Override
        public void onRequestError(String msg) {
            Log.d(TAG, "onError: " + msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        recordPresenter = new RecordPresenter();
        initView();
        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        recordPresenter.onCreate();
        recordPresenter.attachView(sportAppView);
    }



    private void initData() {
        record = new Record();
        record.setCalorie(3211);
        record.setDate("2017-06-25");
        record.setTime(timeFormat.format(new Date()));
        record.setDistance(4201);
        record.setStart_time(timeFormat.format(new Date()));
        record.setEnd_time(timeFormat.format(new Date()));
        record.setSpent_time(321);
        record.setMean_speed(1.2f);
        record.setStep(8541);
        record.setType(0);

        Log.d(TAG, "initData: date>>> " + record.getDate());
    }

    private void initView() {
        start = (Button) findViewById(R.id.start_conversation);
        aId = (EditText) findViewById(R.id.another_id);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (!file.exists()){
//                    Toast.makeText(TestActivity.this, "file is not found", Toast.LENGTH_SHORT).show();
//                    return;
//                }
               fileUploadPresenter.upLoadFile(file, "head");
                recordPresenter.uploadRecord(record);//上传运动记录
            }
        });
    }

    @Override
    protected void onDestroy() {
        recordPresenter.onStop();
        super.onDestroy();
    }
}
