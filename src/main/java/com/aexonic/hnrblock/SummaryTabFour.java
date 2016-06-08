package com.aexonic.hnrblock;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Parikshit Patil on 12/18/2015.
 */
public class SummaryTabFour extends Fragment
{
    int color;
    TextView investmenttv,hratv,ltatv,medicaltv;
    Toolbar toolbar;
    TextView toolbartv;

    String inv1="You have utilized ";// 80% of your investments which will fetch you tax benefits. You can invest ";
    String invpercent="0";
    String inv2=" of your investments which will fetch you tax benefits. You can invest ";
    String inv3="₹";
    String inv4="150000 ";
    String inv5=" more to maximize tax benefits.";
    String rcolorinv,pricecolorinv,pricecolorpercent,finalinv;
    TextView investmenttxt;



   //At ₹ 50000 p.m you get a maximum tax benifit out of your.
   /* String hra1="At ";
    String hra2="₹";
    String hra3="0";
    String hra4=" per month you get a maximum tax benefit.";
*/
    String hra1="At a rent of ";
    String hra2="₹";
    String hra3="0";
    String hra4=" per month you maximize your tax benefit.";
    String pricecolorhra,finalhra;
    TextView hratxt;

    //You can go on vacation for ₹ 5000 more times which you can utilize by 00-Tan-00 and this will fetch you tax benifit.Do not forget to preserve your travels bills.
    //You can go on a tax free vacation XXX which you can utilize by 31st December, 2017. Do not forget to preserve your travel bills.
    String lta1="You can go on a tax free vacation ";
    String lta2="₹";
    String lta3="XXX";
    String lta4=" which you can utilize by 31st December, 2017. Do not forget to preserve your travel bills. ";
    String pricelta,finallta;
    TextView ltatxt;


    //You can claim ₹ 5000 amount more towards medical expenses which can include chemist bills,hospital bills.Don't forget to preserve these bills.
    String medical1="You can claim ";
    String medical2="₹";
    String medical3="15000";
    String medical4=" more towards medical expenses which can include chemist bills, hospital bills. Don't forget to preserve these bills.";
    String pricemedical,finalmedical;
    TextView medicaltxt;


    //You can pay health insurance premium up to ₹ 25000 to maximize your tax benifit.
    String etd1="You can pay an additional health insurance premium upto ";
    String etd2="₹";
    String etd3="XXX";
    String etd4=" to maximize your tax benefit. ";
    String priceetd,finaletd;
    TextView etdtv;

   /* <TextView
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:textColor="@color/textcolorfaint"
    android:textSize="15sp"
    android:text="You can claim deduction upto ₹25,000 for health insurance premium paid for self, spouse and children."/>*/

    String etd11="You can claim deduction upto ";
    String etd22="₹25,000";
    String etd33=" for health insurance premium paid for self, spouse and children.";
    String priceetdno,finaletdno;


    //You are paying an interest on housing loan of ₹ XXXXX
    String hli1="You are paying an interest on housing loan of ";
    String hli2="₹";
    String hli3="0";
    String hli4=". Deduction is capped at ₹2,00,000 for self-occupied property.";
    String pricehli,finalhli;
    TextView hlitv;
    int databasecountinvestment;
    int databasecounthra;
    int databasecountlta;
    int databasecountmedicalreim;
    int databasecounthousingloan;
    int databasecountmedicalinsurance;

    DatabaseHandler databaseHandler;
    int ipf;//=0;
    int ich;//=0;
    int iho;//=0;
    int ilife;//=0;
    int istamp;//=0;
    int inational;//=0;
    int iother;//=0;
    int investtl;//=0;
    int total;//=0;
    int setpercent;//=0;
    String android_Id;

    int amountexcemption=0;
    int optimumhra=0;
    String claimnumber;
    int _medicalexpense;
    int _housingloan;
    int _premiumpaid;

    Button email_btn;
    ConnectionDetector cd;
    Boolean isInternetPresent = false;

    String getinvestment,gethra,getlta,getmedicalreuim,gethla,getetd,onenoselect;
    ImageButton emailsharebtn,messagesharebtn;
    LinearLayout hrahide,housingloanhide,ltahide;
    Button nothanksbtn;
    String id1;

