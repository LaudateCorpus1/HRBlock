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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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
 * Created by Parikshit Patil on 12/21/2015.
 */
public class MedicalInsuranceActivity extends ActionBarActivity
{
    Toolbar toolbar;
    LinearLayout centerlayout,question2,question3layout,question4layout,fourqueyeslayout,fourquenolayout;
    RadioGroup rgquestion1,rgquestion3,rgquestion4;
    RadioButton rb,rb2,rb1;
    RadioButton rbq1yes,rbq1no,rbq3yes,rbq3no,rbq4yes,rbq4no;
    Button done_btn,reset_btn;
    CheckBox chkself,chkspouse,chkchildren,chkparents,chkothers;
    int benefitrestricted=0;
    int benefitdependentparents=0;
    int totalbenifitrestricted=0;
    TextView claimdeduction;
    TextView sentencetv;
    TextView threeque,fourque;
    //You can claim maximum deduction of ₹XXXXX.
    String claim1="You can claim maximum deduction of ";
    String claim2="";
    String claim3=".";
    String priceclaim,finalclaim;
    String hideUnhide="Hide";

    EditText et_totalmedical;

    int totalmedical=0;
    String stotalmedical;

    //Enter the amout of health insurance premium paid till date including expenses for preventive health check-up:
    String sentenceyes="Enter the amout of health insurance premium paid till date including expenses for preventive health check-up:";
    String sentenceno="Enter the health insurance premium paid till date:";
    String three="3) ";
    String four="4) ";
    public static final String PREFS_NAME = "LoginPrefs";
    SharedPreferences settings;
    SharedPreferences.Editor editor;
    String android_Id;
    DatabaseHandler databaseHandler;

    String id1,id2,id3;
   /* String scheckself,scheckspouse,scheckchildren,scheckparents,scheckothers;
    String finalcheck;*/

    String scheckself="false";
    String scheckspouse="false";
    String scheckchildren="false";
    String scheckparents="false";
    String scheckothers="false";

    Boolean bcheckself=false;
    Boolean bcheckspouse=false;
    Boolean bcheckchildren=false;
    Boolean bcheckparents=false;
    Boolean bcheckothers=false;

