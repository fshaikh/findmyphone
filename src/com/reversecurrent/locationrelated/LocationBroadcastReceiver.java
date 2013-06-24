package com.reversecurrent.locationrelated;

import com.reversecurrent.findmyphone.SPConstants;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;


public class LocationBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent)
	{
		// read the location stuff
		LocationModel model = intent.getParcelableExtra(SPConstants.LOCATIONINTENT_KEY);
		// construct the message
		StringBuilder builder = new StringBuilder();
		builder.append(SPConstants.GOOGLEMAPS_URL);
		// http://maps.google.com/maps?q=loc:51.03841,-114.01679S
		builder.append(String.valueOf(model.Latitude));
		builder.append(',');
		builder.append(String.valueOf(model.Longitude));
		//Toast.makeText(context, builder.toString(), Toast.LENGTH_LONG).show();
		// send the SMS
		SmsManager smsMgr = SmsManager.getDefault();
		smsMgr.sendTextMessage(model.SenderNumber,
			null, builder.toString(), null, null);
		
		LocationHelper.StopService(context,false);
	}
}
