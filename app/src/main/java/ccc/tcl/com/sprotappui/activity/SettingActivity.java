package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

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
        RelativeLayout msgSetting = (RelativeLayout) findViewById(R.id.setting_message_setting);
        msgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,MsgSettingActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout authoritySetting = (RelativeLayout) findViewById(R.id.setting_set_sport_permission);
        authoritySetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this,AuthoritySettingActivity.class);
                startActivity(intent);
            }
        });
        clearAppCache();
    }

    private void clearAppCache(){
        //this.clearAppCache();
        Toast.makeText(this,""+this.getCacheDir().getFreeSpace(),Toast.LENGTH_LONG).show();
    }
}
