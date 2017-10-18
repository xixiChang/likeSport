package ccc.tcl.com.sprotappui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.utils.Util;

import static ccc.tcl.com.sprotappui.utils.Util.hideInputMethod;
import static ccc.tcl.com.sprotappui.utils.Util.isShouldHideInput;

/**
 * Created by user on 17-9-6.
 */

public class BaseActivity extends AppCompatActivity {

    protected PermissionResult result;
    protected boolean hasRequestPermission = false;

    protected interface PermissionResult {
        void onGranted(String name, int code);

        void onDenied(int code);
    }

    protected ProgressDialog progressDialog;


    /**
     * 所有继承自BaseActivity的活动都强制竖屏
     * @param savedInstanceState
     * @param persistentState
     */
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
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
     *
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
     *
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
     * 显示progressDialog
     * @param context
     * @param title default:"请稍后"
     * @param msg default:"正在处理"
     */
    protected void showProgressDialog(Context context, String title, String msg) {
        if (progressDialog == null){
            progressDialog = new ProgressDialog(context);
        }
        if (progressDialog.isShowing())
            return;
        progressDialog.setCancelable(false);
        progressDialog.setTitle(Util.isEmpty(title) ? "请稍后" : title);
        progressDialog.setMessage(Util.isEmpty(msg) ? "正在处理" : msg);
        progressDialog.show();
    }


    /**
     * 消失progressDialog
     */
    protected void dismissDialog(){
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    /**
     * 界面拉伸到状态栏
     *
     * @param isFullScreen
     * @return
     */
    public void setFullScreen(boolean isFullScreen) {
        if (Build.VERSION.SDK_INT >= 21 && isFullScreen) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            //getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }


    protected void initPM(PermissionResult result) {
        hasRequestPermission = true;
        this.result = result;
    }


    protected boolean checkPermission(String name) {
        return ContextCompat.checkSelfPermission(this,
                name) == PackageManager.PERMISSION_GRANTED;
    }

    protected void requestPermission(String name, int code) {
        ActivityCompat.requestPermissions(BaseActivity.this, new String[]{name}, code);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (hasRequestPermission) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                result.onGranted(permissions[0], requestCode);
            else {
                result.onDenied(requestCode);
                //Toast.makeText(this, "你拒绝的应用权限", Toast.LENGTH_SHORT).show();
            }
        } else
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
