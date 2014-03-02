package com.gbasem.geomodes;

import com.gbasem.geomodes.GPSTracker;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import android.location.Address;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
	GPSTracker gps;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gps = new GPSTracker(MainActivity.this);
		if(gps.canGetLocation())
		{
		double lat=gps.getLatitude();
		double longi=gps.getLongitude();
		Toast.makeText(this, lat+" "+longi, Toast.LENGTH_LONG).show();
		//Code for Reverse geocoding
		JSONObject locationInfo = gps.getLocationInfo(lat,longi);

		String userAddress=null;
		try {
			userAddress= locationInfo.getJSONArray("results").getJSONObject(0).getString("formatted_address");
			Log.e("Json",userAddress);
			System.out.println(userAddress);
			Toast.makeText(this, userAddress, Toast.LENGTH_LONG).show();
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			Toast.makeText(this, e1.toString(), Toast.LENGTH_LONG).show();
			e1.printStackTrace();
		}
		}
		else
			gps.showSettingsAlert();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
