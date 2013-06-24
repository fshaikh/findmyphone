package com.reversecurrent.findmyphone;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class SharedPreferencesStorage implements IPersistentStorage {

	private final String SHAREDPREFS_NAME = "FindMyPhonePrefs";
	private final String SENDERNUMBER_KEY = "SenderNumber";
	private final String TRIGGERMODE_KEY = "TriggerModeKey";
	private final String PIN_KEY = "PinKey";
	private final String ACTIVITYNAME_KEY = "ActivityNameKey";
	private final String DEFAULTVALUE_STRING = "";
	
	@Override
	public SettingsViewModel GetModel(Context context)
	{
		SettingsViewModel model = new SettingsViewModel();
		try
		{

			// This automatically creates the xml file if it does not exist and then stores in the same. 
			SharedPreferences sharedPrefs =  context.getSharedPreferences(SHAREDPREFS_NAME, 0);
		
			if(sharedPrefs.contains(ACTIVITYNAME_KEY))
			{
			// Read sender number
				model.IsModelEmpty = false;
				model.SenderNumber =  sharedPrefs.getString(SENDERNUMBER_KEY,DEFAULTVALUE_STRING );
				model.TriggerMode = Mode.values()[sharedPrefs.getInt(TRIGGERMODE_KEY,Mode.SMS.ordinal())];
				model.Pin = sharedPrefs.getString(PIN_KEY, DEFAULTVALUE_STRING);
				model.CallingActivity = sharedPrefs.getString(ACTIVITYNAME_KEY, DEFAULTVALUE_STRING);
				model.SendLocation = sharedPrefs.getBoolean(SPConstants.SENDLOCATION_KEY, false);
				return model;
			}
			else
			{
				model.IsModelEmpty = true;
				return model;
			}

			
		}
		catch(Exception exObj)
		{
			Toast.makeText((Context) context, exObj.getMessage(), Toast.LENGTH_LONG).show();
			return null;
		}
	}

	@Override
	public boolean Save(Context context, SettingsViewModel model) 
	{
		return false;
	}

	@Override
	public SettingsViewModel GetModelActivity(Activity activity) {
		SettingsViewModel model = new SettingsViewModel();
		try
		{

			// This automatically creates the xml file if it does not exist and then stores in the same. 
			SharedPreferences sharedPrefs =  activity.getSharedPreferences(SHAREDPREFS_NAME, 0);
		
			if(sharedPrefs.contains(ACTIVITYNAME_KEY))
			{
			// Read sender number
				model.IsModelEmpty = false;
				model.SenderNumber =  sharedPrefs.getString(SENDERNUMBER_KEY,DEFAULTVALUE_STRING );
				model.TriggerMode = Mode.values()[sharedPrefs.getInt(TRIGGERMODE_KEY,Mode.SMS.ordinal())];
				model.Pin = sharedPrefs.getString(PIN_KEY, DEFAULTVALUE_STRING);
				model.CallingActivity = sharedPrefs.getString(ACTIVITYNAME_KEY, DEFAULTVALUE_STRING);
				return model;
			}
			else
			{
				model.IsModelEmpty = true;
				return model;
			}

			
		}
		catch(Exception exObj)
		{
			//Toast.makeText((Context) context, exObj.getMessage(), Toast.LENGTH_LONG).show();
			return null;
		}
	}

}
