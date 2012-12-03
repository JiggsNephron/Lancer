package com.zenfly.lancer;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewJobInvoice extends Activity {
	
	final Context context = this;
	
	Button button_total_earned_tasks;
	Button button_total_expenses;
	
	TextView total_earned_text;
	TextView total_due_text;
	
	int job_id;
	
	float total_expenses;
	
	long minutes_worked = 0;
	float wages = 0;
	float earned_on_task;
	float total_income;
	
	float total_earned;
	float total_tobe_paid;
	
	
	DatabaseHandler db;
	NumberFormat locale_currency_format;
	
	Job current_job;
	
	List<Task> all_job_tasks = new ArrayList<Task>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job_invoice);
        
        db = new DatabaseHandler(this);
        locale_currency_format = NumberFormat.getCurrencyInstance();
        
        job_id = getIntent().getIntExtra("job_id", 0);
        
        // get and set the total task earned button's text
        button_total_earned_tasks = (Button) findViewById(R.id.button_total_earned_tasks);        
        all_job_tasks = db.getAllTasksForJob(job_id);        
        for (Task this_task : all_job_tasks) {
        	minutes_worked = this_task.getMinutesWorked();
        	wages = this_task.getWage();
        	earned_on_task = (minutes_worked/60.00F) * wages;
        	
        	total_income += earned_on_task;
        }
        button_total_earned_tasks.setText(locale_currency_format.format(total_income));
        
        // get and set the total expenses button's text
        button_total_expenses = (Button) findViewById(R.id.button_total_expenses);        
        total_expenses = db.getTotalCostForJob(job_id);        
        button_total_expenses.setText(locale_currency_format.format(total_expenses));   

        // get and set the total earned text       
        total_earned = total_income - total_expenses;          
        total_earned_text = (TextView) findViewById(R.id.total_earned_text);        
        total_earned_text.setText(locale_currency_format.format(total_earned));
        
        total_tobe_paid = total_income + total_expenses;
        total_due_text = (TextView) findViewById(R.id.total_due_text);
        total_due_text.setText(locale_currency_format.format(total_tobe_paid));
        
    }
    
    public void toExpenses (View v) {
       	Intent intent = new Intent(this, ExpensesList.class);
       	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
       	intent.putExtra("task_id", getIntent().getIntExtra("task_id", 0));
       	intent.putExtra("from_invoice", 1);
    	startActivity(intent);	
   } 
    
    public void toTasks (View v) {
       	Intent intent = new Intent(this, TasksList.class);
       	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
       	intent.putExtra("task_id", getIntent().getIntExtra("task_id", 0));
       	intent.putExtra("from_invoice", 1);
    	startActivity(intent);	
   }      
    
}
