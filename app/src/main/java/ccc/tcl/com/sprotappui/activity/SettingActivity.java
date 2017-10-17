package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.util.Util;

import java.io.File;
import java.io.FileFilter;

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
        long size = this.getCacheDir().getTotalSpace() - this.getCacheDir().getUsableSpace();
        Toast.makeText(this,""+this.getCacheDir().getParentFile().getAbsolutePath(),Toast.LENGTH_LONG).show();
        File[] cache = this.getCacheDir().getParentFile().listFiles();
        for (int i = 0;i<cache.length;i++)
        if (cache[i].getName().equals("shared_prefs")) {
            //Log.d("cacheName", "clearAppCache: "+cache[i].getName());
            File[] shared_prefs = cache[i].listFiles();
            for (int j = 0;j<shared_prefs.length;j++)
            if (shared_prefs[j].getName().equals("UserInfo.xml"))
                if (shared_prefs[j].delete())
                    Log.d("succccccccc", "clearAppCache: ");
        }
        Glide.getPhotoCacheDir(this).delete();
        Log.d("cacheSize", "clearAppCache: "+size/1024+">>>>"+Glide.getPhotoCacheDir(this).getAbsolutePath());
    }
}
