package ccc.tcl.com.sprotappui.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.UserPresenter;
import ccc.tcl.com.sprotappui.service.RegisterCodeTimerService;
import ccc.tcl.com.sprotappui.ui.SportAppView;
import ccc.tcl.com.sprotappui.utils.RegisterCodeTimer;


public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText name, phone, code, password;
    private String sName, sPhone, sCode, sPassword;
    private Button getCode, register;
    private TextView goLogin;
    private ImageView vb;
    private UserPresenter userPresenter = new UserPresenter();
    private static final String TAG = "RegisterActivity";
    private static final int START_AC_REG = 5014;
    private boolean passwordDisplayTag;
    private Intent mIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userPresenter.onCreate();
        userPresenter.attachView(sportAppView);
    }

    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        name = (EditText) findViewById(R.id.rg_name);
        phone = (EditText) findViewById(R.id.rg_phone);
        code = (EditText) findViewById(R.id.rg_code);
        password = (EditText) findViewById(R.id.rg_password);
        getCode = (Button) findViewById(R.id.rg_get_code);
        register = (Button) findViewById(R.id.rg_register);
        goLogin =(TextView)findViewById(R.id.register_to_login);
        vb=(ImageView)findViewById(R.id.VISIBLE);
        //
        RegisterCodeTimerService.setHandler(mCodeHandler);
        mIntent = new Intent(RegisterActivity.this, RegisterCodeTimerService.class);

        getCode.setOnClickListener(this);
        register.setOnClickListener(this);
        goLogin.setOnClickListener(this);
        vb.setOnClickListener(this);
    }
    /**
     * 倒计时Handler
     */
    @SuppressLint("HandlerLeak")
    Handler mCodeHandler = new Handler() {
        public void handleMessage(Message msg) {
            if (msg.what == RegisterCodeTimer.IN_RUNNING) {// 正在倒计时
                getCode.setText(msg.obj.toString());
            } else if (msg.what == RegisterCodeTimer.END_RUNNING) {// 完成倒计时
                getCode.setEnabled(true);
                getCode.setText(msg.obj.toString());
            }
        };
    };

    private SportAppView<ResponseResult<String>> sportAppView = new SportAppView<ResponseResult<String>>() {
        @Override
        public void onSuccess(ResponseResult<String> response) {
            if (response.isSuccess()){
                Log.d(TAG, "onSuccess: " + response.toString());
                Toast.makeText(RegisterActivity.this, "发送成功,　请查收哦～", Toast.LENGTH_SHORT).show();
            }else{
                Log.d(TAG, "onfalse: " + response.getMsg());
                Toast.makeText(RegisterActivity.this, "发送失败:", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onRequestError(String msg) {
            Log.d(TAG, "onError: " + msg);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rg_get_code://设置倒计时按键
                String sPhone0 = phone.getText().toString();
                userPresenter.userGetAuthCode(sPhone0);
                Log.d(TAG, "onClick: rg_get_code");
                getCode.setEnabled(false);
                startService(mIntent);
                break;
            case R.id.rg_register:
                sPhone = phone.getText().toString();
                sPassword = password.getText().toString();
                sName = name.getText().toString();
                sCode = code.getText().toString();
                userPresenter.userRegister(sPhone, sName, sPassword, sCode);
                Log.d(TAG, "注册: ");
                break;
            case R.id.register_to_login:
                Intent intent = new Intent(this,LoginActivity.class);
                startActivityForResult(intent, START_AC_REG);
            case R.id.VISIBLE:
                if (!passwordDisplayTag) {
                    // 设置 EditText 的 input type  显示 密码
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // 隐藏 密码
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                passwordDisplayTag = !passwordDisplayTag;
            default:
                break;
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("phone", sPhone);
        intent.putExtra("password", sPassword);
        setResult(START_AC_REG, intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        userPresenter.onStop();
        stopService(mIntent);
    }
}
