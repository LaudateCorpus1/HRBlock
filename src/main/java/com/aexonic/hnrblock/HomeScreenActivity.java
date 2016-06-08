package com.aexonic.hnrblock;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hnrblock.chatfile.SplashActivity;
import com.hnrblock.chatfile.helpers.MyTimeUtils;
import com.hnrblock.chatfile.helpers.PrefsManager;
import com.hnrblock.chatfile.objects.Constants;

/**
 * Created by Parikshit Patil on 12/15/2015.
 */
public class HomeScreenActivity extends ActionBarActivity
{
    Toolbar toolbar;
    LinearLayout taxsavingtrackerclick,efileclick,taxcalculatorclick,myaccountclick;
    ImageView profileclick;
    private TextView efileLastSeen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.home_screen_activity);

//        Integer.parseInt("abc");

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        profileclick=(ImageView)toolbar.findViewById(R.id.profileclick);
        efileclick=(LinearLayout)findViewById(R.id.efileclick);
        efileLastSeen=(TextView) findViewById(R.id.efile_last_seen);
        taxsavingtrackerclick=(LinearLayout)findViewById(R.id.taxsavingtrackerclick);
        taxcalculatorclick=(LinearLayout)findViewById(R.id.taxcalculatorclick);
        myaccountclick=(LinearLayout)findViewById(R.id.myaccountclick);

        setLastSeen();

        taxsavingtrackerclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent gotoprofile=new Intent(HomeScreenActivity.this,ProfileScreenActivity.class);
                startActivity(gotoprofile);
            }
        });

        efileclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //Intent gotoefile=new Intent(HomeScreenActivity.this,EfileActivity.class);
                Intent gotoefile=new Intent(HomeScreenActivity.this, SplashActivity.class);
                startActivity(gotoefile);
            }
        });

        /*efileclick.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        efileclick.setBackgroundColor(R.color.greencolor);
                        break;
                    case MotionEvent.ACTION_UP:

                        //set color back to default
                        efileclick.setBackgroundColor(R.color.white);
                        break;
                }
                return true;
            }
        });*/
        taxcalculatorclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gototaxcalculator=new Intent(HomeScreenActivity.this,TaxCalculatorActivity.class);
                startActivity(gototaxcalculator);
            }
        });

        myaccountclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String url = "https://tax.hrblock.in/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

        profileclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {

                Intent gotoupdateprofile=new Intent(HomeScreenActivity.this,UpdateProfile.class);
                startActivity(gotoupdateprofile);
            }
        });
    }

    private void setLastSeen(){
        long lastSeenTime = PrefsManager.getPrefs(this, Constants.LAST_OPENED,System.currentTimeMillis());

        efileLastSeen.setText(MyTimeUtils.getTime(lastSeenTime+""));
    }

    @Override
    protected void onResume() {
        super.onResume();

        setLastSeen();

        ParseHNRApplication.getInstance().trackScreenView("Home Screen");
    }
}
