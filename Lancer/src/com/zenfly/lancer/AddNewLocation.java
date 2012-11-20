package com.zenfly.lancer;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class AddNewLocation extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_location);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_add_new_location, menu);
        return true;
    }
}
