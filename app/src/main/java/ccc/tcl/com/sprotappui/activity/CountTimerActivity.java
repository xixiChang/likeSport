package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;

import ccc.tcl.com.sprotappui.R;

public class CountTimerActivity extends BaseActivity {
    private Button btnCountTimer;
    private MyCountTimer myCountTimer;
    private String showData[]={"","GO!","1","2","3"};
    private int spinnerFlag;
    private final static  String TAG ="CountTimerActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_count_timer);
        btnCountTimer= (Button) findViewById(R.id.btnCountTimer);
        Intent intent=getIntent();
        spinnerFlag=intent.getIntExtra("FLAG",-1);
        Log.d("tag", "onCreate: "+spinnerFlag);
        //倒计时总时间为10S，时间防止从3s开始显示
        myCountTimer= new MyCountTimer(5000, 1000, btnCountTimer);
        myCountTimer.start();
    }
    //自定义一个类继承自MyCountTimer，实现多少秒后的逻辑
    public class MyCountTimer extends CountDownTimer {
        private TextView btn;
        /**
         * 参数 millisInFuture         倒计时总时间（如30s,60S，120s等）
         * 参数 countDownInterval    渐变时间（每次倒计1s）
         * 参数 btn               点击的按钮(因为Button是TextView子类，为了通用我的参数设置为TextView）
         * 参数 endStrRid   倒计时结束后，按钮对应显示的文字
         */
        public MyCountTimer(long millisInFuture, long countDownInterval, TextView btn ) {
            super(millisInFuture, countDownInterval);
            this.btn = btn;
        }

        /**
         * 计时完毕时触发
         */
        @Override
        public void onFinish() {
            //停止倒计时
            this.cancel();
            Intent intent=new Intent(CountTimerActivity.this,TraceRecordActivity.class);
            intent.putExtra("type",spinnerFlag);
            Log.d("tag", "onFinish: "+spinnerFlag);
            startActivity(intent);
            finish();

        }
        /**
         * 计时过程显示
         */
        @Override
        public void onTick(long millisUntilFinished) {
            btn.setEnabled(false);
            //每隔一秒修改一次UI
            btn.setText(showData[(int) millisUntilFinished/1000]);
            // 设置透明度渐变动画
            final AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
            //设置动画持续时间
            alphaAnimation.setDuration(1000);
            btn.startAnimation(alphaAnimation);
            // 设置缩放渐变动画
            final ScaleAnimation scaleAnimation =new ScaleAnimation(0.5f, 2f, 0.5f,2f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            scaleAnimation.setDuration(1000);
            btn.startAnimation(scaleAnimation);
        }
    }
}
