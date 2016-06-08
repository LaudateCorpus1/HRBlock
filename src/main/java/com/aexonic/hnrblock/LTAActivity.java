package com.aexonic.hnrblock;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Parikshit Patil on 12/19/2015.
 */
public class LTAActivity extends ActionBarActivity
{
    Toolbar toolbar;

    RadioGroup rgquestion1,rgquestion2,rgquestion3;
    RadioButton rb,rb1,rb3;

    RadioButton rbq1yes,rbq1no,rbq2yes,rbq2no,rbq3yes,rbq3no;
    LinearLayout movesection,secondquelayout,thirdquelayout,que3movesectionyes,que3movesectionno;
    Button donebtn,resetbtn;
    String s1="You have only ";// ONE Leave travel allowance(LTA) claim remaining to save your taxes. You can plan for a vacation along with family until 31st December, 2017. ";
    String sbold="ONE ";
    String remain="LTA claim remaining to save your taxes. You can plan for a vacation along with family until 31st December, 2017.";
    //String s2="Know more...";
    String s3;
    String s5;
    String greencolor;
    String textcolor,textcolor1;
    TextView knowmores1,knowmores2;
    String uncheck="uncheck";


    String t1="You can claim LTA ";
    String t2="TWICE ";
    String t3="till 31st December, 2017 to save your taxes. You can plan for a vacation along with your family. ";
    String t4="Know more...";
    String t5;
    String t2greencolor;
    //Click here to know more..
    String knowmore;//="Click here to know more..";

    TextView knowmoreclickable,knowmoreclickabletwo;
    public static final String PREFS_NAME = "LoginPrefs";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    String android_Id;
    DatabaseHandler databaseHandler;
    String id1,id2,id3,claimnumber;

