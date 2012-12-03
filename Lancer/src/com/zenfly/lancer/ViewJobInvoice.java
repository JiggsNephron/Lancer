package com.zenfly.lancer;

import java.text.NumberFormat;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;

public class ViewJobInvoice extends Activity {
	
	final Context context = this;
	
	DatabaseHandler db;
	NumberFormat locale_currency_format;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_job_invoice);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_view_job_invoice, menu);
        return true;
    }
}
