package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ccc.tcl.com.sprotappui.App;
import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.constant.URLConstant;

//import static ccc.tcl.com.sprotappui.service.IMService.mIMKit;

@Deprecated
public class TestActivity extends BaseActivity {
    private Button start;
    private EditText aId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        initView();
    }

    @Deprecated
    private void initView() {
        start = (Button) findViewById(R.id.start_conversation);
        aId = (EditText) findViewById(R.id.another_id);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String anotherId = aId.getText().toString();

//                Intent intent = mIMKit.getChattingActivityIntent(anotherId, URLConstant.BAICHUAN_APP_KEY);
//                startActivity(intent);
            }
        });
    }
}