    int databasecount;
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lta_layout_activity);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        cd=new ConnectionDetector(getApplicationContext());

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        android_Id=settings.getString("android_Id","");
        databaseHandler=new DatabaseHandler(this);


        greencolor="<font color='#7dc243'>"+sbold+"</font>";
        s5=s1+"<b>"+greencolor+"</b>"+remain;


        //textcolor1="<font color='#000000'>"+t4+"</font>";
        //s3=s1+"<b>"+s2+"</b>";

        t2greencolor="<font color='#7dc243'>"+t2+"</font>";
        t5=t1+"<b>"+t2greencolor+"</b>"+t3;

        knowmores1=(TextView)findViewById(R.id.knowmores1);
        knowmores1.setText(Html.fromHtml(s5));

        knowmores2=(TextView)findViewById(R.id.knowmores2);
        knowmores2.setText(Html.fromHtml(t5));

        knowmore="<u>Click here to know more..</u>";
        knowmoreclickable=(TextView)findViewById(R.id.knowmoreclickable);
        knowmoreclickabletwo=(TextView)findViewById(R.id.knowmoreclickabletwo);
        knowmoreclickable.setText(Html.fromHtml(knowmore));
        knowmoreclickabletwo.setText(Html.fromHtml(knowmore));

        movesection=(LinearLayout)findViewById(R.id.movesection);
        secondquelayout=(LinearLayout)findViewById(R.id.secondquestionlayout);
        thirdquelayout=(LinearLayout)findViewById(R.id.thirdquestionlayout);
        que3movesectionyes=(LinearLayout)findViewById(R.id.que3movesectionyes);
        que3movesectionno=(LinearLayout)findViewById(R.id.que3movesectionno);


        donebtn=(Button)findViewById(R.id.done_btn);
       // resetbtn=(Button)findViewById(R.id.reset_btn);
        rgquestion1=(RadioGroup)findViewById(R.id.id_que1);
        rgquestion2=(RadioGroup)findViewById(R.id.id_que2);
        rgquestion3=(RadioGroup)findViewById(R.id.id_que3);

        rbq1yes=(RadioButton)findViewById(R.id.rbq1yes);
        rbq1no=(RadioButton)findViewById(R.id.rbq1no);
        rbq2yes=(RadioButton)findViewById(R.id.rbq2yes);
        rbq2no=(RadioButton)findViewById(R.id.rbq2no);
        rbq3yes=(RadioButton)findViewById(R.id.rbq3yes);
        rbq3no=(RadioButton)findViewById(R.id.rbq3no);

        //rbq1yes.setChecked(true);

       // android_Id,id1,id2,id3,claimnumber
        databasecount=databaseHandler.getLTACount();
        if(databasecount>0)
        {


            List<LTAPojo> ltalist = databaseHandler.getAllLta();

            for (LTAPojo cn : ltalist)
            {
                android_Id=cn.get_androidid();
                id1=cn.get_islta();
                id2=cn.get_isvacation();
                id3=cn.get_isclaimlta();
                claimnumber=cn.get_claimnumber();
            }


            if(id1.equals("Yes"))
            {
              rbq1yes.setChecked(true);
              movesection.setVisibility(View.GONE);
              donebtn.setVisibility(View.GONE);

            }
            if(id1.equals("No"))
            {
                rbq1no.setChecked(true);
                if(movesection.getVisibility()==View.GONE)
                {
                    movesection.setVisibility(View.VISIBLE);
                    que3movesectionyes.setVisibility(View.GONE);
                    que3movesectionno.setVisibility(View.GONE);

                    secondquelayout.setVisibility(View.GONE);
                    thirdquelayout.setVisibility(View.GONE);
                    donebtn.setVisibility(View.VISIBLE);

                }


            }

            if(!id2.equals("null"))
            {
                movesection.setVisibility(View.GONE);

                if(id2.equals("Yes"))
                {
                    rbq2yes.setChecked(true);
                }
                if(id2.equals("No"))
                {
                    rbq2no.setChecked(true);
                }
            }

            if(!id3.equals("null"))
            {
                if(id3.equals("Yes"))
                {
                    rbq3yes.setChecked(true);
                }
                if(id3.equals("No"))
                {
                    rbq3no.setChecked(true);
                }
            }

            if(!claimnumber.equals("null"))
            {
                secondquelayout.setVisibility(View.VISIBLE);
                thirdquelayout.setVisibility(View.VISIBLE);


                if(claimnumber.equals("ONE"))
                {
                    que3movesectionyes.setVisibility(View.VISIBLE);
                    donebtn.setVisibility(View.VISIBLE);
                }
                if(claimnumber.equals("TWICE"))
                {
                    que3movesectionno.setVisibility(View.VISIBLE);
                    donebtn.setVisibility(View.VISIBLE);
                }
            }

        }

/*
        resetbtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               */
