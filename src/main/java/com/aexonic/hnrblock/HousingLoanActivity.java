package com.aexonic.hnrblock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by Parikshit Patil on 12/18/2015.
 */
public class HousingLoanActivity extends ActionBarActivity
{
    Toolbar toolbar;
    Button done_btn;
    TextView hslemailid,plznoteprice;
    String hsl1="If you have let out the property or own multiple properties, do get in touch with us on info@hrblock.in to understand the tax implications of such properties.";

    //Please note that you can get a maximum benefit of ₹ 200,000 on the interest paid towards housing loan payment for a self-occupied property.
    String plzn1="Please note that you can get a maximum benefit of ";
    String plzn2="₹200,000 ";
    String plz3="on the interest payable towards your housing loan for a self-occupied property.";
    String plzcolor,plzfinaltxt;
    EditText et_totalhousingloan;
    String housingloan;
    int housingloanint=0;
    public static final String PREFS_NAME = "LoginPrefs";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    String android_Id;
    DatabaseHandler databaseHandler;
    int databasecount;
    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.housing_loan_layout);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        cd=new ConnectionDetector(getApplicationContext());

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        android_Id=settings.getString("android_Id","");
        databaseHandler=new DatabaseHandler(this);

         //List<HousingLoanPojo> housingList = databaseHandler.getAllHousing();


        et_totalhousingloan=(EditText)findViewById(R.id.et_totalhousingloan);
        plznoteprice=(TextView)findViewById(R.id.plznoteprice);
        plzcolor="<font color='#7dc243'>"+plzn2+"</font>";
        plzfinaltxt=plzn1+plzcolor+plz3;
        plznoteprice.setText(Html.fromHtml(plzfinaltxt));


        hslemailid=(TextView)findViewById(R.id.hslemailid);
        hslemailid.setText(hsl1);
        hslemailid.setLinkTextColor(getResources().getColor(R.color.greencolor));
        Linkify.addLinks(hslemailid,Linkify.EMAIL_ADDRESSES);


        databasecount=databaseHandler.getHOUSINGLOANCount();

        if(databasecount>0)
        {
            List<HousingLoanPojo> housingLoanPojoList = databaseHandler.getAllHousing();
            for (HousingLoanPojo cn : housingLoanPojoList)
            {
                android_Id=cn.get_androidid();
                housingloanint=cn.get_housingloan();
            }

            if(housingloanint<1)
            {
                et_totalhousingloan.setText("");
            }
            else
            {
                et_totalhousingloan.setText(String.valueOf(housingloanint));
            }
        }


        done_btn=(Button)findViewById(R.id.done_btn);
        done_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                housingloan=et_totalhousingloan.getText().toString();
                if(databasecount>0)
                {
                    if(!housingloan.equals(""))
                    {
                        housingloanint = Integer.parseInt(housingloan);
                       /* if (housingloanint > 200000)
                        {
                            Toast.makeText(getApplicationContext(), "Enter housing loan upto 200000 only", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {*/
                            databaseHandler.updateHOUSINGLOAN(new HousingLoanPojo(android_Id, housingloanint));
                            //onBackPressed();
                            isInternetPresent=cd.isConnectingToInternet();

                            if(isInternetPresent)
                            {
                                new HousingLoanWebservice().execute(android_Id,String.valueOf(housingloanint));
                            }
                            else
                            {
                                //no Internet
                            }
                            Intent gotoback=new Intent(HousingLoanActivity.this,InvestmentTabActivity.class);
                            gotoback.putExtra("camefromhousing","camefromhousing");
                          //  gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(gotoback);
                        //}
                    }
                    else
                    {
                        housingloanint=0;
                        databaseHandler.updateHOUSINGLOAN(new HousingLoanPojo(android_Id, housingloanint));
                        isInternetPresent=cd.isConnectingToInternet();

                        if(isInternetPresent)
                        {
                            new HousingLoanWebservice().execute(android_Id,String.valueOf(housingloanint));
                        }
                        else
                        {
                            //no Internet
                        }
                        //onBackPressed();
                        Intent gotoback=new Intent(HousingLoanActivity.this,InvestmentTabActivity.class);
                        gotoback.putExtra("camefromhousing","camefromhousing");
                       // gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                      //  gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(gotoback);
                    }

                }
                else
                {

                    if(!housingloan.equals(""))
                    {
                        housingloanint = Integer.parseInt(housingloan);
                       /* if(housingloanint>200000)
                        {
                            Toast.makeText(getApplicationContext(), "Enter housing loan upto 200000 only", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {*/
                            //save to database....
                            databaseHandler.addHOUSING(new HousingLoanPojo(android_Id, housingloanint));
                            isInternetPresent=cd.isConnectingToInternet();

                            if(isInternetPresent)
                            {
                                new HousingLoanWebservice().execute(android_Id,String.valueOf(housingloanint));
                            }
                            else
                            {
                              //no Internet
                            }

                            // List<MedicalReiumPojo> medicallist = databaseHandler.getAllMedical();
                           // onBackPressed();
                            Intent gotoback=new Intent(HousingLoanActivity.this,InvestmentTabActivity.class);
                            gotoback.putExtra("camefromhousing","camefromhousing");
                           // gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           // gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(gotoback);
                        //}
                    }
                    else
                    {
                        //onBackPressed();
                        Intent gotoback=new Intent(HousingLoanActivity.this,InvestmentTabActivity.class);
                        gotoback.putExtra("camefromhousing","camefromhousing");
                       // gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       // gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(gotoback);
                    }
                }

            }
        });
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

                housingloan=et_totalhousingloan.getText().toString();
                if(databasecount>0)
                {
                    if(!housingloan.equals(""))
                    {
                        housingloanint = Integer.parseInt(housingloan);
                        /*if (housingloanint > 200000)
                        {
                            Toast.makeText(getApplicationContext(), "Enter housing loan upto 200000 only", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {*/
                            databaseHandler.updateHOUSINGLOAN(new HousingLoanPojo(android_Id, housingloanint));
                           // onBackPressed();
                            isInternetPresent=cd.isConnectingToInternet();

                            if(isInternetPresent)
                            {
                                new HousingLoanWebservice().execute(android_Id,String.valueOf(housingloanint));
                            }
                            else
                            {
                                //no Internet
                            }
                            Intent gotoback=new Intent(HousingLoanActivity.this,InvestmentTabActivity.class);
                            gotoback.putExtra("camefromhousing","camefromhousing");
                          //  gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                          //  gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(gotoback);
                        //}
                    }
                    else
                    {
                        housingloanint=0;

                        databaseHandler.updateHOUSINGLOAN(new HousingLoanPojo(android_Id, housingloanint));

                        //isInternetPresent=cd.isConnectingToInternet();

                        if(isInternetPresent)
                        {
                            new HousingLoanWebservice().execute(android_Id,String.valueOf(housingloanint));
                        }
                        else
                        {
                            //no Internet
                        }

                       // onBackPressed();
                        Intent gotoback=new Intent(HousingLoanActivity.this,InvestmentTabActivity.class);
                        gotoback.putExtra("camefromhousing","camefromhousing");
                    //    gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     //   gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(gotoback);
                    }

                }
                else
                {

                    if(!housingloan.equals(""))
                    {
                        housingloanint = Integer.parseInt(housingloan);
                        /*if(housingloanint>200000)
                        {
                            Toast.makeText(getApplicationContext(), "Enter housing loan upto 200000 only", Toast.LENGTH_SHORT).show();
                        }*/
                        //else
                       // {
                            //save to database....
                            databaseHandler.addHOUSING(new HousingLoanPojo(android_Id, housingloanint));
                            // List<MedicalReiumPojo> medicallist = databaseHandler.getAllMedical();
                            isInternetPresent=cd.isConnectingToInternet();

                            if(isInternetPresent)
                            {
                                new HousingLoanWebservice().execute(android_Id,String.valueOf(housingloanint));
                            }
                            else
                            {
                                //no Internet
                            }

                            //onBackPressed();
                            Intent gotoback=new Intent(HousingLoanActivity.this,InvestmentTabActivity.class);
                            gotoback.putExtra("camefromhousing","camefromhousing");
                           // gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                           // gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(gotoback);
                        //}
                    }
                    else
                    {
                        //onBackPressed();
                        Intent gotoback=new Intent(HousingLoanActivity.this,InvestmentTabActivity.class);
                        gotoback.putExtra("camefromhousing","camefromhousing");
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(gotoback);
                    }
                }

                return true;
            //case R.id.action_settings:
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class HousingLoanWebservice extends AsyncTask<String,String,String>
    {
       // api/profile/Post_HLRecord/

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
                String housingloanvalue=(String)args0[1];
                //  String s=(String)args0[2];

                String link="http://tax.hrblock.in/Mobile_App_service/api/profile/Post_HLRecord/";
                String data= URLEncoder.encode("Device_id", "UTF-8")+"="+URLEncoder.encode(deviceidstring,"UTF-8");
                data += "&"+URLEncoder.encode("Housing_loan_amount","UTF-8")+"="+URLEncoder.encode(housingloanvalue,"UTF-8");

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
           //Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
        }
    }

}
