package ccc.tcl.com.sprotappui.activity;

import android.os.Bundle;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.customui.ToolBar;

public class AboutAppActivity extends BaseActivity {
    private ToolBar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        toolBar = (ToolBar) findViewById(R.id.toolbar);
        setToolBar(toolBar, "关于应用", true);

        initView();
    }

    private void initView() {

    }
}
