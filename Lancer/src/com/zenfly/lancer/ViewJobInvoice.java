/**
 * Allows the user to view job total expenses and earned and create email
 * 
 * Authors: Richard Cody, 
 * 
 */

package com.zenfly.lancer;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_view_job_invoice);
        
        db = new DatabaseHandler(this);
        locale_currency_format = NumberFormat.getCurrencyInstance();
        
        // get the job id from the intent and make a current_job object
        job_id = getIntent().getIntExtra("job_id", 0);
        current_job = db.getJob(job_id);
        
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

        // get and set the total to be paid text   
        total_tobe_paid = total_income + total_expenses;
        total_due_text = (TextView) findViewById(R.id.total_due_text);
        total_due_text.setText(locale_currency_format.format(total_tobe_paid));
    }
    
    // run when user click email invoice
    public void emailInvoice (View v) {
    	
    	// Inflater to launch email dialog for entering recipient info and a message
    	LayoutInflater factory = LayoutInflater.from(this);            
        final View recipientEntryView = factory.inflate(R.layout.invoice_email, null);        
    	
        // alerdialog used as the dialog
    	AlertDialog.Builder emaildialog = new AlertDialog.Builder(this);
        // sets the dialog's view to the correct layout
    	emaildialog.setView(recipientEntryView);
    	
    	// get each view element
    	final EditText name = (EditText) recipientEntryView.findViewById(R.id.recipient_name); 
    	final AutoCompleteTextView email = (AutoCompleteTextView) recipientEntryView.findViewById(R.id.recipient_email);
    	final EditText own_name = (EditText) recipientEntryView.findViewById(R.id.own_name);
    	final EditText message = (EditText) recipientEntryView.findViewById(R.id.email_message);
    	
    	// a string to store contact and task email addresses
    	ArrayList<String> emailAddressCollection = new ArrayList<String>();
    	ContentResolver cr = getContentResolver();
    	// gets the phone contacts' email addresses
    	Cursor emailCur = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, null, null, null);
    	// adds the email addresses added to any tasks to the list
    	emailAddressCollection = db.getAllEmailsForJob(job_id);
    	// adds each phone contact email to the list
    	while (emailCur.moveToNext())
    	{
    	    String stemail = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
    	    emailAddressCollection.add(stemail);
    	}    	
    	emailCur.close();    	
    	// a string array used for the AutoCompleteTextView adapter
    	String[] emailAddresses = new String[emailAddressCollection.size()];    	
    	emailAddressCollection.toArray(emailAddresses);
    	// arrayadapter populated with string of emails
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, emailAddresses);
    	
    	// email AutoCompleteTextView populated with the emails
    	email.setAdapter(adapter);
       	
    	message.setText("Thank you for your custom, please see the payables below. Looking forward to doing business with you in the future.");
    	
    	// what to do if the user clicks Send Email
        emaildialog.setPositiveButton("Send Email", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {            	
            	
            	// get each view element's user entered string
                String recipient_name = name.getText().toString();
                String recipient_email = email.getText().toString();
                String stown_name = own_name.getText().toString();
                String own_message = message.getText().toString();
                
                // if the email address, name and own name are not blank, run this block
                // if not, make a toast that informs the user
                if (!recipient_email.equals("") && !recipient_name.equals("") && !stown_name.equals("")) {
                	// if theres a valid email address, it sends an email with the user chosen template date
                	if (checkEmailValid(recipient_email)) {
                		Intent create_email = new Intent(Intent.ACTION_SEND);
                    	
                    	create_email.setType("message/rfc822");
                    	create_email.putExtra(Intent.EXTRA_EMAIL  , new String[]{recipient_email});
                    	create_email.putExtra(Intent.EXTRA_SUBJECT, current_job.getClient() + " invoice");
                    	create_email.putExtra(Intent.EXTRA_TEXT   , "Dear "
                    	+ recipient_name 
                    	+ ",\n\n" 
                    	+ own_message
                    	+ "\n\n"
                    	+ "Total Wages: " 
                    	+ locale_currency_format.format(total_income) 
                    	+ "\n\n" 
                    	+ "Total Expenses: " 
                    	+ locale_currency_format.format(total_expenses)
                    	+ "\n\n"
                    	+ "Total to be Paid: "
                    	+ locale_currency_format.format(total_tobe_paid)
                    	+ "\n\n"
                    	+ "Regards, \n"
                    	+ stown_name);
                    	
                    	try {
                    	    startActivity(Intent.createChooser(create_email, "Send mail..."));
                    	} catch (android.content.ActivityNotFoundException ex) {
                    	    Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    	}
                    	
            		}else Toast.makeText(context, "Invalid email address", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(context, "Please enter a recipient name and email address as well as your own name as it should appear in the email.", Toast.LENGTH_SHORT).show();
            }
        });
        // what to do if the user clicks Cancel
        emaildialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });
        emaildialog.show();
    }
    
    // method to check if user entered email address is correct format
    public static boolean checkEmailValid(String email) {
        
       	boolean isEmailValid = false;

        String emailcheckstring = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(emailcheckstring, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        
        if (matcher.matches()) {
        	isEmailValid = true;
        }
        
        return isEmailValid;
    }
    
    public void toExpenses (View v) {
       	Intent intent = new Intent(this, ExpensesList.class);
       	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
       	intent.putExtra("from_invoice", 1);
    	startActivity(intent);	
    } 
    
    public void toTasks (View v) {
       	Intent intent = new Intent(this, TasksList.class);
       	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
       	intent.putExtra("from_invoice", 1);
    	startActivity(intent);	
    }    

    @Override
    public void onBackPressed() {
    	Intent intent = new Intent(ViewJobInvoice.this, JobsOptions.class);
    	intent.putExtra("job_id", getIntent().getIntExtra("job_id", 0));
    	startActivity(intent);    	
    	return;  	
    }  
}
