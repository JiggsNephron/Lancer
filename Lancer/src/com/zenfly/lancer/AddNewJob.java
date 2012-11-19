package com.zenfly.lancer;

import com.example.lancer.R;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.support.v4.app.NavUtils;

public class AddNewJob extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_job);
        
        EditText jobName = (EditText) findViewById(R.id.job_name);
        EditText jobLocation = (EditText) findViewById(R.id.job_name);
        EditText jobDeadline = (EditText) findViewById(R.id.job_name);
    }
    
    // on pressing Back, go back to main
    public void saveJob(View v) {
    	finish();
    }    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_new_job, menu);
        return true;
    }

    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
