package com.aexonic.hnrblock;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Parikshit Patil on 12/18/2015.
 */
public class NonTaxSalaryOther extends ActionBarActivity
{
    Toolbar toolbar;
    Button done_btn;
    TextView otherlinkemail;
    String ot1="Contact us on info@hrblock.in";
    String info="Not all allowances are tax exempt, while others may be taxed as perquisites. If you are receiving any of the above allowances or any other allowance which may not feature above, feel free to write to us at info@hrblock.in to understand the taxability of such allowances.";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nontaxsalary_other);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);


        otherlinkemail=(TextView)findViewById(R.id.otherlinkemail);
        otherlinkemail.setText(info);
        otherlinkemail.setLinkTextColor(getResources().getColor(R.color.greencolor));
        Linkify.addLinks(otherlinkemail,Linkify.EMAIL_ADDRESSES);



        done_btn=(Button)findViewById(R.id.done_btn);
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

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
