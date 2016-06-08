package com.hnrblock.chatfile;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hnrblock.chatfile.helpers.UploadQuestionsService;
import com.hnrblock.chatfile.objects.Question;

public class DatabaseHelper extends SQLiteOpenHelper {

	private Context _context;

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 7;

	// Database Name
	private static final String DATABASE_NAME = "hnr-chatfile";

	// Tables
	private static final String TABLE_LOGIN = "login";
	private static final String TABLE_PROFILE = "profile";
	private static final String TABLE_QUESTIONS = "questions";
	private static final String TABLE_STATES = "states";
	// private static final String TABLE_RESTRICTIONS = "restrictions";

	// Login Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_MOBILENUMBER = "mobile_number";
	private static final String KEY_NAME = "name";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_GENDER = "gender";
	private static final String KEY_DOB_DATE = "dob_date";
	private static final String KEY_DOB_MONTH = "dob_month";
	private static final String KEY_DOB_YEAR = "dob_year";
	private static final String KEY_Q_ID = "q_id";
	private static final String KEY_Q_SEQ = "q_seq";
	private static final String KEY_Q_TITLE = "q_title";
	private static final String KEY_Q_SHORT_TITLE = "q_short_title";
	private static final String KEY_Q_OPTIONS = "q_options";
	private static final String KEY_Q_TYPE = "q_type";
	private static final String KEY_Q_I_TYPE = "q_i_type";
	private static final String KEY_Q_MIN = "q_min";
	private static final String KEY_Q_MAX = "q_max";
	private static final String KEY_Q_REGEX = "q_regex";
	private static final String KEY_Q_HELP1 = "q_help1";
	private static final String KEY_Q_HELP2 = "q_help2";
	private static final String KEY_Q_ANS = "q_answer";
	private static final String KEY_Q_STATUS = "q_status";
	private static final String KEY_Q_VALID = "q_valid";
	private static final String KEY_Q_TIMESTAMP = "q_timestamp";
	private static final String KEY_Q_A_TIMESTAMP = "q_a_timestamp";
	// private static final String KEY_R_TYPE = "r_type";
	// private static final String KEY_R_DATA = "r_data";

	// All create tables
	private String CREATE_TABLE_LOGIN = "CREATE TABLE " + TABLE_LOGIN + "("
			+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_EMAIL + " TEXT UNIQUE)";

	private String CREATE_TABLE_PROFILE = "CREATE TABLE " + TABLE_PROFILE + "("
			+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
			+ KEY_MOBILENUMBER + " TEXT," + KEY_GENDER + " TEXT,"
			+ KEY_DOB_DATE + " TEXT," + KEY_DOB_MONTH + " TEXT," + KEY_DOB_YEAR
			+ " TEXT," + KEY_EMAIL + " TEXT)";

	private String CREATE_TABLE_QUESTIONS = "CREATE TABLE " + TABLE_QUESTIONS
			+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_Q_ID + " TEXT,"
			+ KEY_Q_SEQ + " TEXT," + KEY_Q_TITLE + " TEXT," + KEY_Q_SHORT_TITLE
			+ " TEXT," + KEY_Q_OPTIONS + " TEXT," + KEY_Q_TYPE + " INTEGER, "
			+ KEY_Q_I_TYPE + " INTEGER, " + KEY_Q_MIN + " INTEGER, "
			+ KEY_Q_MAX + " INTEGER, " + KEY_Q_REGEX + " TEXT," + KEY_Q_HELP1
			+ " TEXT," + KEY_Q_HELP2 + " TEXT," + KEY_Q_ANS + " TEXT,"
			+ KEY_Q_TIMESTAMP + " TEXT DEFAULT ''," + KEY_Q_A_TIMESTAMP
			+ " TEXT DEFAULT ''," + KEY_Q_STATUS + " INTEGER DEFAULT 0,"
			+ KEY_Q_VALID + " INTEGER DEFAULT 0)";