    int databasecount;
    int finalmaximumdeduction;
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_insurance_80d);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        //hide keyboard code....
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        android_Id=settings.getString("android_Id","");
        databaseHandler=new DatabaseHandler(this);
        claimdeduction=(TextView)findViewById(R.id.claimdeduction);

        sentencetv=(TextView)findViewById(R.id.sentencetv);
        threeque=(TextView)findViewById(R.id.threeque);
        fourque=(TextView)findViewById(R.id.fourque);

        et_totalmedical=(EditText)findViewById(R.id.et_totalmedical);
        done_btn=(Button)findViewById(R.id.done_btn);
        fourqueyeslayout=(LinearLayout)findViewById(R.id.fourqueyeslayout);
        centerlayout=(LinearLayout)findViewById(R.id.centerlayout);
        question2=(LinearLayout)findViewById(R.id.question2);
        question3layout=(LinearLayout)findViewById(R.id.question3layout);
        question4layout=(LinearLayout)findViewById(R.id.question4layout);
        rgquestion4=(RadioGroup)findViewById(R.id.id_ques4);
        rgquestion1=(RadioGroup)findViewById(R.id.id_que1);
        rgquestion3=(RadioGroup)findViewById(R.id.id_que3);


        chkself=(CheckBox)findViewById(R.id.chkself);
        chkspouse=(CheckBox)findViewById(R.id.chkspouse);
        chkchildren=(CheckBox)findViewById(R.id.chkchildren);
        chkparents=(CheckBox)findViewById(R.id.chkparents);
        chkothers=(CheckBox)findViewById(R.id.chkothers);

        rbq1yes=(RadioButton)findViewById(R.id.rbq1yes);
        rbq1no=(RadioButton)findViewById(R.id.rbq1no);
        rbq3yes=(RadioButton)findViewById(R.id.rbq3yes);
        rbq3no=(RadioButton)findViewById(R.id.rbq3no);
        rbq4yes=(RadioButton)findViewById(R.id.rbq4yes);
        rbq4no=(RadioButton)findViewById(R.id.rbq4no);
        cd = new ConnectionDetector(getApplicationContext());

        databasecount=databaseHandler.getMEDICALINSURANCECount();

        if(databasecount>0)
        {
            //Toast.makeText(getApplicationContext(),"Data is there",Toast.LENGTH_SHORT).show();
            List<HealthInsurancePojo> healthInsurancePojoList = databaseHandler.getAllHealthInsurance();
            for (HealthInsurancePojo cn : healthInsurancePojoList)
            {
                android_Id=cn.get_androidid();
                id1=cn.get_didhealth();
                scheckself=cn.get_selfcheck();
                scheckspouse=cn.get_spousecheck();
                scheckchildren=cn.get_childrencheck();
                scheckparents=cn.get_dependentparents();
                scheckothers=cn.get_others();
                id2=cn.get_age();
                id3=cn.get_preventive();
                totalbenifitrestricted=cn.get_medicalcalculate();
                totalmedical=cn.get_premiumpaid();
            }

            if(id1.equals("Yes"))
            {
                rbq1yes.setChecked(true);
                centerlayout.setVisibility(View.GONE);
                //done_btn.setVisibility(View.GONE);
                question2.setVisibility(View.VISIBLE);

                if(scheckself.equals("true"))
                {
                    scheckself="true";
                    chkself.setChecked(true);
                }
                else
                {
                    scheckself="false";
                    chkself.setChecked(false);
                }

                if(scheckspouse.equals("true"))
                {
                    scheckspouse="true";
                    chkspouse.setChecked(true);
                }
                else
                {
                    scheckspouse="false";
                    chkspouse.setChecked(false);
                }

                if(scheckchildren.equals("true"))
                {
                    chkchildren.setChecked(true);
                }
                else
                {
                    scheckchildren="false";
                    chkchildren.setChecked(false);
                }

                if(scheckparents.equals("true"))
                {
                    chkparents.setChecked(true);
                    if(question3layout.getVisibility()==View.GONE)
                    {
                        question3layout.setVisibility(View.VISIBLE);
                        threeque.setText(four);
                        fourque.setText(three);
                    }
                    else
                    {
                        question3layout.setVisibility(View.GONE);
                        threeque.setText(three);
                        fourque.setText(four);

                    }
                }
                else
                {
                    scheckparents="false";
                    chkparents.setChecked(false);
                    question3layout.setVisibility(View.GONE);
                }

                if(scheckothers.equals("true"))
                {
                    chkothers.setChecked(true);
                }
                else
                {
                    scheckothers="false";
                    chkothers.setChecked(false);
                }

                if(question4layout.getVisibility()==View.GONE)
                {
                    question4layout.setVisibility(View.VISIBLE);
                    fourqueyeslayout.setVisibility(View.VISIBLE);
                    done_btn.setVisibility(View.VISIBLE);
                    fourque.setText(four);
                    threeque.setText(three);

                   QuestionTwoCalculate();

                }
                else
                {
                    question4layout.setVisibility(View.GONE);
                    fourqueyeslayout.setVisibility(View.GONE);
                    done_btn.setVisibility(View.GONE);
                    fourque.setText(four);
                    threeque.setText(three);

                }

                if(!id2.equals("null"))///Crashed here
                {
                    centerlayout.setVisibility(View.GONE);

                    if(id2.equals("Yes"))
                    {
                        rbq3yes.setChecked(true);
                    }
                    if(id2.equals("No"))
                    {
                        rbq3no.setChecked(true);
                    }
                }


                if(!id3.equals("null"))
                {
                    if(id3.equals("Yes"))
                    {
                        rbq4yes.setChecked(true);
                        sentencetv.setText(sentenceyes);

                    }
                    if(id3.equals("No"))
                    {
                        rbq4no.setChecked(true);
                        sentencetv.setText(sentenceno);

                    }
                }

                if(fourqueyeslayout.getVisibility()==View.VISIBLE)
                {
                    question2.setVisibility(View.VISIBLE);
                    // question3layout.setVisibility(View.VISIBLE);
                    centerlayout.setVisibility(View.GONE);


                    if(id3.equals("Yes"))
                    {
                        fourqueyeslayout.setVisibility(View.VISIBLE);
                        centerlayout.setVisibility(View.GONE);

                    }
                    else
                    {
                        centerlayout.setVisibility(View.GONE);
                    }
                }

                if(totalbenifitrestricted>0)
                {
                    QuestionTwoCalculate();
                }

                if(totalmedical>0)
                {
                    et_totalmedical.setText(String.valueOf(totalbenifitrestricted-totalmedical));
                }
                else if(totalmedical==0)
                {
                    et_totalmedical.setText(String.valueOf(totalbenifitrestricted));
                }




            }
            if(id1.equals("No"))
            {
                   rbq1no.setChecked(true);

                    centerlayout.setVisibility(View.VISIBLE);
                    fourqueyeslayout.setVisibility(View.GONE);
                    //  Toast.makeText(getApplicationContext(),"hhhh",Toast.LENGTH_SHORT).show();
                    question2.setVisibility(View.GONE);
                    question3layout.setVisibility(View.GONE);
                    question4layout.setVisibility(View.GONE);
                    done_btn.setVisibility(View.VISIBLE);

               // Toast.makeText(getApplicationContext(),"Call",Toast.LENGTH_SHORT).show();

            }



        }
        //Question1 functionality..........
        rgquestion1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
            {
                rb=(RadioButton)findViewById(checkedId);
                id1=rb.getText().toString();

                if(id1.equals("Yes"))
                {
                    centerlayout.setVisibility(View.GONE);
                    question2.setVisibility(View.VISIBLE);
                    question2.requestFocus();
                    done_btn.setVisibility(View.GONE);
                    question4layout.setVisibility(View.GONE);

                    chkself.setChecked(false);
                    chkspouse.setChecked(false);
                    chkchildren.setChecked(false);
                    chkparents.setChecked(false);
                    chkothers.setChecked(false);
                }

                else if(id1.equals("No"))
                {
                    centerlayout.setVisibility(View.VISIBLE);
                    chkself.setChecked(false);
                    chkspouse.setChecked(false);
                    chkchildren.setChecked(false);
                    chkparents.setChecked(false);
                    chkothers.setChecked(false);

                    question2.setVisibility(View.GONE);
                    question3layout.setVisibility(View.GONE);

                    done_btn.setVisibility(View.VISIBLE);
                    question4layout.setVisibility(View.GONE);
                    fourqueyeslayout.setVisibility(View.GONE);

                 //   Toast.makeText(getApplicationContext(),"NO call",Toast.LENGTH_SHORT).show();

               }
            }
        });

        //Question2 functionality..........
        chkself.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
            {
                if(isChecked)
                {
                    scheckself="true";
                    QuestionTwoCalculate();

                    if(question4layout.getVisibility()==View.GONE)
                    {
                        question4layout.setVisibility(View.VISIBLE);

                        if(question3layout.getVisibility()==View.VISIBLE)
                        {
                            fourque.setText(four);
                        }
                        else
                        {
                            fourque.setText(three);
                        }
                    }
                    else
                    {
                       //QuestionTwoCalculate();
                    }
                }
                else
                {
                    scheckself="false";
                    QuestionTwoCalculate();


                    if(chkspouse.isChecked()||chkchildren.isChecked())
                    {
                        if(question4layout.getVisibility()==View.GONE)
                        {
                            question4layout.setVisibility(View.VISIBLE);
                            fourque.setText(three);
                            done_btn.setVisibility(View.VISIBLE);
                        }
                    }
                    else if(!chkspouse.isChecked()&&!chkchildren.isChecked()&&!chkparents.isChecked())
                    {
                        question4layout.setVisibility(View.GONE);
                        fourqueyeslayout.setVisibility(View.GONE);
                        done_btn.setVisibility(View.GONE);

                    }

                    else
                    {
                        if(question4layout.getVisibility()==View.VISIBLE)
                        {
                            question4layout.setVisibility(View.GONE);
                            fourque.setText(four);
                            QuestionTwoCalculate();
                        }
                    }
                }
            }
        });


        chkspouse.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
            {

                if(isChecked)
                {
                    scheckspouse="true";
                    QuestionTwoCalculate();
                    if(question4layout.getVisibility()==View.GONE)
                    {
                        question4layout.setVisibility(View.VISIBLE);

                        if(question3layout.getVisibility()==View.VISIBLE)
                        {
                            fourque.setText(four);
                        }
                        else
                        {
                            fourque.setText(three);
                        }
                    }
                    else
                    {
                        //QuestionTwoCalculate();
                    }
                }
                else
                {
                    scheckspouse="false";
                    QuestionTwoCalculate();

                    if(chkself.isChecked()||chkchildren.isChecked())
                    {
                        if(question4layout.getVisibility()==View.GONE)
                        {
                            question4layout.setVisibility(View.VISIBLE);
                            fourque.setText(three);
                            done_btn.setVisibility(View.VISIBLE);

                        }
                    }
                    else if(!chkself.isChecked()&&!chkchildren.isChecked()&&!chkparents.isChecked())
                    {
                        question4layout.setVisibility(View.GONE);
                        fourqueyeslayout.setVisibility(View.GONE);
                        done_btn.setVisibility(View.GONE);

                    }

                    else
                    {
                        if(question4layout.getVisibility()==View.VISIBLE)
                        {
                            question4layout.setVisibility(View.GONE);
                            fourque.setText(four);
                            QuestionTwoCalculate();
                        }
                    }

                }
            }
        });


        chkchildren.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
            {

                if(isChecked)
                {
                    scheckchildren="true";
                    QuestionTwoCalculate();

                    if(question4layout.getVisibility()==View.GONE)
                    {
                        question4layout.setVisibility(View.VISIBLE);

                        if(question3layout.getVisibility()==View.VISIBLE)
                        {
                            fourque.setText(four);

                        }
                        else
                        {
                            fourque.setText(three);
                        }
                    }
                    else
                    {
                       // QuestionTwoCalculate();
                    }
                }
                else
                {
                    scheckchildren="false";
                    QuestionTwoCalculate();

                    if(chkspouse.isChecked()||chkself.isChecked())
                    {
                        if(question4layout.getVisibility()==View.GONE)
                        {
                            question4layout.setVisibility(View.VISIBLE);
                            fourque.setText(three);
                            QuestionTwoCalculate();
                            done_btn.setVisibility(View.VISIBLE);

                        }
                    }
                    else if(!chkspouse.isChecked()&&!chkself.isChecked()&&!chkparents.isChecked())
                    {
                        question4layout.setVisibility(View.GONE);
                        fourqueyeslayout.setVisibility(View.GONE);
                        done_btn.setVisibility(View.GONE);

                    }
                    else
                    {
                        if(question4layout.getVisibility()==View.VISIBLE)
                        {
                            question4layout.setVisibility(View.GONE);
                            fourque.setText(four);

                            QuestionTwoCalculate();

                        }
                    }
                }
            }
        });

        chkparents.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
            {

                if(isChecked)
                {
                    scheckparents="true";
                    QuestionTwoCalculate();

                    if(question3layout.getVisibility()==View.GONE)
                    {
                        question3layout.setVisibility(View.VISIBLE);
                        threeque.setText(three);
                        fourque.setText(four);
                        //done_btn.setVisibility(View.GONE);
                    }
                }

                else
                {
                    scheckparents="false";
                    QuestionTwoCalculate();
                    question3layout.setVisibility(View.GONE);
                    fourque.setText(three);

                    if(chkself.isChecked()||chkspouse.isChecked()||chkchildren.isChecked())
                    {
                        if(question4layout.getVisibility()==View.GONE)
                        {
                            question4layout.setVisibility(View.VISIBLE);
                            QuestionTwoCalculate();
                            done_btn.setVisibility(View.VISIBLE);
                        }
                    }
                    else if(!chkself.isChecked()&&!chkchildren.isChecked()&&!chkspouse.isChecked())
                    {
                        question4layout.setVisibility(View.GONE);
                        fourqueyeslayout.setVisibility(View.GONE);
                        done_btn.setVisibility(View.GONE);

                    }
                    else
                    {
                        QuestionTwoCalculate();
                        //fourque.setText(three);
                    }
                }
            }
        });


        chkothers.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked)
            {
                if(isChecked)
                {
                    scheckothers="true";
                   Toast.makeText(getApplicationContext(),"There is no tax benefit incurred for any other family members",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    scheckothers="false";
                    //done_btn.setVisibility(View.GONE);

/*
                    if(chkself.isChecked()||chkspouse.isChecked()||chkchildren.isChecked())
                    {
                        if(question4layout.getVisibility()==View.GONE)
                        {
                            question4layout.setVisibility(View.VISIBLE);
                            QuestionTwoCalculate();
                            done_btn.setVisibility(View.GONE);
                        }
                    }
*/

                }
            }
        });


        //Question3 functionality..........
        rgquestion3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
            {
                rb1=(RadioButton)findViewById(checkedId);
                id2=rb1.getText().toString();

                if(id2.equals("Yes"))
                {
                    QuestionTwoCalculate();

                    if(question4layout.getVisibility()==View.GONE)
                    {
                        question4layout.setVisibility(View.VISIBLE);
                        question4layout.requestFocus();
                       // done_btn.setVisibility(View.GONE);
                    }

                }
                else if(id2.equals("No"))
                {
                    question4layout.setVisibility(View.VISIBLE);

                    QuestionTwoCalculate();
                    if(question4layout.getVisibility()==View.GONE)
                    {
                        question4layout.setVisibility(View.VISIBLE);
                        question4layout.requestFocus();
                    }
                }
            }
        });

       //Question4 functionality..........
        rgquestion4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId)
            {
                rb2=(RadioButton)findViewById(checkedId);
                id3=rb2.getText().toString();

                if(id3.equals("Yes"))
                {
                    done_btn.setVisibility(View.VISIBLE);
                    fourqueyeslayout.setVisibility(View.VISIBLE);
                    fourqueyeslayout.requestFocus();

                    sentencetv.setText(sentenceyes);

                }
                else if(id3.equals("No"))
                {
                    done_btn.setVisibility(View.VISIBLE);
                    fourqueyeslayout.setVisibility(View.VISIBLE);
                    fourqueyeslayout.requestFocus();

                    sentencetv.setText(sentenceno);

                }
            }
        });

        done_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                stotalmedical=et_totalmedical.getText().toString();

                if(databasecount>0)
                {
                    // update...
                    if(fourqueyeslayout.getVisibility()==View.VISIBLE)
                    {
                        if(stotalmedical.equals(""))
                        {
                            Toast.makeText(getApplicationContext(),"Please enter amount",Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                           int totalmedicalnew=Integer.parseInt(stotalmedical);
                            if(totalmedicalnew>finalmaximumdeduction)
                            {
                                //Toast.makeText(getApplicationContext(),"Your benifit restricted amount is more than maximum deduction",Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(),"The amount entered is more than the maximum deduction eligible.",Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                totalmedical=finalmaximumdeduction-totalmedicalnew;

                                if(done_btn.getVisibility()==View.VISIBLE)
                                {

                                    if(question3layout.getVisibility()==View.GONE)
                                    {
                                        id2="null";
                                    }

                                    databaseHandler.updateHEALTHINSURANCE(new HealthInsurancePojo(android_Id,id1,scheckself,scheckspouse,scheckchildren,scheckparents,scheckothers,id2,id3,finalmaximumdeduction,totalmedical));
                                    //onBackPressed();
                                    isInternetPresent = cd.isConnectingToInternet();
                                    if(isInternetPresent)
                                    {
                                        new MedicalInsuranceWebservice().execute(android_Id,id1,scheckself,scheckspouse,scheckchildren,scheckparents,scheckothers,id2,id3,String.valueOf(finalmaximumdeduction),String.valueOf(totalmedical));
                                    }
                                    else
                                    {

                                    }
                                    Intent gotoback=new Intent(MedicalInsuranceActivity.this,InvestmentTabActivity.class);
                                    gotoback.putExtra("camefrommedicalinsurance","camefrommedicalinsurance");
                                    // gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    // gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(gotoback);
                                }
                                else
                                {
                                    onBackPressed();
                                }

                            }
                        }

                    }
                    else if(centerlayout.getVisibility()==View.VISIBLE)
                    {
                        scheckself="false";
                        scheckspouse="false";
                        scheckchildren="false";
                        scheckparents="false";
                        scheckothers="false";
                        id2="null";
                        id3="null";
                        totalbenifitrestricted=0;
                        totalmedical=0;

                        databaseHandler.updateHEALTHINSURANCE(new HealthInsurancePojo(android_Id,id1,scheckself,scheckspouse,scheckchildren,scheckparents,scheckothers,id2,id3,finalmaximumdeduction,totalmedical));
                        //onBackPressed();
                        isInternetPresent = cd.isConnectingToInternet();
                        if(isInternetPresent)
                        {
                            new MedicalInsuranceWebservice().execute(android_Id,id1,scheckself,scheckspouse,scheckchildren,scheckparents,scheckothers,id2,id3,String.valueOf(finalmaximumdeduction),String.valueOf(totalmedical));
                        }
                        else
                        {

                        }
                        Intent gotoback=new Intent(MedicalInsuranceActivity.this,InvestmentTabActivity.class);
                        gotoback.putExtra("camefrommedicalinsurance","camefrommedicalinsurance");

                        startActivity(gotoback);


                    }
                    else
                    {
                        onBackPressed();
                    }

                }
                else
                {
                    if(fourqueyeslayout.getVisibility()==View.VISIBLE)
                    {
                        if(stotalmedical.equals(""))
                        {
                            Toast.makeText(getApplicationContext(),"Please enter amount",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            int totalmedicalnew=Integer.parseInt(stotalmedical);
                            if(totalmedicalnew>finalmaximumdeduction)
                            {
                               // Toast.makeText(getApplicationContext(),"Your benifit restricted amount is more than maximum deduction",Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(),"The amount entered is more than the maximum deduction eligible.",Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                totalmedical=finalmaximumdeduction-totalmedicalnew;
                                if(done_btn.getVisibility()==View.VISIBLE)
                                {
                                    if(question3layout.getVisibility()==View.GONE)
                                    {
                                        id2="null";
                                    }

                                    databaseHandler.addHEALTHINSURANCE(new HealthInsurancePojo(android_Id, id1, scheckself, scheckspouse, scheckchildren, scheckparents, scheckothers, id2, id3, finalmaximumdeduction, totalmedical));
                                    //onBackPressed();
                                     isInternetPresent = cd.isConnectingToInternet();
                                      if(isInternetPresent)
                                       {


                                           new MedicalInsuranceWebservice().execute(android_Id,id1,scheckself,scheckspouse,scheckchildren,scheckparents,scheckothers,id2,id3,String.valueOf(finalmaximumdeduction),String.valueOf(totalmedical));
                                       }
                                        else
                                        {

                                        }

                                    Intent gotoback = new Intent(MedicalInsuranceActivity.this, InvestmentTabActivity.class);
                                    gotoback.putExtra("camefrommedicalinsurance", "camefrommedicalinsurance");
                                    //gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(gotoback);
                                }
                                else
                                {
                                    onBackPressed();
                                }

                                //  Toast.makeText(getApplicationContext(),"databasecount< in if",Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                    else if(centerlayout.getVisibility()==View.VISIBLE)
                    {
                        scheckself="false";
                        scheckspouse="false";
                        scheckchildren="false";
                        scheckparents="false";
                        scheckothers="false";
                        id2="null";
                        id3="null";
                        totalbenifitrestricted=0;
                        totalmedical=0;

                        databaseHandler.addHEALTHINSURANCE(new HealthInsurancePojo(android_Id,id1,scheckself,scheckspouse,scheckchildren,scheckparents,scheckothers,id2,id3,finalmaximumdeduction,totalmedical));
                        //onBackPressed();
                        isInternetPresent = cd.isConnectingToInternet();
                        if(isInternetPresent)
                        {

                            new MedicalInsuranceWebservice().execute(android_Id,id1,scheckself,scheckspouse,scheckchildren,scheckparents,scheckothers,id2,id3,String.valueOf(finalmaximumdeduction),String.valueOf(totalmedical));
                        }
                        else
                        {

                        }
                        Intent gotoback=new Intent(MedicalInsuranceActivity.this,InvestmentTabActivity.class);
                        gotoback.putExtra("camefrommedicalinsurance","camefrommedicalinsurance");
                        // gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(gotoback);

                        // Toast.makeText(getApplicationContext(),"databasecount< inelse if",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        onBackPressed();
                    }
                }
            }//......
        });

    }

    public void QuestionTwoCalculate()
    {
        int calculatetwo=0;

         if((chkself.isChecked()||chkspouse.isChecked()||chkchildren.isChecked())&&chkparents.isChecked()&&"Yes".equals(id2))
         {
           calculatetwo=55000;
        }
        else if((chkself.isChecked()||chkspouse.isChecked()||chkchildren.isChecked())&&chkparents.isChecked()&&"No".equals(id2))
         {
             calculatetwo=50000;
         }
        else if((chkself.isChecked()||chkspouse.isChecked()||chkchildren.isChecked())&&chkparents.isChecked())
        {
            calculatetwo=50000;
        }
        else if(chkself.isChecked()||chkspouse.isChecked()||chkchildren.isChecked())
        {
            calculatetwo=25000;
        }
        else if(chkparents.isChecked()&&"Yes".equals(id2))
        {
            calculatetwo=30000;
        }
        else if(chkparents.isChecked()&&"No".equals(id2))
        {
            calculatetwo=25000;
        }
        else if(chkparents.isChecked())
        {
            calculatetwo=25000;
        }
        else
        {
           calculatetwo=0;
        }

        finalmaximumdeduction=FinalMaximumDeduction(calculatetwo);

       // Toast.makeText(getApplicationContext(),"Output:"+finalmaximumdeduction,Toast.LENGTH_SHORT).show();

    }


    public int FinalMaximumDeduction(int finalcal)
    {
        int finalcalculation=finalcal;
        priceclaim="<font color='#7dc243'>"+"₹"+finalcalculation+"</font>";
        finalclaim=claim1+priceclaim+claim3;
        claimdeduction.setText(Html.fromHtml(finalclaim));
        return finalcalculation;
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
               // onBackPressed();
                stotalmedical=et_totalmedical.getText().toString();
                if(databasecount>0)
                {
                    // update...
                    if(fourqueyeslayout.getVisibility()==View.VISIBLE)
                    {
                        if(stotalmedical.equals(""))
                        {
                            Toast.makeText(getApplicationContext(),"Please enter amount",Toast.LENGTH_SHORT).show();
                        }

                        else
                        {
                            int totalmedicalnew=Integer.parseInt(stotalmedical);
                            if(totalmedicalnew>finalmaximumdeduction)
                            {
                                //Toast.makeText(getApplicationContext(),"Your benifit restricted amount is more than maximum deduction",Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(),"The amount entered is more than the maximum deduction eligible.",Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                totalmedical=finalmaximumdeduction-totalmedicalnew;

                                if(done_btn.getVisibility()==View.VISIBLE)
                                {
                                    if(question3layout.getVisibility()==View.GONE)
                                    {
                                        id2="null";
                                    }
                                    databaseHandler.updateHEALTHINSURANCE(new HealthInsurancePojo(android_Id,id1,scheckself,scheckspouse,scheckchildren,scheckparents,scheckothers,id2,id3,finalmaximumdeduction,totalmedical));
                                    //onBackPressed();
                                    isInternetPresent = cd.isConnectingToInternet();
                                    if(isInternetPresent)
                                    {
                                        new MedicalInsuranceWebservice().execute(android_Id,id1,scheckself,scheckspouse,scheckchildren,scheckparents,scheckothers,id2,id3,String.valueOf(finalmaximumdeduction),String.valueOf(totalmedical));
                                    }
                                    else
                                    {

                                    }
                                    Intent gotoback=new Intent(MedicalInsuranceActivity.this,InvestmentTabActivity.class);
                                    gotoback.putExtra("camefrommedicalinsurance","camefrommedicalinsurance");
                                    // gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    // gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(gotoback);
                                }
                                else
                                {
                                    onBackPressed();
                                }

                            }
                        }

                    }
                    else if(centerlayout.getVisibility()==View.VISIBLE)
                    {
                        scheckself="false";
                        scheckspouse="false";
                        scheckchildren="false";
                        scheckparents="false";
                        scheckothers="false";
                        id2="null";
                        id3="null";
                        totalbenifitrestricted=0;
                        totalmedical=0;

                        databaseHandler.updateHEALTHINSURANCE(new HealthInsurancePojo(android_Id,id1,scheckself,scheckspouse,scheckchildren,scheckparents,scheckothers,id2,id3,finalmaximumdeduction,totalmedical));
                        //onBackPressed();
                        isInternetPresent = cd.isConnectingToInternet();
                        if(isInternetPresent)
                        {
                            new MedicalInsuranceWebservice().execute(android_Id,id1,scheckself,scheckspouse,scheckchildren,scheckparents,scheckothers,id2,id3,String.valueOf(finalmaximumdeduction),String.valueOf(totalmedical));
                        }
                        else
                        {

                        }
                        Intent gotoback=new Intent(MedicalInsuranceActivity.this,InvestmentTabActivity.class);
                        gotoback.putExtra("camefrommedicalinsurance","camefrommedicalinsurance");

                        startActivity(gotoback);

                    }
                    else
                    {
                        onBackPressed();
                    }

                }
                else
                {
                    if(fourqueyeslayout.getVisibility()==View.VISIBLE)
                    {
                        if(stotalmedical.equals(""))
                        {
                            Toast.makeText(getApplicationContext(),"Please enter amount",Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            int totalmedicalnew=Integer.parseInt(stotalmedical);
                            if(totalmedicalnew>finalmaximumdeduction)
                            {
                                //Toast.makeText(getApplicationContext(),"Your benifit restricted amount is more than maximum deduction",Toast.LENGTH_SHORT).show();
                                Toast.makeText(getApplicationContext(),"The amount entered is more than the maximum deduction eligible.",Toast.LENGTH_SHORT).show();

                            }
                            else
                            {
                                totalmedical=finalmaximumdeduction-totalmedicalnew;
                                if(done_btn.getVisibility()==View.VISIBLE)
                                {
                                    if(question3layout.getVisibility()==View.GONE)
                                    {
                                        id2="null";
                                    }
                                    databaseHandler.addHEALTHINSURANCE(new HealthInsurancePojo(android_Id, id1, scheckself, scheckspouse, scheckchildren, scheckparents, scheckothers, id2, id3, finalmaximumdeduction, totalmedical));
                                    //onBackPressed();
                                    isInternetPresent = cd.isConnectingToInternet();
                                    if(isInternetPresent)
                                    {
                                        new MedicalInsuranceWebservice().execute(android_Id,id1,scheckself,scheckspouse,scheckchildren,scheckparents,scheckothers,id2,id3,String.valueOf(finalmaximumdeduction),String.valueOf(totalmedical));
                                    }
                                    else
                                    {

                                    }
                                    Intent gotoback = new Intent(MedicalInsuranceActivity.this, InvestmentTabActivity.class);
                                    gotoback.putExtra("camefrommedicalinsurance", "camefrommedicalinsurance");
                                    //gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    //gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(gotoback);
                                }
                                else
                                {
                                    onBackPressed();
                                }
                            }
                        }

                    }
                    else if(centerlayout.getVisibility()==View.VISIBLE)
                    {
                        scheckself="false";
                        scheckspouse="false";
                        scheckchildren="false";
                        scheckparents="false";
                        scheckothers="false";
                        id2="null";
                        id3="null";
                        totalbenifitrestricted=0;
                        totalmedical=0;

                        databaseHandler.addHEALTHINSURANCE(new HealthInsurancePojo(android_Id,id1,scheckself,scheckspouse,scheckchildren,scheckparents,scheckothers,id2,id3,finalmaximumdeduction,totalmedical));
                        //onBackPressed();
                        isInternetPresent = cd.isConnectingToInternet();
                        if(isInternetPresent)
                        {


                            new MedicalInsuranceWebservice().execute(android_Id,id1,scheckself,scheckspouse,scheckchildren,scheckparents,scheckothers,id2,id3,String.valueOf(finalmaximumdeduction),String.valueOf(totalmedical));
                        }
                        else
                        {

                        }
                        Intent gotoback=new Intent(MedicalInsuranceActivity.this,InvestmentTabActivity.class);
                        gotoback.putExtra("camefrommedicalinsurance","camefrommedicalinsurance");
                        // gotoback.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        // gotoback.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(gotoback);

                        // Toast.makeText(getApplicationContext(),"databasecount< inelse if",Toast.LENGTH_SHORT).show();

                    }
                    else
                    {
                        onBackPressed();
                    }
                }

                return true;
            // case R.id.action_settings:
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private class MedicalInsuranceWebservice extends AsyncTask<String,String,String>
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
                String sdid_insurance_health=(String)args0[1];
                String sscheckself=(String)args0[2];
                String sscheckspouse=(String)args0[3];
                String sscheckchildren=(String)args0[4];
                String sscheckparents=(String)args0[5];
                String sscheckother=(String)args0[6];
                String ssage=(String)args0[7];
                String sspreventive=(String)args0[8];
                String ssmaxdeduction=(String)args0[9];
                String ssamountentered=(String)args0[10];

                String link="http://tax.hrblock.in/Mobile_App_service/api/profile/Post_MedicalInsuranceRecord/";
                String data= URLEncoder.encode("Device_id", "UTF-8")+"="+URLEncoder.encode(deviceidstring,"UTF-8");
                data += "&"+URLEncoder.encode("Q1_Did_Health_Insurance","UTF-8")+"="+URLEncoder.encode(sdid_insurance_health,"UTF-8");
                data += "&"+URLEncoder.encode("self_check","UTF-8")+"="+URLEncoder.encode(sscheckself,"UTF-8");
                data += "&"+URLEncoder.encode("spouse_check","UTF-8")+"="+URLEncoder.encode(sscheckspouse,"UTF-8");
                data += "&"+URLEncoder.encode("children_check","UTF-8")+"="+URLEncoder.encode(sscheckchildren,"UTF-8");
                data += "&"+URLEncoder.encode("parents_check","UTF-8")+"="+URLEncoder.encode(sscheckparents,"UTF-8");
                data += "&"+URLEncoder.encode("other_check","UTF-8")+"="+URLEncoder.encode(sscheckother,"UTF-8");

                data += "&"+URLEncoder.encode("Q3_For_parent_age","UTF-8")+"="+URLEncoder.encode(ssage,"UTF-8");
                data += "&"+URLEncoder.encode("Q4_Preventive_health_check","UTF-8")+"="+URLEncoder.encode(sspreventive,"UTF-8");
                data += "&"+URLEncoder.encode("maximum_deduction","UTF-8")+"="+URLEncoder.encode(ssmaxdeduction,"UTF-8");
                data += "&"+URLEncoder.encode("amount_entered","UTF-8")+"="+URLEncoder.encode(ssamountentered,"UTF-8");

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
