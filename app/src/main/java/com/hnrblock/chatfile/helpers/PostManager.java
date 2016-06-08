package com.hnrblock.chatfile.helpers;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map.Entry;
import java.util.Set;

import android.content.ContentValues;

public class PostManager {
	private static String TAG = "SmartPost";

	public static String access_token = "";

	static String crlf = "\r\n";
	static String twoHyphens = "--";
	static String boundary = "*****";

	public static String excutePost(String targetURL, ContentValues cv,
			boolean useAuth) {
		// Log.d(TAG, targetURL);
		URL url;
		String urlParameters = "";
		HttpURLConnection connection = null;

		// ready parameters
		Set<Entry<String, Object>> s = cv.valueSet();
		int count = 0;
		for (Entry<String, Object> entry : s) {
			// Log.d(TAG, entry.getKey() + " = " + entry.getValue());
			if (count != 0) {
				urlParameters += "&";
			}
			urlParameters += entry.getKey() + "=" + entry.getValue();
			count++;
		}
		// Log.d(TAG, urlParameters);
		//

		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");
			if (useAuth && !access_token.equalsIgnoreCase("")) {
				connection.setRequestProperty("Authorization", "Bearer "
						+ access_token);
			}

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			// Log.d(TAG, response.toString());
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			// return "";
			return null;

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public static String excutePost(String targetURL, String params,
			String access_token) {
		// Log.d(TAG, targetURL);
		URL url;
		String urlParameters = params;
		HttpURLConnection connection = null;

		// ready parameters
		// Set<Entry<String, Object>> s = cv.valueSet();
		// int count = 0;
		// for (Entry<String, Object> entry : s) {
		// // Log.d(TAG, entry.getKey() + " = " + entry.getValue());
		// if (count != 0) {
		// urlParameters += "&";
		// }
		// urlParameters += entry.getKey() + "=" + entry.getValue();
		// count++;
		// }
		// Log.d(TAG, urlParameters);
		//

		try {
			// Create connection
			url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
//			connection.setRequestProperty("Content-Type",
//					"multipart/form-data;boundary=" + boundary);
			connection.setRequestProperty("Content-Type",
					"application/json");

			connection.setRequestProperty("Content-Length",
					"" + Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");
			if (!access_token.equalsIgnoreCase("")) {
				connection.setRequestProperty("Authorization", "Bearer "
						+ access_token);
			}

			connection.setUseCaches(false);
			connection.setDoInput(true);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(
					connection.getOutputStream());
			wr.writeBytes(urlParameters);
			//
			// wr.writeBytes(twoHyphens + boundary + crlf);
			// wr.writeBytes("Content-Disposition: form-data; " + urlParameters
			// + "\"" + crlf);
			// wr.writeBytes(crlf);
			//
			wr.flush();
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			// Log.d(TAG, response.toString());
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			// return "";
			return null;

		} finally {

			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	public static String excuteGet(String targetURL) {
		String toReturn = "";
		URL url;
		HttpURLConnection urlConnection = null;
		try {
			url = new URL(targetURL);

			urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.setRequestProperty("Authorization", "Bearer "
					+ access_token);

			InputStream in = urlConnection.getInputStream();

			// ///////////////////
			BufferedReader rd = new BufferedReader(new InputStreamReader(in));
			String line;
			StringBuffer response = new StringBuffer();
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			// Log.d(TAG, response.toString());
			toReturn = response.toString();
			// ///////////////////

			// InputStreamReader isw = new InputStreamReader(in);
			//
			// int data = isw.read();
			// while (data != -1) {
			// char current = (char) data;
			// data = isw.read();
			// System.out.print(current);
			// }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (urlConnection != null) {
				urlConnection.disconnect();
			}
		}
		return toReturn;
	}
}