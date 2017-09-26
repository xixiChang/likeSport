package ccc.tcl.com.sprotappui.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import ccc.tcl.com.sprotappui.R;

import static ccc.tcl.com.sprotappui.utils.Util.hideInputMethod;
import static ccc.tcl.com.sprotappui.utils.Util.isShouldHideInput;

/**
 * Created by user on 17-9-6.
 */

public class BaseActivity extends AppCompatActivity {

    /**
     *
     * @param toolbar
     * @param titleID
     * @param canBack 是否能点击左上角返回
     */
    protected void setToolBar(@NonNull Toolbar toolbar, @NonNull int titleID, @NonNull boolean canBack) {
        toolbar.setTitle(titleID);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null)
            return;
        if (canBack)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    protected void setToolBar(@NonNull Toolbar toolbar, @NonNull String title, @NonNull boolean canBack) {
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() == null)
            return;
        if (canBack)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 点击菜单响应事件
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();

                return true;
            case R.id.more:
                Toast.makeText(this, "MORE", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.create_sport:
                //Toast.makeText(this, "创建活动", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 点击空白处隐藏键盘
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                if (hideInputMethod(this, v)) {
                    return true;
                }
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 界面拉伸到状态栏
     * @param isFullScreen
     * @return
     */
    public void setFullScreen(boolean isFullScreen){
        if (Build.VERSION.SDK_INT >= 21 && isFullScreen){
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            //getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }
}
