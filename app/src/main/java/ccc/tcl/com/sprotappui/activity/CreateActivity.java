package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.model.PlatFormActivity;

public class CreateActivity extends BaseActivity {
    PlatFormActivity platFormActivity;
    EditText name;
    EditText joiner_limit;
    EditText detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        super.setToolBar(toolbar, R.string.create_activity,true);
        Intent intent = getIntent();
        platFormActivity = intent.getParcelableExtra("data");
        Toast.makeText(this, platFormActivity.getImage_url(),Toast.LENGTH_SHORT).show();
        name = (EditText) findViewById(R.id.name);
        joiner_limit = (EditText) findViewById(R.id.joiner_limit);
        detail = (EditText) findViewById(R.id.detail);
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
}
