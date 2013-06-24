package com.reversecurrent.findmyphone;


import com.reversecurrent.locationrelated.LocationHelper;
import com.reversecurrent.locationrelated.LocationModel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;



public class MissedCallReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context	, Intent intent) {
		// TODO Auto-generated method stub
		/*TelephonyManager mgr = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		int callState = mgr.getCallState();*/
		
		SettingsViewModel viewModel = new SettingsViewModel();
		if(!LogicCore.PreDoLogic(context,Mode.MissedCall,viewModel))
			return;
		Bundle bundle = intent.getExtras();
		if(bundle != null)
		{
			String callState = bundle.getString(TelephonyManager.EXTRA_STATE);
			
			
			if(callState.compareTo(TelephonyManager.EXTRA_STATE_RINGING) == 0)
			{
				String number = bundle.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
		
				if(android.telephony.PhoneNumberUtils.compare(number, viewModel.SenderNumber))
				{
					LogicCore.DoLogic(context);
					// get the phone's location and send sms to the same number
					LocationModel model = new LocationModel();
					model.SenderNumber = number;
					LocationHelper.StartOp(context, model);
					
					
				}
			}
		}
		
		// callstate:
		// 0 - hungup
		// 1- incomig call
		// 2- answered, outgoing call
	
	
		
		// can come here for incoming call, answered,hangup
		// Is incoming call
		// number is as configured
		// do the core logic
	}

}
