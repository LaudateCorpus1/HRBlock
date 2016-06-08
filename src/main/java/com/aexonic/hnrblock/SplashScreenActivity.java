package com.aexonic.hnrblock;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.view.Window;
import android.view.WindowManager;

import com.flurry.android.FlurryAgent;

/**
 * Created by Parikshit Patil on 12/15/2015.
 */
public class SplashScreenActivity extends ActionBarActivity
{
    private final int WAIT_TIME = 2000;
    Intent gotohome;
    String FLURRY_API_KEY="KZFHN3TZPVG8KYNDS46P";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);

        setContentView(R.layout.splashscreen);

        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                this.Sleep(1000);
               // System.out.println("Going to Profile Data");

                gotohome=new Intent(SplashScreenActivity.this,HomeScreenActivity.class);
                startActivity(gotohome);
                finish();

            }
            private void Sleep(int i)
            {

            }
        },WAIT_TIME);

     }

    @Override
    protected void onStart() {
        super.onStart();

        FlurryAgent.onStartSession(this,FLURRY_API_KEY);
    }
}
