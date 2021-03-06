/**
 * Allows the user to add new notes to their job (and task)
 * 
 * Authors: Richard Cody,
 * 
 */

package com.zenfly.lancer;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseIntArray;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddNewNote extends Activity {
	
	final Context context = this;
	
	DatabaseHandler db;
	
	EditText et_note_subject;
	EditText et_note_body;
	Spinner sp_assign_to_task;
	
	String stnote_subject;
	String stnote_body;
	
	int job_id;
	int task_id;
	
	int counter = 0;
	int set_spinner_to = 0;
	SparseIntArray spinnerandtaskMap;
	
	Task chosen_task;
	
	List<Task> all_tasks = new ArrayList<Task>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_new_note);
        
        db = new DatabaseHandler(context);
        
        // get job id from the passed intent
        job_id = getIntent().getIntExtra("job_id", 0);
        task_id = getIntent().getIntExtra("task_id", 0);
        
        // assign each widget in the view to a variable
        et_note_subject  = (EditText) findViewById(R.id.note_subject);
        et_note_body  = (EditText) findViewById(R.id.note_body);
        sp_assign_to_task  = (Spinner)findViewById(R.id.note_to_task);
        
        spinnerandtaskMap = new SparseIntArray();
        
        // get a list of all tasks to be used for populating the spinner
        all_tasks = db.getAllTasksForJob(job_id);
        // create an ArrayAdapter to fill with task names
        ArrayAdapter <CharSequence> adapter = new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // add one default task choice (no task chosen)
        adapter.add("Not assigned to a task");
        spinnerandtaskMap.put(counter, 0);
        // go through the list of tasks, and for each one, get its name and add it to the adapter array
        for (Task task: all_tasks) {
        	counter++;
        	spinnerandtaskMap.put(counter, task.getId());
        	adapter.add(task.getName());
        	if (task.getId() == task_id) {
        		set_spinner_to = counter;
        	}
        }      
        // set the spinner to use the array contents       
        sp_assign_to_task.setAdapter(adapter);
        // set the spinner to the position of the already set task
        sp_assign_to_task.setSelection(set_spinner_to);
    }
    
    // called when the save button is pressed
    public void saveNote (View v) {
    	
    	Intent back_to_notes_list = new Intent(context, NotesList.class);
    	
    	// get the user entered note body and subject and put to string
    	stnote_body = et_note_body.getText().toString();
    	stnote_subject = et_note_subject.getText().toString();
    	
    	// get the ID of the task chosen by the user in the spinner by spinner's position
    	task_id = spinnerandtaskMap.get(sp_assign_to_task.getSelectedItemPosition());
    	
    	// if the body and subject are populated, a new note is added
    	// if not, a toast is shown to inform the user
    	if (!stnote_body.equals("") && !stnote_subject.equals("")) {
    		
    		Note new_note = new Note(job_id, task_id, stnote_subject, stnote_body);        	
    		db.addNote(new_note);
    		Toast.makeText(getApplicationContext(), "Note " + stnote_subject + " added.", Toast.LENGTH_SHORT).show();
    		back_to_notes_list.putExtra("job_id", job_id);
    		startActivity(back_to_notes_list);
    		
    	} else Toast.makeText(getApplicationContext(), "Please enter a note subject and body", Toast.LENGTH_SHORT).show();
    }
    
    // Override back button to create a more consistent experience
    @Override
    public void onBackPressed() {
    	Intent intent = new Intent(AddNewNote.this, NotesList.class);
    	intent.putExtra("job_id", job_id);
    	intent.putExtra("task_id", getIntent().getIntExtra("task_id", 0));
    	startActivity(intent);
    	return;
    }       
}
