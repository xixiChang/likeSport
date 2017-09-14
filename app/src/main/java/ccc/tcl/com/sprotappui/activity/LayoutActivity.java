package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import ccc.tcl.com.sprotappui.R;

public class LayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//      setContentView(R.layout.activity_layout);
        setContentView(R.layout.news_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.news_details_toolbar);
        super.setToolBar(toolbar, R.string.activity_main_title,true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setSmoothScrollbarEnabled(true);
        layoutManager.setAutoMeasureEnabled(true);

    }
}
