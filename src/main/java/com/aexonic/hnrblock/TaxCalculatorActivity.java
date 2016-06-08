package com.aexonic.hnrblock;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Parikshit Patil on 12/21/2015.
 */
public class TaxCalculatorActivity extends ActionBarActivity
{
    Toolbar toolbar;
    TextView queanyotherincome,queanyother,quetaxablesalary;
    Button calculate_btn;
    EditText et_age,et_tax_salary,et_anyotherincome,et_housing,et_eightyc,et_eightyd,et_deductions;
    String sage,staxsal,sanyotherincome,shousing,seightyc,seightyd,sdeductions;

    int itaxsal=0;
    int ianyotherincome=0;
    int ihousing=0;
    int ieightyc=0;
    int ieightyd=0;
    int ideductions=0;
    long taxpayble=0;
    int nettaxableincome=0;
    long surcharge=0;
    long educationcess=0;
    long totaltaxpayble=0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tax_calculator_activity_v2);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

       //hide keyboard code....
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        calculate_btn=(Button)findViewById(R.id.calculate_btn);
        et_age=(EditText)findViewById(R.id.et_age);
        et_age.setHintTextColor(getResources().getColor(R.color.textcolorfaint));
        et_tax_salary=(EditText)findViewById(R.id.et_taxsal);
        et_anyotherincome=(EditText)findViewById(R.id.et_anyotherincome);
        et_housing=(EditText)findViewById(R.id.et_housingloan);
        et_eightyc=(EditText)findViewById(R.id.et_eightyc);
        et_eightyd=(EditText)findViewById(R.id.et_eightyd);
        et_deductions=(EditText)findViewById(R.id.et_anyotherdeduction);





        queanyotherincome=(TextView)findViewById(R.id.queanyotherincome);
        quetaxablesalary=(TextView)findViewById(R.id.quetaxablesalary);
        queanyother=(TextView)findViewById(R.id.queanyother);
        queanyotherincome.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Dialog dialog = new Dialog(TaxCalculatorActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.any_other_income_taxcalculator);

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

        quetaxablesalary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final Dialog dialog = new Dialog(TaxCalculatorActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.taxable_salary_dialog);

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

        queanyother.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                final Dialog dialog = new Dialog(TaxCalculatorActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                dialog.setContentView(R.layout.any_other_dialogtaxcalculator);

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

        calculate_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                sage=et_age.getText().toString().trim();
                staxsal=et_tax_salary.getText().toString().trim();
                sanyotherincome=et_anyotherincome.getText().toString().trim();
                shousing=et_housing.getText().toString().trim();
                seightyc=et_eightyc.getText().toString().trim();
                seightyd=et_eightyd.getText().toString().trim();
                sdeductions=et_deductions.getText().toString().trim();


                if(sage.equals(""))
                {
                    Toast.makeText(getApplicationContext(),"Please enter your age",Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(sage)<1)
                {
                    Toast.makeText(getApplicationContext(),"Please enter valid age",Toast.LENGTH_SHORT).show();
                }
                else if(Integer.parseInt(sage)>100)
                {
                    Toast.makeText(getApplicationContext(),"Age should be below 100",Toast.LENGTH_SHORT).show();

                }
                else if(Integer.parseInt(sage)<60)
                {
                    if(staxsal.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please enter your taxable salary",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        itaxsal=Integer.parseInt(staxsal);
                        if(!sanyotherincome.equals(""))
                        {
                            ianyotherincome=Integer.parseInt(sanyotherincome);
                        }
                        else
                        {
                            ianyotherincome=0;
                        }

                        if(!shousing.equals("")&&Integer.parseInt(shousing)>=200000)
                        {
                            ihousing=200000;
                        }
                        else if(!shousing.equals("")&&Integer.parseInt(shousing)<200000)
                        {
                            ihousing=Integer.parseInt(shousing);
                        }
                        else if(shousing.equals(""))
                        {
                            ihousing=0;
                        }

                        if(!seightyc.equals("")&&Integer.parseInt(seightyc)>=150000)
                        {
                            ieightyc=150000;
                        }
                        else if(!seightyc.equals("")&&Integer.parseInt(seightyc)<150000)
                        {
                            ieightyc=Integer.parseInt(seightyc);
                        }
                        else if(seightyc.equals(""))
                        {
                            ieightyc=0;
                        }


                        if(!seightyd.equals("")&&Integer.parseInt(seightyd)>=55000)
                        {
                            ieightyd=55000;
                        }
                        else if(!seightyd.equals("")&&Integer.parseInt(seightyd)<55000)
                        {
                            ieightyd=Integer.parseInt(seightyd);
                        }
                        else if(seightyd.equals(""))
                        {
                            ieightyd=0;
                        }


                        if(!sdeductions.equals(""))
                        {
                            ideductions=Integer.parseInt(sdeductions);
                        }
                        else
                        {
                            ideductions=0;
                        }
                        nettaxableincome=itaxsal+ianyotherincome-ihousing-ieightyc-ieightyd-ideductions;

                        if(nettaxableincome<0)
                        {
                            nettaxableincome=0;
                        }

                        //Toast.makeText(getApplicationContext(),""+nettaxableincome,Toast.LENGTH_SHORT).show();

                        //Calculating total tax payble....

                        if(nettaxableincome>=1000000)
                        {
                            double one=nettaxableincome-1000000;
                            double two=one*0.3;
                            taxpayble=Math.round(two+125000);

                        }
                        else if(nettaxableincome>=500000)
                        {
                            double x=nettaxableincome-500000;
                            double y=x*0.2;
                            taxpayble=Math.round(y+25000);

                        }
                        else if(nettaxableincome>=250000)
                        {
                            double p=nettaxableincome-250000;
                            double q=p*0.1;
                            double twoth=q-2000;
                            double oneth=0;
                            double comparetax=Math.max(oneth,twoth);
                            //taxpayble=Math.round(q+0);
                            taxpayble=Math.round(comparetax);

                        }

                        else if(nettaxableincome<250000)
                        {
                            //Calculating EducationCess....
                            taxpayble=0;

                        }


                        if(nettaxableincome<10000000)
                        {
                            surcharge=0;
                        }
                        else
                        {
                            //calculate surcharge....
                            double surch1=nettaxableincome-10000000;
                            double surch11=surch1*0.7;
                            double surch2=taxpayble*0.1;
                            double surchargecompare=Math.min(surch11,surch2);
                            surcharge=Math.round(surchargecompare);


                        }

                       //Educationcess....
                        double edu1=taxpayble+surcharge;
                        double edu2=edu1*0.03;
                        educationcess=(int)edu2;
                        educationcess=Math.round(edu2);

                        totaltaxpayble=taxpayble+surcharge+educationcess;

                        //Toast.makeText(getApplicationContext(),"TotalIncome:"+nettaxableincome+"\n"+"TaxPayble"+taxpayble+"\n"+"Surcharge"+surcharge+"\n"+"Education"+educationcess+"\n"+"Total:"+totaltaxpayble,Toast.LENGTH_SHORT).show();
                        Intent gotocalculated=new Intent(TaxCalculatorActivity.this,TaxCalculatorCalculated.class);
                        gotocalculated.putExtra("nettaxable",nettaxableincome);
                        gotocalculated.putExtra("taxpayble",taxpayble);
                        gotocalculated.putExtra("surcharge", surcharge);
                        gotocalculated.putExtra("educationalcess", educationcess);
                        gotocalculated.putExtra("totaltaxpayble", totaltaxpayble);
                        startActivity(gotocalculated);




                        //1100000


                    }
                }
                else
                {
                    if(staxsal.equals(""))
                    {
                        Toast.makeText(getApplicationContext(),"Please enter your taxable salary",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        itaxsal=Integer.parseInt(staxsal);
                        if(!sanyotherincome.equals(""))
                        {
                            ianyotherincome=Integer.parseInt(sanyotherincome);
                        }
                        else
                        {
                            ianyotherincome=0;
                        }

                        if(!shousing.equals("")&&Integer.parseInt(shousing)>=200000)
                        {
                            ihousing=200000;
                        }
                        else if(!shousing.equals("")&&Integer.parseInt(shousing)<200000)
                        {
                            ihousing=Integer.parseInt(shousing);
                        }
                        else if(shousing.equals(""))
                        {
                            ihousing=0;
                        }

                        if(!seightyc.equals("")&&Integer.parseInt(seightyc)>=150000)
                        {
                            ieightyc=150000;
                        }
                        else if(!seightyc.equals("")&&Integer.parseInt(seightyc)<150000)
                        {
                            ieightyc=Integer.parseInt(seightyc);
                        }
                        else if(seightyc.equals(""))
                        {
                            ieightyc=0;
                        }


                        if(!seightyd.equals("")&&Integer.parseInt(seightyd)>=55000)
                        {
                            ieightyd=55000;
                        }
                        else if(!seightyd.equals("")&&Integer.parseInt(seightyd)<55000)
                        {
                            ieightyd=Integer.parseInt(seightyd);
                        }
                        else if(seightyd.equals(""))
                        {
                            ieightyd=0;
                        }


                        if(!sdeductions.equals(""))
                        {
                            ideductions=Integer.parseInt(sdeductions);
                        }
                        else
                        {
                            ideductions=0;
                        }
                        nettaxableincome=itaxsal+ianyotherincome-ihousing-ieightyc-ieightyd-ideductions;

                        if(nettaxableincome<0)
                        {
                            nettaxableincome=0;
                        }

                        //Toast.makeText(getApplicationContext(),""+nettaxableincome,Toast.LENGTH_SHORT).show();

                        //Calculating total tax payble....

                        if(nettaxableincome>=1000000)
                        {
                            double one=nettaxableincome-1000000;
                            double two=one*0.3;
                            taxpayble=Math.round(two+120000);

                        }
                        else if(nettaxableincome>=500000)
                        {
                            double x=nettaxableincome-500000;
                            double y=x*0.2;
                            taxpayble=Math.round(y+20000);

                        }
                        else if(nettaxableincome>=300000)
                        {
                            double p=nettaxableincome-300000;
                            double q=p*0.1;
                            double twoth=q-2000;
                            double oneth=0;
                            double comparetax=Math.max(oneth,twoth);

                            taxpayble=Math.round(comparetax);



                        }

                        else if(nettaxableincome<300000)
                        {
                            //Calculating EducationCess....
                            taxpayble=0;

                        }


                        if(nettaxableincome<10000000)
                        {
                            surcharge=0;
                        }
                        else
                        {
                            //calculate surcharge....
                            double surch1=nettaxableincome-10000000;
                            double surch11=surch1*0.7;
                            double surch2=taxpayble*0.1;
                            double surchargecompare=Math.min(surch11,surch2);
                            surcharge=Math.round(surchargecompare);

                        }

                        //Educationcess....
                        double edu1=taxpayble+surcharge;
                        double edu2=edu1*0.03;
                        educationcess=(int)edu2;
                        educationcess=Math.round(edu2);

                        totaltaxpayble=taxpayble+surcharge+educationcess;

                        //Toast.makeText(getApplicationContext(),"TotalIncome:"+nettaxableincome+"\n"+"TaxPayble"+taxpayble+"\n"+"Surcharge"+surcharge+"\n"+"Education"+educationcess+"\n"+"Total:"+totaltaxpayble,Toast.LENGTH_SHORT).show();
                        Intent gotocalculated=new Intent(TaxCalculatorActivity.this,TaxCalculatorCalculated.class);
                        gotocalculated.putExtra("nettaxable",nettaxableincome);
                        gotocalculated.putExtra("taxpayble",taxpayble);
                        gotocalculated.putExtra("surcharge", surcharge);
                        gotocalculated.putExtra("educationalcess", educationcess);
                        gotocalculated.putExtra("totaltaxpayble", totaltaxpayble);
                        startActivity(gotocalculated);

                        //1100000
                    }
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
                onBackPressed();
                return true;
            // case R.id.action_settings:
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
