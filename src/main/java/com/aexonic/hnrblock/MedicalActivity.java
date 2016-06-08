package com.aexonic.hnrblock;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
public class MedicalActivity extends ActionBarActivity
{
    Toolbar toolbar;
    Button done_btn;
   // You can submit bills for medical expenses up to ₹ 15000 to your employer.
    TextView medicaltxt;
    String md1="You can submit bills for medical expenses up to ";
    String md2="₹15000 ";
    String md3="to your employer.";
    String mdcolor,finalmdtxt;
    EditText et_totalmedical;
    String totalmedical;
    int medicalint=0;
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
        setContentView(R.layout.medical_layout);

        //hide keyboard code....
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        cd=new ConnectionDetector(getApplicationContext());
        mdcolor="<font color='#7dc243'>"+md2+"</font>";
        finalmdtxt=md1+mdcolor+md3;
        medicaltxt=(TextView)findViewById(R.id.medicaltxt);
        medicaltxt.setText(Html.fromHtml(finalmdtxt));

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        android_Id=settings.getString("android_Id","");
        databaseHandler=new DatabaseHandler(this);

        et_totalmedical=(EditText)findViewById(R.id.et_totalmedical);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        done_btn=(Button)findViewById(R.id.done_btn);


        databasecount=databaseHandler.getMEDICALREIMCount();
/*
        if(databasecount>0)
        {
            List<MedicalReiumPojo> medicallist = databaseHandler.getAllMedical();
            for (MedicalReiumPojo cn : medicallist)
            {
                android_Id=cn.get_androidid();
                medicalint=cn.get_medicalexpense();
            }
            if(medicalint<1)
            {
                et_totalmedical.setText("");
            }
            else
            {
                et_totalmedical.setText(String.valueOf(15000-medicalint));
            }
        }
*/

        if(databasecount>0)
        {
            List<MedicalReiumPojo> medicallist = databaseHandler.getAllMedical();
            for (MedicalReiumPojo cn : medicallist)
            {
                android_Id=cn.get_androidid();
                medicalint=cn.get_medicalexpense();
            }
            if(medicalint<1)
            {
                et_totalmedical.setText("");
            }
            else if(medicalint==15000)
            {
                et_totalmedical.setText("15000");
            }
            else if(medicalint>1)
            {
                et_totalmedical.setText(String.valueOf(medicalint));
            }

        }


        done_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                totalmedical=et_totalmedical.getText().toString();

