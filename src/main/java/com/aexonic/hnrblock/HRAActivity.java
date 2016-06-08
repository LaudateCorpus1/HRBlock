package com.aexonic.hnrblock;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by Parikshit Patil on 12/18/2015.
 */
public class HRAActivity extends ActionBarActivity
{
    Toolbar toolbar;
    TextView exceptiontvclick,optimumtv,maximumtax;
    LinearLayout belowlayout,centerlayout,optimumlayout,maximumlayout;
    Button calculate_hra;
    //Your optimum rent amount would be ₹ 25000 to get most out of your House Rent Allowance.
    String ot1="Your optimum monthly rent amount should be ";
    String ot2="₹25000 ";
    String otunderline;
    String ot3="to get maximum tax benefit out of your House Rent Allowance.";
    String ottextcolor;
    String otfinaltext;

    String maxt1="You can get maximum tax benefit if you stay in more premium property and pay additional rent of ";
    String maxt2="₹25000";
    String maxtextcolor;
    String maxfinaltext;
    String maxunderline;
    Button done_btn;
    Spinner spinner;
    //You can get maximum tax benifit if you stay in more premium property and pay additional rent of ₹ 25000

    //Your optimum rent amount should be rs. 25000.you can get maximum tax benefit if you move to more premium property & pay an additional rent of rsxxx.
    //Your optimum rent amount should be ₹25000. you can get maximum tax benefit if you move to more premium property & pay an additional rent of ";
    String max1="Your optimum rent amount should be ";// ₹25000. you can get maximum tax benefit if you move to more premium property & pay an additional rent of ";
    //String max2="0";
    String max2="0";
    String max3=". ";
    String max4="You can get maximum tax benefit if you move to more premium property & pay an additional rent of ";
    String max5=".";
    //You have fully utilized the benefits received from HRA. Any additional rent paid will not fetch you additional tax benefits.


    String excess1="You are paying excess rent of ";
    String excess2=" for which you won't get any additional tax benefit.";
    String excessequal="You have fully utilized the benefits received from HRA. Any additional rent paid will not fetch you additional tax benefits.";

    String pricemax,finalmax,pricemaxmax;

    int basicsalary=0;
    int actualhra=0;
    int hra=0;
    int rentpaid=0;
    int percenthra=0;
    int minus=0;
    int amountexcemption=0;
    int optimummaximumamount=0;
    int maximumamount=0;

    String sbasicsalary,shra,srentpaid;
    String scityspinner;
   // Object matchcity="Select";
    EditText et_basicsalary,et_hra,et_rentpaid;
    TextView tv_metro,tv_hrareceived,tv_excess,tv_amountexception;
    float percentagehra=0;
    float percentagerent=0;
    String finalhra;
    public static final String PREFS_NAME = "LoginPrefs";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    String android_Id;

