package com.zenfly.lancer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.Window;
import android.widget.TextView;

public class ViewNotes extends Activity {
	
	// below list sets up variables to hold data that can be used anywhere in the class.
	private DatabaseHandler db;
	int NoteId;
	Note note;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE); // RC: this removed the black bar at the top of activities.

		setContentView(R.layout.activity_view_notes);
		
		
		db = new DatabaseHandler(this);
		NoteId = getIntent().getExtras().getInt("note"); 				// gets the task ID from the intent
		Log.v("Id is: ", NoteId+" = Note");
		note = db.getNote(NoteId);
		
		
		TextView noteSubject = (TextView) findViewById(R.id.NoteItemDisplay);				//prepares to access textView
		TextView noteBody = (TextView) findViewById(R.id.NoteText);			//prepares to access textView
	
		noteSubject.setText(note.getSubject());									// sets the text view this data will always be set
		noteBody.setText(note.getBody());								// sets the text view this data will always be set
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_view_notes, menu);
		return true;
	}
	
	public void editNote()
	{
		Intent intent = new Intent(ViewNotes.this, JobsOptions.class);
    	intent.putExtra("NoteId", NoteId);
    	startActivity(intent);
	}

}
