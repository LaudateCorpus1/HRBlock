package com.hnrblock.chatfile.helpers;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class PrefsManager {
	public static String UPLOADED_FIRST_OFFER_RECEIPT = "uploaded_first_offer_receipt";
	public static String LAST_DATE_UPDATE_SHOWN = "last_date_update_shown";
	public static String LOGIN_SKIPPED = "login_skipped";
	public static String DEVICE_ID = "device_id";

	public static String getPrefs(Context context, String key, String defValue) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sharedPreferences.getString(key, defValue);
	}

	public static int getPrefs(Context context, String key, int defValue) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sharedPreferences.getInt(key, defValue);
	}

	public static boolean getPrefs(Context context, String key, boolean defValue) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sharedPreferences.getBoolean(key, defValue);
	}

	public static long getPrefs(Context context, String key, long defValue) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		return sharedPreferences.getLong(key, defValue);
	}

	public static void savePrefs(Context context, String key, String value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static void savePrefs(Context context, String key, int value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();
	}

	public static void savePrefs(Context context, String key, boolean value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public static void savePrefs(Context context, String key, long value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.putLong(key, value);
		editor.commit();
	}
	
	public static void clearPrefs(Activity activity, Context context) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(context);
		Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.commit();
	}

	// public static void clearPrefs(Activity activity, Context context) {
	// SharedPreferences sharedPreferences = PreferenceManager
	// .getDefaultSharedPreferences(context);
	// Editor editor = sharedPreferences.edit();
	// editor.clear();
	// editor.commit();
	// DatabaseHandler.clearDB(context);
	// Intent splashIntent = new Intent(context, SplashActivity.class);
	// splashIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
	// activity.startActivity(splashIntent);
	// activity.finish();
	// }
}