    public SummaryTabFour()
    {

    }

    @SuppressLint("ValidFragment")
    public SummaryTabFour(int color)
    {
        this.color = color;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.summary_tab_four_fragmentv1, container, false);

        databaseHandler=new DatabaseHandler(getActivity());

        cd=new ConnectionDetector(getActivity());

        email_btn=(Button)view.findViewById(R.id.email_btn);
        hrahide=(LinearLayout)view.findViewById(R.id.hrahide);
        ltahide=(LinearLayout)view.findViewById(R.id.ltahide);
        housingloanhide=(LinearLayout)view.findViewById(R.id.housingloanhide);

        databasecountinvestment=databaseHandler.getInvestmentCount();
        databasecounthra=databaseHandler.getHRACount();
        databasecountlta=databaseHandler.getLTACount();
        databasecountmedicalreim=databaseHandler.getMEDICALREIMCount();
        databasecounthousingloan=databaseHandler.getHOUSINGLOANCount();
        databasecountmedicalinsurance=databaseHandler.getMEDICALINSURANCECount();
        //ltahide.setVisibility(View.VISIBLE);

        if(databasecountinvestment>0)
        {

            List<InvestmentScreenPojo> investmentScreenPojoList = databaseHandler.getAllInvestment();

            for (InvestmentScreenPojo cn : investmentScreenPojoList)
            {
                android_Id=cn.get_androidid();
                ipf=cn.get_pfdeducted();
                ilife=cn.get_lifeinsurance();
                iho=cn.get_housingloan();
                ich=cn.get_childrentution();
                istamp=cn.get_stampduty();
                iother=cn.get_otherinvestment();
                total=cn.get_totalinvestment();
                investtl=cn.get_saveinvestment();
                setpercent=cn.get_percentage();
            }

            invpercent=String.valueOf(setpercent);

            /*if(investtl>=150000)
            {
                investtl=0;
                inv4=String.valueOf(investtl);
                inv2=" of your investments which will fetch you tax benefits.";
                pricecolorpercent="<font color='#7dc243'>"+invpercent+"%"+"</font>";
               // pricecolorinv="<font color='#7dc243'>"+inv3+inv4+"</font>";
                finalinv=inv1+pricecolorpercent+inv2;
                investmenttxt=(TextView)view.findViewById(R.id.investmenttxt);
                investmenttxt.setText(Html.fromHtml(finalinv));
                Toast.makeText(getActivity(),"SSSS",Toast.LENGTH_SHORT).show();


            }*/
            if(investtl>0)
            {
                inv4=String.valueOf(investtl);
                pricecolorpercent="<font color='#7dc243'>"+invpercent+"%"+"</font>";
                pricecolorinv="<font color='#7dc243'>"+inv3+inv4+"</font>";
                finalinv=inv1+pricecolorpercent+inv2+pricecolorinv+inv5;
                investmenttxt=(TextView)view.findViewById(R.id.investmenttxt);
                investmenttxt.setText(Html.fromHtml(finalinv));

            }
            else if(investtl==0)
            {
                investtl=0;
                inv4=String.valueOf(investtl);
                inv2=" of your investments which will fetch you tax benefits.";
                pricecolorpercent="<font color='#7dc243'>"+invpercent+"%"+"</font>";
                // pricecolorinv="<font color='#7dc243'>"+inv3+inv4+"</font>";
                finalinv=inv1+pricecolorpercent+inv2;
                investmenttxt=(TextView)view.findViewById(R.id.investmenttxt);
                investmenttxt.setText(Html.fromHtml(finalinv));
                //Toast.makeText(getActivity(),"SSSS",Toast.LENGTH_SHORT).show();
            }
            else
            {
                investtl=150000;
                inv4=String.valueOf(investtl);
                pricecolorpercent="<font color='#7dc243'>"+invpercent+"%"+"</font>";
                pricecolorinv="<font color='#7dc243'>"+inv3+inv4+"</font>";
                finalinv=inv1+pricecolorpercent+inv2+pricecolorinv+inv5;
                investmenttxt=(TextView)view.findViewById(R.id.investmenttxt);
                investmenttxt.setText(Html.fromHtml(finalinv));

            }


        }
        else
        {
            pricecolorpercent="<font color='#7dc243'>"+invpercent+"%"+"</font>";
            pricecolorinv="<font color='#7dc243'>"+inv3+inv4+"</font>";
            finalinv=inv1+pricecolorpercent+inv2+pricecolorinv+inv5;
            investmenttxt=(TextView)view.findViewById(R.id.investmenttxt);
            investmenttxt.setText(Html.fromHtml(finalinv));
        }


