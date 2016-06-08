package com.aexonic.hnrblock;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Parikshit Patil on 12/16/2015.
 */
public class InvestmentScreenActivity extends ActionBarActivity
{
    Toolbar toolbar;
    TextView tv_total;
    TextView democlick;
    TextView percentagetv;
    TextView price1;
    TextView investtotal;
    TextView stampque;
    TextView otherque;
    TextView nationalque;
    Button how_btn;
    RoundCornerProgressBar roundCornerProgressBar;
    EditText etpf,etchild,ethousing,etlife,etstamp,etnational,etother;
    String spf,sch,sho,slife,sstamp,snational,sother;
    TextView childque;
    int ipf;//=0;
    int ich;//=0;
    int iho;//=0;
    int ilife;//=0;
    int istamp;//=0;
   // int inational;//=0;
    int iother;//=0;
    int investtl;//=0;
    int total;//=0;
    int setpercent;//=0;
    //Stamp duty &amp; registration fees (?)

    int pf=0;
    int lip=0;
    int hlp=0;
    int ctf=0;
    int stamp=0;
    int nps=0;
    int otherinv=0;
    int totalinv=0;
    int save=0;
    int perc=0;

    String sdt1="Stamp duty &amp; registration fees ";
    String sdt2="(?)";
    String sdt3="0000";
    String pricesdt,finalsdt;
    //National pension scheme
    String nps1="National Pension Scheme ";
    String nps2="(?)";
    String nps3="0000";
    String pricenps,finalnps;

    //You can invest 0 more to save taxes
    String inv1="You can invest ";
    String rrr="₹";
    String inv2="0";
    String inv3=" more to save taxes.";
    String priceinv,finalinv;
    public static final String PREFS_NAME = "LoginPrefs";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    int databasecount;


    DatabaseHandler databaseHandler;
    String android_Id;
    List<InvestmentScreenPojo> investmentList;
    List<InvestmentScreenPojo> newinvestlist;
    InvestmentScreenPojo investmentScreenPojo;

    //getdata variables from sqlite.....
             int _invid;
            String _androidid;
            int _pfdeducted;
            int _lifeinsurance;
            int _housingloan;
            int _childrentution;
            int _stampduty;
            int _national;
            int _otherinvestment;
            int _totalinvestment;
            int _saveinvestment;
            int _percentage;
          int _investtl=0;

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;
    //int databasecount;
    int _id;
    String _fullname;
    String _birthday;
    String _email;
    String _phonenumber;
    String _imagestring;
    String _androidId;

    LinearLayout remaininglayout;
    String key="hnrblock123@612#";
    String decodemobilenumber;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.investment_screen_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        databaseHandler=new DatabaseHandler(this);

        //hide keyboard code....
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        tv_total = (TextView) findViewById(R.id.tv_total);
        price1 = (TextView) findViewById(R.id.price1);
        percentagetv = (TextView) findViewById(R.id.percentage);
        roundCornerProgressBar = (RoundCornerProgressBar) findViewById(R.id.roundedcornerpb);
        remaininglayout=(LinearLayout)findViewById(R.id.remaininglayout);
       // democlick = (TextView) findViewById(R.id.democlick);
        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        android_Id=settings.getString("android_Id","");

        databasecount=databaseHandler.getProfileCount();

        if(databasecount>0)
        {

            List<ProfilePojo> profilePojoList = databaseHandler.getAllProfile();

            for (ProfilePojo cn : profilePojoList)
            {
                // String imageString=cn.get_imagestring();
                //Toast.makeText(getApplicationContext(),imageString,Toast.LENGTH_SHORT).show();
                _androidId=cn.get_andoid_id();
                _fullname=cn.get_fullname();
                _birthday=cn.get_birthday();
                _email=cn.get_email();
                _phonenumber=cn.get_phonenumber();
                _imagestring=cn.get_imagestring();

            }
            try
            {
                decodemobilenumber = SimpleCryptoNew.decrypt(key, _phonenumber);
            }
            catch (Exception e)
            {

            }

            if(decodemobilenumber!=null)
            {
                ParseUtils.subscribeWithPhoneNumber(decodemobilenumber);

            }

        }


