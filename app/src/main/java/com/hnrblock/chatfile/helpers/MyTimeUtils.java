package com.hnrblock.chatfile.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.text.format.DateFormat;

public class MyTimeUtils {

	public static String getTime(String milliseconds) {
		// Timestamp ts = Timestamp.valueOf(milliseconds);
		String strToReturn = "";
		// long time = ts.getTime();// Long.parseLong(milliseconds);
		try {
			long time = Long.parseLong(milliseconds);
			if (Math.abs(time - System.currentTimeMillis()) > 86400000 * 2) {
				Calendar cal = Calendar.getInstance(Locale.ENGLISH);
				cal.setTimeInMillis(time);
				String date = DateFormat.format("dd-MM-yyyy", cal).toString();
				strToReturn = date;
			} else if (Math.abs(time - System.currentTimeMillis()) > 86400000) {
				strToReturn = "Yesterday";
			} else {
				Calendar cal = Calendar.getInstance(Locale.ENGLISH);
				cal.setTimeInMillis(time);
				String date = DateFormat.format("hh:mm aa", cal).toString()
						.toLowerCase();
				strToReturn = date;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strToReturn;
	}

	public static String getRemainingTime(String milliseconds) {
		String strToReturn = "";
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = formatter.parse(milliseconds);// catch exception
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Calendar thatDay = Calendar.getInstance();
		thatDay.setTime(d); // rest is the same....

		long timeEnd = thatDay.getTimeInMillis() + 86400000;// Long.parseLong(milliseconds);
		if (Math.abs(timeEnd - System.currentTimeMillis()) > 86400000 * 2) {
			String date = "ends in "
					+ ((timeEnd - System.currentTimeMillis()) / ((24 * 60 * 60 * 1000)))
					+ " days";
			strToReturn = date;
		} else if (Math.abs(timeEnd - System.currentTimeMillis()) > 86400000) {
			strToReturn = "ends tomorrow";
		} else {
			strToReturn = "ends in "
					+ ((timeEnd - System.currentTimeMillis()) / ((60 * 60 * 1000)))
					+ " hrs";
		}
		return strToReturn;
	}

}