        if(databasecounthra>0)
        {
            List<HRAPojo> hraPojoList = databaseHandler.getAllHra();
            for (HRAPojo cn : hraPojoList)
            {
                android_Id=cn.get_androidid();
                amountexcemption=cn.get_excemption();
                optimumhra=cn.get_maximumtax();
            }

            hrahide.setVisibility(View.VISIBLE);

            hra3=String.valueOf(optimumhra);
            pricecolorhra="<font color='#7dc243'>"+hra2+hra3+"</font>";
            finalhra=hra1+pricecolorhra+hra4;
            hratxt=(TextView)view.findViewById(R.id.housingloantxt);
            hratxt.setText(Html.fromHtml(finalhra));

        }
        else
        {
            pricecolorhra="<font color='#7dc243'>"+hra2+hra3+"</font>";
            finalhra=hra1+pricecolorhra+hra4;
            hratxt=(TextView)view.findViewById(R.id.housingloantxt);
            hratxt.setText(Html.fromHtml(finalhra));
        }


        if(databasecountlta>0)
        {
            List<LTAPojo> ltaPojoList = databaseHandler.getAllLta();
            for (LTAPojo cn : ltaPojoList)
            {

                android_Id=cn.get_androidid();
                claimnumber=cn.get_claimnumber();
                onenoselect=cn.get_islta();

            }

            if(claimnumber==null||claimnumber.equals("null")&&onenoselect.equals("No"))
            {
                ltahide.setVisibility(View.GONE);
                claimnumber="XXX";
                pricelta="<font color='#7dc243'>"+claimnumber+"</font>";
                finallta=lta1+pricelta+lta4;
                ltatxt=(TextView)view.findViewById(R.id.leavetraveltxt);
                ltatxt.setText(Html.fromHtml(finallta));
                //Toast.makeText(getActivity(),"ZERO",Toast.LENGTH_SHORT).show();
            }
            else
            {
                ltahide.setVisibility(View.VISIBLE);
                //claimnumber="TWO";
                if(claimnumber.equals("ONE"))
                {
                    claimnumber="ONCE";
                    pricelta="<font color='#7dc243'>"+claimnumber+"</font>";
                    finallta=lta1+pricelta+lta4;
                    ltatxt=(TextView)view.findViewById(R.id.leavetraveltxt);
                    ltatxt.setText(Html.fromHtml(finallta));
                }
                else
                {
                    pricelta="<font color='#7dc243'>"+claimnumber+"</font>";
                    finallta=lta1+pricelta+lta4;
                    ltatxt=(TextView)view.findViewById(R.id.leavetraveltxt);
                    ltatxt.setText(Html.fromHtml(finallta));
                }


                //Toast.makeText(getActivity(),"ZERO else",Toast.LENGTH_SHORT).show();
            }

        }
        else
        {
            pricelta="<font color='#7dc243'>"+lta3+"</font>";
            finallta=lta1+pricelta+lta4;
            ltatxt=(TextView)view.findViewById(R.id.leavetraveltxt);
            ltatxt.setText(Html.fromHtml(finallta));
        }

/*
        if(databasecountmedicalreim>0)
        {
            List<MedicalReiumPojo> medicalReiumPojoList = databaseHandler.getAllMedical();
            for (MedicalReiumPojo cn : medicalReiumPojoList)
            {

                android_Id=cn.get_androidid();
                _medicalexpense=cn.get_medicalexpense();

            }

            if(_medicalexpense>0)
            {
                medical3=String.valueOf(_medicalexpense);
                pricemedical="<font color='#7dc243'>"+medical2+medical3+"</font>";
                finalmedical=medical1+pricemedical+medical4;
                medicaltxt=(TextView)view.findViewById(R.id.medicaltxt);
                medicaltxt.setText(Html.fromHtml(finalmedical));
            }
            else if(_medicalexpense==0)
            {
                _medicalexpense=15000;
                medical3=String.valueOf(_medicalexpense);
                pricemedical="<font color='#7dc243'>"+medical2+medical3+"</font>";
                finalmedical=medical1+pricemedical+medical4;
                medicaltxt=(TextView)view.findViewById(R.id.medicaltxt);
                medicaltxt.setText(Html.fromHtml(finalmedical));
            }
            else if(_medicalexpense<0)
            {
                _medicalexpense=0;
                medical3=String.valueOf(_medicalexpense);
                pricemedical="<font color='#7dc243'>"+medical2+medical3+"</font>";
                finalmedical=medical1+pricemedical+medical4;
                medicaltxt=(TextView)view.findViewById(R.id.medicaltxt);
                medicaltxt.setText(Html.fromHtml(finalmedical));
            }
            else
            {
                _medicalexpense=15000;
                medical3=String.valueOf(_medicalexpense);
                pricemedical="<font color='#7dc243'>"+medical2+medical3+"</font>";
                finalmedical=medical1+pricemedical+medical4;
                medicaltxt=(TextView)view.findViewById(R.id.medicaltxt);
                medicaltxt.setText(Html.fromHtml(finalmedical));
            }


        }
        else
        {
            pricemedical="<font color='#7dc243'>"+medical2+medical3+"</font>";
            finalmedical=medical1+pricemedical+medical4;
            medicaltxt=(TextView)view.findViewById(R.id.medicaltxt);
            medicaltxt.setText(Html.fromHtml(finalmedical));
        }
*/


/*
        if(databasecountmedicalreim>0)
        {
            List<MedicalReiumPojo> medicalReiumPojoList = databaseHandler.getAllMedical();
            for (MedicalReiumPojo cn : medicalReiumPojoList)
            {

                android_Id=cn.get_androidid();
                _medicalexpense=cn.get_medicalexpense();

            }

            if(_medicalexpense>0)
            {
                medical3=String.valueOf(_medicalexpense);
                pricemedical="<font color='#7dc243'>"+medical2+medical3+"</font>";
                finalmedical=medical1+pricemedical+medical4;
                medicaltxt=(TextView)view.findViewById(R.id.medicaltxt);
                medicaltxt.setText(Html.fromHtml(finalmedical));
            }
            else
            {
                _medicalexpense=15000;
                medical3=String.valueOf(_medicalexpense);
                pricemedical="<font color='#7dc243'>"+medical2+medical3+"</font>";
                finalmedical=medical1+pricemedical+medical4;
                medicaltxt=(TextView)view.findViewById(R.id.medicaltxt);
                medicaltxt.setText(Html.fromHtml(finalmedical));
            }


        }
        else
        {
            pricemedical="<font color='#7dc243'>"+medical2+medical3+"</font>";
            finalmedical=medical1+pricemedical+medical4;
            medicaltxt=(TextView)view.findViewById(R.id.medicaltxt);
            medicaltxt.setText(Html.fromHtml(finalmedical));
        }
*/


