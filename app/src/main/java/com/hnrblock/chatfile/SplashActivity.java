package com.hnrblock.chatfile;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hnrblock.chatfile.helpers.PostManager;
import com.hnrblock.chatfile.helpers.PrefsManager;
import com.hnrblock.chatfile.objects.AnswerInputTypes;
import com.hnrblock.chatfile.objects.Constants;
import com.hnrblock.chatfile.objects.Question;
import com.hnrblock.chatfile.objects.Restriction;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.widget.Toast;

public class SplashActivity extends Activity {
    public static DisplayImageOptions options;
    DatabaseHelper helper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        initImageConfiguration(this);

        addQues();
        if (helper.getStatesCount() <= 0) {
            new GetStates().execute();
        }
    }

    private void addQues() {
        long c = helper.getQuestionsCount();
        if (c <= 0) {
            new AddQuestions().execute();
        } else {
            checkLogin();
        }
        // new CheckAPI().execute();
    }

    private void checkLogin() {
        startChatActivity();
        // if (helper.isLoggedIn()) {
        // startChatActivity();
        // } else {
        // startLoginActivity();
        // }
    }

    private void startChatActivity() {
        Intent loginIntent = new Intent(this, ChatActivity.class);
        startActivity(loginIntent);
        this.finish();
    }

    private void startLoginActivity() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivity(loginIntent);
        this.finish();
    }

    public static void initImageConfiguration(Context context) {
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .build();
        ImageLoader.getInstance().init(config);
    }

    private void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(SplashActivity.this, text, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public class AddQuestions extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String toReturn = "0";
            long c = helper.getQuestionsCount();
            if (c <= 0) {
                try {
                    ContentValues cv1 = new ContentValues();
                    cv1.put(Constants.HCUID, Constants.HCUID_VAL);
                    cv1.put(Constants.HCPASS, Constants.HCPASS_VAL);
                    String postResp = PostManager.excutePost(
                            Constants.QUESTIONS, cv1, false);
                    if (postResp != null) {
                        JSONArray arr = new JSONArray(postResp);
//                        showToast(arr.length() + "");
                        PrefsManager.savePrefs(getApplicationContext(),
                                Constants.Q_COUNT, arr.length());
                        for (int i = 0; i < arr.length(); i++) {
                            JSONObject que = arr.getJSONObject(i);
                            String id = que.getString("questionNo");
                            String seq = que.getString("sequenceNo");
                            String title = que.getString("question");
                            String shortTitle = "Missing";
                            try {
                                shortTitle = que.getString("fieldName");
                                if (shortTitle == null) {
                                    shortTitle = "Missing";
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            int type = que.getString("questionType")
                                    .equalsIgnoreCase("") ? 0 : 1;
//                            if (type == 1) {
//                                PrefsManager.savePrefs(getApplicationContext(),
//                                        Constants.Q_COUNT,
//                                        PrefsManager.getPrefs(
//                                                getApplicationContext(),
//                                                Constants.Q_COUNT, 0) + 1);
//                            }
                            String in_type = que.getString("inputType");
                            int input_type = 0;
                            if (in_type
                                    .equalsIgnoreCase(AnswerInputTypes.DROPDOWN_STRING2)) {
                                input_type = AnswerInputTypes.DROP_DOWN;
                            } else if (in_type
                                    .equalsIgnoreCase(AnswerInputTypes.BUTTON_OPTIONS_STRING)
                                    || in_type
                                    .equalsIgnoreCase(AnswerInputTypes.BUTTON_OPTIONS_STRING2)) {
                                input_type = AnswerInputTypes.BUTTON_OPTIONS;
                            } else if (in_type
                                    .equalsIgnoreCase(AnswerInputTypes.ONLY_ALPHABETS_STRING)
                                    || in_type
                                    .equalsIgnoreCase(AnswerInputTypes.ONLY_ALPHABETS_STRING)) {
                                input_type = AnswerInputTypes.ONLY_ALPHABETS;
                            } else if (in_type
                                    .equalsIgnoreCase(AnswerInputTypes.ALPHA_NUMERIC_STRING)) {
                                input_type = AnswerInputTypes.ALPHA_NUMERIC;
                            } else if (in_type
                                    .equalsIgnoreCase(AnswerInputTypes.ONLY_NUMBERS_STRING)) {
                                input_type = AnswerInputTypes.ONLY_NUMBERS;
                            } else if (in_type
                                    .equalsIgnoreCase(AnswerInputTypes.CALENDAR_STRING)) {
                                input_type = AnswerInputTypes.CALENDER;
                            } else if (in_type
                                    .equalsIgnoreCase(AnswerInputTypes.ALPHA_AND_NUMBERS_STRING)) {
                                input_type = AnswerInputTypes.ALPHA_AND_NUMBERS;
                            }
                            ArrayList<String> options = new ArrayList<>();
                            if (input_type == AnswerInputTypes.BUTTON_OPTIONS) {
                                String[] opString = que.getString(
                                        "dropDownItemList").split("\\/");
                                for (int j = 0; j < opString.length; j++) {
                                    options.add(opString[j]);
                                }
                                // System.out.println();
                            }
                            int min = que.getInt("minLength");
                            int max = que.getInt("maxLength");
                            String regex = que.getString("regex");
                            String help_1 = que.getString("level1Help");
                            String help_2 = que.getString("level2Help");
                            Question q = new Question(id, seq, title,
                                    shortTitle, options, "", type, input_type,
                                    min, max, regex, help_1, help_2, 0, 0, "",
                                    "");
                            helper.addQuestion(q);
                        }
                        toReturn = "1";
                    }
                } catch (Exception e) {
                    toReturn = "0";
                    e.printStackTrace();
                }
            } else {
                // checkLogin();
                toReturn = "2";
            }
            return toReturn;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // checkLogin();
            if (result.equalsIgnoreCase("1")) {
                checkLogin();
            } else if (result.equalsIgnoreCase("2")) {
                checkLogin();
//				Handler handler = new Handler();
//				handler.postDelayed(new Runnable() {
//					@Override
//					public void run() {
//						checkLogin();
//					}
//				}, 0);
            } else {
                showToast("Error... try again later...");
                SplashActivity.this.finish();
            }
        }

    }

//    public class CheckAPI extends AsyncTask<String, String, String> {
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//            ContentValues cv = new ContentValues();
//            cv.put("grant_type", "password");
//            cv.put("username", "#gdsghty465684fvb#4@$");
//            cv.put("password", "#@mapp245df4545!90");
//            JSONObject resp;
//            try {
//                resp = new JSONObject(PostManager.excutePost(
//                        Constants.AUTH_TOKEN, cv, false));
//                PrefsManager.savePrefs(getApplicationContext(), Constants.TOKEN,
//                        resp.getString(Constants.TOKEN));
//                PostManager.access_token = resp.getString(Constants.TOKEN);
//                //
//                // ContentValues cv = new ContentValues();
//                cv.put("email_id", "patrickfdsouza@gmail.com");
//                PostManager.excutePost(Constants.CHECK_EMAIL, cv, true);
//                //
//                // PrefsManager.savePrefs(getApplicationContext(), "expiry",
//                // resp.getString(Constants.TOKEN));
//                // DatabaseHelper helper = new
//                // DatabaseHelper(SplashActivity.this);
//                long c = helper.getQuestionsCount();
//                if (c <= 0) {
//                    new AddQuestions().execute();
//                } else {
//                    checkLogin();
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            System.out.println();
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            // checkLogin();
//        }
//
//    }

    public class GetStates extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            ContentValues cv = new ContentValues();
            // cv.put("grant_type", "password");
            cv.put(Constants.HCUID, Constants.HCUID_VAL);
            cv.put(Constants.HCPASS, Constants.HCPASS_VAL);
            JSONArray resp;
            try {
                String jsonReturn = PostManager.excutePost(
                        Constants.GET_STATES, cv, false);
                resp = new JSONArray(jsonReturn);
                for (int i = 0; i < resp.length(); i++) {
                    helper.addState(resp.getJSONObject(i)
                            .getString("stateName"));
                }
            } catch (Exception e) {
                showToast("Error, please try again later...");
                SplashActivity.this.finish();
                e.printStackTrace();
            }
            System.out.println();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            // checkLogin();
        }

    }
}
