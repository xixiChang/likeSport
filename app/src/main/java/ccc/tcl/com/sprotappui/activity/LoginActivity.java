package ccc.tcl.com.sprotappui.activity;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.data.UserInfo;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.UserPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;

import static ccc.tcl.com.sprotappui.App.userInfo;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText inputPhone, inputPwd;
    private TextView loginToReg;
    private Button login;
    private static final int START_AC_REG = 5014;
    private UserPresenter userPresenter;
    private static final String TAG = "LoginActivity";


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
        inputPhone = (EditText) findViewById(R.id.user_login_phone);
        inputPwd = (EditText) findViewById(R.id.user_login_pwd);
        loginToReg = (TextView) findViewById(R.id.login_to_register);
        login = (Button) findViewById(R.id.login_button);

        loginToReg.setOnClickListener(this);
        login.setOnClickListener(this);


    }

    private SportAppView<ResponseResult<UserInfo>> sportAppView = new SportAppView<ResponseResult<UserInfo>>() {
        @Override
        public void onSuccess(ResponseResult<UserInfo> responseResult) {
            if (responseResult.isSuccess()) {
                userInfo = responseResult.getResult();
                Log.e(TAG, "onSuccess: id>" + userInfo.getIm_uid());
                Log.e(TAG, "onSuccess: pwd>" + userInfo.getSession());
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void onError(String msg) {
            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
            Log.d(TAG, "onError: " + msg);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_to_register:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivityForResult(intent, START_AC_REG);
                break;
            case R.id.login_button:
                String phone = inputPhone.getText().toString();
                String pwd = inputPwd.getText().toString();
                userPresenter.userLogin(phone, pwd, 0);
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
}
