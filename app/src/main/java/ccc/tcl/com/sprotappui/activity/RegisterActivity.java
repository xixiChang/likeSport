package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.model.ResponseResult;
import ccc.tcl.com.sprotappui.presenter.presenterimpl.UserPresenter;
import ccc.tcl.com.sprotappui.ui.SportAppView;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private EditText name, phone, code, password;
    private String sName, sPhone, sCode, sPassword;
    private Button getCode, register;
    private UserPresenter userPresenter = new UserPresenter();
    private static final String TAG = "RegisterActivity";
    private static final int START_AC_REG = 5014;

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
        name = (EditText) findViewById(R.id.rg_name);
        phone = (EditText) findViewById(R.id.rg_phone);
        code = (EditText) findViewById(R.id.rg_code);
        password = (EditText) findViewById(R.id.rg_password);
        getCode = (Button) findViewById(R.id.rg_get_code);
        register = (Button) findViewById(R.id.rg_register);

        getCode.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    private SportAppView<ResponseResult<String>> sportAppView = new SportAppView<ResponseResult<String>>() {


        @Override
        public void onSuccess(ResponseResult<String> response) {
            if (response.isSuccess()){
                Log.d(TAG, "onSuccess: " + response.toString());
            }
            Log.d(TAG, "onSuccess: " + response.getMsg());
        }

        @Override
        public void onError(String msg) {
            Log.d(TAG, "onError: " + msg);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rg_get_code:
                String sPhone0 = phone.getText().toString();
                userPresenter.userGetAuthCode(sPhone0);
                Log.d(TAG, "onClick: rg_get_code");
                break;
            case R.id.rg_register:
                sPhone = phone.getText().toString();
                sPassword = password.getText().toString();
                sName = name.getText().toString();
                sCode = code.getText().toString();
                userPresenter.userRegister(sPhone, sName, sPassword, sCode);
                break;
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
    }
}
