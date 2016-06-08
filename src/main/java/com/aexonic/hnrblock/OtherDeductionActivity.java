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
 * Created by Parikshit Patil on 12/19/2015.
 */
public class OtherDeductionActivity extends ActionBarActivity
{
    Toolbar toolbar;
   Button done_btn;
    String s1="If you want to know whether a specific expense, not listed above, also qualifies for a deduction then write to us at info@hrblock.in";
    TextView linktvother;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_deduction_layout);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);


        linktvother=(TextView)findViewById(R.id.linkemailtv);
        linktvother.setText(s1);
        linktvother.setLinkTextColor(getResources().getColor(R.color.greencolor));
        Linkify.addLinks(linktvother, Linkify.EMAIL_ADDRESSES);

        done_btn=(Button)findViewById(R.id.done_btn);
        done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
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
