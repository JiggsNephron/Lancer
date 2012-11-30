package com.zenfly.lancer;

import java.util.List;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class NotesList extends Activity {
	
	private DatabaseHandler db;
	int jobId;
	List<Note> note;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notes_list);
        
        TextView jobName = (TextView) findViewById(R.id.job_name);
        db = new DatabaseHandler(this.getApplicationContext());
        jobId = getIntent().getIntExtra("job_id", 0);
        Job job = db.getJob(jobId);
        jobName.setText(job.getClient());
       // Log.v("Expenses list", "task="+ task +"  ~END");
        
    }
    
    public void addNewNote (View v) {
       	Intent intent = new Intent(NotesList.this, AddNewNote.class);
       	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
    	startActivity(intent);	
   }
    
    @Override
    public void onBackPressed() {
    	Intent intent = new Intent(NotesList.this, JobsOptions.class);
    	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
    	startActivity(intent);
    	return;
    }    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_notes_list, menu);
        return true;
    }
}
