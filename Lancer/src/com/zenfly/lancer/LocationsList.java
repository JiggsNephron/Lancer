/**
 * Authors: Richard Cody,
 * 
 */

package com.zenfly.lancer;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

public class LocationsList extends Activity {

	final Context context = this;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_list);
        
        // TODO RC: show list of location names
        
    }
    
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_locations_list, menu);
        return true;
    }
}
