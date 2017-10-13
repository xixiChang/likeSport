package ccc.tcl.com.sprotappui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.widget.Switch;

import ccc.tcl.com.sprotappui.R;

import static android.support.v7.appcompat.R.styleable.SwitchCompat;

public class AuthoritySettingActivity extends BaseActivity {
    SwitchCompat camera;
    SwitchCompat storage;
    SwitchCompat location;
    SwitchCompat vibrate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        super.setToolBar(toolbar, R.string.authority_setting, true);
        initView();
    }

    private void initView(){
        camera = (SwitchCompat) findViewById(R.id.camera);
        storage = (SwitchCompat) findViewById(R.id.storage);
        location = (SwitchCompat) findViewById(R.id.location);
        vibrate = (SwitchCompat) findViewById(R.id.vibrate);


    }
}