        databaseHandler=new DatabaseHandler(this);

        databasecount=databaseHandler.getInvestmentCount();
        // creating connection detector class instance
        cd = new ConnectionDetector(getApplicationContext());

        //Toast.makeText(getApplicationContext(),""+databasecount,Toast.LENGTH_SHORT).show();
        investmentScreenPojo=new InvestmentScreenPojo();

        //investmentList = databaseHandler.getAllInvestment();

        investtotal = (TextView) findViewById(R.id.investtotal);
        priceinv="<font color='#7dc243'>"+rrr+inv2+"</font>";
        finalinv=inv1+priceinv+inv3;
        investtotal.setText(Html.fromHtml(finalinv));

        childque = (TextView) findViewById(R.id.childque);
        stampque=(TextView)findViewById(R.id.stampque);
        pricesdt="<font color='#7dc243'>"+sdt2+"</font>";
        finalsdt=sdt1+pricesdt;
        stampque.setText(Html.fromHtml(finalsdt));

        otherque=(TextView)findViewById(R.id.otherque);
        //nationalque=(TextView)findViewById(R.id.nationalque);
        pricenps="<font color='#7dc243'>"+nps2+"</font>";
        finalnps=nps1+pricenps;
       // nationalque.setText(Html.fromHtml(finalnps));
        how_btn=(Button)findViewById(R.id.how_btn);

        //EditTextID....
        etpf = (EditText) findViewById(R.id.et_pf);
        etchild = (EditText) findViewById(R.id.et_ch);
        ethousing = (EditText) findViewById(R.id.et_house);
        etlife = (EditText) findViewById(R.id.et_life);
        etstamp = (EditText) findViewById(R.id.et_stamp);
        //etnational = (EditText) findViewById(R.id.et_national);
        etother = (EditText) findViewById(R.id.et_other);


