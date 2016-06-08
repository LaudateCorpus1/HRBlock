package com.aexonic.hnrblock;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by Parikshit Patil on 12/15/2015.
 */
public class EfileActivity extends ActionBarActivity
{
    Toolbar toolbar;
    Button self_file_btn;
    Button expert_file_btn;
    ImageView profileclick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.efile_layout);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(null);


      //  profileclick=(ImageView)toolbar.findViewById(R.id.profileclick);
        self_file_btn=(Button)findViewById(R.id.self_file_btn);
        expert_file_btn=(Button)findViewById(R.id.expert_file_btn);

        self_file_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String url = "https://www.hrblock.in/income-tax-services/free-online-income-tax-e-filing.aspx";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        expert_file_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                String url = "https://www.hrblock.in/income-tax-services/assisted-online-income-tax-e-filing.aspx";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);

            }
        });

/*
        profileclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent gotoupdate=new Intent(EfileActivity.this,UpdateProfile.class);
                startActivity(gotoupdate);
            }
        });
*/
    }
}