        if(databasecountmedicalreim>0)
        {
            List<MedicalReiumPojo> medicalReiumPojoList = databaseHandler.getAllMedical();
            for (MedicalReiumPojo cn : medicalReiumPojoList)
            {

                android_Id=cn.get_androidid();
                _medicalexpense=cn.get_medicalexpense();

            }

            if(_medicalexpense>0)
            {
                int getvalue=15000-_medicalexpense;
                medical3=String.valueOf(getvalue);
                pricemedical="<font color='#7dc243'>"+medical2+medical3+"</font>";
                finalmedical=medical1+pricemedical+medical4;
                medicaltxt=(TextView)view.findViewById(R.id.medicaltxt);
                medicaltxt.setText(Html.fromHtml(finalmedical));
            }
            else if(_medicalexpense==15000)
            {
                _medicalexpense=0;
                medical3=String.valueOf(_medicalexpense);
                pricemedical="<font color='#7dc243'>"+medical2+medical3+"</font>";
                finalmedical=medical1+pricemedical+medical4;
                medicaltxt=(TextView)view.findViewById(R.id.medicaltxt);
                medicaltxt.setText(Html.fromHtml(finalmedical));
            }
            else if(_medicalexpense==0)
            {
                _medicalexpense=15000;
                medical3=String.valueOf(_medicalexpense);
                pricemedical="<font color='#7dc243'>"+medical2+medical3+"</font>";
                finalmedical=medical1+pricemedical+medical4;
                medicaltxt=(TextView)view.findViewById(R.id.medicaltxt);
                medicaltxt.setText(Html.fromHtml(finalmedical));
            }


        }
        else
        {
            pricemedical="<font color='#7dc243'>"+medical2+medical3+"</font>";
            finalmedical=medical1+pricemedical+medical4;
            medicaltxt=(TextView)view.findViewById(R.id.medicaltxt);
            medicaltxt.setText(Html.fromHtml(finalmedical));
        }


