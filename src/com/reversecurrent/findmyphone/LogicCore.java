package com.reversecurrent.findmyphone;

import android.content.Context;
import android.media.AudioManager;


public class LogicCore {
	
	public static boolean PreDoLogic(Context context,Mode triggerMode,SettingsViewModel modelOut)
	{
		// first check the mode.
		SettingsViewModel viewModel = (new SharedPreferencesStorage()).GetModel(context);
		if(viewModel == null || viewModel.IsModelEmpty)
				return false;
		if(!(viewModel.TriggerMode == triggerMode || viewModel.TriggerMode == Mode.Both))
			return false;
		modelOut.CallingActivity = viewModel.CallingActivity;
		modelOut.IsModelEmpty = false;
		modelOut.IsRunning = true;
		modelOut.Pin = viewModel.Pin;
		modelOut.SenderNumber = viewModel.SenderNumber;
		modelOut.TriggerMode = viewModel.TriggerMode;
		
		return true;
	}
	public static void DoLogic(Context context)
	{
		//Toast.makeText(context, "In dologic", Toast.LENGTH_LONG).show();
		// change the phone from silent to general mode.increase the volume to max.
		AudioManager audioMgr = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
		audioMgr.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		// set the ringer volume to max
		// first get the highest volume value for the ringer stream
		int ringMaxVolume = audioMgr.getStreamMaxVolume(AudioManager.STREAM_RING);
		//Toast.makeText(context, Integer.toString(ringMaxVolume), Toast.LENGTH_LONG).show();
		audioMgr.setStreamVolume(AudioManager.STREAM_RING, ringMaxVolume, AudioManager.FLAG_ALLOW_RINGER_MODES| AudioManager.FLAG_PLAY_SOUND);
	}
	
	public static boolean IsSendByLocationEnabled(Context context)
	{
		SettingsViewModel viewModel = (new SharedPreferencesStorage()).GetModel(context);
		if(viewModel == null || viewModel.IsModelEmpty)
				return false;
		return viewModel.SendLocation;
	}
	
	public static boolean IsDestinationSMSAddressable(String address)
	{
		if(android.telephony.PhoneNumberUtils.isWellFormedSmsAddress(address))
			return true;
		return false;
	}
}
