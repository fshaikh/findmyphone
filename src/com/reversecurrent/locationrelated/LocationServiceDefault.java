package com.reversecurrent.locationrelated;


import com.reversecurrent.findmyphone.SPConstants;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

public class LocationServiceDefault extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate(){
		
	}
	
	@Override
	public int onStartCommand(Intent intent,int flags, int startId)
	{
		try
		{
			LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
			if(lm != null)
			{
				Location location = lm.getLastKnownLocation("gps");
				if(location != null)
				{
					//Toast.makeText(getApplicationContext(), "Location fpund", Toast.LENGTH_LONG).show();
					LocationModel model = intent.getParcelableExtra("LocationModel");
					model.Latitude = location.getLatitude();
					model.Longitude = location.getLongitude();
					SendLocalBroadcast(intent);
				}
			}
		}
		catch(Exception exObj)
		{
			Toast.makeText(getApplicationContext(), exObj.getMessage(), Toast.LENGTH_LONG).show();
		}
		return START_STICKY;
	}
	
	

	@Override
	public void onDestroy(){
		
	}
	
	public void SendLocalBroadcast(Intent intent) {
		Intent doneintent = new Intent("locationDone");
		doneintent.putExtra(SPConstants.LOCATIONINTENT_KEY, intent.getParcelableExtra(SPConstants.LOCATIONINTENT_KEY));
		LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(doneintent);
		
	}

}
