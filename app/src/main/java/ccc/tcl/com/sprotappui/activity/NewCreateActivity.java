package ccc.tcl.com.sprotappui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import ccc.tcl.com.sprotappui.R;
import ccc.tcl.com.sprotappui.model.Sport;

public class NewCreateActivity extends BaseActivity {
    TextView startTime;
    TextView endTime;
    TextView distance;
    TextView location;
    TextView detail;
    Sport sport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_create);
        Intent intent = getIntent();
        sport = intent.getParcelableExtra("data");
        Toolbar toolbar = (Toolbar) findViewById(R.id.news_details_toolbar);
        super.setToolBar(toolbar, " ",true);
        startTime = (TextView) findViewById(R.id.start_time);
        endTime = (TextView) findViewById(R.id.end_time);
        distance = (TextView) findViewById(R.id.distance);
        location = (TextView) findViewById(R.id.location);
        detail =(TextView) findViewById(R.id.detail);

        startTime.setText(sport.getStart_time());
        endTime.setText(sport.getEnd_time());
        distance.setText(sport.getDistance() + "KM");
        location.setText(sport.getLocation());
        detail.setText(sport.getDetails());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_finish_create_toolbar,menu);
        return super.onCreateOptionsMenu(menu);
    }
}