                if(databasecount>0)
                {
                    if(!totalmedical.equals(""))
                    {
                        int medicalintnew = Integer.parseInt(totalmedical);


                        if (medicalintnew > 15000)
                        {
                            Toast.makeText(getApplicationContext(),"Enter amount up to ₹15000 only", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //medicalint=15000-medicalintnew;
                            medicalint=medicalintnew;

                            databaseHandler.updateMedical(new MedicalReiumPojo(android_Id, medicalint));
                            //onBackPressed();
                            isInternetPresent=cd.isConnectingToInternet();

                            if(isInternetPresent)
                            {
                                new MedicalWebservice().execute(android_Id,String.valueOf(medicalint));
                            }
                            else
                            {
                                //no Internet
                            }
                            Intent gotoback=new Intent(MedicalActivity.this,InvestmentTabActivity.class);
                            gotoback.putExtra("camefrommedicalreim","camefrommedicalreim");
                            gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(gotoback);
                        }
                    }
                    else
                    {
                        //medicalint=15000;
                        medicalint=0;
                        databaseHandler.updateMedical(new MedicalReiumPojo(android_Id, medicalint));
                       // onBackPressed();
                        isInternetPresent=cd.isConnectingToInternet();

                        if(isInternetPresent)
                        {
                           new MedicalWebservice().execute(android_Id,String.valueOf(medicalint));
                        }
                        else
                        {
                            //no Internet
                        }
                        Intent gotoback=new Intent(MedicalActivity.this,InvestmentTabActivity.class);
                        gotoback.putExtra("camefrommedicalreim","camefrommedicalreim");
                        gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(gotoback);
                    }
                }
                else
                {
                    if(!totalmedical.equals(""))
                    {
                        int medicalintnew = Integer.parseInt(totalmedical);
                        if(medicalintnew>15000)
                        {
                            Toast.makeText(getApplicationContext(), "Enter amount up to ₹15000 only", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //save to database....
                           // medicalint=15000-medicalintnew;
                            medicalint=medicalintnew;

                            databaseHandler.addMEDICAL(new MedicalReiumPojo(android_Id, medicalint));
                            isInternetPresent=cd.isConnectingToInternet();

                            if(isInternetPresent)
                            {
                               new MedicalWebservice().execute(android_Id,String.valueOf(medicalint));
                            }
                            else
                            {
                                //no Internet
                            }
                            // List<MedicalReiumPojo> medicallist = databaseHandler.getAllMedical();
                            //onBackPressed();
                            Intent gotoback=new Intent(MedicalActivity.this,InvestmentTabActivity.class);
                            gotoback.putExtra("camefrommedicalreim","camefrommedicalreim");
                            gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(gotoback);
                        }
                    }
                    else
                    {
                        //onBackPressed();
                        Intent gotoback=new Intent(MedicalActivity.this,InvestmentTabActivity.class);
                        gotoback.putExtra("camefrommedicalreim","camefrommedicalreim");
                        gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

                totalmedical=et_totalmedical.getText().toString();

                if(databasecount>0)
                {
                    if(!totalmedical.equals(""))
                    {
                        int medicalintnew = Integer.parseInt(totalmedical);
                        if (medicalintnew > 15000)
                        {
                            Toast.makeText(getApplicationContext(),"Enter amount up to ₹15000 only", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //medicalint=15000-medicalintnew;
                            medicalint=medicalintnew;


                            databaseHandler.updateMedical(new MedicalReiumPojo(android_Id, medicalint));
                           // onBackPressed();
                            isInternetPresent=cd.isConnectingToInternet();

                            if(isInternetPresent)
                            {
                               new MedicalWebservice().execute(android_Id,String.valueOf(medicalint));
                            }
                            else
                            {
                                //no Internet
                            }
                            Intent gotoback=new Intent(MedicalActivity.this,InvestmentTabActivity.class);
                           gotoback.putExtra("camefrommedicalreim","camefrommedicalreim");
                            //gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(gotoback);
                        }
                    }
                    else
                    {
                        medicalint=0;
                        databaseHandler.updateMedical(new MedicalReiumPojo(android_Id, medicalint));
                        isInternetPresent=cd.isConnectingToInternet();

                        if(isInternetPresent)
                        {
                            new MedicalWebservice().execute(android_Id,String.valueOf(medicalint));
                        }
                        else
                        {
                            //no Internet
                        }
                       // onBackPressed();
                        Intent gotoback=new Intent(MedicalActivity.this,InvestmentTabActivity.class);
                        gotoback.putExtra("camefrommedicalreim","camefrommedicalreim");
                       // gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(gotoback);
                    }
                }
                else
                {
                    if(!totalmedical.equals(""))
                    {
                        int medicalintnew = Integer.parseInt(totalmedical);
                        if(medicalintnew>15000)
                        {
                            Toast.makeText(getApplicationContext(), "Enter amount up to ₹15000 only", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            //save to database....
                           // medicalint=15000-medicalintnew;
                            medicalint=medicalintnew;


                            databaseHandler.addMEDICAL(new MedicalReiumPojo(android_Id, medicalint));
                            isInternetPresent=cd.isConnectingToInternet();

                            if(isInternetPresent)
                            {
                               new MedicalWebservice().execute(android_Id,String.valueOf(medicalint));
                            }
                            else
                            {
                                //no Internet
                            }
                            // List<MedicalReiumPojo> medicallist = databaseHandler.getAllMedical();
                            //onBackPressed();
                            Intent gotoback=new Intent(MedicalActivity.this,InvestmentTabActivity.class);
                            gotoback.putExtra("camefrommedicalreim","camefrommedicalreim");
                            //gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(gotoback);
                        }
                    }
                    else
                    {
                        ///onBackPressed();
                        Intent gotoback=new Intent(MedicalActivity.this,InvestmentTabActivity.class);
                        gotoback.putExtra("camefrommedicalreim","camefrommedicalreim");
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(gotoback);
                    }

                }

                return true;
            // case R.id.action_settings:
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private class MedicalWebservice extends AsyncTask<String,String,String>
    {
        //api/profile/Post_MedicalRecord

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
                String medicalexpensesvalue=(String)args0[1];

                String link="http://tax.hrblock.in/Mobile_App_service/api/profile/Post_MedicalRecord/";
                String data= URLEncoder.encode("Device_id", "UTF-8")+"="+URLEncoder.encode(deviceidstring,"UTF-8");
                data += "&"+URLEncoder.encode("Medical_expences","UTF-8")+"="+URLEncoder.encode(medicalexpensesvalue,"UTF-8");

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
