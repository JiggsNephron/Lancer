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
import android.widget.Toast;

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
	
	int counter = 0;
	int set_spinner_to = 0;
	
	Note current_note;
	Task chosen_task;
	
	List<Task> all_tasks = new ArrayList<Task>();
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        
        db = new DatabaseHandler(context);
        
        // get note id from the passed intent
        note_id = getIntent().getIntExtra("note_id", 0);
        
        current_note = db.getNote(note_id);
        
        job_id = current_note.getJob();
        task_id = current_note.getTask();
        
        // assign each widget in the view to a variable
        et_note_subject  = (EditText) findViewById(R.id.note_subject);
        et_note_body  = (EditText) findViewById(R.id.note_body);
        sp_assign_to_task  = (Spinner)findViewById(R.id.note_to_task);
        
        stnote_body = current_note.getBody();
        stnote_subject = current_note.getSubject();
        
        et_note_subject.setText(stnote_subject);
        et_note_body.setText(stnote_body);
        
        
        // get a list of all tasks to be used for populating the spinner
        all_tasks = db.getAllTasksForJob(job_id);
        // create an ArrayAdapter to fill with task names
        ArrayAdapter <CharSequence> adapter = new ArrayAdapter <CharSequence> (this, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // add one default task choice (no task chosen)
        adapter.add("Not assigned to a task");
        // go through the list of tasks, and for each one, get its name and add it to the adapter array
        for (Task task: all_tasks) {
        	counter++;
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
    
    public void saveNoteEdits (View v) {
    	
    	Intent back_to_note = new Intent(context, ViewNotes.class);
    	
    	// get the user entered note body and subject and put to string
    	stnote_body = et_note_body.getText().toString();
    	stnote_subject = et_note_subject.getText().toString();
    	
    	// get the ID of the task chosen by the user in the spinner by spinner's position
    	// if no task was chosen, 0 is used
    	if ((sp_assign_to_task.getSelectedItemPosition()-1) >= 1) {
    		chosen_task = all_tasks.get(sp_assign_to_task.getSelectedItemPosition()-1);
    		task_id = chosen_task.getId();
    	} else task_id = 0;
    	
    	// if the body and subject are populated, the note is saved with the changes
    	// if not, a toast is shown to inform the user
    	if (!stnote_body.equals("") || !stnote_subject.equals("")) {
    		
    		Note new_note = new Note(job_id, task_id, stnote_subject, stnote_body);
    		new_note.setId(note_id);
    		db.updateNote(new_note);
    		
    		Toast.makeText(getApplicationContext(), "Note " + stnote_subject + " edited.", Toast.LENGTH_SHORT).show();
    		back_to_note.putExtra("job_id", job_id);
    		back_to_note.putExtra("note", note_id);
        	startActivity(back_to_note);
    		
    	} else Toast.makeText(getApplicationContext(), "Please enter a note subject and body", Toast.LENGTH_SHORT).show();
    }
    
    public void deleteNote (View v) {
    	
    	Intent back_to_note = new Intent(context, ViewNotes.class);
    	db.deleteNote(note_id);
    	back_to_note.putExtra("job_id", job_id);
		back_to_note.putExtra("note", note_id);
    	startActivity(back_to_note);
    	
    }

}
