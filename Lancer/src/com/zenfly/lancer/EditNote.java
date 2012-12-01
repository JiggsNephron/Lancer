/**
 * Allows the user to edit a note
 * 
 * Authors: Richard Cody, 
 * 
 */

package com.zenfly.lancer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseIntArray;
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
	SparseIntArray spinnerandtaskMap;
	
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
    
    public void saveNoteEdits (View v) {
    	
    	Intent back_to_note = new Intent(context, ViewNotes.class);
    	
    	// get the user entered note body and subject and put to string
    	stnote_body = et_note_body.getText().toString();
    	stnote_subject = et_note_subject.getText().toString();
    	
    	// get the ID of the task chosen by the user in the spinner by spinner's position
    	task_id = spinnerandtaskMap.get(sp_assign_to_task.getSelectedItemPosition());

    	
    	// if the body and subject are populated, the note is saved with the changes
    	// if not, a toast is shown to inform the user
    	if (!stnote_body.equals("") && !stnote_subject.equals("")) {
    		
    		current_note.setBody(stnote_body);
    		current_note.setSubject(stnote_subject);
    		current_note.setTask(task_id);
    		
    		db.updateNote(current_note);
    		
    		Toast.makeText(getApplicationContext(), "Note " + stnote_subject + " edited.", Toast.LENGTH_SHORT).show();
    		back_to_note.putExtra("job_id", job_id);
    		back_to_note.putExtra("note_id", note_id);
        	startActivity(back_to_note);
    		
    	} else Toast.makeText(getApplicationContext(), "Please enter a note subject and body", Toast.LENGTH_SHORT).show();
    }
    
    public void deleteNote (View v) {
    	
    	// confirms the action with the Alert Dialog
    	final AlertDialog.Builder builder=new AlertDialog.Builder(EditNote.this);
    	builder.setTitle("Delete " + current_note.getSubject());
    	builder.setMessage("Are you sure you want to delete this Note?");
    			
    	builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
    		
    		public void onClick(DialogInterface dialog, int id)
    			{
    					db.deleteNote(note_id);
    	    			Toast.makeText(getApplicationContext(), "Deleted " + current_note.getSubject(), Toast.LENGTH_LONG).show();
    	    			Intent back_to_note = new Intent(context, NotesList.class);
    	    			back_to_note.putExtra("job_id", job_id);
    	    	    	startActivity(back_to_note);
    			}
    	});
    	      
    	builder.setNegativeButton("No", new DialogInterface.OnClickListener()
    		{
    			public void onClick(DialogInterface dialog, int id)
    			{
    	 	   	     dialog.cancel();
    			}
    	});    	      
    	builder.create().show();
    }

}