	private String CREATE_TABLE_STATES = "CREATE TABLE " + TABLE_STATES + "("
			+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT UNIQUE)";

	// private String CREATE_TABLE_RESTRICTIONS = "CREATE TABLE "
	// + TABLE_RESTRICTIONS + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
	// + KEY_Q_ID + " TEXT," + KEY_R_TYPE + " TEXT," + KEY_R_DATA
	// + " TEXT)";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		_context = context;
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		createTables(db);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		dropTables(db);
		createTables(db);
	}

	private void createTables(SQLiteDatabase db) {
		db.execSQL(CREATE_TABLE_LOGIN);
		db.execSQL(CREATE_TABLE_PROFILE);
		db.execSQL(CREATE_TABLE_QUESTIONS);
		db.execSQL(CREATE_TABLE_STATES);
		// db.execSQL(CREATE_TABLE_RESTRICTIONS);
	}

	private void dropTables(SQLiteDatabase db) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFILE);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATES);
		// db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESTRICTIONS);
	}

	public static void clearDB(Context context) {
		context.deleteDatabase(DATABASE_NAME);
	}

	// public int logout(Context context) {
	// SQLiteDatabase db = this.getWritableDatabase();
	// // String selectQuery = "UPDATE " + TABLE_LOGIN + " SET " + KEY_Q_ANS
	// // + "='' AND " + KEY_Q_A_TIMESTAMP + "='' AND ";
	// ContentValues values = new ContentValues();
	// values.put(KEY_Q_ANS, "");
	// values.put(KEY_Q_A_TIMESTAMP, "");
	// values.put(KEY_Q_VALID, 0);
	// values.put(KEY_Q_STATUS, 0);
	//
	// return db.update(TABLE_QUESTIONS, values, null, null);
	// }
	public void logout(Context context) {
		SQLiteDatabase db = this.getWritableDatabase();
		dropTables(db);
		createTables(db);
	}

	/**
	 * Storing user details in database
	 * */
	public void addLogin(String emailId) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_EMAIL, emailId);

		// Inserting Row
		db.insert(TABLE_LOGIN, null, values);
		db.close(); // Closing database connection
	}

	public boolean isLoggedIn() {
		boolean toReturn = false;
		SQLiteDatabase db = this.getWritableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_LOGIN;
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.getCount() > 0) {
			toReturn = true;
		}
		return toReturn;
	}

	public int addProfile(String name, String mobileNumber, String uid,
			String gender, String dob_date, String dob_month, String dob_year) {
		int toReturn = -1;
		SQLiteDatabase db = this.getWritableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_PROFILE;
		Cursor cursor = db.rawQuery(selectQuery, null);

		// Prepare values
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, name);
		values.put(KEY_MOBILENUMBER, mobileNumber);
		values.put(KEY_GENDER, gender);
		values.put(KEY_DOB_DATE, dob_date);
		values.put(KEY_DOB_MONTH, dob_month);
		values.put(KEY_DOB_YEAR, dob_year);
		//

		if (cursor.getCount() > 0) {// update profile
			toReturn = db.update(TABLE_PROFILE, values, null, null);
		} else {// add profile
			db.insert(TABLE_PROFILE, null, values);
		}

		db.close(); // Closing database connection
		return toReturn;
	}

	public int getLastAskedQuestion() {
		SQLiteDatabase db = this.getWritableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_QUESTIONS +"WHERE ";
		Cursor cursor = db.rawQuery(selectQuery, null);
		int count = cursor.getCount();
		return count;
	}

	public int getStatesCount() {
		SQLiteDatabase db = this.getWritableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_STATES;
		Cursor cursor = db.rawQuery(selectQuery, null);
		int count = cursor.getCount();
		return count;
	}

	public void addState(String state) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, state);
		// Inserting Row
		db.insert(TABLE_STATES, null, values);
		db.close(); // Closing database connection
	}

	public ArrayList<String> getStates() {
		ArrayList<String> toReturn = new ArrayList<>();
		SQLiteDatabase db = this.getWritableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_STATES;
		Cursor cursor = db.rawQuery(selectQuery, null);
		int count = cursor.getCount();
		if (count > 0) {
			cursor.moveToFirst();
			do {
				toReturn.add(cursor.getString(cursor.getColumnIndex(KEY_NAME)));
			} while (cursor.moveToNext());
		}
		return toReturn;
	}

	public long addQuestion(Question q) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_Q_ID, q.getId());
		values.put(KEY_Q_TITLE, q.getTitle());
		values.put(KEY_Q_SHORT_TITLE, q.getShortTitle());
		values.put(KEY_Q_TYPE, q.getQType());
		values.put(KEY_Q_OPTIONS, q.getOptions().toString());
		values.put(KEY_Q_I_TYPE, q.getInputType());
		values.put(KEY_Q_MIN, q.getMinLength());
		values.put(KEY_Q_MAX, q.getMaxLength());
		values.put(KEY_Q_REGEX, q.getRegex());
		values.put(KEY_Q_HELP1, q.getHelp1());
		values.put(KEY_Q_HELP2, q.getHelp2());
		values.put(KEY_Q_ANS, "");
		// Inserting Row
		long insertID = db.insert(TABLE_QUESTIONS, null, values);

		// Restrictions
		// for (Restriction restriction : restrictions) {
		// ContentValues cv = new ContentValues();
		// cv.put(KEY_Q_ID, q_id);
		// cv.put(KEY_R_TYPE, restriction.getType());
		// cv.put(KEY_R_DATA, restriction.getData());
		// db.insert(TABLE_RESTRICTIONS, null, cv);
		// }

		db.close(); // Closing database connection
		return insertID;
	}

	public int getQuestionsCount() {
		SQLiteDatabase db = this.getWritableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_QUESTIONS;
		Cursor cursor = db.rawQuery(selectQuery, null);
		int count = cursor.getCount();
		return count;
	}

	public String getQuestionTS(String q_id) {
		String toReturn = "";
		SQLiteDatabase db = this.getWritableDatabase();
		String selectQuery = "SELECT " + KEY_Q_TIMESTAMP + " FROM "
				+ TABLE_QUESTIONS + " WHERE " + KEY_Q_ID + "='" + q_id + "'";
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			toReturn = cursor.getString(cursor.getColumnIndex(KEY_Q_TIMESTAMP));
			if (toReturn.equalsIgnoreCase("")) {
				toReturn = System.currentTimeMillis() + "";
				ContentValues cv = new ContentValues();
				cv.put(KEY_Q_TIMESTAMP, toReturn);
				db.update(TABLE_QUESTIONS, cv, KEY_Q_ID + " = ?",
						new String[] { q_id });
			}
		}
		return toReturn;
	}

	public Question getQuestion(int q_id) {
		Question toReturn = null;
		SQLiteDatabase db = this.getWritableDatabase();
		String selectQuery = "SELECT * FROM " + TABLE_QUESTIONS + " WHERE "
				+ KEY_Q_ID + "='" + q_id + "'";
		// String selectQuery2 = "SELECT * FROM " + TABLE_RESTRICTIONS +
		// " WHERE "
		// + KEY_Q_ID + "='" + q_id + "'";
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			String seq = cursor.getString(cursor.getColumnIndex(KEY_Q_SEQ));
			String title = cursor.getString(cursor.getColumnIndex(KEY_Q_TITLE));
			String shortTitle = cursor.getString(cursor
					.getColumnIndex(KEY_Q_SHORT_TITLE));
			int type = cursor.getInt(cursor.getColumnIndex(KEY_Q_TYPE));
			int inputType = cursor.getInt(cursor.getColumnIndex(KEY_Q_I_TYPE));
			String options = cursor.getString(cursor
					.getColumnIndex(KEY_Q_OPTIONS));
			String answer = cursor.getString(cursor.getColumnIndex(KEY_Q_ANS));
			ArrayList<String> optns = new ArrayList<>(Arrays.asList(options
					.split("\\s*,\\s*")));
			int min = cursor.getInt(cursor.getColumnIndex(KEY_Q_MIN));
			int max = cursor.getInt(cursor.getColumnIndex(KEY_Q_MAX));
			String regex = cursor.getString(cursor.getColumnIndex(KEY_Q_REGEX));
			String help1 = cursor.getString(cursor.getColumnIndex(KEY_Q_HELP1));
			String help2 = cursor.getString(cursor.getColumnIndex(KEY_Q_HELP2));
			int isValid = cursor.getInt(cursor.getColumnIndex(KEY_Q_VALID));
			int status = cursor.getInt(cursor.getColumnIndex(KEY_Q_STATUS));
			String timestamp = cursor.getString(cursor
					.getColumnIndex(KEY_Q_TIMESTAMP));
			if (timestamp.equalsIgnoreCase("")) {
				timestamp = System.currentTimeMillis() + "";
				ContentValues cv = new ContentValues();
				cv.put(KEY_Q_TIMESTAMP, timestamp);
				db.update(TABLE_QUESTIONS, cv, KEY_Q_ID + " = ?",
						new String[] { q_id + "" });
			}
			String answerTimestamp = cursor.getString(cursor
			/*
			 * <<<<<<< HEAD .getColumnIndex(KEY_Q_A_TIMESTAMP)); Question q =
			 * new Question(q_id + "", seq, title, optns, answer, type,
			 * inputType, min, max, regex, help1, help2, isValid, status,
			 * timestamp, answerTimestamp); =======
			 */
			.getColumnIndex(KEY_Q_A_TIMESTAMP));
			Question q = new Question(q_id + "", seq, title, shortTitle, optns,
					answer, type, inputType, min, max, regex, help1, help2,
					isValid, status, timestamp, answerTimestamp);
			// >>>>>>> refs/remotes/origin/master
			// toReturn = new WholeQuestion(q, restrictions);
			toReturn = q;
		}
		return toReturn;
	}

	public int copyAnswer(String from_id, String to_id) {
		int toReturn = -1;
		SQLiteDatabase db = this.getWritableDatabase();

		String selectQuery = "SELECT * FROM " + TABLE_QUESTIONS + " WHERE "
				+ KEY_Q_ID + "='" + from_id + "'";
		Cursor cursor = db.rawQuery(selectQuery, null);
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			ContentValues values = new ContentValues();
			values.put(KEY_Q_ANS,
					cursor.getString(cursor.getColumnIndex(KEY_Q_ANS)));
			values.put(KEY_Q_VALID,
					cursor.getString(cursor.getColumnIndex(KEY_Q_VALID)));
			values.put(KEY_Q_STATUS, 0);

			toReturn = db.update(TABLE_QUESTIONS, values, KEY_Q_ID + " = ?",
					new String[] { to_id });
		}
		return toReturn;
	}

	public int removeAnswer(String q_id) {
		int toReturn = -1;
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_Q_ANS, "");
		values.put(KEY_Q_VALID, 0);
		values.put(KEY_Q_STATUS, 0);

		toReturn = db.update(TABLE_QUESTIONS, values, KEY_Q_ID + " = ?",
				new String[] { q_id });
		return toReturn;
	}

	public int updateAnswer(String q_id, String q_ans, boolean updateTimestamp,
			int status) {
		int toReturn = -1;
		SQLiteDatabase db = this.getWritableDatabase();

		String selectQuery = "SELECT " + KEY_Q_A_TIMESTAMP + " FROM "
				+ TABLE_QUESTIONS + " WHERE " + KEY_Q_ID + "='" + q_id + "'";
		Cursor cursor = db.rawQuery(selectQuery, null);
		String timestamp = "";
		if (cursor.getCount() > 0) {
			cursor.moveToFirst();
			timestamp = cursor.getString(cursor
					.getColumnIndex(KEY_Q_A_TIMESTAMP));
		}

		ContentValues values = new ContentValues();
		values.put(KEY_Q_ANS, q_ans);
		values.put(KEY_Q_VALID, 1);
		if (q_id.equalsIgnoreCase(ChatActivity.QNO_EMAIL_ID + "")
				|| q_id.equalsIgnoreCase(ChatActivity.QNO_OLD_PASS + "")
				|| q_id.equalsIgnoreCase(ChatActivity.QNO_NEW_PASS + "")) {
			values.put(KEY_Q_STATUS, 1);
		} else {
			values.put(KEY_Q_STATUS, status);
		}
		// if (timestamp.equalsIgnoreCase("")) {
		if (updateTimestamp) {
			values.put(KEY_Q_A_TIMESTAMP, System.currentTimeMillis());
		}

		toReturn = db.update(TABLE_QUESTIONS, values, KEY_Q_ID + " = ?",
				new String[] { q_id });
		if (q_id.equalsIgnoreCase("45")
				&& (q_ans.equalsIgnoreCase("zero") || q_ans
						.equalsIgnoreCase("multiple"))) {
			// updateHouseDetails(db);
		}
//		if (status == 0) {
//			checkUploadAnswers(5);
//		}
		return toReturn;
	}

	private void updateHouseDetails(SQLiteDatabase db) {
		ContentValues values = new ContentValues();
		values.put(KEY_Q_ANS, "");
		values.put(KEY_Q_VALID, 0);
		values.put(KEY_Q_STATUS, 0);

		db.update(TABLE_QUESTIONS, values, KEY_Q_ID + " IN(?)", new String[] {
				"46", "47", "48" });
	}

	public int getProgress() {
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "Select * from " + TABLE_QUESTIONS + " where "
				+ KEY_Q_VALID + "=1";
		Cursor cursor = db.rawQuery(query, null);
		return cursor.getCount();
	}

	public void checkUploadAnswers(int q_count) {
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "Select * from " + TABLE_QUESTIONS + " where "
				+ KEY_Q_VALID + "=1 AND " + KEY_Q_STATUS + "=0";
		// AND " + KEY_Q_ID+ "!=46";
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.getCount() > q_count) {
			JSONArray arr = new JSONArray();
			cursor.moveToFirst();
			int counter = 0;
			do {
				counter++;
				if (counter <= 5) {
					JSONObject obj = new JSONObject();
					try {
						obj.put("qno", cursor.getString(cursor
								.getColumnIndex(KEY_Q_ID)));
						String ans = ChatActivity.getShortAnswer(cursor
								.getString(cursor.getColumnIndex(KEY_Q_ANS)),
								cursor.getString(cursor
										.getColumnIndex(KEY_Q_ID)));
						obj.put("answer", ans);
					} catch (JSONException e) {
						e.printStackTrace();
					}
					arr.put(obj);
				}
			} while (cursor.moveToNext());

			try {
				Intent it = new Intent(_context, UploadQuestionsService.class);
				it.putExtra("data", arr.toString());
				_context.startService(it);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void getAnswersForUpload() {
		SQLiteDatabase db = this.getWritableDatabase();
		String query = "Select * from " + TABLE_QUESTIONS + " where "
				+ KEY_Q_VALID + "=1 AND " + KEY_Q_STATUS + "=0";
		Cursor cursor = db.rawQuery(query, null);
		if (cursor.getCount() >= 10) {
			_context.startService(new Intent(_context,
					UploadQuestionsService.class));
		}
	}

	public int updateAnswerStatus(String q_id, int status) {
		int toReturn = -1;
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_Q_STATUS, status);

		toReturn = db.update(TABLE_QUESTIONS, values, KEY_Q_ID + " = ?",
				new String[] { q_id });

		return toReturn;
	}

	/**
	 * Re create database Delete all tables and create them again
	 * */
	public void resetTables() {
		SQLiteDatabase db = this.getWritableDatabase();
		// Delete All Rows
		db.delete(TABLE_LOGIN, null, null);
		db.delete(TABLE_PROFILE, null, null);
		db.close();
	}

}