        if(databasecounthousingloan>0)
        {
            housingloanhide.setVisibility(View.VISIBLE);
            List<HousingLoanPojo> housingLoanPojoList = databaseHandler.getAllHousing();
            for (HousingLoanPojo cn : housingLoanPojoList)
            {
                android_Id=cn.get_androidid();
                _housingloan=cn.get_housingloan();
            }

            hli3=String.valueOf(_housingloan);
            pricehli="<font color='#7dc243'>"+hli2+hli3+"</font>";
            finalhli=hli1+pricehli+hli4;
            hlitv=(TextView)view.findViewById(R.id.housingloaninterenttxt);
            hlitv.setText(Html.fromHtml(finalhli));
        }
        else
        {
            housingloanhide.setVisibility(View.GONE);

            pricehli="<font color='#7dc243'>"+hli2+hli3+"</font>";
            finalhli=hli1+pricehli+hli4;
            hlitv=(TextView)view.findViewById(R.id.housingloaninterenttxt);
            hlitv.setText(Html.fromHtml(finalhli));
        }

        if(databasecountmedicalinsurance>0)
        {
            List<HealthInsurancePojo> healthInsurancePojoList = databaseHandler.getAllHealthInsurance();
            for (HealthInsurancePojo cn : healthInsurancePojoList)
            {
                android_Id=cn.get_androidid();
                _premiumpaid=cn.get_premiumpaid();
                id1=cn.get_didhealth();
            }

            if(id1!=null)
            {
                if(id1.equals("No"))
                {
                    //String etd11="You can claim deduction upto ";
                   // String etd22="₹25,000";
                    //String etd33=" for health insurance premium paid for self, spouse and children.";
                    //String priceetdno,finaletdno;
                    etd1="You can claim deduction upto ";
                    etd3="₹25,000";
                    etd4=" for health insurance premium paid for self, spouse and children.";

                    priceetd="<font color='#7dc243'>"+etd3+"</font>";
                    finaletd=etd1+priceetd+etd4;
                    etdtv=(TextView)view.findViewById(R.id.eightydtxt);
                    etdtv.setText(Html.fromHtml(finaletd));
                }
                else
                {
                    etd3=String.valueOf(_premiumpaid);
                    priceetd="<font color='#7dc243'>"+etd2+etd3+"</font>";
                    finaletd=etd1+priceetd+etd4;
                    etdtv=(TextView)view.findViewById(R.id.eightydtxt);
                    etdtv.setText(Html.fromHtml(finaletd));
                }

            }
            else
            {
                etd3=String.valueOf(_premiumpaid);
                priceetd="<font color='#7dc243'>"+etd2+etd3+"</font>";
                finaletd=etd1+priceetd+etd4;
                etdtv=(TextView)view.findViewById(R.id.eightydtxt);
                etdtv.setText(Html.fromHtml(finaletd));
            }

        }
        else
        {
            priceetd="<font color='#7dc243'>"+etd2+etd3+"</font>";
            finaletd=etd1+priceetd+etd4;
            etdtv=(TextView)view.findViewById(R.id.eightydtxt);
            etdtv.setText(Html.fromHtml(finaletd));
        }

