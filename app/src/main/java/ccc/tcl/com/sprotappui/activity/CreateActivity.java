package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.model.PlatFormActivity;

public class CreateActivity extends BaseActivity{
    private PlatFormActivity platFormActivity;
    private EditText name;
    private EditText joiner_limit;
    private EditText detail;
    private TextView nameTextCount;
    private TextView detailTextCount;

    private final int NAME_TEXT_MAX_COUNT = 10;
    private final int DETAILS_MAX_COUNT = 40;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        super.setToolBar(toolbar, R.string.create_activity,true);
        Intent intent = getIntent();
        platFormActivity = intent.getParcelableExtra("data");
        initView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_pick_picture_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.next:
                String nameText = name.getText().toString();
                String detailText = detail.getText().toString();
                if (nameText.isEmpty()||detailText.isEmpty()){
                    break;
                }
                Intent intent = new Intent(this,FinishCreateActivity.class);
                Bundle data = new Bundle();
                platFormActivity.setName(name.getText().toString());
                platFormActivity.setDetails(detail.getText().toString());
                platFormActivity.setJoin_num_all(Integer.parseInt(joiner_limit.getText().toString()));
                data.putParcelable("data", platFormActivity);
                intent.putExtras(data);
                startActivity(intent);
                finish();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private TextWatcher nameWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            nameTextCount.setText(count + "/" + NAME_TEXT_MAX_COUNT);
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > NAME_TEXT_MAX_COUNT) {
                s.delete(s.length() - 1, s.length());
                name.setText(s);
                name.setSelection(NAME_TEXT_MAX_COUNT);
            }
        }
    };

    private TextWatcher detailWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            detailTextCount.setText(count + "/" + DETAILS_MAX_COUNT);
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > DETAILS_MAX_COUNT) {
                s.delete(s.length() - 1, s.length());
                detail.setText(s);
                detail.setSelection(DETAILS_MAX_COUNT);
            }
        }
    };
    private TextWatcher joinWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 3) {
                s.delete(s.length() - 1, s.length());
                joiner_limit.setText(s);
                joiner_limit.setSelection(3);
            }
        }
    };

    private void initView(){
        name = (EditText) findViewById(R.id.name);
        joiner_limit = (EditText) findViewById(R.id.joiner_limit);
        detail = (EditText) findViewById(R.id.detail);
        nameTextCount = (TextView) findViewById(R.id.name_text_count);
        detailTextCount = (TextView) findViewById(R.id.details_text_count);
        name.addTextChangedListener(nameWatcher);
        detail.addTextChangedListener(detailWatcher);
        joiner_limit.addTextChangedListener(joinWatcher);
    }
}