        if(databasecount>0)
        {
           // for retrive data.....
           // databaseHandler.addInvestment(new InvestmentScreenPojo(android_Id,ipf,ilife,iho,ich,istamp,inational,iother,total,investtl,setpercent));
            List<InvestmentScreenPojo> investmentScreenPojoList = databaseHandler.getAllInvestment();

            for (InvestmentScreenPojo cn : investmentScreenPojoList)
            {

                android_Id=cn.get_androidid();
                ipf=cn.get_pfdeducted();
                ilife=cn.get_lifeinsurance();
                iho=cn.get_housingloan();
                ich=cn.get_childrentution();
                istamp=cn.get_stampduty();
               // inational=cn.get_national();
                iother=cn.get_otherinvestment();
                total=cn.get_totalinvestment();
                investtl=cn.get_saveinvestment();
                setpercent=cn.get_percentage();

            }

            if(investtl>0)
            {
                remaininglayout.setVisibility(View.VISIBLE);
            }

            if(ipf<1)
            {
                etpf.setText("");
            }
            else
            {
                etpf.setText(String.valueOf(ipf));
            }
            if(ilife<1)
            {
                etlife.setText("");
            }
            else
            {
                etlife.setText(String.valueOf(ilife));
            }
            if(iho<1)
            {
                ethousing.setText("");

            }
            else
            {
                ethousing.setText(String.valueOf(iho));

            }
            if(ich<1)
            {
                etchild.setText("");

            }
            else
            {
                etchild.setText(String.valueOf(ich));

            }
            if(istamp<1)
            {
                etstamp.setText("");

            }
            else
            {
                etstamp.setText(String.valueOf(istamp));

            }
           /* if(inational<1)
            {
                etnational.setText("");

            }
            else
            {
                etnational.setText(String.valueOf(inational));

            }*/
            if(iother<1)
            {
                etother.setText(String.valueOf(iother));

            }
            else
            {
                etother.setText(String.valueOf(iother));

            }
            //tv_total.setText(String.valueOf(_totalinvestment));
            percentagetv.setText(setpercent + "%");

            if(total==0)
            {
                remaininglayout.setVisibility(View.GONE);

            }

            else if(total<150000)
            {
               // float finalpercentage = (float) total * 100 / 200000;
                remaininglayout.setVisibility(View.VISIBLE);

                roundCornerProgressBar.setProgress(setpercent);
                DecimalFormat formatter = new DecimalFormat("#,###,###");
                String finaltotalofall = formatter.format(total);
                tv_total.setText(finaltotalofall);
                price1.setText(finaltotalofall);

                if(total<1)
                {
                    //investtl=200000-total;
                   // DecimalFormat newformatter = new DecimalFormat("#,###,###");
                    //String finalinvestment = newformatter.format(investtl);
                    String newfinalinvestment="<font color='#7dc243'>"+rrr+"0"+"</font>";
                    finalinv=inv1+newfinalinvestment+inv3;
                    investtotal.setText(Html.fromHtml(finalinv));
                    how_btn.setText("Where should I invest?");
                }
                else
                {
                    investtl=150000-total;
                    DecimalFormat newformatter = new DecimalFormat("#,###,###");
                    String finalinvestment = newformatter.format(investtl);
                    String newfinalinvestment="<font color='#7dc243'>"+rrr+finalinvestment+"</font>";
                    finalinv=inv1+newfinalinvestment+inv3;
                    investtotal.setText(Html.fromHtml(finalinv));
                    how_btn.setText("Where should I invest?");
                }

            }
            else
            {
                remaininglayout.setVisibility(View.VISIBLE);

                total=150000;
                float finalpercentage = (float) total * 100 / 150000;
                roundCornerProgressBar.setProgress(finalpercentage);


                DecimalFormat formatter = new DecimalFormat("#,###,###");
                String finaltotalofall = formatter.format(total);
                tv_total.setText(finaltotalofall);
                price1.setText(finaltotalofall);
                // Toast.makeText(getApplicationContext(),"Price:"+finaltotalofall,Toast.LENGTH_SHORT).show();
                investtl=0;
                investtotal.setText("You have fully utilized your tax saving investments.");
                how_btn.setText("See other investment options");
            }
        }

        //Dialog Code......
        childque.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Dialog dialog = new Dialog(InvestmentScreenActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.childrentutiondialog);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

               /* TextView ok=(TextView)dialog.findViewById(R.id.Ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.dismiss();
                    }
                });*/

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


        stampque.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Dialog dialog = new Dialog(InvestmentScreenActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.stampdutydialog);

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

