package com.zenfly.lancer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class TaskAdapter extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_task_adapter, menu);
        return true;
    }
}
