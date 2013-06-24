package com.reversecurrent.locationrelated;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationRequest;
import com.reversecurrent.findmyphone.SPConstants;



public class LocationService
			extends Service
			implements ConnectionCallbacks, OnConnectionFailedListener {

	private LocationClient _locationClient = null;
	private boolean _isLocationInit = false;
	private Intent _locationIntent = null;
	
	/*  Service method overrides  */
	@Override
	public void onCreate(){
		_locationClient = new LocationClient(getApplicationContext(), this, this);
		_locationClient.connect();
	}
	
	@Override
	public int onStartCommand(Intent intent,int flags, int startId)
	{
		//if(_isLocationInit)
		//{
			_locationIntent = intent;
		//}
		return START_STICKY;
	}
	
	

	@Override
	public void onDestroy(){
		if(_isLocationInit)
		{
			_locationClient.disconnect();
		}
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	/************************************************/
	
	/* ConnectionCallbacks method overrides*/
	@Override
	public void onConnected(Bundle arg0) {
		_isLocationInit = true;
		
		LocationRequest request = LocationRequest.create();
		request.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
		
		Location location = _locationClient.getLastLocation();
		if(location != null)
		{
			Toast.makeText(getApplicationContext(), String.valueOf(location.getLongitude()), Toast.LENGTH_LONG).show();
			
			LocationModel model = _locationIntent.getParcelableExtra("LocationModel");
			model.Latitude = location.getLatitude();
			model.Longitude = location.getLongitude();
			SendLocalBroadcast(_locationIntent);
		}
		else
		{
			Toast.makeText(getApplicationContext(), "Unable to get location details", Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		_isLocationInit = false;
	}
	
	private void SendLocalBroadcast(Intent intent) {
		Intent doneintent = new Intent("locationDone");
		doneintent.putExtra(SPConstants.LOCATIONINTENT_KEY, intent.getParcelableExtra(SPConstants.LOCATIONINTENT_KEY));
		LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(doneintent);
	}
}
