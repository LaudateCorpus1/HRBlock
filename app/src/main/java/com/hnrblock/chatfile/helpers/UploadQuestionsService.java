package com.hnrblock.chatfile.helpers;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hnrblock.chatfile.ChatActivity;
import com.hnrblock.chatfile.DatabaseHelper;
import com.hnrblock.chatfile.objects.Constants;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.widget.Toast;

public class UploadQuestionsService extends Service {
	String q_data, token;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Let it continue running until it is stopped.
		// Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
		// stopSelf();
		token = PrefsManager.getPrefs(getApplicationContext(), "access_token",
				"");
		if (token.equalsIgnoreCase("")) {
			getToken();
		} else {
			q_data = intent.getStringExtra("data");
			submitAnswers();
		}
		return START_STICKY;
	}

	private void getToken() {
		DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
		ContentValues cv = new ContentValues();
		cv.put("email_id", helper.getQuestion(ChatActivity.QNO_EMAIL_ID)
				.getAnswer());
		cv.put("password", helper.getQuestion(ChatActivity.QNO_NEW_PASS)
				.getAnswer());
		if (cv.get("password").toString().equalsIgnoreCase("")) {
			cv.put("password", helper.getQuestion(ChatActivity.QNO_OLD_PASS)
					.getAnswer());
		}
		// cv.put("password", answer);
		new GetToken().execute(cv);
	}

	private void submitAnswers() {
		try {
			ContentValues cv = new ContentValues();
			cv.put("questions", q_data);
			new UploadNow().execute(q_data);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
	}

	public class UploadNow extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String url = Constants.SAVE_QUESTIONS;

			// ContentValues cv = cvs[0];
			// cv.put(Constants.HCUID, Constants.HCUID_VAL);
			// cv.put(Constants.HCPASS, Constants.HCPASS_VAL);
			String result = PostManager.excutePost(url, params[0], token);
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
//			Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG)
//					.show();
			if (result != null)
				result = result.replace("\"", "").trim();
			if (result != null && result.equalsIgnoreCase("Saved successfully")) {
				try {
					JSONArray arr = new JSONArray(q_data);
					for (int i = 0; i < arr.length(); i++) {
						JSONObject q = arr.getJSONObject(i);
						DatabaseHelper helper = new DatabaseHelper(
								getApplicationContext());
						helper.updateAnswerStatus(q.getString("qno"), 1);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				// Toast.makeText(getApplicationContext(), "Error saving data",
				// Toast.LENGTH_LONG).show();
			}
			stopSelf();
		}
	}

	public class GetToken extends AsyncTask<ContentValues, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(ContentValues... cvs) {
			String toReturn = "0";
			String url = Constants.AUTH_TOKEN;
			ContentValues cv = cvs[0];
			cv.put("grant_type", "password");
			cv.put(Constants.HCUID, Constants.HCUID_VAL);
			cv.put(Constants.HCPASS, Constants.HCPASS_VAL);
			String result = PostManager.excutePost(url, cv, false);
			if (result != null) {
				try {
					JSONObject resp = new JSONObject(result);
					PrefsManager.savePrefs(getApplicationContext(),
							Constants.TOKEN, resp.getString(Constants.TOKEN));
					PostManager.access_token = resp.getString(Constants.TOKEN);
					toReturn = "1";
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			return toReturn;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			if (result.equalsIgnoreCase("1")) {
				submitAnswers();
			} else {
				Toast.makeText(getApplicationContext(), "Token error",
						Toast.LENGTH_LONG).show();
				stopSelf();
			}
		}
	}
}
