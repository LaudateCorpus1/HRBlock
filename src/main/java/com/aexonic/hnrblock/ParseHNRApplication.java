package com.aexonic.hnrblock;

import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.analytics.StandardExceptionParser;

import org.acra.ACRA;
import org.acra.annotation.ReportsCrashes;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Parikshit Patil on 1/15/2016.
 */
@ReportsCrashes(formUri = "http://farmerspoint.in/patrick/crash.php")
public class ParseHNRApplication extends Application
{

    // The following line triggers the initialization of ACRA
//    ACRA.init(this);

    String FLURRY_API_KEY="KZFHN3TZPVG8KYNDS46P";
    //KZFHN3TZPVG8KYNDS46P
    public static final String TAG = ParseHNRApplication.class.getSimpleName();

    private static ParseHNRApplication mInstance;


    @Override
    public void onCreate()
    {
        super.onCreate();

        ACRA.init(this);

        Fabric.with(this, new Crashlytics());

        mInstance=this;

        ParseUtils.registerParse(this);

        AnalyticsTrackers.initialize(this);
        AnalyticsTrackers.getInstance().get(AnalyticsTrackers.Target.APP);

        // configure Flurry
      //  FlurryAgent.setLogEnabled(false);

        // init Flurry
        FlurryAgent.init(this,FLURRY_API_KEY);

       // FlurryAgent.onStartSession(this,FLURRY_API_KEY);

    }



    public static synchronized ParseHNRApplication getInstance() {
        return mInstance;
    }

    public synchronized Tracker getGoogleAnalyticsTracker() {
        AnalyticsTrackers analyticsTrackers = AnalyticsTrackers.getInstance();
        return analyticsTrackers.get(AnalyticsTrackers.Target.APP);
    }

    /***
     * Tracking screen view
     *
     * @param screenName screen name to be displayed on GA dashboard
     */
    public void trackScreenView(String screenName) {
        Tracker t = getGoogleAnalyticsTracker();

        // Set screen name.
        t.setScreenName(screenName);

        // Send a screen view.
        t.send(new HitBuilders.ScreenViewBuilder().build());

        GoogleAnalytics.getInstance(this).dispatchLocalHits();
    }

    /***
     * Tracking exception
     *
     * @param e exception to be tracked
     */
    public void trackException(Exception e) {
        if (e != null) {
            Tracker t = getGoogleAnalyticsTracker();

            //t.send(new HitBuilders.ExceptionBuilder().setDescription(new StandardExceptionParser(this, null).getDescription(Thread.currentThread().getName(), e)).setFatal(false).build());
      t.send(new HitBuilders.ExceptionBuilder().setDescription(new StandardExceptionParser(this,null).getDescription(Thread.currentThread().getName(),e)).setFatal(false).build());

        }
    }

    /***
     * Tracking event
     *
     * @param category event category
     * @param action   action of the event
     * @param label    label
     */
    public void trackEvent(String category, String action, String label) {
        Tracker t = getGoogleAnalyticsTracker();

        // Build and send an Event.
        t.send(new HitBuilders.EventBuilder().setCategory(category).setAction(action).setLabel(label).build());
    }


}
