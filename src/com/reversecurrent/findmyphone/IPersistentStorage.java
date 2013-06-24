package com.reversecurrent.findmyphone;

import android.app.Activity;
import android.content.Context;

public interface IPersistentStorage {
	SettingsViewModel GetModel(Context context);
	SettingsViewModel GetModelActivity(Activity activity);
	boolean Save(Context context, SettingsViewModel model);
}
