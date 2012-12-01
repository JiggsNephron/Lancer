package com.zenfly.lancer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class ViewNotes extends Activity {
	
	// below list sets up variables to hold data that can be used anywhere in the class.
	private DatabaseHandler db;
	int NoteId;
	int JobId;
	int TaskId;
	Note note;
	Job job;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // RC: this removed the black bar at the top of activities.

		setContentView(R.layout.activity_view_notes);
		
		
		db = new DatabaseHandler(this);
		NoteId = getIntent().getExtras().getInt("note_id"); 				// gets the task ID from the intent
		TaskId = getIntent().getExtras().getInt("task"); 				// gets the task ID from the intent
		JobId = getIntent().getExtras().getInt("job_id");				// gets the Job  ID from the intent
		
		
		
		Log.v("Id is: ", NoteId+" = Note");
		note = db.getNote(NoteId);
		
		TextView JobNameTitle = (TextView) findViewById(R.id.job_name);				//prepares to access textView
		TextView TaskNameTitle = (TextView) findViewById(R.id.job_option);			//prepares to access textView
		TextView displayName = (TextView) findViewById(R.id.thisTaskName);			//prepares to access textView
		TextView noteSubject = (TextView) findViewById(R.id.NoteItemDisplay);				//prepares to access textView
		TextView noteBody = (TextView) findViewById(R.id.NoteText);			//prepares to access textView
	
		noteSubject.setText(note.getSubject());									// sets the text view this data will always be set
		noteBody.setText(note.getBody());								// sets the text view this data will always be set
		TaskNameTitle.setText("View Task");								// sets the text view this data will always be set

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_notes, menu);
		return true;
	}
	
	public void editNote(View v)
	{
		Intent intent = new Intent(ViewNotes.this, EditNote.class);
    	intent.putExtra("note_id", NoteId);
    	startActivity(intent);
	}

}
