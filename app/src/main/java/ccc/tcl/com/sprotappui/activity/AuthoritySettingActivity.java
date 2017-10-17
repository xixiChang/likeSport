package ccc.tcl.com.sprotappui.activity;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.flyco.animation.BounceEnter.BounceTopEnter;
import com.flyco.dialog.widget.MaterialDialog;

import ccc.tcl.com.sprotappui.R;

public class AuthoritySettingActivity extends BaseActivity implements View.OnClickListener {
    private SwitchCompat camera;
    private SwitchCompat storage;
    private SwitchCompat location;
    private SwitchCompat vibrate;

    private MaterialDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authority_setting);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        super.setToolBar(toolbar, R.string.authority_setting, true);

        initView();
    }


    private void initView(){
        camera = (SwitchCompat) findViewById(R.id.camera);
        storage = (SwitchCompat) findViewById(R.id.storage);
        location = (SwitchCompat) findViewById(R.id.location);
        vibrate = (SwitchCompat) findViewById(R.id.vibrate);

        camera.setOnClickListener(this);
        storage.setOnClickListener(this);
        location.setOnClickListener(this);
        vibrate.setOnClickListener(this);

        initPM(permissionResult);

        dialog = new MaterialDialog(this);
        dialog.setTitle("提示");
        dialog.content("请前往设备设置取消权限,取消应用权限后将会导致部分功能无法使用,影响您的体验")
                .btnText("知道了")
                .showAnim(new BounceTopEnter());

        setStatus();
    }


    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camera:
                if (checkClickStatus(0)){
                    requestPermission(Manifest.permission.CAMERA, 0);
                }
                else {
                    dialog.show();
                    storage.setChecked(true);
                }
                break;
            case R.id.storage:
                if (checkClickStatus(1)){
                    requestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 1);
                }
                else {
                    dialog.show();
                    storage.setChecked(true);
                }
                break;
            case R.id.location:
                if (checkClickStatus(2)){
                    requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, 2);
                }
                else {
                    dialog.show();
                    location.setChecked(true);
                }
                break;
            case R.id.vibrate:
                if (checkClickStatus(3)){
                    requestPermission(Manifest.permission.VIBRATE, 3);
                }
                else {
                    dialog.show();
                    vibrate.setChecked(true);
                }
                break;
            default:
                break;
        }
    }


    private void setStatus(){
        camera.setChecked(checkPermission(Manifest.permission.CAMERA));
        storage.setChecked(checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE));
        location.setChecked(checkPermission(Manifest.permission.ACCESS_FINE_LOCATION));
        vibrate.setChecked(checkPermission(Manifest.permission.VIBRATE));
    }


    private boolean checkClickStatus(int id){
        switch (id){
            case 0:
                return camera.isChecked();
            case 1:
                return storage.isChecked();
            case 2:
                return location.isChecked();
            case 3:
                return vibrate.isChecked();
            default:
                return false;
        }
    }


    private PermissionResult permissionResult = new PermissionResult() {
        @Override
        public void onGranted(String name, int code) {
            switch (code){
                case 0:
                    camera.setChecked(true);
                    break;
                case 1:
                    storage.setChecked(true);
                    break;
                case 2:
                    location.setChecked(true);
                    break;
                case 3:
                    vibrate.setChecked(true);
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onDenied(int code) {
            switch (code){
                case 0:
                    camera.setChecked(false);
                    break;
                case 1:
                    storage.setChecked(false);
                    break;
                case 2:
                    location.setChecked(false);
                    break;
                case 3:
                    vibrate.setChecked(false);
                    break;
                default:
                    break;
            }
        }
    };
}