        email_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                isInternetPresent = cd.isConnectingToInternet();

                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.sharedialog);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.R.color.transparent));

                emailsharebtn=(ImageButton)dialog.findViewById(R.id.emailsharebtn);
                nothanksbtn=(Button)dialog.findViewById(R.id.no_thanks);

                emailsharebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                       // Toast.makeText(getActivity(),"Email share",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Intent.ACTION_SEND);
                        intent.setType("plain/text");
                        intent.putExtra(Intent.EXTRA_EMAIL, new String[] { "" });
                        intent.putExtra(Intent.EXTRA_SUBJECT, "Start saving taxes with H&R Block–Income Tax App");
                        intent.putExtra(Intent.EXTRA_TEXT, "Hello,\n" +
                                "\n" +
                                "I just downloaded H&R Block–Income Tax App on my Android phone.\n" +
                                "\n" +
                                "It not only lets me track my investments but also recommends various investment options based on my tax situation which helps me save taxes. You should try it as well. It’s 100% Free without any annoying ads!\n" +
                                "\n" +
                                "Get your android app now http://bit.ly/iTaxApp");
                        startActivity(Intent.createChooser(intent, ""));

                        dialog.dismiss();
                    }
                });

                messagesharebtn=(ImageButton)dialog.findViewById(R.id.messagesharebtn);
                messagesharebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        //Toast.makeText(getActivity(),"Message Click",Toast.LENGTH_SHORT).show();
                        //Start saving taxes with H&R Block’s Free Android App – a one stop solution for your income tax needs. Get it now <link>", "default content");
                        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                        if(sendIntent!=null)
                        {
                            try
                            {
                                sendIntent.putExtra("sms_body", "Start saving taxes with H&R Block–Income Tax Free Android App – a one stop solution for your income tax needs. Get it now http://bit.ly/HRBApp");
                                sendIntent.setType("vnd.android-dir/mms-sms");
                                startActivity(sendIntent);
                            }
                            catch (ActivityNotFoundException exception)
                            {
                                exception.printStackTrace();
                            }
                        }

                        dialog.dismiss();
                    }
                });

                nothanksbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

                    }
                });

                dialog.show();

                if(isInternetPresent)
                {
                    getinvestment=investmenttxt.getText().toString();
                    gethra=hratxt.getText().toString();
                    getlta=ltatxt.getText().toString();
                    getmedicalreuim=medicaltxt.getText().toString();
                    gethla=hlitv.getText().toString();
                    getetd=etdtv.getText().toString();

                   new SummaryWebservice().execute(android_Id,getinvestment,gethra,getlta,getmedicalreuim,gethla,getetd);

                }
                else
                {

                }

            }
        });

        return view;
    }

    private class SummaryWebservice extends AsyncTask<String,String,String>
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
                String sinvestment=(String)args0[1];
                String shra=(String)args0[2];
                String slta=(String)args0[3];
                String smedical=(String)args0[4];
                String shla=(String)args0[5];
                String setd=(String)args0[6];

                String link="http://tax.hrblock.in/Mobile_App_service/api/profile/Post_SummaryRecord/";
                String data= URLEncoder.encode("Device_id", "UTF-8")+"="+URLEncoder.encode(deviceidstring,"UTF-8");
                data += "&"+URLEncoder.encode("Investment_summary","UTF-8")+"="+URLEncoder.encode(sinvestment,"UTF-8");
                data += "&"+URLEncoder.encode("Hra_summary","UTF-8")+"="+URLEncoder.encode(shra,"UTF-8");
                data += "&"+URLEncoder.encode("LTA_summary","UTF-8")+"="+URLEncoder.encode(slta,"UTF-8");
                data += "&"+URLEncoder.encode("Medical_summary","UTF-8")+"="+URLEncoder.encode(smedical,"UTF-8");
                data += "&"+URLEncoder.encode("Housing_summary","UTF-8")+"="+URLEncoder.encode(shla,"UTF-8");
                data += "&"+URLEncoder.encode("medicalinsurance_summary","UTF-8")+"="+URLEncoder.encode(setd,"UTF-8");

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
            //Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
        }
    }


}
