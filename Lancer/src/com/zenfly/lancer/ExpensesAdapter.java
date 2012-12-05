package com.zenfly.lancer;

import java.text.NumberFormat;
import java.util.List;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ExpensesAdapter extends ArrayAdapter<Expense> {

	  private final Activity activity;
	  private final List<Expense> ExpenseObject;
	  private DatabaseHandler db;
	  private NumberFormat locale_currency_format;
	  public float TotalCost;
	
	  
	public ExpensesAdapter(Activity activity, List<Expense> objects) 
	{
      super(activity, R.layout.activity_expenses_list , objects);
      this.activity = activity;
      this.ExpenseObject = objects;
      db = new DatabaseHandler(this.getContext());
      locale_currency_format = NumberFormat.getCurrencyInstance();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
	    View rowView = convertView;
	    ExpenseView ExpenseItemView = null;
//	    Log.v("Expenses list", "test 6  ~END");
	//    Log.v("Expenses list", "rowView"+ rowView +"  ~END");
	
	    if(rowView == null)
	    {
	    	//Log.v("Expenses list", "test 5  ~END");
	    	//creates new instance of row layout view
	        LayoutInflater inflater = activity.getLayoutInflater();
	        rowView = inflater.inflate(R.layout.expense_item, null);
	        ExpenseItemView = new ExpenseView(); 								//for holding the data
	        
	        ExpenseItemView.itemName = (TextView) rowView.findViewById(R.id.ExpenseItemDisplay);
	        ExpenseItemView.linkedToTask = (TextView) rowView.findViewById(R.id.JobTaskDisplay);
	        ExpenseItemView.cost = (TextView) rowView.findViewById(R.id.ExpenseCostDisplay);
	        rowView.setTag(ExpenseItemView); 									//for later access
	    }
	    ExpenseItemView = (ExpenseView) rowView.getTag();
	    Expense currentExpense = (Expense) ExpenseObject.get(position); 		//casts as expense
	  
	    ExpenseItemView.item = (currentExpense.getItem()); 						//gets the item ID
	    ExpenseItemView.taskId = (currentExpense.getTask()); 					//gets the item TaskId
	    Item itemName = db.getItem(ExpenseItemView.item); 						// gets data on this Item
	    
	    if(ExpenseItemView.taskId != 0)
	    {
		    Task taskObject = db.getTask(ExpenseItemView.taskId); 				// gets task Object
		    ExpenseItemView.linkedToTask.setText(taskObject.getName());			//sets the data
	    }

	    float cost = currentExpense.getQuantity() * itemName.getPrice(); 		// calculates the current cost for that item 
	    TotalCost = TotalCost + cost;
	    
	    ExpenseItemView.itemName.setText(itemName.getName());					//sets the data
	    ExpenseItemView.cost.setText(locale_currency_format.format(cost)); 		//sets the data
	    return rowView;
	}

	protected static class ExpenseView
    {
        protected TextView itemName;
        protected int item;
        protected int taskId;
        protected TextView linkedToTask;
        protected TextView cost;
        protected TextView Total_cost;
    }
}
