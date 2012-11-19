package com.zenfly.lancer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ExpensesList extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_expences_list, menu);
        return true;
    }
}
