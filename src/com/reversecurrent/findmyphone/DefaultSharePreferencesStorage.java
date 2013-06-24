//package com.reversecurrent.findmyphone;
//import android.app.Activity;
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.preference.PreferenceManager;
//import android.widget.Toast;
//
//public class DefaultSharePreferencesStorage implements IPersistentStorage {
//
//
//	
//
//	private final String SENDERNUMBER_KEY = "SenderNumber";
//	private final String TRIGGERMODE_KEY = "TriggerModeKey";
//	private final String PIN_KEY = "PinKey";
//	private final String SENDLOCATION_KEY = "SendLocationKey";
//	private final String ACTIVITYNAME_KEY = "ActivityNameKey";
//	private final String DEFAULTVALUE_STRING = "";
//
//	
//	@Override
//	public SettingsViewModel GetModel(Context context)
//	{
//		SettingsViewModel model = new SettingsViewModel();
//		try
//		{
//
//			// This automatically creates the xml file if it does not exist and then stores in the same. 
//			SharedPreferences sharedPrefs =  PreferenceManager.getDefaultSharedPreferences(context);
//		
//			//if(sharedPrefs.contains(ACTIVITYNAME_KEY))
//			//{
//				
//			model.IsModelEmpty = false;
//			model.SenderNumber =  sharedPrefs.getString(SENDERNUMBER_KEY,DEFAULTVALUE_STRING );
//			model.TriggerMode = Mode.values()[sharedPrefs.getInt(TRIGGERMODE_KEY,Mode.SMS.ordinal())];
//			model.Pin = sharedPrefs.getString(PIN_KEY, DEFAULTVALUE_STRING);
//			model.CallingActivity = sharedPrefs.getString(ACTIVITYNAME_KEY, DEFAULTVALUE_STRING);
//			model.SendLocation = sharedPrefs.getBoolean(SENDLOCATION_KEY, false);	
//				return model;
//			//}
//			
//
//			
//		}
//		catch(Exception exObj)
//		{
//			Toast.makeText((Context) context, exObj.getMessage(), Toast.LENGTH_LONG).show();
//			return null;
//		}
//	}
//
//
//	@Override
//	public SettingsViewModel GetModelActivity(Activity activity) {
//		// TODO Auto-generated method stub
//		
//		return null;
//	}
//
//	@Override
//	public boolean Save(Context context, SettingsViewModel model) {
//		// TODO Auto-generated method stub
//		return true;
//	}
//
//}
