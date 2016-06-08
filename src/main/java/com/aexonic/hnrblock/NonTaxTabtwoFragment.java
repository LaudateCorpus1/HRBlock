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
public class NonTaxTabtwoFragment extends Fragment
{
    int color;
    LinearLayout hrarowclick;
    LinearLayout medicalrowclick;
    LinearLayout otherrowclick;
    LinearLayout ltarowclick;

    Toolbar toolbar;
    TextView toolbartv;
    public static final String PREFS_NAME = "LoginPrefs";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    String android_Id;

    public NonTaxTabtwoFragment()
    {

    }

    @SuppressLint("ValidFragment")
    public NonTaxTabtwoFragment(int color)
    {
        this.color = color;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.non_tax_salary_tabtwo, container, false);

      /*  toolbar=(Toolbar)getActivity().findViewById(R.id.toolbar);
        toolbartv=(TextView)toolbar.findViewById(R.id.toolbartv);

        String heading="My Non Tax Salary";
        toolbartv.setText(heading);*/
        settings=getActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        android_Id=settings.getString("android_Id","");


        hrarowclick=(LinearLayout)view.findViewById(R.id.hrarowclick);
        medicalrowclick=(LinearLayout)view.findViewById(R.id.medicalrowclick);
        otherrowclick=(LinearLayout)view.findViewById(R.id.otherrowclick);
        ltarowclick=(LinearLayout)view.findViewById(R.id.ltarowclick);

        hrarowclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                editor = settings.edit();
                editor.putString("android_Id",android_Id);
                editor.commit();
                Intent gotohra=new Intent(getActivity(),HRAActivity.class);
                startActivity(gotohra);

            }
        });

        ltarowclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                editor = settings.edit();
                editor.putString("android_Id",android_Id);
                editor.commit();
                Intent gotolta=new Intent(getActivity(),LTAActivity.class);
                startActivity(gotolta);
            }
        });

        medicalrowclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                editor = settings.edit();
                editor.putString("android_Id",android_Id);
                editor.commit();
                Intent gotomedical=new Intent(getActivity(),MedicalActivity.class);
                startActivity(gotomedical);
            }
        });

        otherrowclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent gotoother=new Intent(getActivity(),NonTaxSalaryOther.class);
                startActivity(gotoother);
            }
        });

        return view;
    }
}