    DatabaseHandler databaseHandler;
    int databasecount;
    // Connection detector class
    ConnectionDetector cd;
    Boolean isInternetPresent = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hra_activityv1);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);


        //hide keyboard code....
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        android_Id=settings.getString("android_Id","");

        databaseHandler=new DatabaseHandler(this);

        //List<HRAPojo> hralist = databaseHandler.getAllHra();


        ottextcolor="<font color='#7dc243'>"+ot2+"</font>";
        //otunderline="<u>"+ottextcolor+"</u>";
        otfinaltext=ot1+ottextcolor+ot3;

        maxtextcolor="<font color='#7dc243'>"+maxt2+"</font>";
       //maxunderline="<u>"+maxtextcolor+"</u>";
        //maxfinaltext=maxt1+maxtextcolor+max3;

        tv_amountexception=(TextView)findViewById(R.id.tv_amountexception);
        tv_metro=(TextView)findViewById(R.id.tv_metro);
        tv_hrareceived=(TextView)findViewById(R.id.tv_hrareceived);
        tv_excess=(TextView)findViewById(R.id.tv_excess);
        et_basicsalary=(EditText)findViewById(R.id.et_basicsalary);
        et_hra=(EditText)findViewById(R.id.et_hra);
        et_rentpaid=(EditText)findViewById(R.id.et_rentpaid);

        done_btn=(Button)findViewById(R.id.done_btn);
        calculate_hra=(Button)findViewById(R.id.calculate_hra);
        spinner=(Spinner)findViewById(R.id.spinner_accomodation);
        maximumtax=(TextView)findViewById(R.id.maximumtax);
        //maximumtax.setText(Html.fromHtml(maxfinaltext));
       // optimumlayout=(LinearLayout)findViewById(R.id.optimumlayout);
       // maximumlayout=(LinearLayout)findViewById(R.id.maximumlayout);
        //optimumtv=(TextView)findViewById(R.id.optimumtv);
        //optimumtv.setText(Html.fromHtml(otfinaltext));
        belowlayout=(LinearLayout)findViewById(R.id.belowlayout);
        centerlayout=(LinearLayout)findViewById(R.id.centerlayout);
        exceptiontvclick=(TextView)findViewById(R.id.exceptiontvclick);

        cd = new ConnectionDetector(getApplicationContext());

        databasecount=databaseHandler.getHRACount();
        if(databasecount>0)
        {
           List<HRAPojo> hralist = databaseHandler.getAllHra();
           // List<HRAPojo> medicallist = databaseHandler.getAllMedical();
            for (HRAPojo cn : hralist)
            {
                //android_Id,basicsalary,actualhra,rentpaid,scityspinner,amountexcemption,percenthra,actualhra,minus,maximumamount
                android_Id=cn.get_androidid();
                basicsalary=cn.get_basicsalary();
                actualhra=cn.get_actualreceived();
                rentpaid=cn.get_rentpaid();
                scityspinner=cn.get_accomodationcity();
                amountexcemption=cn.get_excemption();
                percenthra=cn.get_metrobsalary();
                actualhra=cn.get_actualreceived();
                minus=cn.get_excessrentpaid();
                optimummaximumamount=cn.get_maximumtax();
                maximumamount=cn.get_maxmaximumtax();
            }

            if(basicsalary<1)
            {
                et_basicsalary.setText("");
            }
            else
            {
                et_basicsalary.setText(String.valueOf(basicsalary));
            }
            if(actualhra<1)
            {
                et_hra.setText("");
            }
            else
            {
                et_hra.setText(String.valueOf(actualhra));
            }
            if(rentpaid<1)
            {
                et_rentpaid.setText("");
            }
            else
            {
                et_rentpaid.setText(String.valueOf(rentpaid));
            }

             //spinner.setSelection();
            //spinner.getSelectedItem().toString();

            if(scityspinner.equals("Mumbai"))
            {
                spinner.setSelection(1);
            }
            if(scityspinner.equals("Delhi"))
            {
                spinner.setSelection(2);

            }
            if(scityspinner.equals("Calcutta"))
            {
                spinner.setSelection(3);

            }
            if(scityspinner.equals("Chennai"))
            {
                spinner.setSelection(4);

            }
            if(scityspinner.equals("Others"))
            {
                spinner.setSelection(5);
            }

            if(amountexcemption<1)
            {
                tv_amountexception.setText("");
            }
            else
            {
                tv_amountexception.setText(String.valueOf(amountexcemption));
            }
            if(percenthra<1)
            {
                tv_metro.setText("");
            }
            else
            {
                tv_metro.setText(String.valueOf(percenthra));
            }
            if(actualhra<1)
            {
                tv_hrareceived.setText("");
            }
            else
            {
                tv_hrareceived.setText(String.valueOf(actualhra));
            }
            if(minus<1)
            {
                tv_excess.setText("");
            }
            else
            {
                tv_excess.setText(String.valueOf(minus));
            }
           /* if(optimummaximumamount<1)
            {
                maximumtax.setText("");
            }
            else
            {
                //maximumtax.setText(String.valueOf(maximumamount));

                DecimalFormat formatter = new DecimalFormat("#,###,###");
                String smaxamt = formatter.format(optimummaximumamount);
                String ssmaxamt=formatter.format(maximumamount);

                pricemax="<font color='#7dc243'>"+"₹"+smaxamt+"</font>";
                pricemaxmax="<font color='#7dc243'>"+"₹"+ssmaxamt+"</font>";

                finalmax=max1+pricemax+max3+max4+pricemaxmax+max5;
                maximumtax.setText(Html.fromHtml(finalmax));
            }*/


            if(rentpaid>optimummaximumamount)
            {
                DecimalFormat formatter = new DecimalFormat("#,###,###");
                String smaxamt = formatter.format(optimummaximumamount);
                String ssmaxamt=formatter.format(maximumamount);

                pricemax="<font color='#7dc243'>"+"₹"+smaxamt+"</font>";
                pricemaxmax="<font color='#7dc243'>"+"₹"+ssmaxamt+"</font>";

                //finalmax=max1+pricemax+max3+max4+pricemaxmax+max5;
                finalmax=max1+pricemax+max3+excess1+pricemaxmax+excess2;
                maximumtax.setText(Html.fromHtml(finalmax));
            }
            else if(rentpaid==optimummaximumamount)
            {
                DecimalFormat formatter = new DecimalFormat("#,###,###");
                String smaxamt = formatter.format(optimummaximumamount);
                String ssmaxamt=formatter.format(maximumamount);

                pricemax="<font color='#7dc243'>"+"₹"+smaxamt+"</font>";
                pricemaxmax="<font color='#7dc243'>"+"₹"+ssmaxamt+"</font>";

                //finalmax=max1+pricemax+max3+max4+pricemaxmax+max5;
               // finalmax=max1+pricemax+max3+excess1+pricemaxmax+excess2;
                finalmax=max1+pricemax+max3+excessequal;

                maximumtax.setText(Html.fromHtml(finalmax));
            }
            else
            {
                DecimalFormat formatter = new DecimalFormat("#,###,###");
                String smaxamt = formatter.format(optimummaximumamount);
                String ssmaxamt=formatter.format(maximumamount);

                pricemax="<font color='#7dc243'>"+"₹"+smaxamt+"</font>";
                pricemaxmax="<font color='#7dc243'>"+"₹"+ssmaxamt+"</font>";

                //finalmax=max1+pricemax+max3+max4+pricemaxmax+max5;
               // finalmax=max1+pricemax+max3+excess1+pricemaxmax+excess2;
                finalmax=max1+pricemax+max3+max4+pricemaxmax+max5;

                maximumtax.setText(Html.fromHtml(finalmax));
            }

            centerlayout.setVisibility(View.VISIBLE);


        }

        calculate_hra.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sbasicsalary=et_basicsalary.getText().toString();
                shra=et_hra.getText().toString();
                srentpaid=et_rentpaid.getText().toString();

                InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                mgr.hideSoftInputFromWindow(calculate_hra.getWindowToken(), 0);

                if(sbasicsalary.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter monthly basic salary",Toast.LENGTH_SHORT).show();
                }
                else if(shra.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter monthly HRA",Toast.LENGTH_SHORT).show();
                }
                else if(srentpaid.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Enter Rent paid",Toast.LENGTH_SHORT).show();
                }
                else if(scityspinner.equals("Select"))
                {
                    Toast.makeText(getApplicationContext(),"Select city",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Toast.makeText(getApplicationContext(),"Done",Toast.LENGTH_SHORT).show();
                    centerlayout.setVisibility(View.VISIBLE);
                    centerlayout.requestFocus();

                    //hide keyboard code....
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                    basicsalary=Integer.parseInt(sbasicsalary);
                    actualhra=Integer.parseInt(shra);
                    String received=String.valueOf(actualhra);
                    tv_hrareceived.setText(received);

                    rentpaid=Integer.parseInt(srentpaid);
                    percentagerent = (float)basicsalary*10/100;
                    int finalpercenrrent=Math.round(percentagerent);
                    minus=rentpaid-finalpercenrrent;
                    if(minus>0)
                    {
                        String excess=String.valueOf(minus);
                        tv_excess.setText(excess);
                    }
                    else
                    {
                        minus=0;
                        String excess=String.valueOf(minus);
                        tv_excess.setText(excess);

                    }

                    //Calcutate hra basic salary...
                    if(scityspinner.equals("Others"))
                    {
                        //calculate 40% hra..
                        // double percentagehra = (float) total * 100 / 200000;
                        // percentagehra = Math.round(basicsalary*10/100);
                        percentagehra = (float)basicsalary*40/100;
                        percenthra=Math.round(percentagehra);
                        finalhra=String.valueOf(percenthra);
                        tv_metro.setText(finalhra);

                        optimummaximumamount=Math.min(percenthra,actualhra)+finalpercenrrent;
                        //String smaxamt=String.valueOf(maximumamount);

                        if(rentpaid>optimummaximumamount)
                        {
                            int imaximumamount=rentpaid-optimummaximumamount;
                            maximumamount=Math.abs(imaximumamount);
                            DecimalFormat formatter = new DecimalFormat("#,###,###");
                            String smaxamt = formatter.format(optimummaximumamount);
                            String ssmaxamt=formatter.format(maximumamount);
                            pricemax="<font color='#7dc243'>"+"₹"+smaxamt+"</font>";
                            pricemaxmax="<font color='#7dc243'>"+"₹"+ssmaxamt+"</font>";

                           // finalmax=max1+pricemax+max3+max4+pricemaxmax+max5;
                            finalmax=max1+pricemax+max3+excess1+pricemaxmax+excess2;

                            maximumtax.setText(Html.fromHtml(finalmax));
                        }
                        else if(rentpaid==optimummaximumamount)
                        {
                            int imaximumamount=rentpaid-optimummaximumamount;
                            maximumamount=Math.abs(imaximumamount);

                            DecimalFormat formatter = new DecimalFormat("#,###,###");
                            String smaxamt = formatter.format(optimummaximumamount);
                           // String ssmaxamt=formatter.format(maximumamount);
                            pricemax="<font color='#7dc243'>"+"₹"+smaxamt+"</font>";
                           // pricemaxmax="<font color='#7dc243'>"+"₹"+ssmaxamt+"</font>";*/

                            // finalmax=max1+pricemax+max3+max4+pricemaxmax+max5;
                            finalmax=max1+pricemax+max3+excessequal;

                            maximumtax.setText(Html.fromHtml(finalmax));
                        }
                        else
                        {
                            int imaximumamount=rentpaid-optimummaximumamount;
                            maximumamount=Math.abs(imaximumamount);

                            DecimalFormat formatter = new DecimalFormat("#,###,###");
                            String smaxamt = formatter.format(optimummaximumamount);
                            String ssmaxamt=formatter.format(maximumamount);
                            pricemax="<font color='#7dc243'>"+"₹"+smaxamt+"</font>";
                            pricemaxmax="<font color='#7dc243'>"+"₹"+ssmaxamt+"</font>";

                             finalmax=max1+pricemax+max3+max4+pricemaxmax+max5;
                          //  finalmax=max1+pricemax+max3+excessequal;

                            maximumtax.setText(Html.fromHtml(finalmax));
                        }


                    }
                    else
                    {
                        //calculate 50% hra..
                        //Toast.makeText(getApplicationContext(),"Else city",Toast.LENGTH_SHORT).show();
                        percentagehra = (float)basicsalary*50/100;
                        percenthra=Math.round(percentagehra);
                        finalhra=String.valueOf(percenthra);
                        tv_metro.setText(finalhra);

                        optimummaximumamount=Math.min(percenthra,actualhra)+finalpercenrrent;
                        //String smaxamt=String.valueOf(maximumamount);
                     /*   int imaximumamount=rentpaid-optimummaximumamount;
                        maximumamount=Math.abs(imaximumamount);
                        DecimalFormat formatter = new DecimalFormat("#,###,###");
                        String smaxamt = formatter.format(optimummaximumamount);
                        String ssmaxamt=formatter.format(maximumamount);

                        pricemax="<font color='#7dc243'>"+"₹"+smaxamt+"</font>";
                        pricemaxmax="<font color='#7dc243'>"+"₹"+ssmaxamt+"</font>";

                        finalmax=max1+pricemax+max3+max4+pricemaxmax+max5;
                        maximumtax.setText(Html.fromHtml(finalmax));*/


                        if(rentpaid>optimummaximumamount)
                        {
                            int imaximumamount=rentpaid-optimummaximumamount;
                            maximumamount=Math.abs(imaximumamount);
                            DecimalFormat formatter = new DecimalFormat("#,###,###");
                            String smaxamt = formatter.format(optimummaximumamount);
                            String ssmaxamt=formatter.format(maximumamount);
                            pricemax="<font color='#7dc243'>"+"₹"+smaxamt+"</font>";
                            pricemaxmax="<font color='#7dc243'>"+"₹"+ssmaxamt+"</font>";

                            // finalmax=max1+pricemax+max3+max4+pricemaxmax+max5;
                            finalmax=max1+pricemax+max3+excess1+pricemaxmax+excess2;

                            maximumtax.setText(Html.fromHtml(finalmax));
                        }
                        else if(rentpaid==optimummaximumamount)
                        {
                            int imaximumamount=rentpaid-optimummaximumamount;
                            maximumamount=Math.abs(imaximumamount);

                            DecimalFormat formatter = new DecimalFormat("#,###,###");
                            String smaxamt = formatter.format(optimummaximumamount);
                            // String ssmaxamt=formatter.format(maximumamount);
                            pricemax="<font color='#7dc243'>"+"₹"+smaxamt+"</font>";
                            // pricemaxmax="<font color='#7dc243'>"+"₹"+ssmaxamt+"</font>";*/

                            // finalmax=max1+pricemax+max3+max4+pricemaxmax+max5;
                            finalmax=max1+pricemax+max3+excessequal;

                            maximumtax.setText(Html.fromHtml(finalmax));
                        }
                        else
                        {
                            int imaximumamount=rentpaid-optimummaximumamount;
                            maximumamount=Math.abs(imaximumamount);

                            DecimalFormat formatter = new DecimalFormat("#,###,###");
                            String smaxamt = formatter.format(optimummaximumamount);
                            String ssmaxamt=formatter.format(maximumamount);
                            pricemax="<font color='#7dc243'>"+"₹"+smaxamt+"</font>";
                            pricemaxmax="<font color='#7dc243'>"+"₹"+ssmaxamt+"</font>";

                            finalmax=max1+pricemax+max3+max4+pricemaxmax+max5;
                            //  finalmax=max1+pricemax+max3+excessequal;

                            maximumtax.setText(Html.fromHtml(finalmax));
                        }

                    }

                    int compareone=Math.min(percenthra,actualhra);
                    amountexcemption=Math.min(compareone,minus);
                    String fixamountexcemption=String.valueOf(amountexcemption);
                    tv_amountexception.setText(fixamountexcemption);

                }
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                int position=spinner.getSelectedItemPosition();
                scityspinner=spinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });


        done_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(databasecount>0)
                {
                    Log.d("Insert: ", "Inserting ..");
                    //  public HRAPojo(String _androidid, int _basicsalary, int _hra, int _rentpaid, String _accomodationcity, int _excemption, int _metrobsalary, int _actualreceived, int _excessrentpaid, int _maximumtax) {
                    // databaseHandler.addInvestment(new InvestmentScreenPojo(android_Id,ipf,ilife,iho,ich,istamp,inational,iother,total,investtl,setpercent));
                    databaseHandler.updateHRA(new HRAPojo(android_Id, basicsalary, actualhra, rentpaid, scityspinner, amountexcemption, percenthra, actualhra, minus, optimummaximumamount,maximumamount));
                    isInternetPresent = cd.isConnectingToInternet();
                    if(isInternetPresent)
                    {
                        new HRAWebservice().execute(android_Id,String.valueOf(basicsalary),String.valueOf(actualhra),scityspinner,String.valueOf(optimummaximumamount),String.valueOf(rentpaid),String.valueOf(amountexcemption),String.valueOf(maximumamount),String.valueOf(percenthra),String.valueOf(actualhra),String.valueOf(minus));
                    }
                    else
                    {

                    }
                    Intent gotoback=new Intent(HRAActivity.this,InvestmentTabActivity.class);
                    gotoback.putExtra("camefromhra","camefromhra");
                    //gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(gotoback);
                }
                else
                {
                    //Insert values in database.....
                    Log.d("Insert: ", "Inserting ..");
                    if(basicsalary==0&&actualhra==0&&rentpaid==0)
                    {

                    }
                    else
                    {
                        databaseHandler.addHRA(new HRAPojo(android_Id, basicsalary, actualhra, rentpaid, scityspinner, amountexcemption, percenthra, actualhra, minus, optimummaximumamount,maximumamount));
                        // get Internet status
                        isInternetPresent = cd.isConnectingToInternet();
                        if(isInternetPresent)
                        {
                            new HRAWebservice().execute(android_Id,String.valueOf(basicsalary),String.valueOf(actualhra),scityspinner,String.valueOf(optimummaximumamount),String.valueOf(rentpaid),String.valueOf(amountexcemption),String.valueOf(maximumamount),String.valueOf(percenthra),String.valueOf(actualhra),String.valueOf(minus));
                        }
                        else
                        {

                        }
                    }
                    //List<InvestmentScreenPojo> investmentList = databaseHandler.getAllInvestment();

                    Intent gotoback=new Intent(HRAActivity.this,InvestmentTabActivity.class);
                    gotoback.putExtra("camefromhra","camefromhra");
                    //gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(gotoback);
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                // API 5+ solution
                //onBackPressed();

                if(databasecount>0)
                {
                    Log.d("Insert: ", "Inserting ..");
                    //  public HRAPojo(String _androidid, int _basicsalary, int _hra, int _rentpaid, String _accomodationcity, int _excemption, int _metrobsalary, int _actualreceived, int _excessrentpaid, int _maximumtax) {
                    // databaseHandler.addInvestment(new InvestmentScreenPojo(android_Id,ipf,ilife,iho,ich,istamp,inational,iother,total,investtl,setpercent));
                    databaseHandler.updateHRA(new HRAPojo(android_Id,basicsalary,actualhra,rentpaid,scityspinner,amountexcemption,percenthra,actualhra,minus,optimummaximumamount,maximumamount));
                    isInternetPresent = cd.isConnectingToInternet();
                    if(isInternetPresent)
                    {


                        new HRAWebservice().execute(android_Id,String.valueOf(basicsalary),String.valueOf(actualhra),scityspinner,String.valueOf(optimummaximumamount),String.valueOf(rentpaid),String.valueOf(amountexcemption),String.valueOf(maximumamount),String.valueOf(percenthra),String.valueOf(actualhra),String.valueOf(minus));
                    }
                    else
                    {

                    }

                    Intent gotoback=new Intent(HRAActivity.this,InvestmentTabActivity.class);
                    gotoback.putExtra("camefromhra","camefromhra");
                    //gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(gotoback);

                }
                else
                {
                    //Insert values in database.....
                    Log.d("Insert: ", "Inserting ..");
                    if(basicsalary==0&&actualhra==0&&rentpaid==0)
                    {

                    }
                    else
                    {
                        databaseHandler.addHRA(new HRAPojo(android_Id,basicsalary,actualhra,rentpaid,scityspinner,amountexcemption,percenthra,actualhra,minus,optimummaximumamount,maximumamount));

                        isInternetPresent = cd.isConnectingToInternet();
                        if(isInternetPresent)
                        {


                          new HRAWebservice().execute(android_Id,String.valueOf(basicsalary),String.valueOf(actualhra),scityspinner,String.valueOf(optimummaximumamount),String.valueOf(rentpaid),String.valueOf(amountexcemption),String.valueOf(maximumamount),String.valueOf(percenthra),String.valueOf(actualhra),String.valueOf(minus));
                        }
                        else
                        {

                        }
                    }
                    //List<InvestmentScreenPojo> investmentList = databaseHandler.getAllInvestment();

                    Intent gotoback=new Intent(HRAActivity.this,InvestmentTabActivity.class);
                    gotoback.putExtra("camefromhra","camefromhra");
                    //gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(gotoback);
                }

                return true;
            // case R.id.action_settings:
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class HRAWebservice extends AsyncTask<String,String,String>
    {
        //api/profile/Post_HRARecord

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
                String sbasicsalary=(String)args0[1];
                String sactualhra=(String)args0[2];
                String scityspinner=(String)args0[3];
                String soptimumrent=(String)args0[4];
                String srentpaid=(String)args0[5];
                String samountexcemption=(String)args0[6];
                String soptimummaximumamount=(String)args0[7];
                String spercenthra=(String)args0[8];
                String sactualhra1=(String)args0[9];
                String sminus=(String)args0[10];

                String link="http://tax.hrblock.in/Mobile_App_service/api/profile/Post_HRARecord/";
                String data= URLEncoder.encode("Device_id", "UTF-8")+"="+URLEncoder.encode(deviceidstring,"UTF-8");
                data += "&"+URLEncoder.encode("Basic_salary","UTF-8")+"="+URLEncoder.encode(sbasicsalary,"UTF-8");
                data += "&"+URLEncoder.encode("HRA","UTF-8")+"="+URLEncoder.encode(sactualhra,"UTF-8");
                data += "&"+URLEncoder.encode("accommodation","UTF-8")+"="+URLEncoder.encode(scityspinner,"UTF-8");
                data += "&"+URLEncoder.encode("optimum_rent","UTF-8")+"="+URLEncoder.encode(soptimumrent,"UTF-8");
                data += "&"+URLEncoder.encode("rent_paid","UTF-8")+"="+URLEncoder.encode(srentpaid,"UTF-8");
                data += "&"+URLEncoder.encode("exemption","UTF-8")+"="+URLEncoder.encode(samountexcemption,"UTF-8");
                data += "&"+URLEncoder.encode("Max_tax_benefit","UTF-8")+"="+URLEncoder.encode(soptimummaximumamount,"UTF-8");
                data += "&"+URLEncoder.encode("basic_40","UTF-8")+"="+URLEncoder.encode(spercenthra,"UTF-8");
                data += "&"+URLEncoder.encode("actual_hra_received","UTF-8")+"="+URLEncoder.encode(sactualhra1,"UTF-8");
                data += "&"+URLEncoder.encode("excess_rent_paid","UTF-8")+"="+URLEncoder.encode(sminus,"UTF-8");


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
          //  Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
        }
    }

}
