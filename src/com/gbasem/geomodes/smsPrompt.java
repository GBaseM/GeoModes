package com.gbasem.geomodes;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.TextView;

public class smsPrompt extends Activity {

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