        otherque.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Dialog dialog = new Dialog(InvestmentScreenActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.otherdialog);

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


/*
        nationalque.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Dialog dialog = new Dialog(InvestmentScreenActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.nationaldialog);

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



        //pf deducted
        etpf.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {
                // String spf,sch,sho,slife,sstamp,snational,sother;
            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(editable.length()==0)
                {
                  // Toast.makeText(getApplicationContext(),"LENGTH:"+editable.length(),Toast.LENGTH_SHORT).show();
                    ipf = 0;
                    CalculateTotal(ipf, ich, iho, ilife, istamp, iother);
                }
                else
                {
                    spf = etpf.getText().toString();
                    if (!spf.equals(""))
                    {
                        ipf = (Integer.parseInt(spf));
                        CalculateTotal(ipf, ich, iho, ilife, istamp, iother);
                    }
                    else
                    {
                        ipf = 0;
                       // CalculateTotal(ipf, ich, iho, ilife, istamp, inational, iother);
                    }
                }

            }
        });

        //life insurance
        etlife.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {



            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(editable.length()==0)
                {
                    ilife = 0;
                    CalculateTotal(ipf, ich, iho, ilife, istamp, iother);

                }
                else
                {
                    slife = etlife.getText().toString();
                    if (!slife.equals("")) {
                        ilife = Integer.parseInt(slife);
                        CalculateTotal(ipf, ich, iho, ilife, istamp, iother);

                    } else {
                        ilife = 0;
                    }
                }

            }
        });

        //housing loan
        ethousing.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(editable.length()==0)
                {
                    iho = 0;
                    CalculateTotal(ipf, ich, iho, ilife, istamp, iother);
                }
                else
                {
                    sho = ethousing.getText().toString();
                    if (!sho.equals("")) {
                        iho = Integer.parseInt(sho);
                        CalculateTotal(ipf, ich, iho, ilife, istamp, iother);

                    } else {
                        iho = 0;
                    }
                }
            }
        });

        //children tution fee
        etchild.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(editable.length()==0)
                {
                    ich = 0;
                    CalculateTotal(ipf, ich, iho, ilife, istamp, iother);

                }
                else
                {
                    sch = etchild.getText().toString();
                    if (!sch.equals("")) {
                        ich = Integer.parseInt(sch);
                        CalculateTotal(ipf, ich, iho, ilife, istamp, iother);
                    } else {
                        ich = 0;
                    }

                }

            }
        });


        //Stamp duty...
        etstamp.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(editable.length()==0)
                {
                    istamp = 0;
                    CalculateTotal(ipf, ich, iho, ilife, istamp, iother);

                }
                else
                {
                    sstamp = etstamp.getText().toString();
                    if (!sstamp.equals("")) {
                        istamp = Integer.parseInt(sstamp);
                        CalculateTotal(ipf, ich, iho, ilife, istamp, iother);

                    } else {
                        istamp = 0;
                    }
                }

            }
        });


        //National pension....
/*
        etnational.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {

            }

            @Override
            public void afterTextChanged(Editable editable)
            {
                if(editable.length()==0)
                {
                    inational = 0;
                    CalculateTotal(ipf, ich, iho, ilife, istamp, inational, iother);
                }
                else
                {
                    snational = etnational.getText().toString();
                    if (!snational.equals(""))
                    {
                        inational = Integer.parseInt(snational);
                        if(inational<50000)
                        {
                            CalculateTotal(ipf, ich, iho, ilife, istamp, inational, iother);
                        }
                        else
                        {
                            inational=50000;
                            CalculateTotal(ipf, ich, iho, ilife, istamp, inational, iother);
                        }
                    }
                    else
                    {
                        inational = 0;
                    }
                }
            }
        });
*/

