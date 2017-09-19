package ccc.tcl.com.sprotappui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.customui.ToolBar;

import static ccc.tcl.com.sprotappui.R.id.toolbar;

public class SettingActivity extends BaseActivity {
    private ToolBar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        toolBar = (ToolBar) findViewById(toolbar);
        super.setToolBar(toolBar, R.string.user_setting, true);
    }
}
