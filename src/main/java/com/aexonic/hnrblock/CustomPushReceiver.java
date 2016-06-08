package com.aexonic.hnrblock;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


/**
 * Created by Ravi on 01/06/15.
 */
public class CustomPushReceiver extends ParsePushBroadcastReceiver
{
    private final String TAG = CustomPushReceiver.class.getSimpleName();

    private NotificationUtils notificationUtils;

    private Intent parseIntent;
    private Context mContext;

    public CustomPushReceiver()
    {
        super();
    }

   /* public CustomPushReceiver(Context mContext)
    {
        this.mContext = mContext;
    }*/

    @Override
    protected void onPushReceive(Context context, Intent intent)
    {
        super.onPushReceive(context, intent);

        if (intent == null)
            return;

        try
        {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));
            Log.e(TAG, "Push received: " + json);
            parseIntent = intent;
            parsePushJson(context, json);
          //Toast.makeText(context, "INSIDE IF onPUSHRECEIVED PARSE PUSH JSON", Toast.LENGTH_SHORT).show();

        }
        catch (JSONException e)
        {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
           // Toast.makeText(context,"INSIDE ELSE onPUSHRECEIVED PARSE PUSH JSON",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPushDismiss(Context context, Intent intent) {
        super.onPushDismiss(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);
    }

    /**
     * Parses the push notification json
     *
     * @param context
     * @param json
     */
    private void parsePushJson(Context context, JSONObject json)
    {
        try
        {
            boolean isBackground = json.getBoolean("is_background");
            JSONObject data = json.getJSONObject("data");
            String title = data.getString("title");
            String message = data.getString("message");

            if(isAppIsInBackground(context))
            {
                Intent resultIntent = new Intent(context, SplashScreenActivity.class);
                showNotificationMessage(context, title, message, resultIntent);

               // Toast.makeText(context,"go notification",Toast.LENGTH_SHORT).show();
            }
            else
            {
                //Toast.makeText(context,"do not go notification",Toast.LENGTH_SHORT).show();

            }

          // if(!isBackground)
           // {
              //  Intent resultIntent = new Intent(context, SplashScreenActivity.class);
               // showNotificationMessage(context, title, message, resultIntent);
                // Toast.makeText(context,"INSIDE IF OF PARSE PUSH JSON",Toast.LENGTH_SHORT).show();
           // }
           //// else
           // {
            //    Intent resultIntent = new Intent(context, SplashScreenActivity.class);
            //    showNotificationMessage(context, title, message, resultIntent);
                //Toast.makeText(context,"Always else",Toast.LENGTH_SHORT).show();

           // }
/*
            if(isApplicationInBackgroundNew(context))
            {
                if(!isBackground)
                {
                    Intent resultIntent = new Intent(context, SplashScreenActivity.class);
                    showNotificationMessage(context, title, message, resultIntent);
                    // Toast.makeText(context,"INSIDE IF OF PARSE PUSH JSON",Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
              Toast.makeText(context,"Not",Toast.LENGTH_SHORT).show();
            }
*/


        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
            //Toast.makeText(context,"INSIDE ELSE PARSE PUSH JSON",Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Shows the notification message in the notification bar
     * If the app is in background, launches the app
     *
     * @param context
     * @param title
     * @param message
     * @param intent
     */
    private void showNotificationMessage(Context context, String title, String message, Intent intent)
    {

        notificationUtils = new NotificationUtils(context);

        intent.putExtras(parseIntent.getExtras());

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        notificationUtils.showNotificationMessage(title, message, intent);
    }


    /**
     * Method checks if the app is in background or not
     *
     * @param context
     * @return
     */
    public static boolean isAppIsInBackground(Context context)
    {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH)
        {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses)
            {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)
                {
                    for (String activeProcess : processInfo.pkgList)
                    {
                        if (activeProcess.equals(context.getPackageName()))
                        {
                            isInBackground = false;
                        }
                    }
                }
            }
            //Toast.makeText(context,"In kitkat",Toast.LENGTH_SHORT).show();
        }
        else
        {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
           // Toast.makeText(context,"Else kitkat",Toast.LENGTH_SHORT).show();

        }
        return isInBackground;
    }

    public static boolean isApplicationInBackgroundNew(Context context)
    {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

}