        //Other...
        etother.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3)
            {

            }
            @Override
            public void afterTextChanged(Editable editable)
            {
                if(editable.length()==0)
                {
                    iother = 0;
                    CalculateTotal(ipf, ich, iho, ilife, istamp, iother);
                }
                else
                {
                    sother = etother.getText().toString();
                    if (!sother.equals("")) {
                        iother = Integer.parseInt(sother);
                        CalculateTotal(ipf, ich, iho, ilife, istamp, iother);

                    } else {
                        iother = 0;
                    }
                }
            }
        });

        how_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(databasecount>0)
                {
                    //Insert values in database.....
                    Log.d("Insert: ", "Inserting ..");

                    databaseHandler.updateInvestment(new InvestmentScreenPojo(android_Id,ipf,ilife,iho,ich,istamp,iother,total,investtl,setpercent));

                    //List<InvestmentScreenPojo> investmentList = databaseHandler.getAllInvestment();
                  isInternetPresent = cd.isConnectingToInternet();
                    if(isInternetPresent)
                    {
                        new InvestmentWebservice().execute(android_Id,String.valueOf(setpercent),String.valueOf(ipf),String.valueOf(ich),String.valueOf(iho),String.valueOf(ilife),String.valueOf(istamp),String.valueOf(iother),String.valueOf(total),String.valueOf(investtl));
                    }
                    else
                    {

                    }
                    editor = settings.edit();
                    editor.putInt("housingloan",iho);
                    editor.putInt("investment",investtl);
                    editor.putInt("total",total);
                    editor.putString("android_Id",android_Id);
                    editor.commit();

                    Intent gototab=new Intent(InvestmentScreenActivity.this,InvestmentTabActivity.class);
                    //gototab.putExtra("housingloan",iho);
                    // gototab.putExtra("investment",investtl);
                    startActivity(gototab);
                }
                else
                {
                    //Insert values in database.....
                    Log.d("Insert: ", "Inserting ..");
                    if(ipf==0&&ilife==0&&iho==0&&ich==0&&istamp==0&&iother==0&&total==0&&investtl==0&&setpercent==0)
                    {

                    }
                    else
                    {
                        databaseHandler.addInvestment(new InvestmentScreenPojo(android_Id,ipf,ilife,iho,ich,istamp,iother,total,investtl,setpercent));
                        // get Internet status
                        isInternetPresent = cd.isConnectingToInternet();
                        if(isInternetPresent)
                        {

                            new InvestmentWebservice().execute(android_Id,String.valueOf(setpercent),String.valueOf(ipf),String.valueOf(ich),String.valueOf(iho),String.valueOf(ilife),String.valueOf(istamp),String.valueOf(iother),String.valueOf(total),String.valueOf(investtl));
                        }
                        else
                        {

                        }
                    }
                    //List<InvestmentScreenPojo> investmentList = databaseHandler.getAllInvestment();
                    editor = settings.edit();
                    editor.putInt("housingloan",iho);
                    editor.putInt("investment",investtl);
                    editor.putInt("total",total);

                    editor.putString("android_Id",android_Id);
                    editor.commit();

                    Intent gototab=new Intent(InvestmentScreenActivity.this,InvestmentTabActivity.class);
                    //gototab.putExtra("housingloan",iho);
                    // gototab.putExtra("investment",investtl);
                    startActivity(gototab);
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                // API 5+ solution
               // onBackPressed();

               /* Intent gotoback=new Intent(InvestmentScreenActivity.this,HomeScreenActivity.class);
                gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(gotoback);*/

                if(databasecount>0)
                {
                    //Insert values in database.....
                    Log.d("Insert: ", "Inserting ..");

                    databaseHandler.updateInvestment(new InvestmentScreenPojo(android_Id,ipf,ilife,iho,ich,istamp,iother,total,investtl,setpercent));

                    //List<InvestmentScreenPojo> investmentList = databaseHandler.getAllInvestment();
                    editor = settings.edit();
                    editor.putInt("housingloan",iho);
                    editor.putInt("investment",investtl);
                    editor.putInt("total",total);

                    editor.putString("android_Id",android_Id);
                    editor.commit();
                    isInternetPresent = cd.isConnectingToInternet();
                    if(isInternetPresent)
                    {
                        new InvestmentWebservice().execute(android_Id,String.valueOf(setpercent),String.valueOf(ipf),String.valueOf(ich),String.valueOf(iho),String.valueOf(ilife),String.valueOf(istamp),String.valueOf(iother),String.valueOf(total),String.valueOf(investtl));
                    }
                    else
                    {

                    }
                    Intent gotoback=new Intent(InvestmentScreenActivity.this,HomeScreenActivity.class);
                    gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(gotoback);
                }
                else
                {
                    //Insert values in database.....
                    Log.d("Insert: ", "Inserting ..");
                    if(ipf==0&&ilife==0&&iho==0&&ich==0&&istamp==0&&iother==0&&total==0&&investtl==0&&setpercent==0)
                    {

                    }
                    else
                    {
                        databaseHandler.addInvestment(new InvestmentScreenPojo(android_Id,ipf,ilife,iho,ich,istamp,iother,total,investtl,setpercent));
                        // get Internet status
                        isInternetPresent = cd.isConnectingToInternet();
                        if(isInternetPresent)
                        {
                            new InvestmentWebservice().execute(android_Id,String.valueOf(setpercent),String.valueOf(ipf),String.valueOf(ich),String.valueOf(iho),String.valueOf(ilife),String.valueOf(istamp),String.valueOf(iother),String.valueOf(total),String.valueOf(investtl));
                        }
                        else
                        {

                        }

                    }
                    //List<InvestmentScreenPojo> investmentList = databaseHandler.getAllInvestment();
                    editor = settings.edit();
                    editor.putInt("housingloan",iho);
                    editor.putInt("investment",investtl);
                    editor.putInt("total",total);

                    editor.putString("android_Id",android_Id);
                    editor.commit();

                    Intent gotoback=new Intent(InvestmentScreenActivity.this,HomeScreenActivity.class);
                    gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(gotoback);
                }
               // finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void CalculateTotal(int ipf,int ich,int iho,int ilife,int istamp,int iother)
    {
        total=ipf+ich+iho+ilife+istamp+iother;
       // Toast.makeText(getApplicationContext(),total,Toast.LENGTH_SHORT).show();

        if(total==0)
        {
            remaininglayout.setVisibility(View.GONE);
             //Toast.makeText(getApplicationContext(),"GONE",Toast.LENGTH_SHORT).show();
            float finalpercentage = (float) total * 100 / 150000;
            //  roundCornerProgressBar.setProgress(finalpercentage);
            setpercent = Math.round(finalpercentage);
            percentagetv.setText(setpercent + "%");
            float progress=roundCornerProgressBar.getProgress();

            ProgressBarAnimation progressBarAnimation = new ProgressBarAnimation(roundCornerProgressBar, progress, finalpercentage);
            progressBarAnimation.setDuration(1000);
            roundCornerProgressBar.startAnimation(progressBarAnimation);

            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String finaltotalofall = formatter.format(total);
            tv_total.setText(finaltotalofall);
            price1.setText(finaltotalofall);
            // Toast.makeText(getApplicationContext(),"Price:"+finaltotalofall,Toast.LENGTH_SHORT).show();

             investtl=0;
            //priceinv=String.valueOf(investtl);
            DecimalFormat newformatter = new DecimalFormat("#,###,###");
            String finalinvestment = newformatter.format(investtl);
            String newfinalinvestment="<font color='#7dc243'>"+rrr+finalinvestment+"</font>";
            finalinv=inv1+newfinalinvestment+inv3;
            investtotal.setText(Html.fromHtml(finalinv));
            how_btn.setText("Where should I invest?");
            //how_btn.setVisibility(View.GONE);

        }

       else if(total<150000)
        {
            remaininglayout.setVisibility(View.VISIBLE);
            //Toast.makeText(getApplicationContext(),"<",Toast.LENGTH_SHORT).show();

            float finalpercentage = (float) total * 100 / 150000;
            //  roundCornerProgressBar.setProgress(finalpercentage);
            setpercent = Math.round(finalpercentage);
            percentagetv.setText(setpercent + "%");
            float progress=roundCornerProgressBar.getProgress();
           // Toast.makeText(getApplicationContext(),"Progress:"+progress,Toast.LENGTH_SHORT).show();

            ProgressBarAnimation progressBarAnimation = new ProgressBarAnimation(roundCornerProgressBar, progress, finalpercentage);
            progressBarAnimation.setDuration(1000);
            roundCornerProgressBar.startAnimation(progressBarAnimation);

            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String finaltotalofall = formatter.format(total);
            tv_total.setText(finaltotalofall);
            price1.setText(finaltotalofall);
           // Toast.makeText(getApplicationContext(),"Price:"+finaltotalofall,Toast.LENGTH_SHORT).show();

            investtl=150000-total;
            //priceinv=String.valueOf(investtl);
            DecimalFormat newformatter = new DecimalFormat("#,###,###");
            String finalinvestment = newformatter.format(investtl);
            String newfinalinvestment="<font color='#7dc243'>"+rrr+finalinvestment+"</font>";
            finalinv=inv1+newfinalinvestment+inv3;
            investtotal.setText(Html.fromHtml(finalinv));
            how_btn.setText("Where should I invest?");
            //how_btn.setVisibility(View.VISIBLE);

         }
        else
        {
            //Toast.makeText(getApplicationContext(),"You cannot invest more that 20 Lakhs",Toast.LENGTH_SHORT).show();
            remaininglayout.setVisibility(View.VISIBLE);
           // Toast.makeText(getApplicationContext(),"=",Toast.LENGTH_SHORT).show();

            total=150000;
            float finalpercentage = (float) total * 100 / 150000;
            //  roundCornerProgressBar.setProgress(finalpercentage);
            setpercent = Math.round(finalpercentage);
            percentagetv.setText(setpercent + "%");
            float progress=roundCornerProgressBar.getProgress();


            ProgressBarAnimation progressBarAnimation = new ProgressBarAnimation(roundCornerProgressBar, progress, finalpercentage);
            progressBarAnimation.setDuration(1000);
            roundCornerProgressBar.startAnimation(progressBarAnimation);

            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String finaltotalofall = formatter.format(total);
            tv_total.setText(finaltotalofall);
            price1.setText(finaltotalofall);
            // Toast.makeText(getApplicationContext(),"Price:"+finaltotalofall,Toast.LENGTH_SHORT).show();

            investtl=0;
            investtotal.setText("You have fully utilized your tax saving investments.");
            how_btn.setText("See other investment options");
            //how_btn.setVisibility(View.VISIBLE);

        }

    }

    private class InvestmentWebservice extends AsyncTask<String,String,String>
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
                String percentage=(String)args0[1];
                String spfdeduc=(String)args0[2];
                String schildt=(String)args0[3];
                String shousing=(String)args0[4];
                String slifeins=(String)args0[5];
                String sstampduty=(String)args0[6];
                String sotherinv=(String)args0[7];
                String stotal=(String)args0[8];
                String ssaving=(String)args0[9];

                String link="http://tax.hrblock.in/Mobile_App_service/api/profile/Post_InvestmentRecord/";
                String data= URLEncoder.encode("Device_id", "UTF-8")+"="+URLEncoder.encode(deviceidstring,"UTF-8");
                data += "&"+URLEncoder.encode("Progress","UTF-8")+"="+URLEncoder.encode(percentage,"UTF-8");
                data += "&"+URLEncoder.encode("PF_deduction","UTF-8")+"="+URLEncoder.encode(spfdeduc,"UTF-8");
                data += "&"+URLEncoder.encode("Tuition_fee","UTF-8")+"="+URLEncoder.encode(schildt,"UTF-8");
                data += "&"+URLEncoder.encode("Housing_loan","UTF-8")+"="+URLEncoder.encode(shousing,"UTF-8");
                data += "&"+URLEncoder.encode("LIC_premium","UTF-8")+"="+URLEncoder.encode(slifeins,"UTF-8");
                data += "&"+URLEncoder.encode("stamp_duty","UTF-8")+"="+URLEncoder.encode(sstampduty,"UTF-8");
                data += "&"+URLEncoder.encode("other","UTF-8")+"="+URLEncoder.encode(sotherinv,"UTF-8");
                data += "&"+URLEncoder.encode("total","UTF-8")+"="+URLEncoder.encode(stotal,"UTF-8");
                data += "&"+URLEncoder.encode("save_invest","UTF-8")+"="+URLEncoder.encode(ssaving,"UTF-8");

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
