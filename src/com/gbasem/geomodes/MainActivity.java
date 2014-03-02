package com.gbasem.geomodes;

import com.gbasem.geomodes.GPSTracker;



import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;
import android.util.JsonReader;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;
import android.location.Address;
import android.location.Geocoder;
import android.media.AudioManager;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
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
		
		try {
			//String userAdd=address.getJSONArray("results").getJSONObject(0).getString("formatted_address");
			Toast.makeText(this, "HeLLO"+lat, Toast.LENGTH_LONG).show();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		Geocoder geocoder = new Geocoder(this, Locale.getDefault());
	    List<Address> addresses;
		try {
			addresses = geocoder.getFromLocation(lat, longi, 1);
			String x=addresses.get(0).getAddressLine(0).toString().toLowerCase();
			
			if(x.contains("main")|| x.contains("school") || x.contains("college") || x.contains("office")|| x.contains("insti") || x.contains("tech") || x.contains("eng") )
			{
				final AudioManager mobilemode = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
				mobilemode.setRingerMode(AudioManager.RINGER_MODE_SILENT);
			}
			else if(x.contains("main")|| x.contains("hospital") || x.contains("office") || x.contains("limit")  || x.contains("services"))
			{final AudioManager mobilemode = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
				
				mobilemode.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
			}
			else
			{
				final AudioManager mobilemode = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
				mobilemode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
			}
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	public void onReceive(Context context, Intent intent) {
		
		Log.i("cs.fsu", "smsReceiver: SMS Received");
		
		Bundle bundle = intent.getExtras();
        	if (bundle != null) {
        		Log.i("cs.fsu", "smsReceiver : Reading Bundle");
        		
        		Object[] pdus = (Object[])bundle.get("pdus");
        		SmsMessage sms = SmsMessage.createFromPdu((byte[])pdus[0]);
        		
        		if(sms.getMessageBody().contains("FLAG")){
        			
        				this.onResume();
        			}
        			
        			else
        			{
        				Log.i("hello","HELLO");
        			}
        }
	}
	public void showAdd()
	{
		Intent inti=new Intent(this,AddActivity.class);
		startActivity(inti);
	}
	public void onResume(){
		Log.i("cs.fsu", "smsActivity : onResume");
		super.onResume();
		setContentView(R.layout.activity_main);

		Intent intent = getIntent();
		Bundle bundle = intent.getBundleExtra("mySMS");

		if (bundle != null) {
			Object[] pdus = (Object[])bundle.get("pdus");
			SmsMessage sms = SmsMessage.createFromPdu((byte[])pdus[0]);
			Log.i("cs.fsu", "smsActivity : SMS is <" +  sms.getMessageBody() +">");
			
			//strip flag
			String message = sms.getMessageBody();
			while (message.contains("FLAG"))
				message = message.replace("FLAG", "");
			final AudioManager mobilemode = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
			mobilemode.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
			//TextView tx = (TextView) findViewById(R.id.textMsg);
			//tx.setText(message);			
		} else
			Log.i("cs.fsu", "smsActivity : NULL SMS bundle");
	}

}
