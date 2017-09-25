package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.File;
import java.util.Map;

import ccc.tcl.com.sprotappui.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class LayoutActivity extends BaseActivity {
    CircleImageView imageView;
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
        imageView = (CircleImageView) findViewById(R.id.circleImageView1);
        Resources resources = getResources();
        Drawable[] drawables = new Drawable[4];
        drawables[0] = resources.getDrawable(R.drawable.head2,null);
        drawables[1] = resources.getDrawable(R.drawable.head3,null);
        drawables[2] = resources.getDrawable(R.drawable.head4,null);
        drawables[3] = resources.getDrawable(R.drawable.head5,null);
        LayerDrawable layerDrawable = new LayerDrawable(drawables);
        Intent intent = getIntent();
        int position = intent.getIntExtra("id",-1);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.share_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.share_activity:
                new ShareAction(LayoutActivity.this)
                        .withText("hello")
                        //.withMedia(new UMImage(LayoutActivity.this,new File("")))
                        .setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                        .setCallback(umShareListener)
                        .open();
        }
        return super.onOptionsItemSelected(item);
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onResult(SHARE_MEDIA share_media) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media) {

        }
    };

    @Override
    protected void onDestroy() {
        UMShareAPI.get(this).release();
        super.onDestroy();
    }
}
