package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private static final int START_AC_REG = 5014;
    private UserPresenter userPresenter;
    private static final String TAG = "LoginActivity";
    private String phone, pwd;
    private SQLParaWrapper sqlParaWrapper;

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
                Log.d(TAG, "onSuccess: ");
                userInfo = responseResult.getResult();
                userInfo.setPhone(phone);
                userInfo.setPassword(pwd);
                Log.d(TAG, "onSuccess: id>" + userInfo.getIm_uid());
                Log.d(TAG, "onSuccess: pwd>" + userInfo.getSession());

                saveUserInfoToDB();

                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }

        @Override
        public void onRequestError(String msg) {
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
                phone = inputPhone.getText().toString();
                pwd = inputPwd.getText().toString();
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
