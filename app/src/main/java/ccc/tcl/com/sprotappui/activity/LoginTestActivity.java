package ccc.tcl.com.sprotappui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import ccc.tcl.com.sprotappui.R;

public class LoginTestActivity extends AppCompatActivity {
    Button wxlogin;
    private SHARE_MEDIA platform = null;
    private UMShareAPI mShareAPI = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_test);
        wxlogin = (Button) findViewById(R.id.button);
        mShareAPI = UMShareAPI.get(this);
        wxlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                platform = SHARE_MEDIA.WEIXIN;
                new ShareAction(LoginTestActivity.this)
                        .withText("hello")
                        .setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                        .setCallback(shareListener)
                        .open();
                //mShareAPI.doOauthVerify(LoginTestActivity.this, platform, umAuthListener);
                //mShareAPI.getPlatformInfo(LoginTestActivity.this, platform, umAuthListener);
            }
        });
    }

    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(LoginTestActivity.this, "Authorize succeed", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( LoginTestActivity.this, "Authorize fail"+action + t, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( LoginTestActivity.this, "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(LoginTestActivity.this,"成功了",Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(LoginTestActivity.this,"失败"+t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(LoginTestActivity.this,"取消了",Toast.LENGTH_LONG).show();

        }
    };
    @Override
    protected void onDestroy() {
        UMShareAPI.get(this).release();
        super.onDestroy();
    }
}
