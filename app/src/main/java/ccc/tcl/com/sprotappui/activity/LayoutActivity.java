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
import android.view.View;
import android.view.ViewStub;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import ccc.tcl.com.sprotappui.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class LayoutActivity extends BaseActivity {
    CircleImageView imageView;
    TextView startTime;
    TextView endTime;
    View dialogView;
    boolean set_start_time = true;
    boolean is_my_create = false;
    ViewStub stub;
    LinearLayout ll = null;
    DatePicker picker;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.news_details_toolbar);
        super.setToolBar(toolbar, R.string.activity_main_title,true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        layoutManager.setSmoothScrollbarEnabled(true);
//        layoutManager.setAutoMeasureEnabled(true);
        imageView = (CircleImageView) findViewById(R.id.circleImageView1);
//        Resources resources = getResources();
//        Drawable[] drawables = new Drawable[4];
//        drawables[0] = resources.getDrawable(R.drawable.head2,null);
//        drawables[1] = resources.getDrawable(R.drawable.head3,null);
//        drawables[2] = resources.getDrawable(R.drawable.head4,null);
//        drawables[3] = resources.getDrawable(R.drawable.head5,null);
//        LayerDrawable layerDrawable = new LayerDrawable(drawables);
        Intent intent = getIntent();
        int position = intent.getIntExtra("id",-1);
        is_my_create = intent.getBooleanExtra("is_my_create",false);
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

    private void initChangeDialog(){
        dialogView = getLayoutInflater().inflate(R.layout.dialog_time_change,null);
        startTime = (TextView) dialogView.findViewById(R.id.start_time);
        endTime = (TextView) dialogView.findViewById(R.id.end_time);
        stub = (ViewStub) findViewById(R.id.viewStub);

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_start_time = true;
                if (ll == null){//inflate只能调用一次，再次调用会报异常
                    stub.inflate();
                }else if(ll.getVisibility()==View.GONE){
                    ll.setVisibility(View.VISIBLE);
                    String start = startTime.getText().toString();
                    picker.updateDate(Integer.parseInt(start.substring(0,4)), Integer.parseInt(start.substring(5,7))-1, Integer.parseInt(start.substring(8)));
                }

            }

        });
        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_start_time = false;
                if (ll == null){//inflate只能调用一次，再次调用会报异常
                    stub.inflate();
                }else if(ll.getVisibility()==View.GONE){
                    ll.setVisibility(View.VISIBLE);
                    String end = endTime.getText().toString();
                    picker.updateDate(Integer.parseInt(end.substring(0,4)), Integer.parseInt(end.substring(5,7))-1, Integer.parseInt(end.substring(8)));
                }

            }

        });
        stub.setOnInflateListener(new ViewStub.OnInflateListener() {
            @Override
            public void onInflate(ViewStub stub, final View inflated) {//加载完成以后回调//下面的代码也可以写到inflate()返回以后调用
                ll = (LinearLayout) inflated;
                final String currTime = format.format(new Date().getTime());
                picker = (DatePicker) findViewById(R.id.datePicker2);
                picker.init(Integer.parseInt(currTime.substring(0,4)), Integer.parseInt(currTime.substring(5,7))-1, Integer.parseInt(currTime.substring(8)), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, monthOfYear, dayOfMonth);
                        if (set_start_time)
                            startTime.setText(format.format(calendar.getTime()));
                        else if (!set_start_time)
                            endTime.setText(format.format(calendar.getTime()));
                    }
                });
            }
        });
    }
}
