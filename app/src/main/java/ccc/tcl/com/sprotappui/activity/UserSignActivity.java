package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.customui.ToolBar;
import ccc.tcl.com.sprotappui.utils.Util;

public class UserSignActivity extends BaseActivity {
    private ToolBar toolBar;
    private EditText content;
    private TextView content_count;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign);
        toolBar = (ToolBar) findViewById(R.id.toolbar);
        super.setToolBar(toolBar, R.string.user_sign, true);

        initView();
        Intent intent = getIntent();
        String s = intent.getStringExtra("sign");
        if (!Util.isEmpty(s)){
            content.setText(s);
            content_count.setText(2*s.length() + "/100");
        }
    }

    private void initView() {
        content = (EditText) findViewById(R.id.sign_content);
        content_count = (TextView) findViewById(R.id.content_count);

        content.addTextChangedListener(new EditStatusListener());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent();
                intent.putExtra("sign", content.getText().toString());
                intent.setFlags(-1);
                setResult(1759, intent);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("sign", content.getText().toString());
        intent.setFlags(-1);
        setResult(1759, intent);
        finish();
    }

    class EditStatusListener implements TextWatcher {
        private CharSequence temp = "";
        private int editStart;
        private int editEnd;
        private final int charMaxNum = 100;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            temp = s;
        }


        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            content_count.setText((temp.length() + s.length()) + "/100");
        }


        @Override
        public void afterTextChanged(Editable s) {
            editStart = content.getSelectionStart();
            editEnd = content.getSelectionEnd();
            if (temp.length() > charMaxNum) {
                Toast.makeText(getApplicationContext(), "你输入的字数已经超过了限制！", Toast.LENGTH_LONG).show();
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                content.setText(s);
                content.setSelection(tempSelection);
            }
        }
    }
}
