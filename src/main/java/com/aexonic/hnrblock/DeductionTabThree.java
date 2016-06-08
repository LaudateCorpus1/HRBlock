package com.aexonic.hnrblock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Parikshit Patil on 12/17/2015.
 */
public class DeductionTabThree extends Fragment
{
    int color;
    LinearLayout housingloanclick,otherdeductionrowclick,educationloanclick,donationclick,medicalinsuranceclick;

    Toolbar toolbar;
    TextView toolbartv;
    public static final String PREFS_NAME = "LoginPrefs";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    String android_Id;

    public DeductionTabThree()
    {

    }

    @SuppressLint("ValidFragment")
    public DeductionTabThree(int color)
    {
        this.color = color;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.deduction_tab_threefragment, container, false);

       /* toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbartv=(TextView)toolbar.findViewById(R.id.toolbartv);

        String heading="My Deduction";
        toolbartv.setText(heading);*/
        settings=getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        android_Id=settings.getString("android_Id","");

        housingloanclick=(LinearLayout)view.findViewById(R.id.housingloanclick);
        otherdeductionrowclick=(LinearLayout)view.findViewById(R.id.otherdeductionrowclick);
        educationloanclick=(LinearLayout)view.findViewById(R.id.educationloanclick);
        donationclick=(LinearLayout)view.findViewById(R.id.donationclick);
        medicalinsuranceclick=(LinearLayout)view.findViewById(R.id.medicalinsuranceclick);

        housingloanclick.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editor = settings.edit();
                editor.putString("android_Id",android_Id);
                editor.commit();
                Intent gotohousingloan=new Intent(getActivity(),HousingLoanActivity.class);
                startActivity(gotohousingloan);
            }
        });

        otherdeductionrowclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent gotoother=new Intent(getActivity(),OtherDeductionActivity.class);
                startActivity(gotoother);
            }
        });

        educationloanclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent gotoeducationloan=new Intent(getActivity(),EducationLoanActivity.class);
                startActivity(gotoeducationloan);
            }
        });

        donationclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent gotodeduction=new Intent(getActivity(),DonationActivity.class);
                startActivity(gotodeduction);
            }
        });

        medicalinsuranceclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                editor = settings.edit();
                editor.putString("android_Id",android_Id);
                editor.commit();
                Intent gotomedicalinsurance=new Intent(getActivity(),MedicalInsuranceActivity.class);
                startActivity(gotomedicalinsurance);
            }
        });

        return view;
    }
}
