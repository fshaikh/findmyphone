package com.reversecurrent.findmyphone;

import com.furqan.findmyphone.R;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;


public class FindMyPhoneActivity extends Activity {
	
	private SettingsViewModel _viewModel = new SettingsViewModel();
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // hook up the event handlers for the View
        // Pick contact event handler
        HookUpSenderPick();
        // save event handler
        HookUpSaveEvent();
        // cancel event handler
        // mode selection event handler
        HookUpTriggerModeChangeEvent();
        
        Initialize();
    }
    
   
    
    private void Initialize() {
   	
    	_viewModel = GetModel();
    	if(null == GetModel() || GetModel().IsModelEmpty)
    	{
    		// sharedprefs does not exist. show the UI as it is
    		return;
    	}
    	// fill up the UI with the model values
    	PopulateView();
	}



	private void HookUpTriggerModeChangeEvent() {
		// hookup checked change event for SMS
    	RadioButton smsRadioBtn = (RadioButton)findViewById(R.id._smsRadio);
		smsRadioBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {	
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				SetPinEditText(arg1);
				
			}
		});
		// hookup checked change event for missed call
		RadioButton callRadioBtn = (RadioButton)findViewById(R.id._missedCallRadio);
		callRadioBtn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				SetPinEditText(!isChecked);
			}
		});
	}
    
    private void SetPinEditText(Boolean enable)
    {
    	EditText pinEditText = (EditText)findViewById(R.id._pinEditText);
    	if(enable)
    	{
    		pinEditText.setEnabled(true);
    	}
    	else
    	{
    		pinEditText.setEnabled(false);
    	}
    }

	/** Called when the activity is created and menu is to be provisioned */
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	
    	
    	
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.optionsmenu, menu);
    	return true;
    }
	
    @Override
	public boolean onOptionsItemSelected(MenuItem menuItem)
	{
    	
		return true;
	}


	public void onStart()
    {
    	// get the ui values from persistent storage
    	// check if the model is empty
    	// if model available, fill the UI with the model
    	super.onStart();
    	//Toast.makeText(this, "on start", Toast.LENGTH_LONG).show();
    	
    	
    }
	
	public void onResume()
	{
		super.onResume();
		//Toast.makeText(this, "on resume", Toast.LENGTH_LONG).show();
	}
    
    private void PopulateView() {
		// TODO Auto-generated method stub
		EditText senderText = (EditText)findViewById(R.id._senderEditText);
		senderText.setText(_viewModel.SenderNumber);
		
		EditText pinText = (EditText)findViewById(R.id._pinEditText);
		pinText.setText(_viewModel.Pin);
		
		// set the trigger mode
		SetTriggerMode();
		// Set Location Mode
		SetLocationMode();
	
	}
    
    private void SetLocationMode()
    {
    	CheckBox cb = (CheckBox)findViewById(R.id._sendLocationCheckBox);
    	cb.setChecked(_viewModel.SendLocation);
    	
    }

	private SettingsViewModel GetModel() {
		
		try
		{

			// This automatically creates the xml file if it does not exist and then stores in the same. 
			SharedPreferences sharedPrefs =  getSharedPreferences(SPConstants.SHAREDPREFS_NAME, 0);
		
			if(sharedPrefs.contains(SPConstants.ACTIVITYNAME_KEY))
			{
			// Read sender number
				_viewModel.IsModelEmpty = false;
				_viewModel.SenderNumber =  sharedPrefs.getString(SPConstants.SENDERNUMBER_KEY,SPConstants.DEFAULTVALUE_STRING );
				_viewModel.TriggerMode = Mode.values()[sharedPrefs.getInt(SPConstants.TRIGGERMODE_KEY,Mode.SMS.ordinal())];
				_viewModel.Pin = sharedPrefs.getString(SPConstants.PIN_KEY, SPConstants.DEFAULTVALUE_STRING);
				_viewModel.CallingActivity = sharedPrefs.getString(SPConstants.ACTIVITYNAME_KEY, SPConstants.DEFAULTVALUE_STRING);
				_viewModel.SendLocation = sharedPrefs.getBoolean(SPConstants.SENDLOCATION_KEY, false);
				return _viewModel;
			}
			else
			{
				_viewModel.IsModelEmpty = true;
				return _viewModel;
			}

			
		}
		catch(Exception exObj)
		{
			//Toast.makeText((Context) context, exObj.getMessage(), Toast.LENGTH_LONG).show();
			return null;
		}
	}
    
    private void HookUpSenderPick()
    {
    	ImageButton senderPickBtn = (ImageButton)findViewById(R.id._senderPickButton);
    	senderPickBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0)
			{
				// need to call inbuilt application with intent. Intent has 4 parts:
				// 1. Name
				// 2. Category
				// 3. Data
				// 4. Type.
				// To invoke an intent which returns a value use callActivityForResult
				Intent contactPickIntent = new Intent(android.content.Intent.ACTION_PICK);
				// Show contacts with phone numbers
				contactPickIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
				startActivityForResult(contactPickIntent, SPConstants.CONTACTPICK_REQUESTCODE);
			}
		});		
	}
    
    // Will be invoked by runtime when called activity returns data
    protected void onActivityResult(int requestCode,int resultCode,Intent data)
    {
    	if(SPConstants.CONTACTPICK_REQUESTCODE == requestCode)
    	{
    		if(RESULT_OK == resultCode)
    		{
    			// pick the contact
    			// data returned is of the format: 
    			// content://com.android.contacts/data/2
    			//Toast.makeText(this, contactSelected, Toast.LENGTH_LONG).show();
    			String[] projections = new String[]{Contacts._ID,Contacts.DISPLAY_NAME,Phone.NUMBER};
    			Cursor c = this.getContentResolver().query(data.getData()	, projections, null, null, null);
    			if(c.moveToFirst())
    			{
    				// get the contact id.
    				//contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
    				String number = c.getString(2);
    				// remove dashes , if any
    				number = number.replace("-", "");
    				number = number.replace("(", "");
    				number = number.replace(")","");
    				_viewModel.SenderNumber = number;
    				SetSenderEditText(number);				
    			}
    			c.close();
    		}
    	}
    	
    }
    
    private void SetSenderEditText(String number)
    {
		EditText senderEditText = (EditText)findViewById(R.id._senderEditText);
		senderEditText.setText(number);
	}

	private void HookUpSaveEvent() 
    {
		
    	
    	Button saveBtn = (Button)findViewById(R.id._saveButton);
    	saveBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// validation.
		    	_viewModel.CallingActivity = SPConstants.ACTIVITYNAME;
		    	_viewModel.IsModelEmpty = false;
		    	EditText pinText = (EditText)findViewById(R.id._pinEditText);
		    	_viewModel.Pin = pinText.getText().toString();
		    	_viewModel.TriggerMode = GetTriggerMode();
				_viewModel.SendLocation = GetSendLocationOption();
		    	Save();
		    	
		    	// close the activity
		    	finish();
			}
		});
 	}

    public boolean Save() 
	{
    	SharedPreferences prefs = getSharedPreferences(SPConstants.SHAREDPREFS_NAME, 0);
    	Editor editor = prefs.edit();
    	
    	editor.putString(SPConstants.ACTIVITYNAME_KEY, _viewModel.CallingActivity);
    	editor.putString(SPConstants.PIN_KEY, _viewModel.Pin);
    	editor.putString(SPConstants.SENDERNUMBER_KEY, _viewModel.SenderNumber);
    	editor.putInt(SPConstants.TRIGGERMODE_KEY, _viewModel.TriggerMode.ordinal());
    	editor.putBoolean(SPConstants.SENDLOCATION_KEY, _viewModel.SendLocation);
    	editor.commit();
		return true;
	}
    
    private boolean GetSendLocationOption()
    {
    	CheckBox cb = (CheckBox)findViewById(R.id._sendLocationCheckBox);
    	return cb.isChecked();
    }

	private Mode GetTriggerMode() {
		RadioButton smsRadioBtn = (RadioButton)findViewById(R.id._smsRadio);
		if(smsRadioBtn.isChecked())
		{
			return Mode.SMS;
		}
		RadioButton missedCallRadioBtn = (RadioButton)findViewById(R.id._missedCallRadio);
		if(missedCallRadioBtn.isChecked())
		{
			return Mode.MissedCall;
		}
		return Mode.Both;
	}
	
	private void SetTriggerMode()
	{
		if(_viewModel.TriggerMode == Mode.SMS)
		{
			RadioButton smsRadioBtn = (RadioButton)findViewById(R.id._smsRadio);
			smsRadioBtn.setChecked(true);
			SetPinEditText(true);
		}
		else if(_viewModel.TriggerMode == Mode.MissedCall)
		{
			RadioButton misseCallRadioBtn = (RadioButton)findViewById(R.id._missedCallRadio);
			misseCallRadioBtn.setChecked(true);
			SetPinEditText(false);
		}
	}
}