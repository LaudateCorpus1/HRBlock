package com.aexonic.hnrblock;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Parikshit Patil on 12/22/2015.
 */
public class TaxCalculatorCalculated extends ActionBarActivity
{
    Toolbar toolbar;
    Button done_btn;
    TextView tv_nettaxableincome,tv_basictax,tv_educationcess,tv_surcharge,tv_totaltaxes;
    String snettaxableincome,sbasictax,seducationcess,ssurcharge,stotaltaxes;
 //   long snettaxableincome,sbasictax,seducationcess,ssurcharge,stotaltaxes;
    //String stnettaxableincome,stbasictax,steducationcess,stsurcharge,sttotaltaxes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculated_layout);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);

        //hide keyboard code....
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);


        /*snettaxableincome=getIntent().getIntExtra("nettaxable",0);
        sbasictax=getIntent().getLongExtra("taxpayble", 0);
        seducationcess=getIntent().getIntExtra("educationalcess", 0);
        ssurcharge=getIntent().getLongExtra("surcharge",0);
        stotaltaxes=getIntent().getLongExtra("totaltaxpayble",0);*/
        DecimalFormat formatter = new DecimalFormat("#,###,###");

       /* snettaxableincome=String.valueOf(getIntent().getIntExtra("nettaxable", 0));
        sbasictax=String.valueOf(getIntent().getLongExtra("taxpayble", 0));
        seducationcess=String.valueOf(getIntent().getIntExtra("educationalcess", 0));
        ssurcharge=String.valueOf(getIntent().getLongExtra("surcharge",0));
        stotaltaxes=String.valueOf(getIntent().getLongExtra("totaltaxpayble",0));*/

        snettaxableincome=String.valueOf(formatter.format(getIntent().getIntExtra("nettaxable", 0)));
        sbasictax=String.valueOf(formatter.format(getIntent().getLongExtra("taxpayble", 0)));
        seducationcess=String.valueOf(formatter.format(getIntent().getLongExtra("educationalcess", 0)));
        ssurcharge=String.valueOf(formatter.format(getIntent().getLongExtra("surcharge",0)));
        stotaltaxes=String.valueOf(formatter.format(getIntent().getLongExtra("totaltaxpayble",0)));

       // String finaltotalofall = formatter.format(total);

        done_btn=(Button)findViewById(R.id.done_btn);

        tv_nettaxableincome=(TextView)findViewById(R.id.tv_nettaxableincome);
        tv_basictax=(TextView)findViewById(R.id.tv_basictax);
        tv_educationcess=(TextView)findViewById(R.id.tv_educationcess);
        tv_surcharge=(TextView)findViewById(R.id.tv_surcharge);
        tv_totaltaxes=(TextView)findViewById(R.id.tv_totaltaxes);


        if(snettaxableincome!=null)
        {

            tv_nettaxableincome.setText(snettaxableincome);
        }
        else
        {

        }
        if(sbasictax!=null)
        {
            tv_basictax.setText(sbasictax);
        }
        else
        {

        }
        if(seducationcess!=null)
        {
            tv_educationcess.setText(seducationcess);
        }
        else
        {

        }
        if(ssurcharge!=null)
        {
            tv_surcharge.setText(ssurcharge);
        }
        else
        {

        }
        if(stotaltaxes!=null)
        {
            tv_totaltaxes.setText(stotaltaxes);
        }
        else
        {

        }


        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //onBackPressed();
                Intent gotohome=new Intent(TaxCalculatorCalculated.this,HomeScreenActivity.class);
                startActivity(gotohome);

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
                onBackPressed();
                return true;
            // case R.id.action_settings:
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
