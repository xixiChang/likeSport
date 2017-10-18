package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
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
import ccc.tcl.com.sprotappui.data.BaseData;
import ccc.tcl.com.sprotappui.data.UserInfo;
import ccc.tcl.com.sprotappui.db.SQLParaWrapper;
import ccc.tcl.com.sprotappui.db.SQLStatement;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.UserPresenter;
import ccc.tcl.com.sprotappui.service.IMService;
import ccc.tcl.com.sprotappui.ui.SportAppView;

import static ccc.tcl.com.sprotappui.App.userInfo;

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText inputPhone, inputPwd;
    private TextView loginToReg;
    private Button login;
    private ImageView passwordShow;
    private static final int START_AC_REG = 5014;
    private UserPresenter userPresenter;
    private static final String TAG = "LoginActivity";
    private String phone, pwd;
    private SQLParaWrapper sqlParaWrapper;
    private boolean passwordDisplayTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        userPresenter = new UserPresenter();
        userPresenter.onCreate();
        userPresenter.attachView(sportAppView);
    }

    private void initView() {
        //隐藏该活动状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        inputPhone = (EditText) findViewById(R.id.user_login_phone);
        inputPwd = (EditText) findViewById(R.id.user_login_pwd);

        login = (Button) findViewById(R.id.login_button);
        loginToReg = (TextView) findViewById(R.id.login_to_register);
        passwordShow=(ImageView) findViewById(R.id.password_show);

        loginToReg.setOnClickListener(this);
        login.setOnClickListener(this);
        passwordShow.setOnClickListener(this);

    }

    private SportAppView<ResponseResult<UserInfo>> sportAppView = new SportAppView<ResponseResult<UserInfo>>() {
        @Override
        public void onSuccess(ResponseResult<UserInfo> responseResult) {
            dismissDialog();
            if (responseResult.isSuccess()) {
                Log.d(TAG, "onSuccess: ");
                userInfo = responseResult.getResult();
                userInfo.setPhone(phone);
                userInfo.setPassword(pwd);

                saveUserInfoToDB();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }
            else {
                Toast.makeText(LoginActivity.this, "登录失败:" + responseResult.getMsg(), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onRequestError(String msg) {
            dismissDialog();
            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
        }
    };



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_to_register:
                startActivity(new Intent(this,RegisterActivity.class));
                break;
            case R.id.login_button:
                phone = inputPhone.getText().toString();
                pwd = inputPwd.getText().toString();
                showProgressDialog(this, null, "正在登录");
                userPresenter.userLogin(phone, pwd, 0);//做判断
                break;
            case R.id.password_show:
                if (!passwordDisplayTag) {
                    // 设置 EditText 的 input type  显示 密码
                    inputPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // 隐藏 密码
                    inputPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                passwordDisplayTag = !passwordDisplayTag;
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == START_AC_REG) {
                String sPhone = data.getStringExtra("phone");
                String sPwd = data.getStringExtra("password");
                if (sPhone != null) {
                    inputPhone.setText(sPhone);
                }
                if (sPwd != null) {
                    inputPwd.setText(sPwd);
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        userPresenter.onStop();
    }

    private void saveUserInfoToDB() {
        try {
            sqlParaWrapper= new SQLParaWrapper(this);
            sqlParaWrapper.sqLiteDatabase.execSQL(SQLStatement.AddUser,
                    sqlParaWrapper.getUserStringArray(userInfo));
        }catch (SQLiteException e){
            Log.e(TAG, "saveUserInfoToDB: " + e.getMessage() );
        }
        BaseData baseData = new BaseData(this);
        baseData.updateUserID(userInfo.getId());
        new IMService();
    }

}
