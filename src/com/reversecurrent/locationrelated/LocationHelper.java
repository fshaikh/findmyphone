package com.reversecurrent.locationrelated;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.reversecurrent.findmyphone.LogicCore;
import com.reversecurrent.findmyphone.SPConstants;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;


public class LocationHelper {
	public static void RegisterLocationReceiver(Context context)
	{
		LocalBroadcastManager.getInstance(context)
		.registerReceiver(new LocationBroadcastReceiver(),
						  new IntentFilter("locationDone"));
	}
	
	public static void StartService(Context context,LocationModel model,boolean playService)
	{
		Intent intent = GetServiceIntent(context, playService);
		intent.putExtra(SPConstants.LOCATIONINTENT_KEY, model);
		context.startService(intent);
	}
	
	public static void StopService(Context context,boolean playService)
	{
		Intent intent = GetServiceIntent(context, playService);
		context.stopService(intent);
	}
	
	public static boolean IsPlayServicesAvailable(Context context)
	{
		int code = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
		if(ConnectionResult.SUCCESS == code)
		{
			return true;
		}
		return false;
	}
	
	
	
	public static void StartOp(Context context,LocationModel model)
	{
		boolean play = IsPlayServicesAvailable(context);
		if(play)
		{
			if(LogicCore.IsSendByLocationEnabled(context))
			{
				if(LogicCore.IsDestinationSMSAddressable(model.SenderNumber))
				{
					LocationHelper.RegisterLocationReceiver(context);
					LocationHelper.StartService(context, model,true);
				}
			}
		}
		else
		{
			Toast.makeText(context, "Google play services not available", Toast.LENGTH_LONG).show();
		}
	}
	
	private static Intent GetServiceIntent(Context context,boolean playService)
	{
		Intent intent = null;
		if(playService)
		{
			intent = new Intent(context,LocationService.class);
		}
		else
		{
			intent = new Intent(context,LocationServiceDefault.class);
		}
		return intent;
	}
}
