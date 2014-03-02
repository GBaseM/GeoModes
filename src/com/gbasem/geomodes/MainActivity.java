package com.gbasem.geomodes;

import com.gbasem.geomodes.GPSTracker;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {
	GPSTracker gps;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gps = new GPSTracker(MainActivity.this);
		double lat=gps.getLatitude();
		double longi=gps.getLongitude();
		//Code for Reverse geocoding
		String address=gps.getAddress(lat,longi);
		Toast.makeText(this, address, Toast.LENGTH_LONG).show();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
