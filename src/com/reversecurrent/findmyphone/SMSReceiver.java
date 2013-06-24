package com.reversecurrent.findmyphone;

import com.reversecurrent.locationrelated.LocationHelper;
import com.reversecurrent.locationrelated.LocationModel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent)
	{
		//Toast.makeText(context, "in onReceive", Toast.LENGTH_LONG).show();
		SettingsViewModel viewModel = new SettingsViewModel();
		if(!LogicCore.PreDoLogic(context,Mode.SMS,viewModel))
			return;
			Bundle bundle = intent.getExtras();
			if(bundle != null)
			{
				Object[] pdus = (Object[])bundle.get("pdus");
				for(int i=0;i<pdus.length;i++)
				{
					SmsMessage message = SmsMessage.createFromPdu((byte[])pdus[i]);
					String sender = message.getOriginatingAddress();
					String body = message.getMessageBody();
					Toast.makeText(context, sender + " sent " + body, Toast.LENGTH_LONG).show();
					
					
					Toast.makeText(context, "setNumber : " + viewModel.SenderNumber, Toast.LENGTH_LONG).show();
					
					if(android.telephony.PhoneNumberUtils.compare(sender, viewModel.SenderNumber))
					{
						if(body.compareTo(viewModel.Pin) == 0)
						{
							Toast.makeText(context, sender + " sent " + body, Toast.LENGTH_LONG).show();
							LogicCore.DoLogic(context);
							// get the phone's location and send sms to the same number
							LocationModel model = new LocationModel();
							model.SenderNumber = sender;
							LocationHelper.StartOp(context, model);
						}
					}
				}
			}
	}

}
