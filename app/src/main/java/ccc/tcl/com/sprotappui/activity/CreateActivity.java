package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.model.Sport;

public class CreateActivity extends BaseActivity {
    Sport sport;
    EditText name;
    EditText brief;
    EditText detail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        super.setToolBar(toolbar, R.string.create_activity,true);
        Intent intent = getIntent();
        sport = intent.getParcelableExtra("data");
        Toast.makeText(this,sport.getImage_url(),Toast.LENGTH_SHORT).show();
        name = (EditText) findViewById(R.id.name);
        brief = (EditText) findViewById(R.id.brief);
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
                Intent intent = new Intent(this,FinishCreateActivity.class);
                Bundle data = new Bundle();
                sport.setName(name.getText().toString());
                sport.setDetails(detail.getText().toString());
                data.putParcelable("data",sport);
                intent.putExtras(data);
                startActivity(intent);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
