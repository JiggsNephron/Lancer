package com.zenfly.lancer;


import java.text.NumberFormat;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class NoteAdapter extends ArrayAdapter<Note> {

	  private final Activity activity;
	  private final List<Note> NoteObject;
	  private DatabaseHandler db;
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
	        
	        NoteItemView.noteName = (TextView) rowView.findViewById(R.id.ExpenseItemDisplay);

	        rowView.setTag(NoteItemView); 							//for later access
	    }
	    NoteItemView = (NoteView) rowView.getTag();
	    Note currentNote = (Note) NoteObject.get(position); //casts as expense
	  
	    //ExpenseItemView.quantity.setText(currentExpense.getQuantity()); //sets the data
	    //int quantityOfItem = currentExpense.getQuantity(); 				//gets the quantity of the Item
	    NoteItemView.item = (currentNote.getItem()); 				//gets the item ID
	    Item itemName = db.getItem(NoteItemView.item); 				// gets data on this Item


	    
	    NoteItemView.itemName.setText(itemName.getName());			//sets the data
	    //Log.v("Expenses list", "TotalCost =" + TotalCost  + "~END"); 
	  
	    return rowView;
	}

    protected static class NoteView
    {
        protected TextView itemName;
        protected int item;
        protected TextView cost;
        protected TextView Total_cost;
        protected TextView quantity;
    }
}