/* secondquelayout.setVisibility(View.GONE);
                thirdquelayout.setVisibility(View.GONE);
                que3movesectionyes.setVisibility(View.GONE);
                que3movesectionno.setVisibility(View.GONE);
                donebtn.setVisibility(View.GONE);
                resetbtn.setVisibility(View.GONE);*//*

                //rb1.setChecked(false);

                finish();
                startActivity(getIntent());


            }
        });
*/

        rgquestion1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
            {

                rb=(RadioButton)findViewById(checkedId);
                id1=rb.getText().toString();


                if(id1.equals("Yes"))
                {
                    //Toast.makeText(getApplicationContext(),rb.getText().toString(),Toast.LENGTH_SHORT).show();
                    secondquelayout.setVisibility(View.VISIBLE);
                    secondquelayout.requestFocus();
                    movesection.setVisibility(View.GONE);
                    donebtn.setVisibility(View.GONE);
                   // resetbtn.setVisibility(View.VISIBLE);

                    thirdquelayout.setVisibility(View.GONE);
                    que3movesectionyes.setVisibility(View.GONE);
                    que3movesectionno.setVisibility(View.GONE);

                }
                else if(id1.equals("No"))
                {
                   // Toast.makeText(getApplicationContext(),rb.getText().toString(),Toast.LENGTH_SHORT).show();
                    movesection.setVisibility(View.VISIBLE);
                    donebtn.setVisibility(View.VISIBLE);
                    secondquelayout.setVisibility(View.GONE);
                  //  resetbtn.setVisibility(View.GONE);

                    thirdquelayout.setVisibility(View.GONE);
                    que3movesectionyes.setVisibility(View.GONE);
                    que3movesectionno.setVisibility(View.GONE);

                }
            }
        });

        rgquestion2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
            {
                rb1=(RadioButton)findViewById(checkedId);
                id2=rb1.getText().toString();

                if(id2.equals("Yes"))
                {
                    //Toast.makeText(getApplicationContext(),rb.getText().toString(),Toast.LENGTH_SHORT).show();
                   // resetbtn.setVisibility(View.VISIBLE);
                    thirdquelayout.setVisibility(View.VISIBLE);
                    thirdquelayout.requestFocus();
                   // donebtn.setVisibility(View.GONE);
                }
                else if(id2.equals("No"))
                {
                    // Toast.makeText(getApplicationContext(),rb.getText().toString(),Toast.LENGTH_SHORT).show();
                    //donebtn.setVisibility(View.GONE);
                    //resetbtn.setVisibility(View.VISIBLE);
                    thirdquelayout.setVisibility(View.VISIBLE);

                    //que3movesectionyes.setVisibility(View.GONE);
                    //que3movesectionno.setVisibility(View.GONE);
                }
            }
        });

        rgquestion3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
            {
                rb3=(RadioButton)findViewById(checkedId);
                id3=rb3.getText().toString();
                uncheck="uncheck";


                if(id3.equals("Yes"))
                {
                    //Toast.makeText(getApplicationContext(),rb.getText().toString(),Toast.LENGTH_SHORT).show();
                   // resetbtn.setVisibility(View.GONE);
                    donebtn.setVisibility(View.VISIBLE);
                    que3movesectionyes.setVisibility(View.VISIBLE);
                    que3movesectionyes.requestFocus();
                    que3movesectionno.setVisibility(View.GONE);
                    uncheck="check";
                    claimnumber="ONE";

                }
                else if(id3.equals("No"))
                {
                    // Toast.makeText(getApplicationContext(),rb.getText().toString(),Toast.LENGTH_SHORT).show();
                    donebtn.setVisibility(View.VISIBLE);
                   // resetbtn.setVisibility(View.GONE);
                    uncheck="check";
                    que3movesectionno.setVisibility(View.VISIBLE);
                    que3movesectionno.requestFocus();
                    que3movesectionyes.setVisibility(View.GONE);
                    claimnumber="TWICE";
                }
            }
        });

        knowmoreclickable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final Dialog dialog = new Dialog(LTAActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.know_moredialog_oneyes);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

                ImageView crossclick=(ImageView)dialog.findViewById(R.id.crossclick);
                crossclick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }

        });


        knowmoreclickabletwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final Dialog dialog = new Dialog(LTAActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.know_moredialog_oneyes);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

                ImageView crossclick=(ImageView)dialog.findViewById(R.id.crossclick);
                crossclick.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }

        });

        donebtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                if(databasecount>0)
                {
                    //update datahere...
                    if(que3movesectionyes.getVisibility()==View.VISIBLE||que3movesectionno.getVisibility()==View.VISIBLE)
                    {
                        databaseHandler.updateLTA(new LTAPojo(android_Id, id1, id2, id3, claimnumber));

                        Intent gotoback = new Intent(LTAActivity.this, InvestmentTabActivity.class);
                        gotoback.putExtra("camefromlta", "camefromlta");
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(gotoback);
                      //  Toast.makeText(getApplicationContext(),"databasecount> inif",Toast.LENGTH_SHORT).show();
                        isInternetPresent=cd.isConnectingToInternet();

                        isInternetPresent=cd.isConnectingToInternet();
                        if(isInternetPresent)
                        {
                            //Call API....
                            new LTAWebservice().execute(android_Id,id1,id2,id3,claimnumber);

                        }
                        else
                        {

                        }
                    }

                    else if(movesection.getVisibility()==View.VISIBLE)
                    {
                        id2="null";
                        id3="null";
                        claimnumber="null";

                        databaseHandler.updateLTA(new LTAPojo(android_Id, id1, id2, id3, claimnumber));

                        Intent gotoback = new Intent(LTAActivity.this, InvestmentTabActivity.class);
                        gotoback.putExtra("camefromlta", "camefromlta");
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(gotoback);
                     //   Toast.makeText(getApplicationContext(),"databasecount> inelse if",Toast.LENGTH_SHORT).show();
                        isInternetPresent=cd.isConnectingToInternet();

                        if(isInternetPresent)
                        {
                            //Call API....
                            new LTAWebservice().execute(android_Id,id1,id2,id3,claimnumber);

                        }
                        else
                        {

                        }

                    }
                    else
                    {
                        onBackPressed();
                      //  Toast.makeText(getApplicationContext(),"databasecount> onBackpress",Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    //add data here....

                    if(que3movesectionyes.getVisibility()==View.VISIBLE||que3movesectionno.getVisibility()==View.VISIBLE)
                    {
                        databaseHandler.addLTA(new LTAPojo(android_Id, id1, id2, id3, claimnumber));

                        Intent gotoback=new Intent(LTAActivity.this,InvestmentTabActivity.class);
                        gotoback.putExtra("camefromlta","camefromlta");
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(gotoback);
                      //  Toast.makeText(getApplicationContext(),"databasecount< in if",Toast.LENGTH_SHORT).show();

                        isInternetPresent=cd.isConnectingToInternet();
                        if(isInternetPresent)
                        {
                            //Call API....
                           new LTAWebservice().execute(android_Id,id1,id2,id3,claimnumber);

                        }
                        else
                        {

                        }


                    }
                    else if(movesection.getVisibility()==View.VISIBLE)
                    {
                        //id1=null;
                        id2="null";
                        id3="null";
                        claimnumber="null";

                        databaseHandler.addLTA(new LTAPojo(android_Id,id1,id2,id3,claimnumber));

                        Intent gotoback=new Intent(LTAActivity.this,InvestmentTabActivity.class);
                        gotoback.putExtra("camefromlta","camefromlta");
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(gotoback);
                      //  Toast.makeText(getApplicationContext(),"databasecount< inelse if",Toast.LENGTH_SHORT).show();
                        isInternetPresent=cd.isConnectingToInternet();

                        if(isInternetPresent)
                        {
                            //Call API....
                           new LTAWebservice().execute(android_Id,id1,id2,id3,claimnumber);

                        }
                        else
                        {

                        }

                    }
                    else
                    {
                        onBackPressed();
                      //  Toast.makeText(getApplicationContext(),"databasecount< onBack",Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });

/*
        que3movesectionno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final Dialog dialog = new Dialog(LTAActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.know_moredialog_oneyes);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

                ImageView crossclick=(ImageView)dialog.findViewById(R.id.crossclick);
                crossclick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }

        });
*/

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                // API 5+ solution
               // onBackPressed();

                if(databasecount>0)
                {
                    //update datahere...
                    if(que3movesectionyes.getVisibility()==View.VISIBLE||que3movesectionno.getVisibility()==View.VISIBLE)
                    {
                        databaseHandler.updateLTA(new LTAPojo(android_Id, id1, id2, id3, claimnumber));

                        Intent gotoback = new Intent(LTAActivity.this, InvestmentTabActivity.class);
                        gotoback.putExtra("camefromlta", "camefromlta");
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(gotoback);
                       // Toast.makeText(getApplicationContext(),"databasecount> inif",Toast.LENGTH_SHORT).show();
                        isInternetPresent=cd.isConnectingToInternet();

                        if(isInternetPresent)
                        {
                            //Call API....
                          new LTAWebservice().execute(android_Id,id1,id2,id3,claimnumber);

                        }
                        else
                        {

                        }
                    }

                    else if(movesection.getVisibility()==View.VISIBLE)
                    {
                        id2="null";
                        id3="null";
                        claimnumber="null";

                        databaseHandler.updateLTA(new LTAPojo(android_Id, id1, id2, id3, claimnumber));

                        Intent gotoback = new Intent(LTAActivity.this, InvestmentTabActivity.class);
                        gotoback.putExtra("camefromlta", "camefromlta");
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(gotoback);
                       // Toast.makeText(getApplicationContext(),"databasecount> inelse if",Toast.LENGTH_SHORT).show();
                        isInternetPresent=cd.isConnectingToInternet();

                        if(isInternetPresent)
                        {
                            //Call API....
                           new LTAWebservice().execute(android_Id,id1,id2,id3,claimnumber);

                        }
                        else
                        {

                        }

                    }
                    else
                    {
                        onBackPressed();
                       // Toast.makeText(getApplicationContext(),"databasecount> onBackpress",Toast.LENGTH_SHORT).show();
                    }

                }
                else
                {
                    //add data here....

                    if(que3movesectionyes.getVisibility()==View.VISIBLE||que3movesectionno.getVisibility()==View.VISIBLE)
                    {
                        databaseHandler.addLTA(new LTAPojo(android_Id, id1, id2, id3, claimnumber));

                        Intent gotoback=new Intent(LTAActivity.this,InvestmentTabActivity.class);
                        gotoback.putExtra("camefromlta","camefromlta");
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(gotoback);
                      //  Toast.makeText(getApplicationContext(),"databasecount< in if",Toast.LENGTH_SHORT).show();
                        isInternetPresent=cd.isConnectingToInternet();
                        if(isInternetPresent)
                        {
                            //Call API....
                           new LTAWebservice().execute(android_Id,id1,id2,id3,claimnumber);

                        }
                        else
                        {

                        }


                    }
                    else if(movesection.getVisibility()==View.VISIBLE)
                    {
                        //id1=null;
                        id2="null";
                        id3="null";
                        claimnumber="null";

                        databaseHandler.addLTA(new LTAPojo(android_Id,id1,id2,id3,claimnumber));

                        Intent gotoback=new Intent(LTAActivity.this,InvestmentTabActivity.class);
                        gotoback.putExtra("camefromlta","camefromlta");
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(gotoback);
                      //  Toast.makeText(getApplicationContext(),"databasecount< inelse if",Toast.LENGTH_SHORT).show();
                        isInternetPresent=cd.isConnectingToInternet();
                        if(isInternetPresent)
                        {
                            //Call API....
                            new LTAWebservice().execute(android_Id,id1,id2,id3,claimnumber);

                        }
                        else
                        {

                        }

                    }
                    else
                    {
                        onBackPressed();
                        //Toast.makeText(getApplicationContext(),"databasecount< onBack",Toast.LENGTH_SHORT).show();

                    }
                }

                return true;
            // case R.id.action_settings:
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class LTAWebservice extends AsyncTask<String,String,String>
    {

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args0)
        {
            try
            {
                String deviceidstring=(String)args0[0];
                String sid1=(String)args0[1];
                String sid2=(String)args0[2];
                String sid3=(String)args0[3];
                String sclaimnumber=(String)args0[4];

                //  String s=(String)args0[2];

                String link="http://tax.hrblock.in/Mobile_App_service/api/profile/Post_LTARecord/";
                String data= URLEncoder.encode("Device_id", "UTF-8")+"="+URLEncoder.encode(deviceidstring,"UTF-8");
                data += "&"+URLEncoder.encode("Is_LTA","UTF-8")+"="+URLEncoder.encode(sid1,"UTF-8");
                data += "&"+URLEncoder.encode("On_vacation","UTF-8")+"="+URLEncoder.encode(sid2,"UTF-8");
                data += "&"+URLEncoder.encode("claim_LTA","UTF-8")+"="+URLEncoder.encode(sid3,"UTF-8");
                data += "&"+URLEncoder.encode("claim_remaining","UTF-8")+"="+URLEncoder.encode(sclaimnumber,"UTF-8");

                URL url = new URL(link);
                URLConnection conn = url.openConnection();
                conn.setDoOutput(true);
                OutputStreamWriter wr = new OutputStreamWriter
                        (conn.getOutputStream());
                wr.write( data );
                wr.flush();
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                // Read Server Response
                while((line = reader.readLine()) != null)
                {
                    sb.append(line);
                    break;
                }
                return sb.toString();
            }catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
            //return null;
        }

        @Override
        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);
          // Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
        }
    }

}
