package com.zenfly.lancer;

import com.example.lancer.R;
import com.example.lancer.R.layout;
import com.example.lancer.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AddNewTask extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_new_task, menu);
        return true;
    }
}
