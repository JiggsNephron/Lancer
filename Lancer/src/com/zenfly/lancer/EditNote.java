package com.zenfly.lancer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class EditNote extends Activity {

	final Context context = this;
	
	DatabaseHandler db;
	
	EditText et_note_subject;
	EditText et_note_body;
	Spinner sp_assign_to_task;
	
	String stnote_subject;
	String stnote_body;
	
	int job_id;
	int task_id;
	int note_id;
	
	Note current_note;
	Task chosen_task;
	
	List<Task> all_tasks = new ArrayList<Task>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        
        db = new DatabaseHandler(context);
        
        // get job id from the passed intent
        job_id = getIntent().getIntExtra("job_id", 0);
        task_id = getIntent().getIntExtra("task_id", 0); 
        note_id = getIntent().getIntExtra("note_id", 0);
        
        // assign each widget in the view to a variable
        et_note_subject  = (EditText) findViewById(R.id.note_subject);
        et_note_body  = (EditText) findViewById(R.id.note_body);
        sp_assign_to_task  = (Spinner)findViewById(R.id.note_to_task);
        
        // get a list of all tasks to be used for populating the spinner
        all_tasks = db.getAllTasksForJob(job_id);
        // create an ArrayAdapter to fill with task names
        ArrayAdapter <CharSequence> adapter = new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // add one default task choice (no task chosen)
        adapter.add("Not assigned to a task");
        // go through the list of tasks, and for each one, get its name and add it to the adapter array
        for (Task task: all_tasks) {
        	adapter.add(task.getName());
        }      
        // set the spinner to use the array contents       
        sp_assign_to_task.setAdapter(adapter);
    }
    
    public void saveNoteEdits (View v) {
    	Intent back_to_note = new Intent(context, ViewNotes.class);
    	
    	startActivity(back_to_note);
    }
    public void deleteNote (View v) {
    }

}
