package ccc.tcl.com.sprotappui.weixin.callback;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.umeng.weixin.callback.WXCallbackActivity;

import ccc.tcl.com.sprotappui.R;

public class WXEntryActivity extends WXCallbackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
    }
}
