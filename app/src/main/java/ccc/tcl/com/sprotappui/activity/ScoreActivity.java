package ccc.tcl.com.sprotappui.activity;

import android.app.Activity;
import android.os.Bundle;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.customui.ToolBar;

import static ccc.tcl.com.sprotappui.R.id.toolbar;

public class ScoreActivity extends BaseActivity {
    private ToolBar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
        toolBar = (ToolBar) findViewById(toolbar);
        this.setToolBar(toolBar, R.string.user_score);
    }
}
