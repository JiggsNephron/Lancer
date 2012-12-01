package com.zenfly.lancer;


import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NoteAdapter extends ArrayAdapter<Note> {

	  private final Activity activity;
	  private final List<Note> NoteObject;
	  private DatabaseHandler db;
	  Task task;
	  Job job;
	  public float TotalCost;
	
	  
	public NoteAdapter(Activity activity, List<Note> objects) 
	{
		super(activity, R.layout.activity_expenses_list , objects);
	    this.activity = activity;
	    this.NoteObject = objects;
	    db = new DatabaseHandler(this.getContext());
 	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
	    View rowView = convertView;
	    NoteView NoteItemView = null;

	
	    if(rowView == null)
	    {
	    	//Log.v("Expenses list", "test 5  ~END");
	    	//creates new instance of row layout view
	        LayoutInflater inflater = activity.getLayoutInflater();
	        rowView = inflater.inflate(R.layout.expense_item, null);
	        NoteItemView = new NoteView(); 						//for holding the data
	        
	        NoteItemView.noteSubject = (TextView) rowView.findViewById(R.id.NoteItemDisplay);
	        NoteItemView.noteJobTask = (TextView) rowView.findViewById(R.id.JobTaskDisplay);	

	        rowView.setTag(NoteItemView); 							//for later access
	    }
	    NoteItemView = (NoteView) rowView.getTag();
	    Note currentNote = (Note) NoteObject.get(position); //casts as note

	    Job jobObject = db.getJob(currentNote.getJob()); 				// gets data on this Item

	    NoteItemView.noteSubject.setText(currentNote.getSubject());			//sets the data
	    NoteItemView.noteJobTask.setText(jobObject.getClient());			//sets the data
  
	    return rowView;
	}

    protected static class NoteView
    {
        protected TextView noteSubject;
        protected TextView noteJobTask;

    }
}

