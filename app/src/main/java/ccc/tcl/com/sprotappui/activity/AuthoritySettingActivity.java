package ccc.tcl.com.sprotappui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Switch;

import ccc.tcl.com.sprotappui.R;

public class AuthoritySettingActivity extends BaseActivity {
    Switch camera;
    Switch storage;
    Switch location;
    Switch vibrate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        super.setToolBar(toolbar, R.string.authority_setting, true);
        initView();
    }

    private void initView(){
        camera = (Switch) findViewById(R.id.camera);
        storage = (Switch) findViewById(R.id.storage);
        location = (Switch) findViewById(R.id.location);
        vibrate = (Switch) findViewById(R.id.vibrate);


    }
}
