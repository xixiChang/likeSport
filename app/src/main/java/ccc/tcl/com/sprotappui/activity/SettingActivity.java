package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;

import ccc.tcl.com.sprotappui.App;
import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.customui.ToolBar;

import static ccc.tcl.com.sprotappui.R.id.toolbar;

public class SettingActivity extends BaseActivity {
    private ToolBar toolBar;
    private TextView cacheSiez;
    private ImageView clearCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        toolBar = (ToolBar) findViewById(toolbar);
        super.setToolBar(toolBar, R.string.user_setting, true);
        cacheSiez = (TextView) findViewById(R.id.cache_size);
        cacheSiez.setText(formatFileSize(getDirSize(getCacheDir())));
        clearCache = (ImageView) findViewById(R.id.setting_clear_cache_image);
        clearCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearCacheFolder(getCacheDir());
                cacheSiez.setText(formatFileSize(getDirSize(getCacheDir())));
                Toast.makeText(SettingActivity.this,"清楚缓存成功",Toast.LENGTH_SHORT).show();
            }
        });


        RelativeLayout msgSetting = (RelativeLayout) findViewById(R.id.setting_message_setting);
        msgSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, MsgSettingActivity.class);
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

        //clearCacheFolder(getCacheDir());


        RelativeLayout aboutApp = (RelativeLayout) findViewById(R.id.setting_about_app);
        aboutApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, AboutAppActivity.class);
                startActivity(intent);
            }
        });

        TextView appUserId = (TextView) findViewById(R.id.app_user_id);
        appUserId.setText(App.userInfo.getId());
        // clearAppCache();
    }

    private void clearCacheFolder(File dir){
        long curTime = System.currentTimeMillis();
            if (dir!= null && dir.isDirectory()) {
                try {
                    for (File child:dir.listFiles()) {
                        if (child.isDirectory()) {
                            clearCacheFolder(child);
                        }
                        if (child.lastModified() < curTime) {
                            if (child.delete()) {
                                ;
                            }
                        }
                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }

    }

    public static long getDirSize(File dir) {
        if (dir == null) {
            return 0;
        }
        if (!dir.isDirectory()) {
            return 0;
        }
        long dirSize = 0;
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                dirSize += file.length();
            } else if (file.isDirectory()) {
                dirSize += file.length();
                dirSize += getDirSize(file); // 递归调用继续统计
            }
        }
        return dirSize;
    }

    public static String formatFileSize(long fileS) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "MB";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
}
