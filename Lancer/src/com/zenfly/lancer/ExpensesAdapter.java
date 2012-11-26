package com.zenfly.lancer;

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
	  public DatabaseHandler db;
	
	public ExpensesAdapter(Activity activity, List<Expense> objects) 
	{
      super(activity, R.layout.activity_expenses_list , objects);
      this.activity = activity;
      this.ExpenseObject = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
	    View rowView = convertView;
	    ExpenseView ExpenseItemView = null;
	
	    if(rowView == null)
	    {
	    	//creates new instance of row layout view
	        LayoutInflater inflater = activity.getLayoutInflater();
	        rowView = inflater.inflate(R.layout.expense_item, null);
	        ExpenseItemView = new ExpenseView(); //for holding the data
	        
	        ExpenseItemView.item = (TextView) rowView.findViewById(R.id.ExpenseItemDisplay);
	        ExpenseItemView.cost = (TextView) rowView.findViewById(R.id.ExpenseCostDisplay);
	        rowView.setTag(ExpenseItemView); //for later access
	    }
	    else ExpenseItemView = (ExpenseView) rowView.getTag();
	    Expense currentExpense = (Expense) ExpenseObject.get(position); //casts as course
	  
	    ExpenseItemView.quantity.setText(currentExpense.getQuantity()); //sets the data
	    int quantityOfItem = currentExpense.getQuantity();
	    ExpenseItemView.item.setText(currentExpense.getItem()); //sets the data
	    String itemName = null;
	    ExpenseItemView.cost.setText(currentExpense.getQuantity()); //sets the data
	   // int ID = currentExpense.getQuantity();
	  
	    return rowView;
	}

    protected static class ExpenseView
    {
        protected TextView job;
        protected TextView task;
        protected TextView item;
        protected TextView cost;
        protected TextView quantity;

    }
}
