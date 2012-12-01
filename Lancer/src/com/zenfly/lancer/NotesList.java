package com.zenfly.lancer;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.ListView;
import android.widget.TextView;

public class NotesList extends ListActivity {
	
	private DatabaseHandler db;
	int jobId;
	List<Note> note;
	Task task;
	int task_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_notes_list);
        
        TextView jobName = (TextView) findViewById(R.id.job_name);
        db = new DatabaseHandler(this.getApplicationContext());
        jobId = getIntent().getIntExtra("job_id", 0);
        
        task_id = getIntent().getIntExtra("task_id", 0);
        
        Job job = db.getJob(jobId);
        
        if (task_id != 0) {        	
        	note = db.getAllNotesForTask(task_id); //makes a list of jobs to send to the list View
        } else note = db.getAllNotesForJob(jobId); //makes a list of jobs to send to the list View
        
        jobName.setText(job.getClient());
        Log.v("Expenses list", "note="+ note +"  ~note");
        setListAdapter(new NoteAdapter(this, note)); //starts the list View

        
    }
    
    public void addNewNote (View v) {
       	Intent intent = new Intent(NotesList.this, AddNewNote.class);
       	intent.putExtra("task_id", getIntent().getIntExtra("task_id", 0));
       	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
    	startActivity(intent);	
   }
    
	@Override
	  public void onListItemClick(ListView parent, View v, int position, long id)
	  {	 
	  	Intent intent = new Intent(NotesList.this, ViewNotes.class);
	  	Log.v("Id is: ", position+" = Note");
	  	int noteId = note.get(position).getId();
	  	int taskId = note.get(position).getTask();
	  	int JobId = note.get(position).getJob();
	  	
	  	Log.v("Id is: ", noteId+" = Note");
	  	intent.putExtra("note_id", noteId); //sends the note id
	  	intent.putExtra("task", getIntent().getIntExtra("task_id", 0)); //sends the task id
	  	intent.putExtra("job_id", JobId); //sends the Job id
	    startActivity(intent);
	  }
    
    @Override
    public void onBackPressed() {
    	
    	if (task_id != 0) {
    		Intent intent = new Intent(NotesList.this, ViewTask.class);
    		intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
    		intent.putExtra("task", task_id);
    		startActivity(intent);
    	} else {
    		Intent intent = new Intent(NotesList.this, JobsOptions.class);
        	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
        	startActivity(intent);
    	}    	
    	return;
    	
    }    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_notes_list, menu);
        return true;
    }
}
